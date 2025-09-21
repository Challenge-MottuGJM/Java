package com.challenge.mottu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.mottu.model.Patio;

@Repository
public interface PatioRepository extends JpaRepository<Patio, Long> {

}
