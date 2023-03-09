package com.sample.coffeeshop.order.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Schema(description = "주문")
public class OrderDto {
    @Schema(description = "주문 ID")
    Long orderId;
    @Schema(description = "유저 ID")
    String userId;
    @Schema(description = "메뉴 ID")
    Long menuId;
    @Schema(description = "메뉴명", example = "아이스 아메리카노")
    String menuName;
    @Schema(description = "주문 가격")
    Long orderPrice;
    @Schema(description = "주문일시")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    LocalDateTime orderedAt;
}
