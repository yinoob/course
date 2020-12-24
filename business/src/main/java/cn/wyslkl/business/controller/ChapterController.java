package cn.wyslkl.business.controller;


import cn.wyslkl.server.domain.Chapter;
import cn.wyslkl.server.service.ChapterService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@ComponentScan("cn.wyslkl")
@MapperScan("cn.wyslkl.server.mapper")
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    @RequestMapping("/chapter")
    public List<Chapter> chapter(){
        return chapterService.list();
    }
}
