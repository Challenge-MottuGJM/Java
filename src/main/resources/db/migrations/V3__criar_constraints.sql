alter table if exists ANDAR 
  add constraint fk_andar_galpao_id__ref_galpao_id
  foreign key (GALPAO_ID) references GALPAO(ID);

alter table if exists PATIO 
  add constraint fk_patio_andar_id__ref_andar_id
  foreign key (ANDAR_ID) references ANDAR(ID);

alter table if exists BLOCO 
  add constraint fk_bloco_patio_id__ref_patio_id
  foreign key (PATIO_ID) references PATIO(ID);

alter table if exists VAGA 
  add constraint fk_vaga_bloco_id__ref_bloco_id
  foreign key (BLOCO_ID) references BLOCO(ID);

alter table if exists MOTO
  add constraint fk_moto_vaga_id__ref_vaga_id
  foreign key (VAGA_ID) references VAGA(ID);

alter table if exists USUARIO_CARGO 
  add constraint fk_usuario_cargo_usuario_id__ref_usuario_id
  foreign key (USUARIO_ID) references USUARIO(ID);

alter table if exists USUARIO_CARGO 
  add constraint fk_usuario_cargo_cargo_id__ref_cargo_id
  foreign key (CARGO_ID) references CARGO(ID);
