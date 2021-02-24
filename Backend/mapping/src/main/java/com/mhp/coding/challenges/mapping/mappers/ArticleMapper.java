package com.mhp.coding.challenges.mapping.mappers;

import com.mhp.coding.challenges.mapping.models.db.Article;
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ArticleMapper {

    ArticleBlockMapper articleBlockMapper = new ArticleBlockMapper();

    public ArticleDto map(Article article){
        ArticleDto articleDto = new ArticleDto();
        if (article != null) {
            articleDto.setId(article.getId());
            articleDto.setTitle(article.getTitle());
            articleDto.setAuthor(article.getAuthor());
            articleDto.setDescription(article.getDescription());
            if (article.getBlocks() != null) {
                articleDto.setBlocks(article.getBlocks().stream().map(articleBlockMapper::map).sorted().collect(Collectors.toList()));
            }
        }
        return articleDto;
    }

    public Article map(ArticleDto articleDto) {
        // Nicht Teil dieser Challenge.
        return new Article();
    }
}
