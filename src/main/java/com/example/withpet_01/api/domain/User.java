package com.example.withpet_01.api.domain;

import com.example.withpet_01.api.domain.common.CommonDateEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "user")
public class User extends CommonDateEntity implements UserDetails  {

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long uNum;

    @Column(nullable = false, unique = true, length = 50) // SNS ID
    private String id;
    @Setter
    @Column(nullable = true, unique = true) // 닉네임
    private String name;
    @Column(nullable = true)
    private String phoneNum;
    @Email
    @Column(nullable = true)
    private String email;
    @Column(length = 100)
    private String provider;

    @Column(length = 100)
    private String searchLocation; //검색용 주소

    @Column(length = 1000)
    private String searchX; //검색용 x좌표

    @Column(length = 1000)
    private String searchY; //검색용 y좌표

//    @JsonIgnore // 일단은..
//    @OneToMany(mappedBy = "owner")
//    private List<Pet> pets = new ArrayList<>();

//    @JsonIgnore // 일단은..
//    @OneToMany(mappedBy = "postAuthor")
//    private List<Post> posts = new ArrayList<>();


    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    private String usrToken; //회원 토큰

    public User update(String name){
        this.name = name;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
//
//    public String getRoleKey(){
//        return this.userRole.getKey();
//    }
}
