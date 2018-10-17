package com.shenghesun.college.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.shenghesun.college.news.dao.SysNewsDao;
import com.shenghesun.college.news.entity.SysNews;

@Service
public class SysNewsService {

	@Autowired
	private SysNewsDao sysNewsDao;

	public Page<SysNews> findBySpecification(Specification<SysNews> specification, Pageable pageable) {
		return sysNewsDao.findAll(specification, pageable);
	}

	public SysNews findById(Long id) {
		return sysNewsDao.findOne(id);
	}

	public SysNews save(SysNews sysNews) {
		return sysNewsDao.save(sysNews);
	}
}
