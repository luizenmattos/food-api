package com.laps.lapsfood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.laps.lapsfood.domain.exception.EntidadeNaoEcontradaException;
import com.laps.lapsfood.domain.model.Cidade;
import com.laps.lapsfood.domain.model.Estado;
import com.laps.lapsfood.domain.repository.CidadeRepository;
import com.laps.lapsfood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {
	
	@Autowired
	CidadeRepository cidadeRepository;
	
	@Autowired
	EstadoRepository estadoRepository;
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Optional<Estado> estado = estadoRepository.findById(estadoId);
		
		if(estado == null) { //Usa uma Excpetion de domínio
			throw new EntidadeNaoEcontradaException(String.format("O Estado com código %d não foi encontrado", estadoId));
		}
		
		return cidadeRepository.save(cidade);
	}
	
	public void deletar(Long id) {
		try {
			cidadeRepository.deleteById(id);
			
		}catch (EmptyResultDataAccessException e) { // Transfere uma excption de Infrestrutura pra uma exception de domínio
			throw new EntidadeNaoEcontradaException(String.format("A cidade com código %d não foi encontrada", id));
		}
	}
}
