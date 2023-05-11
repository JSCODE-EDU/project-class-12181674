package com.jscode.boardService.repository;

import com.jscode.boardService.domain.Bulletin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BulletinRepository extends JpaRepository<Bulletin, Long> {

}
