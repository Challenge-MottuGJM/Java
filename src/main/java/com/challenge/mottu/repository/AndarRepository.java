package com.challenge.mottu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.mottu.model.Andar;

@Repository
public interface AndarRepository extends JpaRepository<Andar, Long>{

}
