package com.shenghesun.college.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.shenghesun.college.news.dao.SysSpecialDao;
import com.shenghesun.college.news.entity.SysSpecial;

@Service
public class SysSpecialService {

	@Autowired
	private SysSpecialDao sysSpecialDao;

	public Page<SysSpecial> findBySpecification(Specification<SysSpecial> specification, Pageable pageable) {
		return sysSpecialDao.findAll(specification, pageable);
	}

	public SysSpecial findById(Long id) {
		return sysSpecialDao.findOne(id);
	}

	public SysSpecial save(SysSpecial entity) {
		return sysSpecialDao.save(entity);
	}
}
