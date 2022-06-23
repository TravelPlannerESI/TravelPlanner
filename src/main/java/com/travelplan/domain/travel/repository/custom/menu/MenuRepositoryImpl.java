package com.travelplan.domain.travel.repository.custom.menu;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelplan.domain.menu.entity.Menu;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.travelplan.domain.menu.entity.QMenu.*;

@Repository
public class MenuRepositoryImpl implements MenuRepositoryCustom{

    private final JPAQueryFactory query;

    public MenuRepositoryImpl(EntityManager em) {
        query = new JPAQueryFactory(em);
    }

    //같은 루트에 해당되는 상위 메뉴들의 노드번호를 증가시킨다.
    @Override
    public void updateMenu(Menu subMeun, Integer id) {
        query.update(menu)
                .set(menu.lft, menu.lft.add(2))
                .where(menu.lft.goe(subMeun.getLft())
                        .and(menu.rootMenuId.eq(subMeun.getRootMenuId()))
                        .and(menu.parentMenuId.ne(subMeun.getParentMenuId()))
                )
                .execute();
        query.update(menu)
                .set(menu.rgt, menu.rgt.add(2))
                .where(menu.rgt.goe(subMeun.getRgt())
                        .and(menu.rootMenuId.eq(subMeun.getRootMenuId()))
                        .and(menu.parentMenuId.ne(subMeun.getParentMenuId()))
                        .and(menu.id.ne(subMeun.getParentMenuId().getId()))
                        .or(menu.id.eq(subMeun.getRootMenuId()))
                )
                .execute();
    }

    @Override
    public void deleteMenu(Menu subMeun, Integer id) {
        query.update(menu)
                .set(menu.lft, menu.lft.subtract(2))
                .where(menu.lft.goe(subMeun.getLft())
                        .and(menu.rootMenuId.eq(subMeun.getRootMenuId()))
                        .and(menu.parentMenuId.ne(subMeun.getParentMenuId()))
                )
                .execute();
        query.update(menu)
                .set(menu.rgt, menu.rgt.subtract(2))
                .where(menu.rgt.goe(subMeun.getRgt())
                        .and(menu.rootMenuId.eq(subMeun.getRootMenuId()))
                        .or(menu.id.eq(subMeun.getRootMenuId()))
                )
                .execute();

        query.delete(menu).where(menu.id.eq(id));
    }


}
