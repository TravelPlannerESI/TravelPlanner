package com.travelplan.domain.travel.repository.custom.menu;

import com.travelplan.domain.menu.entity.Menu;

import java.util.List;

public interface MenuRepositoryCustom {
    void updateMenu(Menu subMeun, Integer id);

    void deleteMenu(Menu subMeun, Integer id);
}
