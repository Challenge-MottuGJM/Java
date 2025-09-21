package com.challenge.mottu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.mottu.model.Bloco;

@Repository
public interface BlocoRepository extends JpaRepository<Bloco, Long>{

}
