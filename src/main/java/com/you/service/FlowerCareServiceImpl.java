package com.you.service;

import com.alibaba.fastjson.JSON;
import com.you.Controller.FileUploadUtil;
import com.you.entity.FlowerCareEntity;
import com.you.mapper.FlowerCareMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyj on 2018/4/26.
 */
@Service
public class FlowerCareServiceImpl implements IFlowerCareService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public FlowerCareEntity getCare(Integer id) {
        FlowerCareEntity entity = flowerCareMapper.getCare(id);
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
        if(StringUtils.isEmpty(entity.getThumbnail())) {
            entity.setThumbnail(null);
        }
    }

    @Override
    public void deleteCare(Integer id) {
        if(id == null) {
            return;
        }

        FlowerCareEntity care = getCare(id);
        if(care == null) {
            return;
        }

        //上传图片标记删除
        List<String> imgs = new ArrayList<>();
        if(!StringUtils.isEmpty(care.getThumbnail())) {
            imgs.add(care.getThumbnail());
        }
        if(!CollectionUtils.isEmpty(care.getImgList())) {
            imgs.addAll(care.getImgList());
        }
        if(!CollectionUtils.isEmpty(imgs)) {
            for(String imgFile : imgs) {
                File file = new File(FileUploadUtil.BASE_FILE + imgFile);
                if(file.exists()) {
                    imgFile = imgFile.substring(0, imgFile.lastIndexOf(".")) + "_removed" +
                    imgFile.substring(imgFile.lastIndexOf("."), imgFile.length());
                    file.renameTo(new File(FileUploadUtil.BASE_FILE + imgFile));
                }
            }
        }

        flowerCareMapper.deleteCare(id);
    }

}
