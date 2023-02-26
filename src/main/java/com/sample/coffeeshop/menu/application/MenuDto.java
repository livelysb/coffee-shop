package com.sample.coffeeshop.menu.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Schema(description = "메뉴")
public class MenuDto {
    @Schema(description = "메뉴 ID")
    private Long menuId;
    @Schema(description = "메뉴명", example = "아이스 아메리카노")
    private String menuName;
    @Schema(description = "메뉴 가격")
    private Long menuPrice;
}
