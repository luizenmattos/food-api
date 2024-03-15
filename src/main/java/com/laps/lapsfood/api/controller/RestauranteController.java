package com.laps.lapsfood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laps.lapsfood.domain.exception.EntidadeNaoEcontradaException;
import com.laps.lapsfood.domain.model.Restaurante;
import com.laps.lapsfood.domain.repository.RestauranteRepository;
import com.laps.lapsfood.domain.service.CadastroRestauranteService;


@Controller
@ResponseBody
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	RestauranteRepository restauranteRepository;
	
	@Autowired
	CadastroRestauranteService cadastroRestauranteService;
	
	@GetMapping
	public List<Restaurante> listar(){
		return restauranteRepository.findAll();
	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
		Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
		
		if(restaurante.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(restaurante.get());
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante){
		try {
			restaurante = cadastroRestauranteService.salvar(restaurante);	
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		
		}catch(EntidadeNaoEcontradaException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
		
	}
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante){
		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
		if(restauranteAtual.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		try {
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
			Restaurante restauranteAtualizado = cadastroRestauranteService.salvar(restauranteAtual.get());
			return ResponseEntity.ok(restauranteAtualizado);
		
		}catch (EntidadeNaoEcontradaException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{restauranteId}")
	public ResponseEntity<?> remover(@PathVariable Long restauranteId){
		try {
			cadastroRestauranteService.remover(restauranteId);
			return ResponseEntity.noContent().build();
			
		}catch (EntidadeNaoEcontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> parametrosRestaurante){
		Optional<Restaurante> restaurante = restauranteRepository.findById(id);
		
		if(restaurante.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		partialMerge(parametrosRestaurante, restaurante.get());
		
		return atualizar(id,restaurante.get());
	}
	
	private void partialMerge(Map<String, Object> paramsOrigem, Restaurante restauranteDestino) {
		ObjectMapper mapper = new ObjectMapper();
		Restaurante restauranteOrigem = mapper.convertValue(paramsOrigem, Restaurante.class);
		
		paramsOrigem.forEach((key, value) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, key);
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
}
