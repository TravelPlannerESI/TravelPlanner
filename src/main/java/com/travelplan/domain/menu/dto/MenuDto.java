package com.travelplan.domain.menu.dto;

import com.travelplan.domain.menu.entity.Menu;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MenuDto {

    public MenuDto(String menuName, Integer lft, Integer rgt, Integer depth) {
        this.menuName = menuName;
        this.lft = lft;
        this.rgt = rgt;
        this.depth = depth;
    }

    public MenuDto(Menu menu) {
        this.id = menu.getId();
        this.menuName = menu.getMenuName();
        this.lft = menu.getLft();
        this.rgt = menu.getRgt();
        this.menus = menu.getMenus().stream().map(MenuDto::new).collect(Collectors.toList());
        this.depth = menu.getDepth();
    }

    private Integer id;
    private String menuName;
    private Integer lft;
    private Integer rgt;
    List<MenuDto> menus = new ArrayList<>();
    private Integer depth;



}
