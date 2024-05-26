package com.example.demo.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@ToString
@Table
@EntityListeners(AuditingEntityListener.class)
@Entity
public class UserAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id

    private String userId;

    private String userPassword;

    private String email;

    private String nickname;

    private String memo;

}
