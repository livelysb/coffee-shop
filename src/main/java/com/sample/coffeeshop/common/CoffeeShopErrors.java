package com.sample.coffeeshop.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum CoffeeShopErrors {
    MENU_NOT_FOUND("메뉴가 존재하지 않습니다."),
    USER_NOT_FOUND("회원이 존재하지 않습니다."),
    INSUFFICIENT_USER_POINT("유저의 잔여 포인트가 부족합니다."),
    INTERNAL_SERVER_ERROR("서비스 내부 에러가 발생하였습니다."),
    LOCK_ACQUISITION_FAILED("작업을 시작하지 못했습니다.");

    String errorMsg;
}
