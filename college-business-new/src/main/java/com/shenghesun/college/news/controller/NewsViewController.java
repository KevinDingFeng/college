package com.shenghesun.college.news.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shenghesun.college.news.entity.SysNews;
import com.shenghesun.college.news.service.SysNewsService;

@Controller
@RequestMapping(value = "/news_view")
public class NewsViewController {

	@Autowired
	private SysNewsService newsService;

	/**
	 * 新闻首页，即新闻列表页，也作为当前系统的首页使用
	 * 
	 * @param pageNum
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String index(@RequestParam(value = "pageNum", required = false) Integer pageNum, Model model) {
		// 构建分页信息
		pageNum = pageNum == null ? 0 : pageNum;
		Pageable pageable = this.getListPageable(pageNum);
		Page<SysNews> page = newsService.findBySpecification(this.getSpecification(), pageable);
		model.addAttribute("page", page);
		model.addAttribute("pageNum", pageNum);
		return "news_show/list";
	}

	private Pageable getListPageable(Integer pageNum) {
		Sort sort = new Sort(Direction.DESC, "creation");
		Pageable pageable = new PageRequest(pageNum, 50, sort);
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

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String form(@PathVariable("id") Long id, Model model) {
		// 判断该操作是新增还是修改
		SysNews news = newsService.findById(id);
		model.addAttribute("entity", news);
		return "news_show/detail";
	}
}
