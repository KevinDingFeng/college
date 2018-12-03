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
	 * 列表中的图片
	 */
	@Column(nullable = false, length = 125)
	private String headImg;
	
	/**
	 * 摘要
	 */
	@Column(nullable = false, length = 512)
	private String abstractText;
	
	/**
	 * 内容
	 */
	@Column(nullable = false, length = 2048)
	private String content;
	
	/**
	 * 是否已发布
	 */
	private boolean released = false;
	
	/**
	 * 是否已删除
	 */
	private boolean removed = false;
}
