package com.laps.lapsfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.laps.lapsfood.domain.exception.EntidadeEmUsoException;
import com.laps.lapsfood.domain.exception.EntidadeNaoEcontradaException;
import com.laps.lapsfood.domain.model.Cozinha;
import com.laps.lapsfood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {
	
	@Autowired
	CozinhaRepository cozinhaRepository;
	 
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	public void excluir(Long id) {
		try {
			cozinhaRepository.deleteById(id);
			
		}catch (EmptyResultDataAccessException e) { // Transfere uma excption de Infrestrutura pra uma exception de domínio
			throw new EntidadeNaoEcontradaException(String.format("Cozinha com código %d não encontrada.", id));
			
		}catch (DataIntegrityViolationException e) { // Transfere uma excption de Infrestrutura pra uma exception de domínio
			throw new EntidadeEmUsoException(String.format("Cozinha de código %d está em uso e não pode ser removida.", id));
		}
	}
	
}
