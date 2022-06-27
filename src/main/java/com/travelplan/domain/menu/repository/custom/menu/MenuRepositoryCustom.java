package com.travelplan.domain.menu.repository.custom.menu;

import com.travelplan.domain.menu.entity.Menu;

public interface MenuRepositoryCustom {
    void updateMenu(Menu subMeun, Integer id);

    void deleteMenu(Menu subMeun, Integer id);
}
