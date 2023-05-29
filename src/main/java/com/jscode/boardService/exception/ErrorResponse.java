package com.jscode.boardService.exception;

import com.jscode.boardService.domain.ExceptionMessageConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@ApiModel(description = "에러 발생 시, 응답으로 주는 형식")
public class ErrorResponse {

    @ApiModelProperty(value = "발생 시간")
    private final LocalDateTime timestamp = LocalDateTime.now();

    @ApiModelProperty(value = "응답 코드")
    private final int statusCode;

    @ApiModelProperty(value = "에러명")
    private final String error;

    @ApiModelProperty(value = "에러 메시지")
    private final String message;
}
