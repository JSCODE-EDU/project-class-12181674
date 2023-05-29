package com.jscode.boardService.service;

import com.jscode.boardService.domain.Bulletin;
import com.jscode.boardService.domain.dto.BulletinDto;
import com.jscode.boardService.repository.BulletinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.jscode.boardService.domain.ExceptionMessageConst.*;
import static org.apache.logging.log4j.util.Strings.*;

@Service
@RequiredArgsConstructor
public class BulletinService {

    private final BulletinRepository bulletinRepository;

    public Bulletin save(BulletinDto bulletinDto){
        Bulletin bulletin = Bulletin.builder()
                .title(bulletinDto.getTitle())
                .content(bulletinDto.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        bulletinRepository.save(bulletin);
        return bulletin;
    }

    public Page<Bulletin> getList(Pageable pageable){
        return bulletinRepository.findAll(pageable);
    }

    public Bulletin getBulletin(Long id){
        Optional<Bulletin> result = bulletinRepository.findById(id);

        if(result.isPresent()){
            return result.get();
        }
        else{
            throw new IllegalArgumentException(NOT_EXIST_Bulletin.getMessage());
        }
    }

    @Transactional
    public Bulletin updateBulletin(Long id, BulletinDto bulletinDto){
        Bulletin result = getBulletin(id);
        result.update(bulletinDto);
        bulletinRepository.save(result);
        return result;
    }

    public void deleteBulletin(Long id){
        checkId(id);
        bulletinRepository.deleteById(id);
    }

    private void checkId(Long id){
        Optional<Bulletin> result = bulletinRepository.findById(id);

        if(!result.isPresent()){
            throw new IllegalArgumentException(NOT_EXIST_Bulletin.getMessage());
        }
    }

    public Page<Bulletin> searchTitle(String keyword, Pageable pageable){
        checkKeyword(keyword);
        return bulletinRepository.findBulletinByTitleContaining(keyword, pageable);
    }

    private void checkKeyword(String keyword){

        if(isEmpty(keyword) || isBlank(keyword)){
            throw new IllegalArgumentException("검색어는 1글자 이상이어야합니다.");
        }
    }
}
