package cn.wyslkl.file.controller.admin;


import cn.wyslkl.server.domain.Test;
import cn.wyslkl.server.dto.ResponseDto;
import cn.wyslkl.server.dto.FileDto;
import cn.wyslkl.server.enums.FileUseEnum;
import cn.wyslkl.server.service.FileService;
import cn.wyslkl.server.service.TestService;
import cn.wyslkl.server.util.Base64ToMultipartFile;
import cn.wyslkl.server.util.UuidUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Value("${file.domain}")
    private String FILE_DOMAIN;

    @Value("${file.path}")
    private String FILE_PATH;

    @Resource
    private FileService fileService;

    @RequestMapping("/upload")
    public ResponseDto upload(@RequestParam MultipartFile file) throws IOException {
        LOG.info("上传文件开始:{}",file);
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));

        //保存文件到本地
        String key =UuidUtil.getShortUuid();
        String fileName=file.getOriginalFilename();
        String suffix=fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
        String path="resources/" +key + "." +suffix;
        String fullPath=FILE_PATH + path;
        File dest=new File(fullPath);
        file.transferTo(dest);
        LOG.info(dest.getAbsolutePath());

        LOG.info("保存文件记录开始");
        FileDto fileDto=new FileDto();
        fileDto.setPath(path);
        fileDto.setName(fileName);
        fileDto.setSize(Math.toIntExact(file.getSize()));
        fileDto.setSuffix(suffix);
        fileDto.setUsed("");
        fileService.save(fileDto);
        ResponseDto responseDto=new ResponseDto();
        responseDto.setContent(FILE_DOMAIN + path);
        return responseDto;
    }
       /**
        LOG.info("上传文件开始");
        String use = fileDto.getUsed();
        String key = fileDto.getKey();
        String suffix = fileDto.getSuffix();
        String shardBase64 = fileDto.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardBase64);

        // 保存文件到本地
        FileUseEnum useEnum = FileUseEnum.getByCode(use);

        //如果文件夹不存在则创建
        String dir = useEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dir);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }

//        String path = dir + File.separator + key + "." + suffix + "." + fileDto.getShardIndex();
        String path = new StringBuffer(dir)
                .append(File.separator)
                .append(key)
                .append(".")
                .append(suffix)
                .toString(); // course\6sfSqfOwzmik4A4icMYuUe.mp4
        String localPath = new StringBuffer(path)
                .append(".")
                .append(fileDto.getShardIndex())
                .toString(); // course\6sfSqfOwzmik4A4icMYuUe.mp4.1
        String fullPath = FILE_PATH + localPath;
        File dest = new File(fullPath);
        shard.transferTo(dest);
        LOG.info(dest.getAbsolutePath());

        LOG.info("保存文件记录开始");
        fileDto.setPath(path);
        fileService.save(fileDto);

        ResponseDto responseDto = new ResponseDto();
        fileDto.setPath(FILE_DOMAIN + path);
        responseDto.setContent(fileDto);

      //if (fileDto.getShardIndex().equals(fileDto.getShardTotal())) {
      //    this.merge(fileDto);
      //}
        return responseDto;
    }
        **/

}
