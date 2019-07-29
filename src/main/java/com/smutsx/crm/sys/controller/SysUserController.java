package com.smutsx.crm.sys.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smutsx.crm.common.BaseController;
import com.smutsx.crm.common.Result;
import com.smutsx.crm.config.JacksonUtil;
import com.smutsx.crm.sys.entity.SysUser;
import com.smutsx.crm.sys.mapper.SysDepartmentMapper;
import com.smutsx.crm.sys.mapper.SysRoleMapper;
import com.smutsx.crm.sys.mapper.SysUserMapper;


/**
 * 用户管理Controller
 * @author bill
 *
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController extends BaseController {
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysDepartmentMapper sysDepartmentMapper;
	@Autowired
	private SysRoleMapper sysRoleMapper;
	/**
	 * 用户登录
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping("/login")
	public Result login( String username,String password,HttpServletRequest request ) {
		Result result = new Result();
		SysUser parms = new SysUser();
		parms.setLoginName(username);
		parms.setPassword(password);
		SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>(parms));
		if(user == null) {
			result.fail("用户名或密码错误!");
		} else {
			result.setData(user);
			request.getSession().setAttribute("user",user);
		}
		return result;
	}
	
	/**
	 * 退出登录
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping("/logout")
	public Result logout( HttpServletRequest request ) {
		Result result = new Result();
		request.getSession().removeAttribute("user");
		return result;
	}
	
	/**
	 * 用户列表
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getAll")
	public Result getAll(int currentPage,int pageSize,String queryParms) {
		Result result = new Result();
		// 获取查询条件参数,由json格式转换为Map对象
		 try {
			Map<String, Object> queryParmsMap = JacksonUtil.getObjectMapper().readValue(queryParms, HashMap.class);
			
			Page<SysUser> page = new Page<SysUser>(currentPage,pageSize);
			SysUser parms = new SysUser();
			QueryWrapper<SysUser> wrapper =new QueryWrapper<SysUser>(parms);
			wrapper.orderByAsc("id");
			if(queryParmsMap.get("userNo") != null && !"".equals(queryParmsMap.get("userNo"))) {
				wrapper.like("user_no", queryParmsMap.get("userNo"));
			}
			if(queryParmsMap.get("userStatus") != null && !"".equals(queryParmsMap.get("userStatus"))) {
				wrapper.eq("user_status", queryParmsMap.get("userStatus"));
			}
			if(queryParmsMap.get("loginName") != null && !"".equals(queryParmsMap.get("loginName"))) {
				wrapper.like("login_name", queryParmsMap.get("loginName"));
			}
			result.setData(sysUserMapper.selectPage(page, wrapper));
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
	@RequestMapping("/saveUser")
	public Result saveUser(@RequestBody SysUser user,HttpServletRequest request) {
		Result result = new Result();
		SysUser currentUser = (SysUser)request.getSession().getAttribute("user");
		System.out.println(currentUser);
		System.out.println(user.getId());
		if(user.getId() == null) {
			user.setCreator(currentUser.getId()+"");
			user.setModifier(currentUser.getId()+"");
			Date currtenDate = new Date();
			user.setCreateTime(currtenDate);
			user.setModifyTime(currtenDate);
			sysUserMapper.insert(user);
		} else {
			user.setModifier(currentUser.getId()+"");
			Date currtenDate = new Date();
			user.setModifyTime(currtenDate);
			sysUserMapper.updateById(user);
		}
		return result;
	}
	
	/**
	 *  保存用户
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/updateUser")
	public Result updateUser(@RequestBody SysUser user,HttpServletRequest request) {
		Result result = new Result();
		SysUser currentUser = (SysUser)request.getSession().getAttribute("user");
		System.out.println(currentUser);
		System.out.println(user.getId());
		user.setModifier(currentUser.getId()+"");
		Date currtenDate = new Date();
		user.setModifyTime(currtenDate);
		sysUserMapper.updateById(user);
		return result;
	}
	
	/**
	 *  删除用户
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/deleteUser")
	public Result addUser(String ids) {
		Result result = new Result();
		List<Long> deleteIds = new ArrayList<Long>();
		for (String id : ids.split(",")) {
			deleteIds.add(Long.parseLong(id));
		}
		sysUserMapper.deleteBatchIds(deleteIds);
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
		//System.out.println(status);
		//System.out.println(ids);
		SysUser user=new SysUser();
		
		for(String id : ids.split(",")) {
			user.setId(Long.parseLong(id));
			user.setUserStatus(status);
			sysUserMapper.updateById(user);
		}
		return result;
	}
	
	/*
	 * 获取某个用户的信息
	 */
	@RequestMapping("/getUser")
	public Result getUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("user");
		SysUser user = (SysUser)obj;
		Result result = new Result();
		result.setData(user);
		return result;
	}
	@RequestMapping("/alldepart")
	public Result getalldepart() {
		Result result = new Result();
		result.setData(sysDepartmentMapper.selectList(null));
		return result;
	}
	@RequestMapping("/allrole")
	public Result getallrole() {
		Result result = new Result();
		result.setData(sysRoleMapper.selectList(null));
		return result;
	}
	
	/**
	 *  重置密码
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/resetPass")
	public Result resetPass (String ids) {
		Result result = new Result();
		SysUser user=new SysUser();
		
		System.out.println(ids);
		
		for(String id:ids.split(",")) {
			user.setId(Long.parseLong(id));
			user.setPassword("123456");
			sysUserMapper.updateById(user);
		}
		return result;
	}
}
