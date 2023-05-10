package com.jscode.boardService.service;

import com.jscode.boardService.domain.Bulletin;
import com.jscode.boardService.domain.dto.BulletinDto;
import com.jscode.boardService.repository.BulletinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.jscode.boardService.domain.ExceptionMessageConst.*;

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

    public List<Bulletin> getList(){
        return bulletinRepository.findAll();
    }

    public Bulletin getBulletin(Long id){
        Optional<Bulletin> result = bulletinRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        else{
            throw new IllegalArgumentException(NOT_EXIST_ID.getMessage());
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
        bulletinRepository.deleteById(id);
    }
}
