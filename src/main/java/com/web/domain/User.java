package com.web.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uNum;

    @NotNull
    @Column(unique = true)
    private String userId;
    @NotNull
    private String UserPw;
    @NotNull
    private String userName;
    private String userPhone;
    private String userEmail;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private List<UserRole> roles;
}
