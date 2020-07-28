package com.felicia.speakers.repositories;

import com.felicia.speakers.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
