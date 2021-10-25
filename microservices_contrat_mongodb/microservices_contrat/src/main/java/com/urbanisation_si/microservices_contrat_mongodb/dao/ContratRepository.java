package com.urbanisation_si.microservices_contrat_mongodb.dao;


import java.time.LocalDate;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.urbanisation_si.microservices_contrat_mongodb.modele.Contrat;

public interface ContratRepository extends MongoRepository<Contrat, Integer>{

	List<Contrat> findByNumeroContrat(Long numeroContrat);
	List<Contrat> findByNumeroAssure(Long numeroAssure);
	List<Contrat> findByNumeroProduit(Long numeroProduit);
	List<Contrat> findByDateDebutBefore(LocalDate date);


}

