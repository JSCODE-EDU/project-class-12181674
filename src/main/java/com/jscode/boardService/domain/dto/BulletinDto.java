package com.jscode.boardService.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class BulletinDto {

    @NotBlank(message = "제목을 입력해주세요.(공백만으로는 불가합니다.)")
    @Size(min = 1, max = 15, message = "제목은 1~15글자 이하여야 합니다.")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요.")
    @Size(min = 1, max = 1000,message = "내용은 1~1000글자 이하여야 합니다.")
    private String content;
}
