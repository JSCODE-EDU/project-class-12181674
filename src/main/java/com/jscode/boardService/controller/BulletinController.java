package com.jscode.boardService.controller;

import com.jscode.boardService.domain.Bulletin;
import com.jscode.boardService.domain.dto.BulletinDto;
import com.jscode.boardService.service.BulletinService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bulletins")
public class BulletinController {

    private final BulletinService bulletinService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bulletin saveBulletin(@RequestBody BulletinDto bulletin){

        return bulletinService.save(bulletin);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Bulletin> getList(@PageableDefault(size = 100, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        return bulletinService.getList(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Bulletin getBulletin(@PathVariable("id") Long id){

        return bulletinService.getBulletin(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Bulletin updateBulletin(@PathVariable("id") Long id, @RequestBody BulletinDto bulletinDto){

        return bulletinService.updateBulletin(id, bulletinDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteBulletin(@PathVariable("id") Long id){

        bulletinService.deleteBulletin(id);
        return "삭제되었습니다.";
    }

    @GetMapping("/search-title")
    @ResponseStatus(HttpStatus.OK)
    public Page<Bulletin> searchBulletin(@RequestParam String keyword, @PageableDefault(size = 100, sort="id", direction = Sort.Direction.DESC) Pageable pageable){

        return bulletinService.searchTitle(keyword, pageable);
    }
}
