package com.sample.coffeeshop.menu.controller;

import com.sample.coffeeshop.menu.application.MenuDto;
import com.sample.coffeeshop.menu.application.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @Operation(summary = "모든 커피메뉴를 조회한다.")
    @GetMapping("/all-menu")
    public List<MenuDto> getAllMenu() {
        return menuService.getAllMenu();
    }
}
