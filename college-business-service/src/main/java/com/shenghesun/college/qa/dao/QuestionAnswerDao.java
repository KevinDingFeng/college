package com.shenghesun.college.qa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.shenghesun.college.qa.entity.QuestionAnswer;

@Repository
public interface QuestionAnswerDao extends JpaRepository<QuestionAnswer, Long>, JpaSpecificationExecutor<QuestionAnswer> {

	QuestionAnswer findByUuid(String uuid);

}
