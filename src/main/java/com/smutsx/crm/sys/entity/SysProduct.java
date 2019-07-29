package com.smutsx.crm.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.smutsx.crm.common.BaseEntity;

/**
 * 产品管理
 * 
 * @author chris
 *
 */
public class SysProduct extends BaseEntity{
	/**
	 * 物理主键
	 */
	@TableId(value = "id",type = IdType.AUTO)
	private Long id;
	/**
	 * 产品编码
	 */
	private String productId;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 产品类型
	 */
	private String type;
	/**
	 * 产品规格
	 */
	private String specifications;
	/**
	 * 产品图片
	 */
	private String img;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSpecifications() {
		return specifications;
	}
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

	
}
