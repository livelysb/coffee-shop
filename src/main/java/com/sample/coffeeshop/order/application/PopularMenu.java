package com.sample.coffeeshop.order.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Schema(description = "인기메뉴")
public class PopularMenu implements Serializable {
    @Schema(description = "메뉴 ID")
    private Long menuId;
    @Schema(description = "메뉴명")
    private String menuName;
    @Schema(description = "주문 횟수")
    private Long orderedCnt;
}
