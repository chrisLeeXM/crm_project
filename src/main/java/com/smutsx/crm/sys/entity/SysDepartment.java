package com.smutsx.crm.sys.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class SysDepartment {
	/**
	 * 物理主键
	 */
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id;
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
	 * 部门编号
	 */
	private String departmentNo;
	/**
	 * 部门名称
	 */
	private String departmentName;
	public String getDepartmentNo() {
		return departmentNo;
	}
	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	/**
	 * 描述
	 */
	private String remark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
