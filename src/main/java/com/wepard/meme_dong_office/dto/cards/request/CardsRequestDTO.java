package com.wepard.meme_dong_office.dto.cards.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardsRequestDTO {
    private String name;
    private String oneLiner;
    private String imageURL;
}