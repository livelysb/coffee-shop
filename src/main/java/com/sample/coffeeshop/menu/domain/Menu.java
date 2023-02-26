package com.sample.coffeeshop.menu.domain;

import com.sample.coffeeshop.menu.application.MenuDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "menu")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    private String menuName;

    private Long menuPrice;

    public Menu(String menuName, Long menuPrice) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }

    public MenuDto toDto() {
        return new MenuDto(menuId, menuName, menuPrice);
    }
}
