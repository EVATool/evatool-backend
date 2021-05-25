package com.evatool.domain.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "parent_child_counter")
@Entity(name = "parent_child_counter")
@EqualsAndHashCode
@ToString
public class ParentChildCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Getter
    @Column(name = "parent_id", updatable = false, nullable = false)
    private String parentId;

    @Getter
    @Column(name = "parent_class", updatable = false, nullable = false)
    private String parentClass;

    @Getter
    @Column(name = "child_class", updatable = false, nullable = false)
    private String childClass;

    @Getter
    @Column(name = "counter", nullable = false)
    private Integer counter;
}
