package com.shenghesun.college.qa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.shenghesun.college.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class QuestionAnswer extends BaseEntity {
	
	/**
	 * 分组编号
	 * 	普通的类似 code 的值，比如： 第一类 gid = 10 
	 */
	@Column(nullable = false, length = 255)
	private String gid;
	/**
	 * 编号
	 */
	@Column(nullable = false, length = 255)
	private String uuid;
	
	/**
	 * 统计数
	 */
	private int count = 0;
}
