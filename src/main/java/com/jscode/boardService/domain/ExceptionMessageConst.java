package com.jscode.boardService.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessageConst {

    NOT_EXIST_ID("존재하지 않는 게시글 입니다.");

    private final String message;

}
