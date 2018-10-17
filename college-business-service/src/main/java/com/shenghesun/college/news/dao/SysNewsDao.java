package com.shenghesun.college.news.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.shenghesun.college.news.entity.SysNews;


@Repository
public interface SysNewsDao extends JpaRepository<SysNews, Long>, JpaSpecificationExecutor<SysNews> {

}
