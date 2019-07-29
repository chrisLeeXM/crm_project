package com.smutsx.crm.sys.service;

import com.smutsx.crm.sys.entity.SysFile;
import com.smutsx.crm.sys.entity.SysUser;

/**
 * 文件--服务
 * @author  Bill.Lin
 * @date    2019-06-16 22:48:10
 */
public abstract interface SysFileService {

    /**
     * 保存文件
     * @param fileContext   文件内容
     * @param fileType      文件类型
     * @param user      	当前用户
     * @return
     */
    public String saveFile(byte[] fileContext,String fileType);


    /**
     * 获取文件内容
     * @param fileNo 文件编号
     * @return
     */
    public SysFile getFile(String fileNo);
}
