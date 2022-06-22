package com.travelplan.domain.menu.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

    public Menu(String menuName, Integer lft, Integer rgt, Integer depth) {
        this.menuName = menuName;
        this.lft = lft;
        this.rgt = rgt;
        this.depth = depth;
    }

    public Menu(Menu parent , String menuName) {
        this.menuName = menuName;
        this.lft = parent.rgt;
        this.rgt = parent.rgt + 1;
        this.depth = parent.depth +1;
        this.rootMenuId = parent.depth == 1 ? parent.id : parent.rootMenuId;
        this.parentMenuId = parent;
        parent.addRgt(2);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Integer id;

    @Column(name = "menu_name")
    private String menuName;

    private Integer lft;
    private Integer rgt;

    private Integer rootMenuId;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "parent_menu_id")
    private Menu parentMenuId;

    private Integer depth;

    public void addRgt(Integer rtg) {
        this.rgt += rtg;
    }



}
