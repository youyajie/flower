package com.you.service;

import com.you.entity.FlowerCareEntity;

import java.util.List;

/**
 * Created by yyj on 2018/4/26.
 */
public interface IFlowerCareService {
    Integer insertCare(FlowerCareEntity entity);

    FlowerCareEntity getCare(Integer id);

    List<FlowerCareEntity> getCares();

    void deleteCare(Integer id);
}
