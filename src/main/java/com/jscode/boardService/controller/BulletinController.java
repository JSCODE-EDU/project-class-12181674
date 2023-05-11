package com.jscode.boardService.controller;

import com.jscode.boardService.domain.Bulletin;
import com.jscode.boardService.domain.dto.BulletinDto;
import com.jscode.boardService.service.BulletinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bulletins")
public class BulletinController {

    private final BulletinService bulletinService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Bulletin saveBulletin(@RequestBody BulletinDto bulletin){

        return bulletinService.save(bulletin);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Bulletin> getList() {

        return bulletinService.getList();
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

}
