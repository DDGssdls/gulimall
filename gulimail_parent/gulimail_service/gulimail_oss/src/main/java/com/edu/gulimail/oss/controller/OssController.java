package com.edu.gulimail.oss.controller;


import com.edu.common.utils.R;
import com.edu.gulimail.oss.service.impl.OssServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/oss/fileoss")
@CrossOrigin
public class OssController {
    // 文件上传使用的是springmvc中的默认的对象 MultPartFile 两点需要注意的就是
    // 设置文件的唯一的名称 2 就是本地上传使用的 transforTo

    private final OssServiceImpl ossService;

    @Autowired
    public OssController(OssServiceImpl ossService) {
        this.ossService = ossService;
    }

    @PostMapping("/upload")
    public R uploadFile(MultipartFile file){
        // 进行调用 service中的方法  返回的是文件的url地址
        String url =  ossService.uploadFileAvatar(file);


        return R.ok().put("data", url);
    }
}
