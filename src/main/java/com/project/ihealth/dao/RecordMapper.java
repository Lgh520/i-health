package com.project.ihealth.dao;

import com.project.ihealth.dao.po.Record;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Record record);

    int insertSelective(Record record);

    Record selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Record record);

    int updateByPrimaryKey(Record record);
}