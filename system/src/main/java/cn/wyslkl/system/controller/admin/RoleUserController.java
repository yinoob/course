package cn.wyslkl.system.controller.admin;

import cn.wyslkl.server.dto.RoleUserDto;
import cn.wyslkl.server.dto.PageDto;
import cn.wyslkl.server.dto.ResponseDto;
import cn.wyslkl.server.service.RoleUserService;
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
@RequestMapping("/admin/roleUser")
public class RoleUserController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleUserController.class);
    public static final String BUSINESS_NAME = "角色用户关联表";

    @Resource
    private RoleUserService roleUserService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list( PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        roleUserService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save( RoleUserDto roleUserDto) {
        // 保存校验
        ValidatorUtil.require(roleUserDto.getRoleId(), "角色");
        ValidatorUtil.require(roleUserDto.getUserId(), "用户");
        ResponseDto responseDto = new ResponseDto();
        roleUserService.save(roleUserDto);
        responseDto.setContent(roleUserDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public ResponseDto delete( String id) {
        ResponseDto responseDto = new ResponseDto();
        roleUserService.delete(id);
        return responseDto;
    }
}
