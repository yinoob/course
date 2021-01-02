package cn.wyslkl.system.controller.admin;

import cn.wyslkl.server.dto.*;
import cn.wyslkl.server.service.UserService;
import cn.wyslkl.server.util.UuidUtil;
import cn.wyslkl.server.util.ValidatorUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.ComponentScan;
import org.mybatis.spring.annotation.MapperScan;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
@ComponentScan("cn.wyslkl")
@MapperScan("cn.wyslkl.server.mapper")
@ResponseBody
@CrossOrigin
@RequestMapping("/admin/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    public static final String BUSINESS_NAME = "用户表";

    @Resource
    private UserService userService;

    @Resource
    public RedisTemplate redisTemplate;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list( PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        userService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save( UserDto userDto) {
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        // 保存校验
        ValidatorUtil.require(userDto.getLoginName(), "登录名");
        ValidatorUtil.length(userDto.getLoginName(), "登录名", 1, 50);
        ValidatorUtil.length(userDto.getName(), "昵称", 1, 50);
        ValidatorUtil.require(userDto.getPassword(), "密码");
        ResponseDto responseDto = new ResponseDto();
        userService.save(userDto);
        responseDto.setContent(userDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public ResponseDto delete( String id) {
        ResponseDto responseDto = new ResponseDto();
        userService.delete(id);
        return responseDto;
    }

    /**
     * 重置密码
     */
    @PostMapping("/save-password")
    public ResponseDto savePassword( UserDto userDto) {
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();
        userService.login(userDto);
        responseDto.setContent(userDto);
        return responseDto;
    }

    /**
     * 登录
     /
    @PostMapping("/login")
    public ResponseDto login( UserDto userDto, HttpServletRequest request) {
        LOG.info("用户登录开始");
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();

        // 根据验证码token去获取缓存中的验证码，和用户输入的验证码是否一致
         String imageCode = (String) request.getSession().getAttribute(userDto.getImageCodeToken());
        //String imageCode = (String) redisTemplate.opsForValue().get(userDto.getImageCodeToken());
        LOG.info("从redis中获取到的验证码：{}", imageCode);
        if (StringUtils.isEmpty(imageCode)) {
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码已过期");
            LOG.info("用户登录失败，验证码已过期");
            return responseDto;
        }
        if (!imageCode.toLowerCase().equals(userDto.getImageCode().toLowerCase())) {
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码不对");
            LOG.info("用户登录失败，验证码不对");
            return responseDto;
        } else {
            // 验证通过后，移除验证码
            request.getSession().removeAttribute(userDto.getImageCodeToken());
            //redisTemplate.delete(userDto.getImageCodeToken());
        }

        LoginUserDto loginUserDto = userService.login(userDto);
        String token = UuidUtil.getShortUuid();
        loginUserDto.setToken(token);
        request.getSession().setAttribute(Constants.LOGIN_USER, loginUserDto);
        //redisTemplate.opsForValue().set(token, JSON.toJSONString(loginUserDto), 3600, TimeUnit.SECONDS);
        responseDto.setContent(loginUserDto);
        return responseDto;
    }
    */

    /**
     * 登录
     */
    @PostMapping("/login")
    public ResponseDto Login( UserDto userDto) {
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();
        LoginUserDto loginUserDto=userService.login(userDto);
        responseDto.setContent(loginUserDto);
        return responseDto;
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout/{token}")
    public ResponseDto logout(@PathVariable String token) {
        ResponseDto responseDto = new ResponseDto();
//        request.getSession().removeAttribute(Constants.LOGIN_USER);
        redisTemplate.delete(token);
        LOG.info("从redis中删除token:{}", token);
        return responseDto;
    }
}
