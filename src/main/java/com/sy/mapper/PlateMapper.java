package com.sy.mapper;

import com.sy.model.Plate;
import org.apache.ibatis.annotations.Select;

public interface PlateMapper {

    @Select("select * from plate where id=#{id}")
    Plate selectById(int id) throws Exception;
}
