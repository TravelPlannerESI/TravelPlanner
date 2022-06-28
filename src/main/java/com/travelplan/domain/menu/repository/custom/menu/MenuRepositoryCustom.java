package com.travelplan.domain.menu.repository.custom.menu;

import com.travelplan.domain.menu.domain.Menu;

public interface MenuRepositoryCustom {
    void updateMenu(Menu subMeun, Integer id);

    void deleteMenu(Menu subMeun, Integer id);
}
