package com.you.service;

import com.you.entity.FlowerCareEntity;
import com.you.mapper.FlowerCareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return flowerCareMapper.getCare();
    }

    @Override
    public List<FlowerCareEntity> getCares() {
        return flowerCareMapper.getCares();
    }
}
