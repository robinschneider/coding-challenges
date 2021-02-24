package com.mhp.coding.challenges.mapping.mappers;

import com.mhp.coding.challenges.mapping.models.db.Image;
import com.mhp.coding.challenges.mapping.models.db.blocks.*;
import com.mhp.coding.challenges.mapping.models.dto.ImageDto;
import com.mhp.coding.challenges.mapping.models.dto.blocks.ArticleBlockDto;
import com.mhp.coding.challenges.mapping.models.dto.blocks.GalleryBlockDto;
import com.mhp.coding.challenges.mapping.models.dto.blocks.ImageBlock;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ArticleBlockMapper {

    ImageMapper imageMapper = new ImageMapper();

    public ArticleBlockDto map(ArticleBlock articleBlock) {
        ArticleBlockDto articleBlockDto = new ArticleBlockDto();
        if (articleBlock instanceof GalleryBlock) {
            GalleryBlockDto galleryBlockDto = new GalleryBlockDto();
            galleryBlockDto.setImages(((GalleryBlock) articleBlock).getImages().stream().map(image -> {
                if (image != null) {
                    return imageMapper.map(image);
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList()));
            articleBlockDto = galleryBlockDto;
        } else if (articleBlock instanceof com.mhp.coding.challenges.mapping.models.db.blocks.ImageBlock) {
            ImageBlock imageBlock = new ImageBlock();
            if (imageBlock.getImage() != null) {
                Image image = new Image();
                image.setUrl(((com.mhp.coding.challenges.mapping.models.db.blocks.ImageBlock) articleBlock).getImage().getUrl());
                image.setImageSize(((com.mhp.coding.challenges.mapping.models.db.blocks.ImageBlock) articleBlock).getImage().getImageSize());
                ImageDto imageDto = imageMapper.map(image);
                imageBlock.setImage(imageDto);
            }
            // Return image as null
            articleBlockDto = imageBlock;
        } else if (articleBlock instanceof TextBlock) {
            com.mhp.coding.challenges.mapping.models.dto.blocks.TextBlock textBlock = new com.mhp.coding.challenges.mapping.models.dto.blocks.TextBlock();
            textBlock.setText(((TextBlock) articleBlock).getText());
            articleBlockDto = textBlock;
        } else if (articleBlock instanceof VideoBlock) {
            com.mhp.coding.challenges.mapping.models.dto.blocks.VideoBlock videoBlock = new com.mhp.coding.challenges.mapping.models.dto.blocks.VideoBlock();
            videoBlock.setUrl(((VideoBlock) articleBlock).getUrl());
            videoBlock.setType(((VideoBlock) articleBlock).getType());
            articleBlockDto = videoBlock;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_IMPLEMENTED, "Not yet implemented, will be added in a future version");
        }
        articleBlockDto.setSortIndex(articleBlock.getSortIndex());
        return articleBlockDto;
    }
}
