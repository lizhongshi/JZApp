package com.dly.app.dao;

import com.dly.app.pojo.TMethodCount;
import com.dly.app.pojo.TMethodCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TMethodCountMapper {
    long countByExample(TMethodCountExample example);

    int deleteByExample(TMethodCountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TMethodCount record);
    int inserts(@Param("methods") List<TMethodCount> list);

    int insertSelective(TMethodCount record);

    List<TMethodCount> selectByExample(TMethodCountExample example);

    TMethodCount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TMethodCount record, @Param("example") TMethodCountExample example);

    int updateByExample(@Param("record") TMethodCount record, @Param("example") TMethodCountExample example);

    int updateByPrimaryKeySelective(TMethodCount record);

    int updateByPrimaryKey(TMethodCount record);
}