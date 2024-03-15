package com.laps.lapsfood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laps.lapsfood.domain.exception.EntidadeEmUsoException;
import com.laps.lapsfood.domain.exception.EntidadeNaoEcontradaException;
import com.laps.lapsfood.domain.model.Estado;
import com.laps.lapsfood.domain.repository.EstadoRepository;
import com.laps.lapsfood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	EstadoRepository estadoRepository;
	
	@Autowired
	CadastroEstadoService cadastroEstadoService;
	
	@GetMapping
	public List<Estado> listar(){
		return estadoRepository.findAll();
	}
	
	@GetMapping("/{estadoId}")
	public ResponseEntity<Estado> buscar(@PathVariable Long estadoId){
		Optional<Estado> estado = estadoRepository.findById(estadoId);
		
		if(estado == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(estado.get());
	}
	
	@PostMapping
	public ResponseEntity<Estado> adicionar (@RequestBody Estado estado){
		estado = cadastroEstadoService.salvar(estado);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(estado);
	}
	
	@PutMapping("/{estadoId}")
	public ResponseEntity<Estado> atualizar(@PathVariable Long estadoId,@RequestBody Estado estado){
		Optional<Estado> estadoAtual = estadoRepository.findById(estadoId);
		
		if(estadoAtual.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		BeanUtils.copyProperties(estado, estadoAtual,"id");
		cadastroEstadoService.salvar(estadoAtual.get());
		return ResponseEntity.ok(estadoAtual.get());
	}
	
	@DeleteMapping("/{estadoId}")
	public ResponseEntity<?> remover(@PathVariable Long estadoId){
		try {
			cadastroEstadoService.deletar(estadoId);
			return ResponseEntity.noContent().build();
		
		}catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
			
		}catch (EntidadeNaoEcontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
