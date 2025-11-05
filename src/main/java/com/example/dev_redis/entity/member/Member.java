package com.example.dev_redis.entity.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor // JPA가 프록시 만들 때 기본 생성자 필요
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk증설
    private Long id;

    @Column(nullable = false)
    private String name;

    public Member(String name) {
        this.name = name;
    }
}
