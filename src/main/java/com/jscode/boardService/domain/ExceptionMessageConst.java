package com.jscode.boardService.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessageConst {

    NOT_EXIST_ID("존재하지 않는 게시글 입니다."),
    UNEXPECTED_EXCEPTION("서버에 예상치 못한 오류가 발생하였습니다. 잠시만 기다려 주십시오.");

    private final String message;

}
