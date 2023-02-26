package com.sample.coffeeshop.menu.application;

import com.sample.coffeeshop.common.CoffeeShopBadRequestException;
import com.sample.coffeeshop.common.CoffeeShopErrors;
import com.sample.coffeeshop.menu.domain.Menu;
import com.sample.coffeeshop.menu.domain.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sample.coffeeshop.common.CoffeeShopErrors.MENU_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public List<MenuDto> getAllMenu() {
        return menuRepository.findAll().stream().map(Menu::toDto).collect(Collectors.toList());
    }

    public MenuDto getMenu(Long menuId) {
        Optional<Menu> byId = menuRepository.findById(menuId);
        return byId.orElseThrow(() -> new CoffeeShopBadRequestException(MENU_NOT_FOUND)).toDto();
    }
}
