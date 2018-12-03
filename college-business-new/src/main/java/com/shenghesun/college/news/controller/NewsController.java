package com.shenghesun.college.news.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.shenghesun.college.news.entity.SysNews;
import com.shenghesun.college.news.service.SysNewsService;
import com.shenghesun.college.sso.model.LoginInfo;
import com.shenghesun.college.system.service.SysUserService;
import com.shenghesun.college.utils.FileUtils;
import com.shenghesun.college.utils.JsonUtils;

@Controller
@RequestMapping(value = "/sys_new")
public class NewsController {

	/**
	 * 设置允许自动绑定的属性名称
	 * 
	 * @param binder
	 * @param req
	 */
	@InitBinder("entity")
	private void initBinder(ServletRequestDataBinder binder, HttpServletRequest req) {
		List<String> fields = new ArrayList<String>(Arrays.asList("title", "content", "abstractText"));
		switch (req.getMethod().toLowerCase()) {
		case "post": // 新增 和 修改
			binder.setAllowedFields(fields.toArray(new String[fields.size()]));
			break;
		default:
			break;
		}
	}

	/**
	 * 预处理，一般用于新增和修改表单提交后的预处理
	 * 
	 * @param id
	 * @param req
	 * @return
	 */
	@ModelAttribute("entity")
	public SysNews prepare(@RequestParam(value = "id", required = false) Long id, HttpServletRequest req) {
		String method = req.getMethod().toLowerCase();
		if (id != null && id > 0 && "post".equals(method)) {// 修改表单提交后数据绑定之前执行
			return newsService.findById(id);
		} else if ("post".equals(method)) {// 新增表单提交后数据绑定之前执行
			return new SysNews();
		} else {
			return null;
		}
	}

	@Autowired
	private SysNewsService newsService;
	@Autowired
	private SysUserService userService;

