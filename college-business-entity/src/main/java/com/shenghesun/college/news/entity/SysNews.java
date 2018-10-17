package com.shenghesun.college.news.entity;

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
public class SysNews extends BaseEntity {
	/**
	 * 标题
	 */
	@Column(nullable = false, length = 255)
	private String title;
	
	/**
	 * 内容
	 */
	@Column(nullable = false, length = 2048)
	private String content;
	
	
	/**
	 * 是否已删除
	 */
	private boolean removed = false;
}
