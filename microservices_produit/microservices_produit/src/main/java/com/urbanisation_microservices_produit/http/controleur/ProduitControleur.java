package com.urbanisation_microservices_produit.http.controleur;



import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.urbanisation_microservices_produit.dao.ProduitRepository;
import com.urbanisation_microservices_produit.exceptions.ProduitIntrouvableException;
import com.urbanisation_microservices_produit.modele.Produit;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "API pour les opérations CRUD pour les produits")   

@RestController  
@RequestMapping(path="/produit") 
public class ProduitControleur {

	@Autowired 
	private ProduitRepository  produitRepository;

	Logger log =  LoggerFactory.getLogger(this.getClass());

	@PostMapping(path="/ajouterProduit")
	public ResponseEntity<Void> creerAssure(@Valid @RequestBody Produit produit) {
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

	@GetMapping(path="/listerLesProduits") public @ResponseBody Iterable<Produit>
	getAllProduits() { log.info(" getAllProduits"); 
	return produitRepository.findAll(); }

	@GetMapping(path="/numeroProduit/{numeroProduit}")
	public List<Produit> rechercherProduitNumeroProduit(@PathVariable Long numeroProduit) {
		return produitRepository.findByNumeroProduit(numeroProduit);    
	}

	@GetMapping(path="/libelleLike/{chaine}")
	public List<Produit>  rechercherProduitContientChaine(@PathVariable String chaine) {

		return  produitRepository.findByLibelleLike("%"+chaine+"%");

	}

	@ApiOperation(value = "Recherche un produit grâce à son ID à condition que celui-ci existe.")    
	@GetMapping(path="/rechercheBryId/{id}")
	public Optional<Produit> rechercherProduitId(@PathVariable Integer id) throws ProduitIntrouvableException {
		return  produitRepository.findById(id);
	}
}
