package com.laps.lapsfood.infraestructure.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.laps.lapsfood.domain.model.Restaurante;
import com.laps.lapsfood.infraestructure.repository.spec.RestauranteComNomeSemelhanteSpec;

public class RestaurantesSpecs {

		public static Specification<Restaurante> comFreteGratis(){
			//Retorna uma classe anônima usando expressões lambda; deixa a necessidade de existir a classe;
			// O único método que precisa retornar é "toPredicate"
			return (root, query, builder) -> builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
		}
		
		public static Specification<Restaurante> comNomeSemelhante(String nome){
			return new RestauranteComNomeSemelhanteSpec(nome);
		}
}
