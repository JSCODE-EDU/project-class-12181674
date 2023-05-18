package com.jscode.boardService.domain;

import com.jscode.boardService.domain.dto.BulletinDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@ApiModel(description = "게시글 정보를 지닌 클래스")
public class Bulletin {

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "primary key")
    private Long id;

    @ApiModelProperty(value = "게시글 제목")
    private String title;

    @ApiModelProperty(value = "게시글 내용")
    private String content;

    @ApiModelProperty(value = "게시글 생성 시간")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "게시글 수정 시간")
    private LocalDateTime updatedAt;

    @Builder
    public Bulletin(String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void update(BulletinDto bulletinDto){
        this.title = bulletinDto.getTitle();
        this.content = bulletinDto.getContent();
        this.updatedAt = LocalDateTime.now();
    }
}
