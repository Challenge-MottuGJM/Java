package com.challenge.mottu.dto;

import com.challenge.mottu.model.Moto;
import com.challenge.mottu.model.Vaga;

public class MotoDTO{
	
	private Long id;
	private Vaga vaga;
	private String status;
	private String modelo;
	private String marca;
	private String placa;
	private String chassi;
	
	public MotoDTO() {}
	
	public MotoDTO(Moto moto) {
		this.id = moto.getId();
		this.vaga = moto.getVaga();
		this.status = moto.getStatus();
		this.modelo = moto.getModelo();
		this.marca = moto.getMarca();
		this.placa = moto.getPlaca();
		this.chassi = moto.getChassi();
	}
	
	public MotoDTO(Long id, Vaga vaga, String status, String modelo, String marca, String placa, String chassi) {
		super();
		this.id = id;
		this.vaga = vaga;
		this.status = status;
		this.modelo = modelo;
		this.marca = marca;
		this.placa = placa;
		this.chassi = chassi;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Vaga getVaga() {
		return vaga;
	}
	public void setVaga(Vaga vaga) {
		this.vaga = vaga;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getChassi() {
		return chassi;
	}
	public void setChassi(String chassi) {
		this.chassi = chassi;
	} 
	
	
}
