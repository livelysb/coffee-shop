package com.sample.coffeeshop.order.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Schema(description = "주문")
public class OrderDto {
    @Schema(description = "주문 ID")
    private Long orderId;
    @Schema(description = "유저 ID")
    private String userId;
    @Schema(description = "메뉴 ID")
    private Long menuId;
    @Schema(description = "메뉴명", example = "아이스 아메리카노")
    private String menuName;
    @Schema(description = "주문 가격")
    private Long orderPrice;
    @Schema(description = "주문일시")
    private LocalDateTime orderedAt;
}
