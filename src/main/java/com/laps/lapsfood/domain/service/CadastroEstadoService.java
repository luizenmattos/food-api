package com.laps.lapsfood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.laps.lapsfood.domain.exception.EntidadeEmUsoException;
import com.laps.lapsfood.domain.exception.EntidadeNaoEcontradaException;
import com.laps.lapsfood.domain.model.Estado;
import com.laps.lapsfood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	@Autowired 
	EstadoRepository  estadoRepository;

	@Transactional
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public void deletar(Long id) {
		try {
			estadoRepository.deleteById(id);
			
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("O Estado com código %d está em uso.", id));
			
		}catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEcontradaException(String.format("O Estado com código %d não existe.", id));
		}
	}
}
