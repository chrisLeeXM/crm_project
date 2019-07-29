package com.smutsx.crm.sys.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.smutsx.crm.common.BaseEntity;

/**
  * 用户--实体类
 * @author 	Bill.Lin
 */
public class SysUser extends BaseEntity {
	/**
	 * 物理主键
	 */
	@TableId(value = "id",type = IdType.AUTO)
	private Long id;
	/**
	 * 创建人
	 */
	private String creator;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改人
	 */
	private String modifier;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 用户编号
	 */
	private String userNo;
	/**
	 * 角色编号
	 */
	private String roleName;
	/**
	 * 用户名
	 */
	private String loginName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 中文名称
	 */
	private String nameCn;
	/**
	 * 电话
	 */
	private String tel;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 所属部门
	 */
	private String department;
	/**
	 * 是否激活
	 */
	private String isActivate;
	/**
	 * 是否加锁
	 */
	private String isLock;
	/**
	 * 状态
	 */
	private String userStatus;
	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;

	/**
	 * Get 物理主键
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Set 物理主键
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * Get 创建人
	 */
	public String getCreator() {
		return creator;
	}
	
	/**
	 * Set 创建人
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	/**
	 * Get 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * Set 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * Get 修改人
	 */
	public String getModifier() {
		return modifier;
	}
	
	/**
	 * Set 修改人
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	/**
	 * Get 修改时间
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	
	/**
	 * Set 修改时间
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	/**
	 * Get 用户编号
	 */
	public String getUserNo() {
		return userNo;
	}
	
	/**
	 * Set 用户编号
	 */
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	
	/**
	 * Get 角色编号
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * Set 角色编号
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * Get 用户名
	 */
	public String getLoginName() {
		return loginName;
	}
	
	/**
	 * Set 用户名
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * Get 密码
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Set 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Get 中文名称
	 */
	public String getNameCn() {
		return nameCn;
	}
	
	/**
	 * Set 中文名称
	 */
	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}
	/**
	 * Get 电话
	 */
	public String getTel() {
		return tel;
	}
	
	/**
	 * Set 电话
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * Get 手机
	 */
	public String getMobile() {
		return mobile;
	}
	
	/**
	 * Set 手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * Get 备注
	 */
	public String getRemark() {
		return remark;
	}
	
	/**
	 * Set 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * Get 邮箱
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Set 邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Get 所属部门
	 */
	public String getDepartment() {
		return department;
	}
	
	/**
	 * Set 所属部门
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * Get 是否激活
	 */
	public String getIsActivate() {
		return isActivate;
	}
	
	/**
	 * Set 是否激活
	 */
	public void setIsActivate(String isActivate) {
		this.isActivate = isActivate;
	}
	/**
	 * Get 是否加锁
	 */
	public String getIsLock() {
		return isLock;
	}
	
	/**
	 * Set 是否加锁
	 */
	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}
	/**
	 * Get 状态
	 */
	public String getUserStatus() {
		return userStatus;
	}
	
	/**
	 * Set 状态
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	/**
	 * Get 最后登录时间
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	
	/**
	 * Set 最后登录时间
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
}
