package com.sample.coffeeshop.user.controller;

import com.sample.coffeeshop.user.application.UserPointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserPointService userPointService;

    @Operation(summary = "회원의 포인트를 충전한다. 존재하지 않는 회원이 요청되면 신규가입 이후 포인트를 충전한다.")
    @PatchMapping("/user/{userId}/charge")
    public void charge(
            @PathVariable("userId")
            @Schema(description = "유저 ID")
            String userId,
            @RequestParam(name = "chargingPoint")
            @Schema(description = "충전금액")
            Long chargingPoint
    ) {
        userPointService.charge(userId, chargingPoint);
    }
}
