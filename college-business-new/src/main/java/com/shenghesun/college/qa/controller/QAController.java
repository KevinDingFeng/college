package com.shenghesun.college.qa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.shenghesun.college.qa.entity.QuestionAnswer;
import com.shenghesun.college.qa.service.QuestionAnswerService;
import com.shenghesun.college.utils.JsonUtils;

@RestController
@RequestMapping(value = "/qa")
public class QAController {
	
	@Autowired
	private QuestionAnswerService qaService;

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public JSONObject find() {
		Pageable pageable = this.getListPageable(0);
		Page<QuestionAnswer> page = qaService.findAllByPage(pageable);
		if(page != null) {
			List<QuestionAnswer> list = page.getContent();
			if(list != null && list.size() > 0) {
				return JsonUtils.getSuccessJSONObject(list);
			}
		}
		return JsonUtils.getSuccessJSONObject(new ArrayList<>());
	}

	private Pageable getListPageable(Integer pageNum) {
		Sort sort = new Sort(Direction.DESC, "count");
		Pageable pageable = new PageRequest(pageNum, 3, sort);
		return pageable;
	}

	@RequestMapping(value = "/add/{uuid}", method = RequestMethod.POST)
	public JSONObject add(@PathVariable String uuid) {
		qaService.addCount(uuid);
		return JsonUtils.getSuccessJSONObject();
	}

}
