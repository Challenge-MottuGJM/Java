package com.challenge.mottu.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.mottu.model.Galpao;

@Repository
public interface GalpaoRepository extends JpaRepository<Galpao, Long>{

}
