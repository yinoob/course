package cn.wyslkl.server.service;

import cn.wyslkl.server.domain.Chapter;
import cn.wyslkl.server.domain.ChapterExample;
import cn.wyslkl.server.mapper.ChapterMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChapterService {

    @Resource
    private ChapterMapper chapterMapper;

    public List<Chapter> list(){
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.setOrderByClause("id asc");
        return chapterMapper.selectByExample(chapterExample);
    }
}
