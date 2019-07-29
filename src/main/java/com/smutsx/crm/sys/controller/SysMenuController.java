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
import com.smutsx.crm.common.BaseController;
import com.smutsx.crm.common.Result;
import com.smutsx.crm.sys.entity.SysMenu;
import com.smutsx.crm.sys.entity.SysProduct;
import com.smutsx.crm.sys.entity.SysRoleAndMenu;
import com.smutsx.crm.sys.entity.SysUser;
import com.smutsx.crm.sys.entity.SysUserAndRole;
import com.smutsx.crm.sys.mapper.SysMenuMapper;
import com.smutsx.crm.sys.mapper.SysRoleAndMenuMapper;
import com.smutsx.crm.sys.mapper.SysUserAndRoleMapper;
import com.smutsx.crm.sys.mapper.SysUserMapper;


/**
 * 菜单管理Controller
 * @author bill
 *
 */
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController extends BaseController {
	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private SysUserAndRoleMapper sysUserAndRoleMapper;
	
	@Autowired
	private SysRoleAndMenuMapper sysRoleAndMenuMapper;
	
	/**
	 * 	根据当前用户菜单信息
	 * @return
	 */
	@RequestMapping("/getMenus")
	public Result getMenus(String loginName) {
		Result result = new Result();
		QueryWrapper<SysMenu> menuWarpper =new QueryWrapper<SysMenu>();
		if("admin".equals(loginName)) {
			// admin权限，加载全部菜单
			result.setData(sysMenuMapper.selectList(menuWarpper));
		} else if(loginName != null && !"".equals(loginName)){
			// 非admin权限，根据登录用户名的角色取得对应的菜单权限
			
			// 菜单编号集合
			Map<String,String> menuCodesMap = new HashMap<String, String>();
			
			// 根据登录名查找用户编号
			SysUser userQuery= new SysUser();
			userQuery.setLoginName(loginName);
			SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>(userQuery));
			if(user != null) {
				// 根据用户编号查询出对应的角色
				SysUserAndRole  roleParams = new SysUserAndRole();
				roleParams.setUserNo(user.getUserNo());
				List<SysUserAndRole> roles = sysUserAndRoleMapper.selectList(new QueryWrapper<SysUserAndRole>(roleParams));
				if(roles != null && !roles.isEmpty()) {
					//根据角色加载对应的菜单编号
					for (SysUserAndRole sysUserAndRole : roles) {
						SysRoleAndMenu  menuParams = new SysRoleAndMenu();
						menuParams.setRoleNo(sysUserAndRole.getRoleNo());
						List<SysRoleAndMenu> menus = sysRoleAndMenuMapper.selectList(new QueryWrapper<SysRoleAndMenu>(menuParams));
						for (SysRoleAndMenu menu : menus) {
							menuCodesMap.put(menu.getMenuCode(), menu.getMenuCode());
						}
					}
				}
			}
			if(menuCodesMap.size() > 0) {
				menuWarpper.in("menu_code", menuCodesMap);
				// 加载菜单
				result.setData(sysMenuMapper.selectList(menuWarpper));
			}
		} else {
			result.fail("登录名为空，无法获取对应的菜单权限！");
		}
		
		return result;
	}
	
	/**
	 * 	菜单列表
	 * @param menu
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getAlls")
	public Result getAlls(SysMenu menu,int currentPage,int pageSize) {
		Result result = new Result();
		Page<SysMenu> page = new Page<SysMenu>(currentPage,pageSize);
		
		QueryWrapper<SysMenu> wrapper =new QueryWrapper<SysMenu>(menu);
		result.setData(sysMenuMapper.selectPage(page, wrapper));
		return result;
	}
	
	/**
	 *	 删除菜单
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/deleteMenu")
	public Result DeleteMenu(String ids) {
		Result result = new Result();
		List<Long> deleteIds = new ArrayList<Long>();
		for (String id : ids.split(",")) {
			deleteIds.add(Long.parseLong(ids));
		}
		sysMenuMapper.deleteBatchIds(deleteIds);
		return result;
	}
	
	/**
	 * 	保存产品
	 * 
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/saveMenu")
	public Result saveMenu(@RequestBody SysMenu menu, HttpServletRequest request) {
		Result result = new Result();
		SysMenu currentMenu = (SysMenu)request.getSession().getAttribute("");
		if(menu.getId() == null) {
			menu.setCreator("chris");
			menu.setModifier("chris");
			Date currtenDate = new Date();
			menu.setCreateTime(currtenDate);
			menu.setModifyTime(currtenDate);
			sysMenuMapper.insert(menu);
		} else {
			menu.setModifier("chris");
			Date currtenDate = new Date();
			menu.setModifyTime(currtenDate);
			sysMenuMapper.updateById(menu);
		}
		return result;
	}
}
