package com.urbanisation.microservices_contrat.http.controleur;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.urbanisation.microservices_contrat.configuration.ApplicationPropertiesConfiguration;
import com.urbanisation.microservices_contrat.dao.ContratRepository;
import com.urbanisation.microservices_contrat.exception.ContratIntrouvableException;
import com.urbanisation.microservices_contrat.modele.Contrat;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "API pour les opérations CRUD pour les contrats")   

@RestController  
@RequestMapping(path="/contrat") 
public class ContratControleur {

	@Autowired 
	private ContratRepository  contratRepository;
	
	@Autowired    
    ApplicationPropertiesConfiguration appProperties; 

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
	@GetMapping(path="/listerLesContrats")  public List<Contrat> getAllContrats() {

    	
    	
    	   Iterable<Contrat> contratsIterable = contratRepository.findAll();
           List contratsList = StreamSupport 
                   .stream(contratsIterable.spliterator(), false) 
                   .collect(Collectors.toList()); 
           List<Contrat> listeLimitee = contratsList.subList(0, appProperties.getLimiteNombreContrat());
           return listeLimitee;
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
	(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate sdate) {

		return contratRepository.findByDateDebutBefore( sdate);

	}

	  @ApiOperation(value = "Recherche un contrat grâce à son ID à condition que celui-ci existe.")    
	    @GetMapping(path="/Contrat/{id}")
	  public MappingJacksonValue rechercherContratId(@PathVariable Integer id) throws ContratIntrouvableException {
		 // return  assureRepository.findById(id);
		  Optional<Contrat> contrat =  contratRepository.findById(id);


			if (!contrat.isPresent()) throw new ContratIntrouvableException("Le contrat avec l'id " + id + " n'existe pas !"); 

			FilterProvider listeFiltres = creerFiltre("filtreDynamiqueJson", "numeroAssure");

			
			Contrat c = contrat.get();
			ArrayList<Contrat> ar = new ArrayList<Contrat>();
			ar.add(contrat.get());

			return filtrerContrats(ar, listeFiltres);
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


		public MappingJacksonValue filtrerContrats(List<Contrat> contrats, FilterProvider listeFiltres) {

			MappingJacksonValue contratsFiltres = new MappingJacksonValue(contrats);

			contratsFiltres.setFilters(listeFiltres);

			return contratsFiltres;
		}
}

