package com.urbanisation_microservices_produit.dao;

import java.util.List;


import org.springframework.data.repository.CrudRepository;

import com.urbanisation_microservices_produit.modele.Produit;




public interface ProduitRepository extends CrudRepository<Produit, Integer>{


	List<Produit> findByLibelleLike(String chaine);
	List<Produit> findByNumeroProduit(Long numeroProduit);

	
}
