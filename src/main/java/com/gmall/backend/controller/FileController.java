package com.gmall.backend.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gmall.backend.common.Result;
import com.gmall.backend.entity.Files;
import com.gmall.backend.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

//文件上传

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;


    @Value("${server.ip}")
    private String serverIp;


    @Resource
    private FileMapper fileMapper;

//    文件上传接口
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
//        name
        String originalFilename = file.getOriginalFilename();
//        type
        String type = FileUtil.extName(originalFilename);
//        size
        long size = file.getSize();
//        标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid+StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileUUID);


        File parentFile = uploadFile.getParentFile();
        if (!parentFile.exists()){
            parentFile.mkdirs();
        }

        String url;
//        上传文件到磁盘
        file.transferTo(uploadFile);
//        获取md5
        String md5 = SecureUtil.md5(uploadFile);
        Files dbFiles = getFileByMd5(md5);
        if (dbFiles != null){
            url = dbFiles.getUrl();
            uploadFile.delete();
        }else {
            url = "http://"+serverIp+":9090/file/" + fileUUID;
        }


//        存储
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);


        return url;
    }

//    下载
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID , HttpServletResponse response) throws IOException{
//        根据文件的唯一标识获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
//        设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileUUID,"UTF-8"));
        response.setContentType("application/octet-stream");
//    读取字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

//    通过文件的md5查询
    private Files getFileByMd5(String md5){
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<Files> filesList = fileMapper.selectList(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }

    //分页
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name){
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete",false);
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)){
            queryWrapper.like("name",name);
        }
        return Result.success(fileMapper.selectPage(new Page<>(pageNum, pageSize),queryWrapper));
    }
    //    删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        Files files = fileMapper.selectById(id);
        files.setDelete(true);
        fileMapper.updateById(files);
        return Result.success();
    }
    //    批量删除
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        List<Files> files = fileMapper.selectList(queryWrapper);
        for (Files file : files) {
            file.setDelete(true);
            fileMapper.updateById(file);
        }
        return Result.success();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Files files) {
        return Result.success(fileMapper.updateById(files));
    }

}
