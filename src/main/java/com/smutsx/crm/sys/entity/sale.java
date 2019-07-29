package com.smutsx.crm.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.smutsx.crm.common.BaseEntity;

public class sale extends BaseEntity{
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id;
	private Integer num;
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	private Integer sale;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSale() {
		return sale;
	}
	public void setSale(Integer sale) {
		this.sale = sale;
	}
	
}
