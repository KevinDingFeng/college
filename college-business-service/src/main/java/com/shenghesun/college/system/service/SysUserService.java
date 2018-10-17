package com.shenghesun.college.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenghesun.college.system.dao.SysUserDao;
import com.shenghesun.college.system.entity.SysUser;

@Service
public class SysUserService {

	@Autowired
	private SysUserDao sysUserDao;
	
	public SysUser findById(Long id) {
		return sysUserDao.findOne(id);
	}

	public SysUser findByAccount(String account) {
		return sysUserDao.findByAccount(account);
	}
	
	/**
	 * 使用 SysUser 实体中的 sysId 属性来作为简单判断是否拥有权限的一个标准
	 * @param id
	 * @return
	 */
	public boolean hasPermission(Long id) {
		if(id == null || id < 1L) {
			return false;
		}
		SysUser user = this.findById(id);
		return user == null ? false : "System".equals(user.getSysId());
	}
	
}
