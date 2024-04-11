package com.laps.lapsfood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.laps.lapsfood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries{

	List<Restaurante> consultar(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	
	List<Restaurante> consultarCriteria(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);

}