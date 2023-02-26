package com.sample.coffeeshop.order.application;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Schema(description = "주문 생성 요청")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {
    @Schema(description = "메뉴 ID")
    @NotNull(message = "메뉴 ID는 필수로 입력해야 합니다.")
    private Long menuId;
    @Schema(description = "유저 ID")
    @NotNull(message = "유저 ID는 필수로 입력해야 합니다.")
    private String userId;
}
