package com.wepard.meme_dong_office.dto.usersCategories.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class UsersCategoriesResponseDTO {
    private Map<String, Integer> categories;
}