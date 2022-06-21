package com.travelplan.travel.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelplan.travel.repository.MenuRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static com.travelplan.travel.domain.QMenu.menu;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MenuTest {

    @Autowired
    EntityManager em;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MenuRepository menuRepository;

    JPAQueryFactory query;

    @BeforeEach
    void init() {
        query = new JPAQueryFactory(em);
    }

    @Test
    public void 메뉴_생성_테스트() throws Exception{

        //root메뉴 생성
        Menu depth1_1 = new Menu("전체조회", 1, 2, 1);
        Menu depth1_2 = new Menu("관리", 1, 2, 1);

        //root 메뉴 추가
        em.persist(depth1_1);
        em.persist(depth1_2);

        //2번째 계층
        Menu depth2_1 = new Menu(depth1_1, "날짜별 조회");
        Menu depth2_2 = new Menu(depth1_1, "금액조회");
        Menu depth2_3 = new Menu(depth1_2, "인증관리");
        Menu depth2_4 = new Menu(depth1_2, "날짜관리");

        //해당 루트 메뉴들에 2depth 추가한다.
        em.persist(depth2_1);
        em.persist(depth2_2);
        em.persist(depth2_3);
        em.persist(depth2_4);

        //3번째 계층
        Menu depth3_1 = new Menu(depth2_3,"차단된 멤버");
        Menu depth3_2 = new Menu(depth2_3,"승인된 멤버");

        em.persist(depth3_1);
        em.persist(depth3_2);

        //3depth 부터 아래의 menuUpdate 메서드가 필수적으로 일어나야한다 ( 1depth -> 독립적 , 2depth -> 바로 위의 1depth 의존적 )
        //벌크성 업데이트 -> 영속성 컨텍스트와 싱크가 안맞게된다 clear 필수.
        menuRepository.menuUpdate(depth3_1,depth2_3.getId());
        menuRepository.menuUpdate(depth3_2,depth2_3.getId());

        // 영속성 컨텍스트 클리어
        em.clear();

        Menu rootMenu = query.selectFrom(menu).where(menu.menuName.eq("전체조회")).fetchOne();
        List<Menu> menus = query.selectFrom(menu).where(menu.rootMenuId.eq(rootMenu.getId())).fetch();
        Menu vali1 = query.selectFrom(menu).where(menu.menuName.eq("날짜별 조회")).fetchOne();
        Menu vali2 = query.selectFrom(menu).where(menu.menuName.eq("금액조회")).fetchOne();

        Assertions.assertThat(menus).containsExactly(vali1, vali2);

    }
    





}