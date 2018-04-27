package com.you.mapper;

import com.you.entity.FlowerCareEntity;

import java.util.List;

/**
 * Created by yyj on 2018/4/26.
 */
public interface FlowerCareMapper {
    Integer insertCare(FlowerCareEntity entity);

    FlowerCareEntity getCare();

    List<FlowerCareEntity> getCares();
}
