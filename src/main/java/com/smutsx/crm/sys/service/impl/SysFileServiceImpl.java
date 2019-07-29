package com.smutsx.crm.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smutsx.crm.sys.entity.SysFile;
import com.smutsx.crm.sys.entity.SysUser;
import com.smutsx.crm.sys.mapper.SysFileMapper;
import com.smutsx.crm.sys.service.SysFileService;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

/**
 * 文件--服务实现类
 */
@Service
public class SysFileServiceImpl implements SysFileService {
    @Autowired
    private SysFileMapper sysFileMapper;

    /**
     * 保存文件
     * @param fileContext   文件内容
     * @param fileType      文件类型
     * @return
     */
    public String saveFile(byte[] fileContext,String fileType){
        // 获取当前时间
        Calendar currentDate = Calendar.getInstance();

        // 生成文件编号
        String fileNo = "";
        fileNo+=Thread.currentThread().getId();
        fileNo+=currentDate.getTimeInMillis();

        // 生成文件保存信息
        SysFile file = new SysFile();
        file.setFileNo(fileNo);
        file.setCreator("1");
        file.setModifier("1");
        if(fileType.startsWith(".")){
            file.setFileType(fileType.toLowerCase());
        }else{
            file.setFileType("."+fileType.toLowerCase());
        }
        
		Date currtenDate = new Date();
		file.setCreateTime(currtenDate);
		file.setModifyTime(currtenDate);
        file.setFileContent(fileContext);
        sysFileMapper.insert(file);
        return fileNo;
    }

    /**
     * 获取文件内容
     * @param fileNo 文件编号
     * @return
     */
    public SysFile getFile(String fileNo){
    	SysFile file = null;
        if(fileNo != null && !"".equals(fileNo)){
        	SysFile query = new SysFile();
            query.setFileNo(fileNo);
            file = this.sysFileMapper.selectOne(new QueryWrapper<SysFile>(query));
        }
        return file;
    }
}