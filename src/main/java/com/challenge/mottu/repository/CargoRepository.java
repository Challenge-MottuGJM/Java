package com.challenge.mottu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.mottu.model.Cargo;

@Repository
public interface CargoRepository  extends JpaRepository<Cargo, Long>{

}
