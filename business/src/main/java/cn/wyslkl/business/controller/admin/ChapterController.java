package cn.wyslkl.business.controller.admin;



import cn.wyslkl.server.dto.ChapterDto;
import cn.wyslkl.server.service.ChapterService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@ComponentScan("cn.wyslkl")
@MapperScan("cn.wyslkl.server.mapper")
@RequestMapping("/admin/chapter")
@CrossOrigin
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    @RequestMapping("/list")
    public List<ChapterDto> list(){
        return chapterService.list();
    }
}
