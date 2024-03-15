package com.laps.lapsfood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.laps.lapsfood.api.model.CozinhaXmlAdpter;
import com.laps.lapsfood.domain.exception.EntidadeEmUsoException;
import com.laps.lapsfood.domain.exception.EntidadeNaoEcontradaException;
import com.laps.lapsfood.domain.model.Cozinha;
import com.laps.lapsfood.domain.repository.CozinhaRepository;
import com.laps.lapsfood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
	
		@Autowired
		CozinhaRepository cozinhaRepository;
		
		@Autowired
		CadastroCozinhaService cadastroCozinhaService;
		
		@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
		public List<Cozinha> listar() {
			return cozinhaRepository.findAll();
		}
		
		@GetMapping(produces = {MediaType.APPLICATION_XML_VALUE})
		public CozinhaXmlAdpter listarXml() {
			return new CozinhaXmlAdpter(cozinhaRepository.findAll());
		}
		
		@ResponseStatus(HttpStatus.OK)
		@GetMapping("/{cozinhaid}")	
		public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaid") Long  id) {
			Optional<Cozinha> cozinha = cozinhaRepository.findById(id);	
			
			if(cozinha.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			
			return ResponseEntity.ok(cozinha.get());
		}
		
		@ResponseStatus(HttpStatus.CREATED)
		@PostMapping
		public Cozinha adicionar(@RequestBody Cozinha cozinha) {
			return cadastroCozinhaService.salvar(cozinha);
		}
		
		@PutMapping("/{cozinhaId}")
		public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId,
				@RequestBody Cozinha cozinha){
			Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(cozinhaId);
			
			if(cozinhaAtual.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			
			BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
			Cozinha cozinhaSalva = cadastroCozinhaService.salvar(cozinhaAtual.get());
			return ResponseEntity.ok(cozinhaSalva);
		}
		
		@DeleteMapping("/{cozinhaId}")
		public ResponseEntity<?> remover(@PathVariable Long cozinhaId){

			try {
				cadastroCozinhaService.excluir(cozinhaId);
				return ResponseEntity.noContent().build();
				
			}catch(EntidadeEmUsoException e) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
			
			}catch(EntidadeNaoEcontradaException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
			
		}
}
