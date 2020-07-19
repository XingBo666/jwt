package com.xb.entity;

import com.xb.util.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "tb_user")
public class User extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name ="nick_name")
    private String nickName;

}
