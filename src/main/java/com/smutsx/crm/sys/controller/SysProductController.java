package com.smutsx.crm.sys.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smutsx.crm.common.BaseController;
import com.smutsx.crm.common.Result;
import com.smutsx.crm.sys.entity.SysProduct;
import com.smutsx.crm.sys.entity.SysUser;
import com.smutsx.crm.sys.mapper.SysProductMapper;
import com.smutsx.crm.sys.service.SysFileService;

@RestController
@RequestMapping("/sysProduct")
	public class SysProductController extends BaseController {
		@Autowired
		private SysProductMapper sysProductMapper;
		@Autowired
	    private SysFileService sysFileService;
		
		@RequestMapping("/saveProduct")
		public Result saveProduct(@RequestBody SysProduct Product,HttpServletRequest request) {
			Result result = new Result();
			if(Product.getId() == null) {
				sysProductMapper.insert(Product);
			} else {
				sysProductMapper.updateById(Product);
			}
			return result;
		}
		
		@RequestMapping("/saveProductPic")
		public Result saveProductPic(HttpServletRequest request) {
			Result result = new Result();
			System.out.println(111);
			SysUser currentUser = (SysUser)request.getSession().getAttribute("user");
			List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
			System.out.println(files);
			String productid = request.getParameter("productId");
			SysProduct Product = new SysProduct();
			Product.setProductId(productid);
			Product = sysProductMapper.selectOne(new QueryWrapper<SysProduct>(Product));
			if(Product == null) {
				result.fail("未找到编号为的用户");
			} else {
				MultipartFile file = null;
			    BufferedOutputStream stream = null;
			    for (int i = 0; i < files.size(); ++i) {
			      file = files.get(i);
			      if (!file.isEmpty()) {
			        try {
			          String uploadFilePath = file.getOriginalFilename();
			          
			          // 截取上传文件的文件名
			          //String uploadFileName = uploadFilePath.substring(uploadFilePath.lastIndexOf('\\') + 1,uploadFilePath.indexOf('.'));
			        
			          // 截取上传文件的后缀
			          if(uploadFilePath != null) {
			        	  String uploadFileSuffix = uploadFilePath.substring( uploadFilePath.indexOf('.') + 1, uploadFilePath.length());
				          
				          // 保存文件
				          String fileNo = sysFileService.saveFile(file.getBytes(), uploadFileSuffix);
				          System.out.println(file.getBytes());
				          System.out.println(fileNo);
				          
				          // 更新用户头像信息
				          Product.setImg(fileNo);
				          sysProductMapper.updateById(Product);
				          result.setData(fileNo);
			          }
			          
			        } catch (Exception e) {
			          e.printStackTrace();
			        } finally {
			          try {
			            if (stream != null) {
			              stream.close();
			            }
			          } catch (IOException e) {
			            e.printStackTrace();
			          }
			        }
			      } else {
			        result.fail("上传文件为空");
			      }
			    }
			}
			
			return result;
		}
			//查询产品
			@RequestMapping(value = "/selectProduct")
			public Result selectMenu(String product,int currentPage,int pageSize) {
				Result result = new Result(); 
				Page<SysProduct> page = new Page<SysProduct>(currentPage,pageSize);
				SysProduct pro = new SysProduct();	
				QueryWrapper<SysProduct> warpper =new QueryWrapper<SysProduct>(null);
				if(product!=null)
				{
					warpper.like("product",product);
				}
				result.setData(sysProductMapper.selectPage(page,warpper));
				return result;
			}
			@RequestMapping("/deleteProduct")
			public Result addUser(String ids) {
				Result result = new Result();
				List<Long> deleteIds = new ArrayList<Long>();
				for (String id : ids.split(",")) {
					deleteIds.add(Long.parseLong(id));
				}
				sysProductMapper.deleteBatchIds(deleteIds);
				return result;
			}
}
