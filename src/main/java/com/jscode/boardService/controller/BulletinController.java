package com.jscode.boardService.controller;

import com.jscode.boardService.domain.Bulletin;
import com.jscode.boardService.domain.dto.BulletinDto;
import com.jscode.boardService.service.BulletinService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "게시판 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/bulletins")
public class BulletinController {

    private final BulletinService bulletinService;

    @ApiOperation(value = "게시글 작성 기능", notes = "제목, 내용을 입력해 게시글을 작성한다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(code = 201, message = "게시글 작성 성공."),
            @ApiResponse(code = 400, message = "제목 또는 내용이 유효성 검사에서 실패할 때.", response = String.class)
    })
    public Bulletin saveBulletin(
            @Valid
            @RequestBody
            @ApiParam(value = "게시글의 제목, 내용을 담는 DTO", required = true) BulletinDto bulletinDto){

        return bulletinService.save(bulletinDto);
    }

    @ApiOperation(value = "게시글 전체 조회 기능", notes = "기본적으로 첫번째 페이지 목록 리턴, page 값이 있다면 해당 페이지의 게시글을 불러온다.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(
            @ApiResponse(code = 200, message = "전체 게시글 조회 성공")
    )
    public Page<Bulletin> getList(
            @PageableDefault(size = 100, sort = "id", direction = Sort.Direction.DESC)
            @ApiParam(value = "페이징관련 인터페이스", required = false) Pageable pageable) {

        return bulletinService.getList(pageable);
    }

    @ApiOperation(value = "특정 게시글 조회 기능", notes = "기본적으로 첫번째 페이지 목록 리턴, page 값이 있다면 해당 페이지의 게시글을 불러온다.")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(code = 200, message = "특정 게시글 조회 성공"),
            @ApiResponse(code = 400, message = "존재하지 않는 게시글 일때.", response = String.class)
    })
    public Bulletin getBulletin(
            @PathVariable("id")
            @ApiParam(value = "게시글의 primary key 값", required = true, example = "13") Long id){

        return bulletinService.getBulletin(id);
    }

    @ApiOperation(value = "특정 게시글 수정 기능", notes = "해당 id 값을 가지는 게시글을 수정한다.")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(code = 200, message = "게시글 수정 성공"),
            @ApiResponse(code = 400, message = "존재하지 않는 게시글 일때.", response = String.class)
    })
    public Bulletin updateBulletin(
            @PathVariable("id")
            @ApiParam(value = "PK", required = true) Long id,
            @Valid @RequestBody
            @ApiParam(value = "게시글의 제목, 내용을 담는 DTO", required = true) BulletinDto bulletinDto){

        return bulletinService.updateBulletin(id, bulletinDto);
    }

    @ApiOperation(value = "특정 게시글 삭제 기능", notes = "해당 id값을 가지는 게시글을 삭제.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(code = 200, message = "게시글 삭제 성공"),
            @ApiResponse(code = 400, message = "존재하지 않는 게시글, 제목 또는 내용 유효성 검사 실패", response = String.class)
    })
    public String deleteBulletin(
            @PathVariable("id")
            @ApiParam(value = "삭제할 게시글의 primary key", required = true) Long id){

        bulletinService.deleteBulletin(id);
        return "삭제되었습니다.";
    }

    @ApiOperation(value = "게시글 검색 기능", notes = "게시글 제목 중 keyword값이 포함된 목록 조회.")
    @GetMapping("/search-title")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(code = 200, message = "게시글 검색 성공"),
            @ApiResponse(code = 400, message = "검색어 유효성이 맞지 않아 실패", response = String.class)
    })
    public Page<Bulletin> searchBulletin(
            @RequestParam
            @ApiParam(value = "제목에 포함되는 단어", required = true, example = "감자") String keyword,
            @PageableDefault(size = 100, sort="id", direction = Sort.Direction.DESC)
            @ApiParam(value = "페이징 인터페이스") Pageable pageable){

        return bulletinService.searchTitle(keyword, pageable);
    }
}
