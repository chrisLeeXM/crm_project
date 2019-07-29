package com.smutsx.crm.sys.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smutsx.crm.common.BaseController;
import com.smutsx.crm.common.Result;
import com.smutsx.crm.config.JacksonUtil;
import com.smutsx.crm.sys.entity.SysMenu;
import com.smutsx.crm.sys.entity.SysRole;
import com.smutsx.crm.sys.entity.SysRoleAndMenu;
import com.smutsx.crm.sys.entity.SysUser;
import com.smutsx.crm.sys.mapper.SysMenuMapper;
import com.smutsx.crm.sys.mapper.SysRoleAndMenuMapper;
import com.smutsx.crm.sys.mapper.SysRoleMapper;

/*
 * 角色管理Controller
 * @author L
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController extends BaseController{
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Autowired
	private  SysRoleAndMenuMapper  sysRoleAndMenuMapper;

	
	/*
	 * 所有角色信息
	 */
	@RequestMapping(value="allRole")
	public Result getRole(int currentPage,int pageSize) {
		Result result = new Result();
		Page<SysRole> page = new Page<SysRole>(currentPage,pageSize);
		SysRole role = new SysRole();
		QueryWrapper<SysRole> warpper =new QueryWrapper<SysRole>(role);
		result.setData(sysRoleMapper.selectPage(page, warpper));
		return result;
	}
	
	/*
	 * 角色查询
	 * 
	 */
	@RequestMapping(value = "selectRole")
	public Result selectRole(int currentPage,int pageSize,String queryParms) {
		Result result = new Result();
		// 获取查询条件参数,由json格式转换为Map对象
		 try {
			Map<String, Object> queryParmsMap = JacksonUtil.getObjectMapper().readValue(queryParms, HashMap.class);
			
			Page<SysRole> page = new Page<SysRole>(currentPage,pageSize);
			SysRole parms = new SysRole();
			QueryWrapper<SysRole> wrapper =new QueryWrapper<SysRole>(parms);
			wrapper.orderByAsc("id");
			if(queryParmsMap.get("roleNo") != null && !"".equals(queryParmsMap.get("roleNo"))) {
				wrapper.like("role_no", queryParmsMap.get("roleNo"));
			}
			if(queryParmsMap.get("roleName") != null && !"".equals(queryParmsMap.get("roleName"))) {
				wrapper.like("role_name", queryParmsMap.get("roleName"));
			}
			result.setData(sysRoleMapper.selectPage(page, wrapper));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/*
	 * 角色添加
	 * 
	 */
	@RequestMapping(value = "addRole")
	public Result addtRole(@RequestBody SysRole role,HttpServletRequest request) {
		Result result = new Result();
		SysUser currentUser = (SysUser)request.getSession().getAttribute("user");
		if(role.getId()==null) {
			role.setCreator(currentUser.getLoginName()+"");
			Date currtenDate = new Date();
			role.setCreateTime(currtenDate);
			sysRoleMapper.insert(role);
		}
		else {
			sysRoleMapper.updateById(role);
		}
		return result;
	}
	
	/*
	 * 角色删除
	 * 
	 */
	
	@RequestMapping(value = "deleteRole")
	public Result deleteRole(String ids) {
		Result result = new Result(); 
		List<Long> roleList = new ArrayList<Long>();
		for (String id : ids.split(",")) {
			roleList.add(Long.parseLong(id));
		}
		sysRoleMapper.deleteBatchIds(roleList);
		return result;
	}
	
	/**
	 * 已分配权限获取
	 */	
	@RequestMapping(value = "allocatedMenu")
	public Result allocatedMenu(String roleNos) {
		Result result = new Result(); 
		int currentPage = 1;
		int pageSize = 10;
		Page<SysMenu> page = new Page<SysMenu>(currentPage,pageSize);
		SysMenu men = new SysMenu();
		QueryWrapper<SysMenu> warpper =new QueryWrapper<SysMenu>(men);
		IPage<SysMenu> Records = sysMenuMapper.selectPage(page, warpper);
		QueryWrapper<SysMenu> menuWarpper =new QueryWrapper<SysMenu>();
		// 菜单编号集合
		Map<String,String> menuCodesMap = new HashMap<String, String>();
		//根据角色编号搜索菜单
		SysRoleAndMenu rm = new SysRoleAndMenu();
		rm.setRoleNo(roleNos);
		List<SysRoleAndMenu> menus = sysRoleAndMenuMapper.selectList(new QueryWrapper<SysRoleAndMenu>(rm));
		for (SysRoleAndMenu menu : menus) {
			if(!menu.getMenuCode().contains("100"))
			menuCodesMap.put(menu.getMenuCode(), menu.getMenuCode());
		}
		
		//根据菜单编号获取菜单名称
		if(menuCodesMap.size() > 0) {
			menuWarpper.in("menu_code", menuCodesMap.values());

			result.setData(sysMenuMapper.selectList(menuWarpper));
		}
		else {
			result.setData(null);
		}
		return result;
	}
	
	/*
	 * 未分配的权限获取
	 * 
	 */
	@RequestMapping(value = "unallocatedMenu")
	public Result unallocatedMenu(String roleNos) {
		Result result = new Result(); 
		SysMenu men = new SysMenu();
		QueryWrapper<SysMenu> warpper =new QueryWrapper<SysMenu>(men);

		//全部的菜单编号
		Map<String,String> allMenuCodesMap = new HashMap<String, String>();
		List<SysMenu> m = sysMenuMapper.selectList(warpper);
		for (SysMenu menu :m) {
			if(!menu.getParentCode().equals("-1")) {
				allMenuCodesMap.put(menu.getMenuCode(), menu.getMenuCode());
			}
		}		
		//根据菜单编号获取菜单名称
		if(allMenuCodesMap.size() > 0) {
			warpper.in("menu_code", allMenuCodesMap.values());
			// 加载菜单
			result.setData(sysMenuMapper.selectList(warpper));
		}
		return result;
	}
	
	
	@RequestMapping(value = "addroleMenu")
	public Result addroleandMenu(@RequestBody String param,HttpServletRequest request) {
		Result result = new Result(); 
		JSONObject jsonobj=JSONObject.parseObject(param);//将字符串转化成json对象 
		String  roleNos = jsonobj.getString("roleNos");
		JSONArray value = jsonobj.getJSONArray("value");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		String creator = user.getNameCn();
		Date data = new Date();
		
		SysRoleAndMenu rm = new SysRoleAndMenu();
		rm.setRoleNo(roleNos);
		QueryWrapper<SysRoleAndMenu> SysRoleAndMenu_warpper =new QueryWrapper<SysRoleAndMenu>(rm);
		sysRoleAndMenuMapper.delete(SysRoleAndMenu_warpper);
		
		rm.setCreateTime(data);
		rm.setModifyTime(data);
		rm.setCreator(creator);
		rm.setModifier(creator);
		
		SysMenu men = new SysMenu();
		QueryWrapper<SysMenu> SysMenu_warpper =new QueryWrapper<SysMenu>(men);
		List<SysMenu> m = sysMenuMapper.selectList(SysMenu_warpper);
		for (SysMenu menu :m) {
			if(menu.getParentCode().equals("-1")) {
				rm.setMenuCode(menu.getMenuCode());
				sysRoleAndMenuMapper.insert(rm);
			}
		}
	
		for(int i=0;i<value.size();i++)
		{
			rm.setMenuCode(value.get(i).toString());
			sysRoleAndMenuMapper.insert(rm);
		}

		return result;
	}
}

