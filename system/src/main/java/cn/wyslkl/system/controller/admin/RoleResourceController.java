package cn.wyslkl.system.controller.admin;

import cn.wyslkl.server.dto.RoleResourceDto;
import cn.wyslkl.server.dto.PageDto;
import cn.wyslkl.server.dto.ResponseDto;
import cn.wyslkl.server.service.RoleResourceService;
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
@RequestMapping("/admin/roleResource")
public class RoleResourceController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleResourceController.class);
    public static final String BUSINESS_NAME = "角色资源关联";

    @Resource
    private RoleResourceService roleResourceService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list( PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        roleResourceService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save( RoleResourceDto roleResourceDto) {
        // 保存校验
        ValidatorUtil.require(roleResourceDto.getRoleId(), "角色");
        ValidatorUtil.require(roleResourceDto.getResourceId(), "资源");
        ResponseDto responseDto = new ResponseDto();
        roleResourceService.save(roleResourceDto);
        responseDto.setContent(roleResourceDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public ResponseDto delete( String id) {
        ResponseDto responseDto = new ResponseDto();
        roleResourceService.delete(id);
        return responseDto;
    }
}
