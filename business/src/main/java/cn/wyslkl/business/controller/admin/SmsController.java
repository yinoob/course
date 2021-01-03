package cn.wyslkl.business.controller.admin;

import cn.wyslkl.server.dto.SmsDto;
import cn.wyslkl.server.dto.PageDto;
import cn.wyslkl.server.dto.ResponseDto;
import cn.wyslkl.server.service.SmsService;
import cn.wyslkl.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.ComponentScan;
import org.mybatis.spring.annotation.MapperScan;

import javax.annotation.Resource;

@RestController
@ComponentScan("cn.wyslkl")
@MapperScan("cn.wyslkl.server.mapper")
@ResponseBody
@CrossOrigin
@RequestMapping("/admin/sms")
public class SmsController {

    private static final Logger LOG = LoggerFactory.getLogger(SmsController.class);
    public static final String BUSINESS_NAME = "短信验证码";

    @Resource
    private SmsService smsService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list( PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        smsService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save( SmsDto smsDto) {
        // 保存校验
        ValidatorUtil.require(smsDto.getMobile(), "手机号");
        ValidatorUtil.length(smsDto.getMobile(), "手机号", 1, 50);
        ValidatorUtil.require(smsDto.getCode(), "验证码");
        ValidatorUtil.require(smsDto.getUsed(), "用途");
        ValidatorUtil.require(smsDto.getAt(), "生成时间");
        ValidatorUtil.require(smsDto.getStatus(), "状态");
        ResponseDto responseDto = new ResponseDto();
        smsService.save(smsDto);
        responseDto.setContent(smsDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public ResponseDto delete( String id) {
        ResponseDto responseDto = new ResponseDto();
        smsService.delete(id);
        return responseDto;
    }
}
