package com.mhp.coding.challenges.mapping.mappers;

import com.mhp.coding.challenges.mapping.models.db.Image;
import com.mhp.coding.challenges.mapping.models.dto.ImageDto;

public class ImageMapper {
    public ImageDto map(Image image){
        ImageDto imageDto = new ImageDto();
        imageDto.setUrl(image.getUrl());
        imageDto.setImageSize(image.getImageSize());
        return imageDto;
    }
}
