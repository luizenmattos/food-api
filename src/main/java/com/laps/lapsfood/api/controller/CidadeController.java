package com.laps.lapsfood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.laps.lapsfood.domain.exception.EntidadeNaoEcontradaException;
import com.laps.lapsfood.domain.model.Cidade;
import com.laps.lapsfood.domain.service.CadastroCidadeService;
import com.laps.lapsfood.domain.repository.CidadeRepository;

@Controller
@ResponseBody
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	CidadeRepository cidadeRespository;
	
	@Autowired
	CadastroCidadeService cadastroCidadeService;
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Cidade> listar(){
		return cidadeRespository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable Long id){
		Optional<Cidade> cidade = cidadeRespository.findById(id);
		
		if(cidade.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(cidade);
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Cidade cidade){
		try {
			cidade = cadastroCidadeService.salvar(cidade);
			return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
		
		}catch (EntidadeNaoEcontradaException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cidade cidade){
		Optional<Cidade> cidadeAtual = cidadeRespository.findById(id);
		
		if(cidadeAtual.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		try {
			BeanUtils.copyProperties(cidade, cidadeAtual, "id");
			cadastroCidadeService.salvar(cidadeAtual.get());
			return ResponseEntity.ok(cidadeAtual);
			
		}catch (EntidadeNaoEcontradaException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id){
		try {
			cadastroCidadeService.deletar(id);
			return ResponseEntity.ok().build();
			
		}catch (EntidadeNaoEcontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
}
