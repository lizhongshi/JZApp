package com.dly.app.dao;

import com.dly.app.pojo.TPush;
import com.dly.app.pojo.TPushExample;
import java.util.List;

import org.springframework.data.repository.query.Param;

public interface TPushMapper {
    long countByExample(TPushExample example);

    int deleteByExample(TPushExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TPush record);

    int insertSelective(TPush record);

    List<TPush> selectByExample(TPushExample example);

    TPush selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TPush record, @Param("example") TPushExample example);

    int updateByExample(@Param("record") TPush record, @Param("example") TPushExample example);

    int updateByPrimaryKeySelective(TPush record);

    int updateByPrimaryKey(TPush record);
}