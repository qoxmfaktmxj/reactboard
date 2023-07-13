package com.app.kms.reactboard.api.repository;

import com.app.kms.reactboard.api.entity.BoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {
}

