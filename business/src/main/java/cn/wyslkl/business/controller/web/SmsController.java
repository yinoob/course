package cn.wyslkl.business.controller.web;

import cn.wyslkl.server.dto.ResponseDto;
import cn.wyslkl.server.dto.SmsDto;
import cn.wyslkl.server.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("webSmsController")
@RequestMapping("/web/sms")
public class SmsController {

    private static final Logger LOG = LoggerFactory.getLogger(SmsController.class);
    public static final String BUSINESS_NAME = "短信验证码";

    @Resource
    private SmsService smsService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseDto send( SmsDto smsDto) {
        LOG.info("发送短信请求开始: {}", smsDto);
        ResponseDto responseDto = new ResponseDto();
        smsService.sendCode(smsDto);
        LOG.info("发送短信请求结束");
        return responseDto;
    }
}
