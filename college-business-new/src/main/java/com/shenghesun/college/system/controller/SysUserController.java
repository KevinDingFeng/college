package com.shenghesun.college.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.shenghesun.college.system.entity.SysPool;
import com.shenghesun.college.system.entity.SysUser;
import com.shenghesun.college.system.service.SysPoolService;
import com.shenghesun.college.system.service.SysUserService;
import com.shenghesun.college.utils.JsonUtils;
import com.shenghesun.college.utils.PasswordUtils;
import com.shenghesun.college.utils.RandomUtil;

@RestController
@RequestMapping(value = "/sys_user")
public class SysUserController {
	
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysPoolService sysPoolService;

	@RequestMapping(method = RequestMethod.GET)
	public JSONObject manage(
			@RequestParam(value = "type") String type, 
			@RequestParam(value = "account") String account, 
			@RequestParam(value = "password") String password, 
			@RequestParam(value = "newPassword", required = false) String newPassword) {
		try {
			if("create".equals(type)) {//新增
				//检查 account 是否重复
				if(this.exist(account)) {
					return JsonUtils.getFailJSONObject("account is exist");
				}
				SysUser sysUser = this.create(account, password);
				sysUserService.save(sysUser);
			}else if("update".equals(type)) {//修改
				if(StringUtils.isEmpty(newPassword)) {
					return JsonUtils.getFailJSONObject("new password is null");
				}
				SysUser u = sysUserService.findByAccount(account);
				if(u == null || u.getId() == null) {
					return JsonUtils.getFailJSONObject("invalid account");
				}
				if(this.invalidPassword(u.getPassword(), password, u.getSalt())) {
					return JsonUtils.getFailJSONObject("invalid password");
				}
				String salt = u.getSalt();
				u.setPassword(PasswordUtils.encrypt(newPassword, salt));
				sysUserService.save(u);
			}else {
				return JsonUtils.getFailJSONObject("invalid type");
			}
			return JsonUtils.getSuccessJSONObject();	
		}catch(Exception e) {
			e.printStackTrace();
			return JsonUtils.getFailJSONObject();
		}
	}

	private boolean invalidPassword(String dbPassword, String password, String salt) {
		return !dbPassword.equals(PasswordUtils.encrypt(password, salt));
	}

	private boolean exist(String account) {
		SysUser u = sysUserService.findByAccount(account);
		return u != null && u.getId() != null;
	}

	private SysUser create(String account, String password) {
		SysUser u = new SysUser();
		Long sysPoolId = 1L;
		SysPool sysPool = sysPoolService.findById(sysPoolId);
		u.setSysPool(sysPool);
		u.setSysPoolId(sysPoolId);
		
		u.setAccount(account);
		u.setName(account);
		
		String salt = RandomUtil.randomString(16);
		String pwd = PasswordUtils.encrypt(password, salt);
		u.setSalt(salt);
		u.setPassword(pwd);
		
		return u;
	}
}
