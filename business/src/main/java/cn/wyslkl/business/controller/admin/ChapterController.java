package cn.wyslkl.business.controller.admin;



import cn.wyslkl.server.dto.ChapterDto;
import cn.wyslkl.server.dto.ChapterPageDto;
import cn.wyslkl.server.dto.PageDto;
import cn.wyslkl.server.dto.ResponseDto;
import cn.wyslkl.server.service.ChapterService;
import cn.wyslkl.server.util.ValidatorUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static org.springframework.boot.Banner.Mode.LOG;

@RestController
@ComponentScan("cn.wyslkl")
@MapperScan("cn.wyslkl.server.mapper")
@ResponseBody
@CrossOrigin
@RequestMapping(value = "/admin/chapter", method = RequestMethod.POST)//, consumes="application/json", produces="application/json"
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);
    public static final String BUSINESS_NAME = "大章";

    @PostMapping("/list")
    public ResponseDto list(ChapterPageDto chapterPageDto){
        ResponseDto responseDto=new ResponseDto();
        ValidatorUtil.require(chapterPageDto.getCourseId(), "课程ID");
        chapterService.list(chapterPageDto);
        responseDto.setContent(chapterPageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(ChapterDto chapterDto){
        ResponseDto responseDto=new ResponseDto();
        chapterService.save(chapterDto);
        responseDto.setContent(chapterDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public ResponseDto delete(String id) {
        ResponseDto responseDto = new ResponseDto();
        chapterService.delete(id);
        return responseDto;
    }

   /**
   public List<ChapterDto> list(){
       return chapterService.list();
   }
   **/

}
