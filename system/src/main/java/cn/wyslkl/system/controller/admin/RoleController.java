package cn.wyslkl.system.controller.admin;

import cn.wyslkl.server.dto.RoleDto;
import cn.wyslkl.server.dto.PageDto;
import cn.wyslkl.server.dto.ResponseDto;
import cn.wyslkl.server.service.RoleService;
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
@RequestMapping("/admin/role")
public class RoleController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);
    public static final String BUSINESS_NAME = "角色";

    @Resource
    private RoleService roleService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list( PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        roleService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save( RoleDto roleDto) {
        // 保存校验
        ValidatorUtil.require(roleDto.getName(), "角色");
        ValidatorUtil.length(roleDto.getName(), "角色", 1, 50);
        ValidatorUtil.require(roleDto.getDescIs(), "描述");
        ValidatorUtil.length(roleDto.getDescIs(), "描述", 1, 10);
        ResponseDto responseDto = new ResponseDto();
        roleService.save(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public ResponseDto delete( String id) {
        ResponseDto responseDto = new ResponseDto();
        roleService.delete(id);
        return responseDto;
    }
}
