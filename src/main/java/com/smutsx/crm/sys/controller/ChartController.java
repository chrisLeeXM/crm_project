package com.smutsx.crm.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.smutsx.crm.common.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smutsx.crm.common.BaseController;
import com.smutsx.crm.sys.mapper.ChartMapper;
import com.smutsx.crm.sys.entity.sale;

@RestController
@RequestMapping("/chart")
public class ChartController extends BaseController{
	@Autowired
	private ChartMapper chartmapper;
	@RequestMapping("/sale")
	public Result linechart() {
		Result result = new Result();
		sale chart=new sale();
		QueryWrapper<sale> wrapper=new QueryWrapper<sale>(chart);
		result.setData(chartmapper.selectList(wrapper));

		return result;
	}
}
