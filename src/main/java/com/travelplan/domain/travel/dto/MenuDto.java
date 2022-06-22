package com.travelplan.domain.travel.dto;

import com.travelplan.domain.menu.entity.Menu;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MenuDto {

    public MenuDto(String shopRole, Integer lft, Integer rgt, Integer depth) {
        this.shopRole = shopRole;
        this.lft = lft;
        this.rgt = rgt;
        this.depth = depth;
    }

    public MenuDto(Menu menu) {
        this.id = menu.getId();
        this.shopRole = menu.getMenuName();
        this.lft = menu.getLft();
        this.rgt = menu.getRgt();
//        this.shops = menu.getMenus().stream().map(MenuDto::new).collect(Collectors.toList());
        this.depth = menu.getDepth();
    }

    private Integer id;
    private String shopRole;
    private Integer lft;
    private Integer rgt;
    List<MenuDto> shops = new ArrayList<>();
    private Integer depth;



}
