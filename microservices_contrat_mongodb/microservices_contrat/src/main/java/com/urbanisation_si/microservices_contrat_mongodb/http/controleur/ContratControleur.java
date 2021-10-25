package com.urbanisation_si.microservices_contrat_mongodb.http.controleur;

import java.net.URI;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.urbanisation_si.microservices_contrat_mongodb.dao.ContratRepository;
import com.urbanisation_si.microservices_contrat_mongodb.exception.ContratIntrouvableException;
import com.urbanisation_si.microservices_contrat_mongodb.modele.Contrat;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "API pour les opérations CRUD pour les contrats")   

@RestController  
@RequestMapping(path="/contrat") 
public class ContratControleur {

	@Autowired 
	private ContratRepository  contratRepository;

	Logger log =  LoggerFactory.getLogger(this.getClass());

	@PostMapping(path="/createContrat")
	public ResponseEntity<Void> creerContrat(@Valid @RequestBody Contrat contrat) {
		Contrat contratCreate = contratRepository.save(contrat);

		if (contratCreate == null)
			return ResponseEntity.noContent().build();

		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(contratCreate.getId())
				.toUri();

		return ResponseEntity.created(uri).build(); 
	}
	@GetMapping(path="/listerLesContrats") public @ResponseBody Iterable<Contrat>
	getAllContrats() { log.info(" getAllContrats"); 
	return contratRepository.findAll(); 
	}

	@GetMapping(path="/numeroAssure/{numeroAssure}")
	public List<Contrat> rechercherNumeroAssure(@PathVariable Long numeroAssure) {
		return contratRepository.findByNumeroContrat(numeroAssure);    
	}

	@GetMapping(path="/numeroContrat/{numeroContrat}")
	public List<Contrat> rechercherNumeroContrat(@PathVariable Long numeroContrat) {
		return contratRepository.findByNumeroContrat(numeroContrat);    
	}

	@GetMapping(path="/numeroProduit/{numeroProduit}")
	public List<Contrat> rechercherNumeroProduit(@PathVariable Long numeroProduit) {
		return contratRepository.findByNumeroProduit(numeroProduit);    
	}

	@GetMapping(path="/Contrat/dateDebut/{sdate}")
	public List<Contrat> rechercherContratAvantDateDebut
	(@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate sdate) {

		return contratRepository.findByDateDebutBefore( sdate);

	}

	@ApiOperation(value = "Recherche un contrat grâce à son ID à condition que celui-ci existe.")    
	@GetMapping(path="/rechercheById/{id}")
	public Optional<Contrat> rechercherContratId(@PathVariable Integer id) throws ContratIntrouvableException {
		return  contratRepository.findById(id);
	}
}

