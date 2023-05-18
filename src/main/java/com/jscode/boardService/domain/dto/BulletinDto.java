package com.jscode.boardService.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@ApiModel(description = "게시글 작성 및 수정 시 데이터 수신을 위한 DTO")
public class BulletinDto {

    @NotBlank(message = "제목을 입력해주세요.(공백만으로는 불가합니다.)")
    @Size(min = 1, max = 15, message = "제목은 1~15글자 이하여야 합니다.")
    @ApiModelProperty(value = "게시글 제목")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요.")
    @Size(min = 1, max = 1000,message = "내용은 1~1000글자 이하여야 합니다.")
    @ApiModelProperty(value = "게시글 내용")
    private String content;
}
