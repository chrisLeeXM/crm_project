package com.smutsx.crm.sys.controller;


import java.text.SimpleDateFormat;
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
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.smutsx.crm.common.BaseController;
import com.smutsx.crm.common.Result;
import com.smutsx.crm.config.JacksonUtil;
import com.smutsx.crm.sys.entity.SysMenu;
import com.smutsx.crm.sys.entity.SysOrder;
import com.smutsx.crm.sys.entity.SysUser;
import com.smutsx.crm.sys.mapper.SysCustomerMapper;
import com.smutsx.crm.sys.mapper.SysMenuMapper;
import com.smutsx.crm.sys.mapper.SysOrderMapper;
import com.smutsx.crm.sys.mapper.SysProductMapper;
import com.smutsx.crm.sys.mapper.SysUserMapper;
/**
 * 订单管理Controller
 * @author bill
 *
 */

@RestController
@RequestMapping("/sysOrder")
public class SysOrderController extends BaseController{

	@Autowired
	private SysOrderMapper sysOrderMapper;
	
	@Autowired
	private SysUserMapper sysUserMapper;
	

	@Autowired
	private SysCustomerMapper sysCustomerMapper;
	
	@Autowired
	private SysProductMapper sysProductMapper;
	/**
	 * 查询订单
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getAll")
	
	public Result<IPage<SysOrder>> getAll(int currentPage,int pageSize,String queryParms) {
		Result<IPage<SysOrder>> result = new Result<IPage<SysOrder>>();
		// 获取查询条件参数,由json格式转换为Map对象
		 try {
			Map<String, Object> queryParmsMap = JacksonUtil.getObjectMapper().readValue(queryParms, HashMap.class);

			Page<SysOrder> page = new Page<SysOrder>(currentPage,pageSize);
			SysOrder parms = new SysOrder();
			QueryWrapper<SysOrder> warpper =new QueryWrapper<SysOrder>(parms);
			warpper.orderByDesc("modify_time");
			if(queryParmsMap.get("orderNo") != null && !"".equals(queryParmsMap.get("orderNo"))) {
				warpper.like("order_no", queryParmsMap.get("orderNo"));
			}
			if(queryParmsMap.get("customer") != null && !"".equals(queryParmsMap.get("customer"))) {
				warpper.like("customer", queryParmsMap.get("customer"));
			}
			if(queryParmsMap.get("product") != null && !"".equals(queryParmsMap.get("product"))) {
				warpper.like("product", queryParmsMap.get("product"));
			}
			if(queryParmsMap.get("orderStatus") != null && !"".equals(queryParmsMap.get("orderStatus"))) {
				warpper.eq("order_status", queryParmsMap.get("orderStatus"));
			}
			result.setData(sysOrderMapper.selectPage(page, warpper));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 *  状态变更
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/changeStatus")
	public Result changeStatus (String ids,String status) {
		Result result = new Result();
		System.out.println(status);
		System.out.println(ids);
		SysOrder order=new SysOrder();
		
		for(String id : ids.split(",")) {
			order.setId(Long.parseLong(id));
			order.setOrderStatus(status);
			sysOrderMapper.updateById(order);
		}
		return result;
	}
	
	/**
	 *  删除订单
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/deleteOrder")
	public Result deleteOrder(String ids) {
		Result result = new Result();
		List<Long> deleteIds = new ArrayList<Long>();
		for (String id : ids.split(",")) {
			deleteIds.add(Long.parseLong(id));
		}
		sysOrderMapper.deleteBatchIds(deleteIds);
		return result;
	}
	/**
	 *  保存订单
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/saveOrder")
	public Result saveOrder(@RequestBody SysOrder order,HttpServletRequest request) {
		Result result = new Result();
		SysUser currentUser = (SysUser)request.getSession().getAttribute("user");
		if(order.getId() == null) {
			Date currentDate = new Date();
			String dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(currentDate);
			String orderNo=new SimpleDateFormat("yyyyMMddHHmmss").format(currentDate);
		    order.setCreator(currentUser.getLoginName());
		    order.setModifier(currentUser.getLoginName());
		    order.setOrderNo(orderNo);
			order.setCreateTime(currentDate);
			order.setModifyTime(currentDate);
			order.setOrderTime(dateStr);
			sysOrderMapper.insert(order);
		} else {
			order.setModifier(currentUser.getLoginName()+"");
			Date currtenDate = new Date();
			order.setModifyTime(currtenDate);
			sysOrderMapper.updateById(order);
		}
		return result;
	}
	@RequestMapping("getAllCustomer")
	public Result getAllCustomer() {
		Result result = new Result();
		result.setData(sysCustomerMapper.selectList(null));
		return result;
	}

	@RequestMapping("getAllProduct")
	public Result getAllproduct() {
		Result result = new Result();
		result.setData(sysProductMapper.selectList(null));
		return result;
	}

}
