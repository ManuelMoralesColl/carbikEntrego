package com.carbik.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carbik.models.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByParticipantes_Id(Long usuarioId);
}
