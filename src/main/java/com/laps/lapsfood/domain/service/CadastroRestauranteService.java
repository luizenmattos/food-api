package com.laps.lapsfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.laps.lapsfood.domain.exception.EntidadeNaoEcontradaException;
import com.laps.lapsfood.domain.model.Cozinha;
import com.laps.lapsfood.domain.model.Restaurante;
import com.laps.lapsfood.domain.repository.CozinhaRepository;
import com.laps.lapsfood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	RestauranteRepository restauranteRepository;
	
	@Autowired
	CozinhaRepository cozinhaRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
				.orElseThrow(()-> new EntidadeNaoEcontradaException(
					String.format("Cozinha com código %d não encontrda",cozinhaId)
				));
		
		restaurante.setCozinha(cozinha);

		return restauranteRepository.save(restaurante);
	}
	
	public void remover(Long id) {
		try {
			restauranteRepository.deleteById(id);			
			
		}catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEcontradaException(String.format("Restaurante com código %d não foi encontrado", id));
		}
	}
}
