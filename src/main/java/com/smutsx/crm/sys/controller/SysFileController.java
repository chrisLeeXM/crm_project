package com.smutsx.crm.sys.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smutsx.crm.common.BaseController;
import com.smutsx.crm.common.Result;
import com.smutsx.crm.sys.entity.SysFile;
import com.smutsx.crm.sys.service.SysFileService;

/**
 * 文件管理Controller
 * @author bill
 *
 */
@RestController
@RequestMapping("/sysFile")
public class SysFileController extends BaseController {
	@Autowired
    private SysFileService sysFileService;
	
	/**
     * 加载文件
     * @param fileNo 文件编号
     * @return
     */
    @RequestMapping("/load")
    public Result loadHandler(@RequestParam String fileNo, HttpServletResponse response) {
        Result result = new Result();
        if(fileNo != null && !"".equals(fileNo)){
        	SysFile file = sysFileService.getFile(fileNo);
            if(file != null){
                try {
                    OutputStream out = response.getOutputStream();
                    out.write(file.getFileContent());
                    out.flush();
                    out.close();
                    if(".png".equals(file.getFileType())){
                        response.setContentType("image/png");
                    }else{
                        response.setContentType("image/png");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                result.fail("找不到编码为【"+fileNo+"】的文件");
            }
        } else {
            result.fail("文件编码不能为空");
        }

        return null;
    }
	
    /**
     * 下载文件
     * @param paramsMap 用户信息
     * @return
     */
    @RequestMapping("/download")
    public Result downloadHandler(@RequestParam String fileNo,HttpServletResponse response) {
        Result result = new Result();
        if(fileNo != null && !"".equals(fileNo)){
        	SysFile file = sysFileService.getFile(fileNo);
            if(file != null){
                try {
                    OutputStream out = response.getOutputStream();
                    out.write(file.getFileContent());
                    out.flush();
                    out.close();
                    // 配置文件下载
                    response.setHeader("content-type", "application/octet-stream");
                    response.setContentType("application/octet-stream");
                    // 下载文件能正常显示中文
                    response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getFileType(), "UTF-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                result.fail("找不到编码为【"+fileNo+"】的文件");
            }
        } else {
            result.fail("文件编码不能为空");
        }

        return null;
    }
}
