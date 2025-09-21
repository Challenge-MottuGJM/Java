package com.challenge.mottu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.challenge.mottu.model.Moto;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long>{
	
	@Query("from Moto m where m.placa = :placa")
	public List<Moto> retornaMotosPorPlaca(String placa);

	@Query("from Moto m where m.placa = :modelo")
	public List<Moto> retornaMotosPorModelo(String modelo);

	@Query("from Moto m where m.status = :status")
	public List<Moto> retornaMotosPorStatus(String status);
}