package com.jscode.boardService.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@ApiModel(description = "게시글 작성 및 수정 시 데이터 수신을 위한 DTO")
public class MemberDto {

    @NotBlank(message = "이메일은 반드시 입력해야 합니다.")
    @Email(message = "이메일 형식에 맞지않습니다.")
    @Pattern(regexp = "\\S+", message = "이메일에 공백이 없어야 합니다.")
    @ApiModelProperty(value = "이메일")
    private String email;


    @Size(min = 8, max = 15, message = "패스워드는 8~15글자 이하여야 합니다.")
    @Pattern(regexp = "\\S+", message = "패스워드에 공백이 없어야 합니다.")
    @ApiModelProperty(value = "패스워드")
    private String password;
}
