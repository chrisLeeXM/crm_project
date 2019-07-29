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
import com.smutsx.crm.sys.entity.SysDepartment;
import com.smutsx.crm.sys.entity.SysRole;
import com.smutsx.crm.sys.entity.SysUser;
import com.smutsx.crm.sys.mapper.SysDepartmentMapper;
import com.smutsx.crm.sys.mapper.SysRoleMapper;

@RestController
@RequestMapping("/sysDepartment")
public class SysDepartmentController {
	
	@Autowired
	private SysDepartmentMapper sysDepartmentMapper;
	
	/**
	 * 查询部门
	 */
	@RequestMapping("/getAll")
	public Result getAll(int currentPage,int pageSize,String departmentNo,String departmentName) {
		Result result = new Result();
		System.out.println(departmentNo+" "+departmentName);
		Page<SysDepartment> page = new Page<SysDepartment>(currentPage,pageSize);
		if(departmentNo == null && departmentName == null)
		{
			SysDepartment parms = new SysDepartment();
			QueryWrapper<SysDepartment> warpper =new QueryWrapper<SysDepartment>(parms);
			result.setData(sysDepartmentMapper.selectPage(page, warpper));
		}
		else
		{
			SysDepartment parms = new SysDepartment();
			QueryWrapper<SysDepartment> warpper =new QueryWrapper<SysDepartment>(parms);
			warpper.eq("department_no", departmentNo);
			warpper.eq("department_name", departmentName);
			result.setData(sysDepartmentMapper.selectPage(page, warpper));
		}
		return result;
	}
	
	/**
	 * 查询部门
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/findDepartment")
	public Result findDepartment(int currentPage,int pageSize,String queryParms) {
		Result result = new Result();
		// 获取查询条件参数,由json格式转换为Map对象
		 try {
			Map<String, Object> queryParmsMap = JacksonUtil.getObjectMapper().readValue(queryParms, HashMap.class);
			
			Page<SysDepartment> page = new Page<SysDepartment>(currentPage,pageSize);
			SysDepartment parms = new SysDepartment();
			QueryWrapper<SysDepartment> wrapper =new QueryWrapper<SysDepartment>(parms);
			wrapper.orderByAsc("id");
			if(queryParmsMap.get("departmentNo") != null && !"".equals(queryParmsMap.get("departmentNo"))) {
				wrapper.like("department_no", queryParmsMap.get("departmentNo"));
			}
			if(queryParmsMap.get("departmentName") != null && !"".equals(queryParmsMap.get("departmentName"))) {
				wrapper.like("department_name", queryParmsMap.get("departmentName"));
			}
			result.setData(sysDepartmentMapper.selectPage(page, wrapper));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 新增/修改部门
	 */
	@RequestMapping("/addDepartment")
	public Result saveDepartment(@RequestBody SysDepartment department,HttpServletRequest request) {
		Result result = new Result();
		System.out.println(department.getDepartmentNo()+" "+department.getDepartmentName());
		SysUser user = (SysUser)request.getSession().getAttribute("user");
		if(department.getId() == null) {
			department.setCreator(user.getId()+"");
			department.setModifier(user.getId()+"");
			Date currtenDate = new Date();
			department.setCreateTime(currtenDate);
			department.setModifyTime(currtenDate);
			sysDepartmentMapper.insert(department);
		}
		else {
			department.setModifier(user.getId()+"");
			Date currtenDate = new Date();
			department.setModifyTime(currtenDate);
			sysDepartmentMapper.updateById(department);
		}
		return result;
	}
	
	/**
	 * 删除部门
	 */
	@RequestMapping("/deleteDepartment")
	public Result deleteDepartment(String ids) {
		Result result = new Result();
		System.out.println(ids);
		List<Long> deleteIds = new ArrayList<Long>();
		for (String id : ids.split(",")) {
			deleteIds.add(Long.parseLong(id));
		}
		sysDepartmentMapper.deleteBatchIds(deleteIds);
		return result;
	}
}