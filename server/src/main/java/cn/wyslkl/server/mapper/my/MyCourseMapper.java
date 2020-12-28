package cn.wyslkl.server.mapper.my;

import cn.wyslkl.server.dto.CourseDto;
import cn.wyslkl.server.dto.CoursePageDto;
import cn.wyslkl.server.dto.SortDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyCourseMapper {

    //List<CourseDto> list(@Param("pageDto") CoursePageDto pageDto);

    int updateTime(@Param("courseId") String courseId);

    //int updateSort(SortDto sortDto);

    //int moveSortsBackward(SortDto sortDto);

    //int moveSortsForward(SortDto sortDto);
}
