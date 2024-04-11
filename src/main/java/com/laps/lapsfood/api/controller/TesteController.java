package com.laps.lapsfood.api.controller;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.laps.lapsfood.domain.model.Restaurante;
import com.laps.lapsfood.domain.repository.RestauranteRepository;
import com.laps.lapsfood.infraestructure.repository.spec.RestauranteComFreteGratisSpec;
import com.laps.lapsfood.infraestructure.repository.spec.RestauranteComNomeSemelhanteSpec;

@Controller
@ResponseBody
@RequestMapping("/teste")
public class TesteController {
	
	@Autowired
	RestauranteRepository restauranteRepository;

	@GetMapping("/restaurantes/porNome")
	public Restaurante obterPorNome(String nome) {
		return restauranteRepository.findByNome(nome).get();
	}
	
	@GetMapping("/restaurantes/porNomeSemelhante")
	public List<Restaurante> obterPorNomeSemelhante(String nome) {
		return restauranteRepository.findByNomeContaining(nome);
	}
	
	@GetMapping("/restaurantes/consultar")
	public List<Restaurante> consultar(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.consultar(nome, taxaInicial, taxaFinal);
	}
	
	@GetMapping("/restaurantes/consultarCriteria")
	public List<Restaurante> consultarCriteria(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.consultarCriteria(nome, taxaInicial, taxaFinal);
	}
	
	@GetMapping("/restaurantes/consultarCriteriaSpec")
	public List<Restaurante> consultarCriteriaSpec(String nome) {
		var comFreteGratis = new RestauranteComFreteGratisSpec();
		var comNomeSemelhante = new RestauranteComNomeSemelhanteSpec(nome);
		
		return restauranteRepository.findAll(comFreteGratis.and(comNomeSemelhante));
	}
}
