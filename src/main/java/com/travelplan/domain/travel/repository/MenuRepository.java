package com.travelplan.domain.travel.repository;

import com.travelplan.domain.menu.entity.Menu;
import com.travelplan.domain.travel.repository.custom.menu.MenuRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> , MenuRepositoryCustom {

    Menu findByMenuName(String menuName);

    List<Menu> findByRootMenuId(Integer id);

    List<Menu> findAllBy();
}
