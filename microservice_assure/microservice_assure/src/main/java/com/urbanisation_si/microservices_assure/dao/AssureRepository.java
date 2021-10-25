package com.urbanisation_si.microservices_assure.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.converter.json.MappingJacksonValue;

import com.urbanisation_si.microservices_assure.exceptions.AssureIntrouvableException;
import com.urbanisation_si.microservices_assure.modele.Assure;

public interface AssureRepository extends CrudRepository<Assure, Integer> {
	/*
	 * @Query("SELECT p  FROM Personne p where p.nom=:nom AND p.prenom=:prenom")
	 * List<Assure> findByNomAndPrenom(@Param("nom") String nom, @Param("prenom")
	 * String prenom);
	 * 
	 * @Query("SELECT p  FROM Personne p where p.nom=:nom AND p.nom LIKE ‘%B%’ ")
	 * List<Assure> findByNomContient(@Param("nom") String nom);
	 */



	
	  @Query("from Assure a where a.numeroAssure = :na") List<Assure>
	  rechercherAssureNumeroAssure(@Param("na") Long numeroAssure);
	  
	  
	  @Query("from Assure a where a.numeroPersonne = :np") List<Assure>
	  rechercherAssureNumeroPersonne(@Param("np") Long numeroPersonne);
	 
	List<Assure> findByNumeroPersonne(Long numeroPersonne); 
	
	
	List<Assure> findByNomAndPrenom(String nom, String prenom);
	
	
	List<Assure> findByNomLike(String chaine);
	
	
	List<Assure> findByDateNaissanceBefore(LocalDate date);
	
	
	List<Assure> findByNomContaining(String chaine);

	@Query("from Assure a where a.id = :id")
	List<Assure>  rechercherAssureId(@Param("id") Integer id);


	List<Assure> findByNumeroAssure(Long numeroAssure);
	}
