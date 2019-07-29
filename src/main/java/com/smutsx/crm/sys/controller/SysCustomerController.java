package com.smutsx.crm.sys.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smutsx.crm.common.Result;
import com.smutsx.crm.config.JacksonUtil;
import com.smutsx.crm.sys.entity.SysCustomer;
import com.smutsx.crm.sys.entity.SysUser;
import com.smutsx.crm.sys.mapper.SysCustomerMapper;

@RestController
@RequestMapping("customer")
public class SysCustomerController {
@Autowired SysCustomerMapper  sysCustomerMapper;

	/**
	 * 客户列表
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
@RequestMapping("/getAllCustomer")
public Result getAllOrder(int currentPage,int pageSize,String queryParms) {
	Result result = new Result();
	// 获取查询条件参数,由json格式转换为Map对象
	 try {
		Map<String, Object> queryParmsMap = JacksonUtil.getObjectMapper().readValue(queryParms, HashMap.class);
		
		Page<SysCustomer> page = new Page<SysCustomer>(currentPage,pageSize);
		SysCustomer parms = new SysCustomer();
		QueryWrapper<SysCustomer> warpper =new QueryWrapper<SysCustomer>();
		
		if(queryParmsMap.get("customerid") != null && !"".equals(queryParmsMap.get("customerid"))) {
			warpper.like("customerid", queryParmsMap.get("customerid"));
		}
		
		if(queryParmsMap.get("name") != null && !"".equals(queryParmsMap.get("name"))) {
			warpper.like("name", queryParmsMap.get("name"));
		}
		result.setData(sysCustomerMapper.selectPage(page, warpper));
	} catch (Exception e) {
		e.printStackTrace();
	}
	return result;
}
/**
 *  保存用户
 * @param currentPage
 * @param pageSize
 * @return
 */
@RequestMapping("/saveCustomer")
public Result saveUser(@RequestBody SysCustomer customer,HttpServletRequest request) {
	Result result = new Result();
	if(customer.getId() == null) {
		sysCustomerMapper.insert(customer);
	} else {
		sysCustomerMapper.updateById(customer);
	}
	return result;
}

/**
 *  删除用户
 * @param currentPage
 * @param pageSize
 * @return
 */
@RequestMapping("/deleteCustomer")
public Result addUser(String ids) {
	Result result = new Result();
	List<Long> deleteIds = new ArrayList<Long>();
	for (String id : ids.split(",")) {
		deleteIds.add(Long.parseLong(ids));
	}
	sysCustomerMapper.deleteBatchIds(deleteIds);
	return result;
}
}
