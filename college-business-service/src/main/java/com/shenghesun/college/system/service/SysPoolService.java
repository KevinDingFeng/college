package com.shenghesun.college.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenghesun.college.system.dao.SysPoolDao;
import com.shenghesun.college.system.entity.SysPool;

@Service
public class SysPoolService {

	@Autowired
	private SysPoolDao sysPoolDao;
	
	public SysPool findById(Long id) {
		return sysPoolDao.findOne(id);
	}
}
