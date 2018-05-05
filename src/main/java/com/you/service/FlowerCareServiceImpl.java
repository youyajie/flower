package com.you.service;

import com.alibaba.fastjson.JSON;
import com.you.entity.FlowerCareEntity;
import com.you.mapper.FlowerCareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by yyj on 2018/4/26.
 */
@Service
public class FlowerCareServiceImpl implements IFlowerCareService {

    @Autowired
    private FlowerCareMapper flowerCareMapper;

    @Override
    public Integer insertCare(FlowerCareEntity entity) {
        entity.setCreatedTime(System.currentTimeMillis() / 1000);
        entity.setUpdatedTime(System.currentTimeMillis() / 1000);
        entity.setStatus(1);
        return flowerCareMapper.insertCare(entity);
    }

    @Override
    public FlowerCareEntity getCare() {
        FlowerCareEntity entity = flowerCareMapper.getCare();
        convertCare(entity);
        return entity;
    }

    @Override
    public List<FlowerCareEntity> getCares() {
        List<FlowerCareEntity> entities = flowerCareMapper.getCares();
        convertCares(entities);
        return entities;
    }

    private void convertCares(List<FlowerCareEntity> entities) {
        if(CollectionUtils.isEmpty(entities)) {
            return;
        }

        for (FlowerCareEntity entity : entities) {
            convertCare(entity);
        }
    }

    private void convertCare(FlowerCareEntity entity) {
        if(entity == null) {
            return;
        }

        if(!StringUtils.isEmpty(entity.getImg())) {
            List<String> imgList = JSON.parseArray(entity.getImg(), String.class);
            entity.setImgList(imgList);
        }
    }
}
