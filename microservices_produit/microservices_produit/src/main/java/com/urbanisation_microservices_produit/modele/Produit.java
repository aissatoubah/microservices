package com.urbanisation_microservices_produit.modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity  
public class Produit {
	
	@Id    
    @GeneratedValue(strategy=GenerationType.AUTO)     
     private Integer id;
	
	 private String libelle;

	 private Long numeroProduit;

	@Override
	public String toString() {
		return "Produit [id=" + id + ", libelle=" + libelle + ", numeroProduit=" + numeroProduit + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Long getNumeroProduit() {
		return numeroProduit;
	}

	public void setNumeroProduit(Long numeroProduit) {
		this.numeroProduit = numeroProduit;
	}


}
