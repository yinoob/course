package cn.wyslkl.file.controller.admin;


import cn.wyslkl.server.domain.Test;
import cn.wyslkl.server.dto.ResponseDto;
import cn.wyslkl.server.service.TestService;
import cn.wyslkl.server.util.UuidUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@ComponentScan("cn.wyslkl")
@MapperScan("cn.wyslkl.server.mapper")
@RequestMapping(value={"/admin/teacher"})//,consumes="multipart/form-data"
public class UploadController {

    private static final Logger LOG= LoggerFactory.getLogger(UploadController.class);

    public static final String BUSINESS_NAME="文件上传";

    @RequestMapping("/upload")
    public ResponseDto upload(@RequestParam MultipartFile file) throws IOException {
        LOG.info("上传文件开始:{}",file);
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));
        //保存文件到本地
        String fileName=file.getOriginalFilename();
        String key= UuidUtil.getShortUuid();
        String fullPath="F:/download/course-online-master/resources/"+key+"-"+fileName;
        File dest=new File(fullPath);
        file.transferTo(dest);
        LOG.info(dest.getAbsolutePath());
        ResponseDto responseDto=new ResponseDto();
        return responseDto;
    }
}
