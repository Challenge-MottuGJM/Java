package com.challenge.mottu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.challenge.mottu.model.Galpao;

public interface GalpaoRepository extends JpaRepository<Galpao, Long>{

}
