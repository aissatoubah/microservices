package com.urbanisation_si.microservices_contrat.dao;


import java.time.LocalDate;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.urbanisation_si.microservices_contrat.modele.Contrat;

public interface ContratRepository extends CrudRepository<Contrat, Integer>{

	@Query("from Contrat c where c.id = :id")
	List<Contrat>  rechercherContratId(@Param("id") Integer id);

	List<Contrat> findByNumeroContrat(Long numeroContrat);
	List<Contrat> findByNumeroAssure(Long numeroAssure);
	List<Contrat> findByNumeroProduit(Long numeroProduit);
	List<Contrat> findByDateDebutBefore(LocalDate date);


}

