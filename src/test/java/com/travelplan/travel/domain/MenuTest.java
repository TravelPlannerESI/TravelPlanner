package com.travelplan.travel.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelplan.domain.menu.domain.Menu;
import com.travelplan.domain.menu.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@Slf4j
class MenuTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MenuRepository menuRepository;

    JPAQueryFactory query;

    @BeforeEach
    void init() {
        query = new JPAQueryFactory(em);

        // root메뉴 생성
        Menu depth1_1 = new Menu("전체조회", 1, 2, 1);
        Menu depth1_2 = new Menu("관리", 1, 2, 1);

        // root 메뉴 추가
        em.persist(depth1_1);
        em.persist(depth1_2);

        // 2번째 계층
        Menu depth2_1 = new Menu(depth1_1, "날짜별 조회");
        Menu depth2_2 = new Menu(depth1_1, "금액조회");
        Menu depth2_3 = new Menu(depth1_2, "인증관리");
        Menu depth2_4 = new Menu(depth1_2, "날짜관리");

        // 해당 루트 메뉴들에 2depth 추가한다.
        em.persist(depth2_1);
        em.persist(depth2_2);
        em.persist(depth2_3);
        em.persist(depth2_4);

        // 3번째 계층
        Menu depth3_1 = new Menu(depth2_3, "차단된 멤버");
        Menu depth3_2 = new Menu(depth2_3, "승인된 멤버");

        em.persist(depth3_1);
        em.persist(depth3_2);

        // 3depth 부터 아래의 menuUpdate 메서드가 필수적으로 일어나야한다 ( 1depth -> 독립적 , 2depth -> 바로 위의 1depth 의존적 )
        // 벌크성 업데이트 -> 영속성 컨텍스트와 싱크가 안맞게된다 clear 필수.
        menuRepository.updateMenu(depth3_1, depth2_3.getId());
        menuRepository.updateMenu(depth3_2, depth2_3.getId());

        // 영속성 컨텍스트 클리어
        em.clear();

    }

    @Test
    public void 메뉴_생성_테스트() throws Exception {
        Menu rootMenu = menuRepository.findByMenuName("전체조회");
        Menu vali1 = menuRepository.findByMenuName("날짜별 조회");
        Menu vali2 = menuRepository.findByMenuName("금액조회");

        assertThat(rootMenu.getMenus()).containsExactly(vali1, vali2);
    }

    @Test
    public void 메뉴_삭제_테스트() {
        Menu deleteMenu = menuRepository.findByMenuName("승인된 멤버");
        menuRepository.deleteMenu(deleteMenu,deleteMenu.getId());
        em.clear();

        Menu root = menuRepository.findByMenuName("관리");
        root.getMenus().forEach((child) -> {
            String childName = child.getMenuName();
            int childRgt = child.getRgt();
            if("인증관리".equals(childName)) assertThat(childRgt).isEqualTo(5);
            else assertThat(childRgt).isEqualTo(7);
        });
        assertThat(root.getRgt()).isEqualTo(8);
    }


}