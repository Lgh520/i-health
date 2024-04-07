package com.project.ihealth.dao;

import com.project.ihealth.dao.po.CollectionEntry;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectionMapper {

    void save(CollectionEntry collectionEntity);

    CollectionEntry findById(Long id);

    List<CollectionEntry> findByUserId(Long userId);
}