	/**
	 * 新闻首页，即新闻列表页，也作为当前系统的首页使用
	 * 
	 * @param pageNum
	 * @param model
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public String index(@RequestParam(value = "pageNum", required = false) Integer pageNum, Model model) {
		// 获取登录用户，判断其是否拥有查看新闻的权限
		LoginInfo info = (LoginInfo) SecurityUtils.getSubject().getPrincipal();
		if (userService.hasPermission(info.getId())) {
			// 构建分页信息
			pageNum = pageNum == null ? 0 : pageNum;
			Pageable pageable = this.getListPageable(pageNum);
			Page<SysNews> page = newsService.findBySpecification(this.getSpecification(), pageable);
			model.addAttribute("page", page);
			model.addAttribute("pageNum", pageNum);
			return "news/list";
		} else {
			return "redirect:/logout";
		}
	}

	private Pageable getListPageable(Integer pageNum) {
		Sort sort = new Sort(Direction.DESC, "creation");
		Pageable pageable = new PageRequest(pageNum, 20, sort);
		return pageable;
	}

	private Specification<SysNews> getSpecification() {

		return new Specification<SysNews>() {
			@Override
			public Predicate toPredicate(Root<SysNews> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				list.add(cb.equal(root.get("removed"), false));

				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		};
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String form(@RequestParam(value = "id", required = false) Long id, Model model) {
		// 获取登录用户，判断其是否拥有查看新闻的权限
		LoginInfo info = (LoginInfo) SecurityUtils.getSubject().getPrincipal();
		if (userService.hasPermission(info.getId())) {
			// 判断该操作是新增还是修改
			SysNews news = null;
			if (id != null && id > 0L) {
				news = newsService.findById(id);
			}
			if (news == null) {
				news = new SysNews();
			}
			model.addAttribute("entity", news);
			return "news/form";
		} else {
			return "redirect:/logout";
		}
	}

	/**
	 * 富文本图片保存
	 * 
	 * @author zhanping.yang
	 * @version 创建时间：2017年12月5日 下午2:57:18
	 * @param uploadFile
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String ckeditorUpload(@RequestParam(value = "uploadFile", required = true) MultipartFile uploadFile) {
		// 获取登录用户，判断其是否拥有查看新闻的权限
		LoginInfo info = (LoginInfo) SecurityUtils.getSubject().getPrincipal();
		if (userService.hasPermission(info.getId())) {
			if (uploadFile.getSize() != 0L) {
				try {
					return showFilePath + this.uploadFile(uploadFile.getOriginalFilename(), uploadFile.getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Value("${file.path.upload}")
	private String uploadFilePath;
	@Value("${file.path.show}")
	private String showFilePath;

	/**
	 * 保存上传文件
	 * 
	 * @param filename
	 *            the original filename in the client's filesystem.
	 * @param is
	 *            上传文件流
	 * @return web中的文件访问路径
	 */
	private String uploadFile(String filename, InputStream is) {
		int pos = filename.lastIndexOf('.');
		if (pos == -1) {
			throw new RuntimeException("文件名格式错误，不能读取扩展名");
		}
		String ext = filename.substring(pos + 1);
		String subPath = FileUtils.generateSubPathStr();
		String path = uploadFilePath + subPath;
		String name = FileUtils.generateFilename() + "." + ext;
		File folder = new File(path);
		try {
			if (!folder.exists()) {
				org.apache.commons.io.FileUtils.forceMkdir(folder);
			}
			org.apache.commons.io.FileUtils.copyInputStreamToFile(is, new File(folder, name));
			return subPath + name;
		} catch (IOException e) {
			throw new RuntimeException("保存上传文件时出现错误", e);
		}
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute("entity") SysNews sysNews, BindingResult result, 
			@RequestParam(value = "headImg", required = false) MultipartFile headImg, Model model) {
		// 获取登录用户，判断其是否拥有查看新闻的权限
		LoginInfo info = (LoginInfo) SecurityUtils.getSubject().getPrincipal();
		if (userService.hasPermission(info.getId())) {
			if(result.hasErrors()) {
				return this.returnErrorForm(sysNews, "请检查信息内容", model);
			}
			//创建时 headImg 不可以为空
			if(headImg == null || StringUtils.isEmpty(headImg.getOriginalFilename())) {
				return this.returnErrorForm(sysNews, "头图为必填项", model);
			}
			//判断 title \ abstractText \ content 为非空
			if(StringUtils.isEmpty(sysNews.getTitle())) {
				return this.returnErrorForm(sysNews, "标题为必填项", model);
			}
			if(StringUtils.isEmpty(sysNews.getAbstractText())) {
				return this.returnErrorForm(sysNews, "摘要为必填项", model);
			}
			if(StringUtils.isEmpty(sysNews.getContent())) {
				return this.returnErrorForm(sysNews, "内容为必填项", model);
			}
			try {
				sysNews.setHeadImg(this.uploadFile(headImg.getOriginalFilename(), headImg.getInputStream()));
			} catch (Exception e) {
				e.printStackTrace();
				return this.returnErrorForm(sysNews, "上传头图有异常", model);
			}
			newsService.save(sysNews);
			return "redirect:/sys_new";// 去往首页
		} else {
			return "redirect:/logout";
		}
	}
	private String returnErrorForm(SysNews sysNews, String message, Model model) {
		model.addAttribute("errorMessage", message);
		model.addAttribute("entity", sysNews);
		return "news/form";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute("entity") SysNews sysNews, BindingResult result, 
			@RequestParam(value = "headImg", required = false) MultipartFile headImg, Model model) {
		// 获取登录用户，判断其是否拥有查看新闻的权限
		LoginInfo info = (LoginInfo) SecurityUtils.getSubject().getPrincipal();
		if (userService.hasPermission(info.getId())) {
			if(result.hasErrors()) {
				return this.returnErrorForm(sysNews, "请检查信息内容", model);
			}
			//判断 title \ abstractText \ content 为非空
			if(StringUtils.isEmpty(sysNews.getTitle())) {
				return this.returnErrorForm(sysNews, "标题为必填项", model);
			}
			if(StringUtils.isEmpty(sysNews.getAbstractText())) {
				return this.returnErrorForm(sysNews, "摘要为必填项", model);
			}
			if(StringUtils.isEmpty(sysNews.getContent())) {
				return this.returnErrorForm(sysNews, "内容为必填项", model);
			}
			//修改时 headImg 可以为空
			if(headImg != null && !StringUtils.isEmpty(headImg.getOriginalFilename())) {
				try {
					sysNews.setHeadImg(this.uploadFile(headImg.getOriginalFilename(), headImg.getInputStream()));
				} catch (Exception e) {
					e.printStackTrace();
					return this.returnErrorForm(sysNews, "上传头图有异常", model);
				}
			}
			newsService.save(sysNews);
			return "redirect:/sys_new";// 去往首页
		} else {
			return "redirect:/logout";
		}
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject update(@RequestParam(value = "id", required = false) Long id) {
		// 获取登录用户，判断其是否拥有查看新闻的权限
		LoginInfo info = (LoginInfo) SecurityUtils.getSubject().getPrincipal();
		if (userService.hasPermission(info.getId())) {
			SysNews news = newsService.findById(id);
			if (news != null) {
				news.setRemoved(true);
				newsService.save(news);
			}
			return JsonUtils.getSuccessJSONObject();
		} else {
			return JsonUtils.getFailJSONObject("没有操作权限");
		}
	}
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(@RequestParam(value = "id", required = false) Long id, Model model) {
		// 获取登录用户，判断其是否拥有查看新闻的权限
		LoginInfo info = (LoginInfo) SecurityUtils.getSubject().getPrincipal();
		if (userService.hasPermission(info.getId())) {
			SysNews news = newsService.findById(id);
			if(news == null) {
				news = new SysNews();
			}
			model.addAttribute("entity", news);
			return "news/view";
		} else {
			return "redirect:/logout";
		}
	}
	

}
