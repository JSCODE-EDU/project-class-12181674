package com.jscode.boardService.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@ApiModel(description = "회원 정보 클래스")
public class Member {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "primary key")
    private Long id;

    @ApiModelProperty("이메일")
    private String email;

    @ApiModelProperty("비밀번호")
    private String password;

    @ApiModelProperty("회원가입 시간")
    private LocalDateTime createdAt;

    @Builder
    public Member(String email, String password, LocalDateTime createdAt) {
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }
}
