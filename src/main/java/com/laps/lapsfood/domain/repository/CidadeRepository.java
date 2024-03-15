package com.laps.lapsfood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laps.lapsfood.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{
	//Pode ser implementada na mão ou pelo JpaRepository
	//public List<Cidade> listar();
	//public Cidade buscar(Long id);
	//public Cidade salvar(Cidade cidade);
	//public void remover(Long id);
}
