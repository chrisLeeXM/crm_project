package com.smutsx.crm.sys.entity;


import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.smutsx.crm.common.BaseEntity;

public class SysOrder  extends BaseEntity{
	
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
	 * 订单编号
	 */
	private String orderNo;
	/**
	 * 订单时间
	 */
	private String orderTime;
	/**
	 * 客户
	 */
	private String customer;
	/**
	 * 产品
	 */
	private String product;
	/**
	 * 数量
	 */
	private String num;
	/**
	 * 地址
	 */
	private String address;
	
	private String orderStatus;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime =orderTime;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}

}
