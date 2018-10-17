package com.shenghesun.college.qa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shenghesun.college.qa.dao.QuestionAnswerDao;
import com.shenghesun.college.qa.entity.QuestionAnswer;

@Service
public class QuestionAnswerService {

	@Autowired
	private QuestionAnswerDao qaDao;
	
	/**
	 * 分页查询所有记录
	 * @param pageable
	 * @return
	 */
	public Page<QuestionAnswer> findAllByPage(Pageable pageable){
		return qaDao.findAll(pageable);
	}
	/**
	 * 根据 uuid 增加统计数据
	 * @param uuid
	 * @return
	 */
	public QuestionAnswer addCount(String uuid) {
		QuestionAnswer qa = this.findByUuid(uuid);
		if(qa != null) {
			qa.setCount(qa.getCount() + 1);
			this.save(qa);
		}
		return null;
	}

	private QuestionAnswer save(QuestionAnswer qa) {
		return qaDao.save(qa);
	}

	private QuestionAnswer findByUuid(String uuid) {
		return qaDao.findByUuid(uuid);
	}
	
	
}
