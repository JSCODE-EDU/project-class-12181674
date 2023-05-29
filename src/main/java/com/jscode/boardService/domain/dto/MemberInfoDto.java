package com.jscode.boardService.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@ApiModel(description = "회원 정보 조회 서비스 응답 DTO")
@Getter
public class MemberInfoDto {

    @ApiModelProperty(value = "primary key")
    private Long id;

    @ApiModelProperty(value = "유저 이메일")
    private String email;

    @ApiModelProperty(value = "회원가입 시간")
    private LocalDateTime createdAt;

    @Builder
    public MemberInfoDto(Long id, String email, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.createdAt = createdAt;
    }
}
