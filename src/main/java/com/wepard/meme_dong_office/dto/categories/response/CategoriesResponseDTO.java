package com.wepard.meme_dong_office.dto.categories.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CategoriesResponseDTO {
    private Map<String, String> category;
}