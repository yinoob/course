package cn.wyslkl.file.controller.admin;


import cn.wyslkl.file.util.Base64ToMultipartFile;
import cn.wyslkl.server.domain.Test;
import cn.wyslkl.server.dto.ResponseDto;
import cn.wyslkl.server.dto.FileDto;
import cn.wyslkl.server.enums.FileUseEnum;
import cn.wyslkl.server.service.FileService;
import cn.wyslkl.server.service.TestService;

import cn.wyslkl.server.util.UuidUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@ComponentScan("cn.wyslkl")
@MapperScan("cn.wyslkl.server.mapper")
@RequestMapping(value={"/admin/file"})//,consumes="multipart/form-data"
public class UploadController {

    private static final Logger LOG= LoggerFactory.getLogger(UploadController.class);

    public static final String BUSINESS_NAME="文件上传";

    @Value("${file.domain}")
    private String FILE_DOMAIN;

    @Value("${file.path}")
    private String FILE_PATH;

    @Resource
    private FileService fileService;

    @PostMapping(value={"/upload"})
    public  ResponseDto upload(//@RequestBody String shard,
                              //           String used,
                              //String name,
                              //String suffix,
                              //String keyId,
                              //Integer size,
                              //Integer shardIndex,
                              //Integer shardSize,
                              //Integer shardTotal
                                FileDto fileDto//HashMap<String,String> jsonParam
                              ) throws Exception {//@RequestParam
        LOG.info("上传文件开始");
     // LOG.info("shard",jsonParam.get("shard"));
     // LOG.info("shardIndex",jsonParam.get("shardIndex"));
     // LOG.info("size",jsonParam.get("size"));

     // String shard=jsonParam.get("shard");
     // int shardIndex=Integer.parseInt(jsonParam.get("shardIndex"));
     // int shardSize=Integer.parseInt(jsonParam.get("shardSize"));
     // int shardTotal=Integer.parseInt(jsonParam.get("shardTotal"));
     // String used=jsonParam.get("used");
     // String name=jsonParam.get("name");
     // String suffix=jsonParam.get("suffix");
     // long size=Long.parseLong(jsonParam.get("size"));
     // String keyId=jsonParam.get("keyId");





        String used = fileDto.getUsed();
        String keyId = fileDto.getKeyId();
        String suffix = fileDto.getSuffix();
        //FileDto fileDto=new FileDto();
    //  fileDto.setShard(shard);
    //  fileDto.setUsed(used);
    //  fileDto.setName(name);
    //  fileDto.setSuffix(suffix);
    //  fileDto.setKeyId(keyId);
    //  fileDto.setSize(size);
    //  fileDto.setShardIndex(shardIndex);
    //  fileDto.setShardSize(shardSize);
    //  fileDto.setShardTotal(shardTotal);
        LOG.info(fileDto.toString());
        LOG.info("used为",used);
        LOG.info("keyId为",keyId);
        LOG.info("suffix为",suffix);
        String shardBase64 = fileDto.getShard();
        MultipartFile shardfile = Base64ToMultipartFile.base64ToMultipart(shardBase64);

        // 保存文件到本地
        FileUseEnum useEnum = FileUseEnum.getByCode(used);


        //如果文件夹不存在则创建
        String dir = useEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dir);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }

//        String path = dir + File.separator + key + "." + suffix + "." + fileDto.getShardIndex();
        String path = new StringBuffer(dir)
                .append(File.separator)
                .append(keyId)
                .append(".")
                .append(suffix)
                .toString(); // course\6sfSqfOwzmik4A4icMYuUe.mp4
        String localPath = new StringBuffer(path)
                .append(".")
                .append(fileDto.getShardIndex())
                .toString(); // course\6sfSqfOwzmik4A4icMYuUe.mp4.1
        String fullPath = FILE_PATH + localPath;
        File dest = new File(fullPath);
        shardfile.transferTo(dest);
        LOG.info(dest.getAbsolutePath());

        LOG.info("保存文件记录开始");
        fileDto.setPath(path);
        fileService.save(fileDto);

        ResponseDto responseDto = new ResponseDto();
        fileDto.setPath(FILE_DOMAIN + path);
        responseDto.setContent(fileDto);

        if (fileDto.getShardIndex().equals(fileDto.getShardTotal())) {
            this.merge(fileDto);
        }
        return responseDto;
    }


    public void merge(FileDto fileDto) throws Exception {
        LOG.info("合并分片开始");
        String path = fileDto.getPath(); //http://127.0.0.1:9000/file/f/course\6sfSqfOwzmik4A4icMYuUe.mp4
        path = path.replace(FILE_DOMAIN, ""); //course\6sfSqfOwzmik4A4icMYuUe.mp4
        Integer shardTotal = fileDto.getShardTotal();
        File newFile = new File(FILE_PATH + path);
        FileOutputStream outputStream = new FileOutputStream(newFile, true);//文件追加写入
        FileInputStream fileInputStream = null;//分片文件
        byte[] byt = new byte[10 * 1024 * 1024];
        int len;

        try {
            for (int i = 0; i < shardTotal; i++) {
                // 读取第i个分片
                fileInputStream = new FileInputStream(new File(FILE_PATH + path + "." + (i + 1))); //  course\6sfSqfOwzmik4A4icMYuUe.mp4.1
                while ((len = fileInputStream.read(byt)) != -1) {
                    outputStream.write(byt, 0, len);
                }
            }
        } catch (IOException e) {
            LOG.error("分片合并异常", e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                outputStream.close();
                LOG.info("IO流关闭");
            } catch (Exception e) {
                LOG.error("IO流关闭", e);
            }
        }
        LOG.info("合并分片结束");

        System.gc();
        Thread.sleep(100);

        // 删除分片
        LOG.info("删除分片开始");
        for (int i = 0; i < shardTotal; i++) {
            String filePath = FILE_PATH + path + "." + (i + 1);
            File file = new File(filePath);
            boolean result = file.delete();
            LOG.info("删除{}，{}", filePath, result ? "成功" : "失败");
        }
        LOG.info("删除分片结束");
    }


    @GetMapping("/check/{keyId}")
    public ResponseDto check(@PathVariable String keyId) throws Exception {
        LOG.info("检查上传分片开始：{}", keyId);
        ResponseDto responseDto = new ResponseDto();
        FileDto fileDto = fileService.findByKeyId(keyId);
        /*
        if (fileDto != null) {
            if (StringUtils.isEmpty(fileDto.getVod())) {
                fileDto.setPath(OSS_DOMAIN + fileDto.getPath());
            } else {
                DefaultAcsClient vodClient = VodUtil.initVodClient(accessKeyId, accessKeySecret);
                GetMezzanineInfoResponse response = VodUtil.getMezzanineInfo(vodClient, fileDto.getVod());
                System.out.println("获取视频信息, response : " + JSON.toJSONString(response));
                String fileUrl = response.getMezzanine().getFileURL();
                fileDto.setPath(fileUrl);
            }
        }

         */
        responseDto.setContent(fileDto);
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
