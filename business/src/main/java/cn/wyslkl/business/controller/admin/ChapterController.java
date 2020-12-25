package cn.wyslkl.business.controller.admin;



import cn.wyslkl.server.dto.ChapterDto;
import cn.wyslkl.server.dto.PageDto;
import cn.wyslkl.server.service.ChapterService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@ComponentScan("cn.wyslkl")
@MapperScan("cn.wyslkl.server.mapper")
@ResponseBody
@RequestMapping(value = "/admin/chapter", method = RequestMethod.POST)//, consumes="application/json", produces="application/json"
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    @PostMapping("/list")
    public PageDto list(PageDto pageDto){
        chapterService.list(pageDto);
        return pageDto;
    }

   /**
   public List<ChapterDto> list(){
       return chapterService.list();
   }
   **/

}
