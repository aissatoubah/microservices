package com.urbanisation_microservices_produit.http.controleur;




import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.urbanisation_microservices_produit.configuration.ApplicationPropertiesConfiguration;
import com.urbanisation_microservices_produit.dao.ProduitRepository;
import com.urbanisation_microservices_produit.exceptions.ProduitIntrouvableException;
import com.urbanisation_microservices_produit.modele.Produit;


import java.util.stream.Collectors;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "API pour les opérations CRUD pour les produits")   

@RestController  
@RequestMapping(path="/produit") 
public class ProduitControleur {

	@Autowired 
	private ProduitRepository  produitRepository;
	
	@Autowired
    ApplicationPropertiesConfiguration appProperties; 
	
	Logger log =  LoggerFactory.getLogger(this.getClass());

	@PostMapping(path="/ajouterProduit")
	public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Produit produit) {
		Produit produitAjoute = produitRepository.save(produit);

		if (produitAjoute == null)
			return ResponseEntity.noContent().build();

		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(produitAjoute.getId())
				.toUri();

		return ResponseEntity.created(uri).build(); 
	}

	@GetMapping(path="/listerLesProduits")
	public List<Produit> getAllProduits() {
		Iterable<Produit> produitsIterable = produitRepository.findAll();
        List produitsList = StreamSupport 
                .stream(produitsIterable.spliterator(), false) 
                .collect(Collectors.toList()); 
        List<Produit> listeLimitee = produitsList.subList(0, appProperties.getLimiteNombreProduit());
        return listeLimitee;
       
	}
	 public FilterProvider creerFiltre(String nomDuFiltre, String attribut) {

			SimpleBeanPropertyFilter unFiltre;
			if (attribut != null) {
				unFiltre = SimpleBeanPropertyFilter.serializeAllExcept(attribut);
			}
			else {
				unFiltre = SimpleBeanPropertyFilter.serializeAll();
			}

			return new SimpleFilterProvider().addFilter(nomDuFiltre, unFiltre);
		}

	@GetMapping(path="/Produit/rechercherNumeroProduit/{numeroProduit}")
	public List<Produit> rechercherProduitNumeroProduit(@PathVariable Long numeroProduit) {
		return produitRepository.findByNumeroProduit(numeroProduit);  
	}
	@GetMapping(path="/libelleLike/{chaine}")
	public List<Produit>  rechercherProduitContientChaine(@PathVariable String chaine) {

		return  produitRepository.findByLibelleLike("%"+chaine+"%");

	}

	@ApiOperation(value = "Recherche un produit grâce à son ID à condition que celui-ci existe.")    
	@GetMapping(path="/Produit/{id}")
	public MappingJacksonValue rechercherProduitId(@PathVariable Integer id) throws ProduitIntrouvableException {
		 Optional<Produit> produit =  produitRepository.findById(id);
		 if (!produit.isPresent()) throw new ProduitIntrouvableException("Le produit avec l'id " + id + " n'existe pas !"); 

			FilterProvider listeFiltres = creerFiltre("filtreDynamiqueJson", "libelle");

			Produit p = produit.get();
			ArrayList<Produit> ar = new ArrayList<Produit>();
			ar.add(produit.get());

			return filtrerProduits(ar, listeFiltres);
	  }

	public MappingJacksonValue filtrerProduits(List<Produit> produits, FilterProvider listeFiltres) {

			MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);

			produitsFiltres.setFilters(listeFiltres);

			return produitsFiltres;
		}
	
   
}