package com.laps.lapsfood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.laps.lapsfood.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>,RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante>{
	
	// -------------------------------------------------------------------------------
	// ---------- Consulta pelo Spring Data JPA atraveś de JPA Query Methods ---------- 
	Optional<Restaurante> findByNome(String nome); 
	
	List<Restaurante> findByNomeContaining(String nome); 

	
	// -------------------------------------------------------------------------------
	// ---------------------- Query Method nomeado usando JPQL -----------------------
	@Query("from Restaurante where nome like :nome")
	List<Restaurante> obterPorNome(String nome);

	@Query("from Restaurante where nome like %:nome%")
	List<Restaurante> obterPorNomeSemelhante(String nome);
	
	// -------------------------------------------------------------------------------
	// --------------------- Repositorio customizado com JPQL ------------------------
	// - Motivo: Quando é necessário implementar um código além das consulta automáticas
	// - Explicação: É criado uma implementação para o repo com o sufixo Impl. O SDJ faz 
	// essa ligação entre as interfaces automaticamente por mais que por Java não tenha nenhuma.
	// - Melhoria: Essa forma de implementação do SDJ tira a ferramenta de compilação do Java, para 
	// superar isso, não basta implementar RestauranteRepository pelo RestauranteRepositoryImpl 
	// pois teria que implementar diversos outros métodos, assim é criado uma interface
	// que é extendida por RestauranteRepository e é implementada por RestauranteRepositoryImpl.
	// - Sugestão de nome: RestauranteRepositoryQueries
	// - Conclusão: Todas as assinaturas de métodos poderiam ir para a nova classe.
	
	
	// -------------------------------------------------------------------------------
	// ---------------------------------- Criteria API --------------------------------
	// - Motivo: Criar queries através de código JAva ao invés de JPQL (mas no fim gerará uma SQL para consulta
	// - Explicação: é usado alguns objetos para isso
	//		1 - CriteriaBuilder: construtor de vários utilitários para a solução (como criteriaQuery, e predicates)
	//		2 - CriteriaQuery: construtor de clausulas de consulta
	//		3 - Root: raiz da consulta que representa a entidade; é usada para fazer where, group...
	//		4 - Predicate: como se fossem os filtros
	//		5 - TypedQuery: faz a tradução da estrutura de objetos em uma query;
	// - Obs: pode ser implementado de forma dinâmica com ifs
		
	
	// -------------------------------------------------------------------------------	
	// --------------------------- Specification (DDD) com SDJ------------------------
	// - Explicação: 
	//		1 - os filtros são separados em classes explicitando a regra de negócio
	//		2 - pelo padrão do Spring, implementar Spec tem de retornar Predicate (que representa um filtro)
	//		3 - os specs podem ser combinados e usado no findAll se repository extender JpaSpecificationExecuter
	// - Desvantagem: o consumidor fica responsável por definir a implementação
	
	
	// -------------------------------------------------------------------------------	
	// --------------------- Factory de Specification (DDD) com SDJ ------------------
	// - Explicação: 
	//		1 - Cria uma classe que retorna uma classe anônima 
	// - Desvantagem: o consumidor fica responsável por definir a implementação
}

