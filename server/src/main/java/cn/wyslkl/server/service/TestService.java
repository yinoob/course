package cn.wyslkl.server.service;

import cn.wyslkl.server.domain.Test;
import cn.wyslkl.server.domain.TestExample;
import cn.wyslkl.server.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    public List<Test> list(){
        //TestExample testExample = new TestExample();
        //testExample.setOrderByClause("id asc");
        return testMapper.selectByExample(null);
    }
}
