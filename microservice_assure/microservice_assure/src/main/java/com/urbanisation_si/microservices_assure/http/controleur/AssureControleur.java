package com.urbanisation_si.microservices_assure.http.controleur;


import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.urbanisation_si.microservices_assure.configuration.ApplicationPropertiesConfiguration;
import com.urbanisation_si.microservices_assure.dao.AssureRepository;
import com.urbanisation_si.microservices_assure.exceptions.AssureIntrouvableException;
import com.urbanisation_si.microservices_assure.modele.Assure;
import java.util.stream.Collectors;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "API pour les opérations CRUD pour les assurés")   

@RestController  
@RequestMapping(path="/previt")  
public class AssureControleur {
	
    @Autowired 
    private AssureRepository assureRepository;
    
    
    @Autowired
    ApplicationPropertiesConfiguration appProperties; 
    
	
	  Logger log = LoggerFactory.getLogger(this.getClass());
	  
	  
	  
	  @PostMapping(path="/ajouterAssure") public ResponseEntity<Void>
	  creerAssure(@Valid @RequestBody Assure assure) { Assure assureAjoute =
	  assureRepository.save(assure);
	  
	  if (assureAjoute == null) return ResponseEntity.noContent().build();
	  
	  URI uri = ServletUriComponentsBuilder .fromCurrentRequest() .path("/{id}")
	  .buildAndExpand(assureAjoute.getId()) .toUri();
	  
	  return ResponseEntity.created(uri).build(); }
	 
	
    @GetMapping(path="/listerLesAssures")
    public List<Assure> getAllAssures() {

    	// return assureRepository.findAll();
    	
    	Iterable<Assure> assuresIterable = assureRepository.findAll();
        List assuresList = StreamSupport 
                .stream(assuresIterable.spliterator(), false) 
                .collect(Collectors.toList()); 
        List<Assure> listeLimitee = assuresList.subList(0, appProperties.getLimiteNombreAssure());
        return listeLimitee;

    }
    

	  @GetMapping(path="/Assure/numeroPersonne/{numeroPersonne}")
		public List<Assure> rechercherAssureNumeroPersonne(@PathVariable Long numeroPersonne) {
			return assureRepository.findByNumeroPersonne(numeroPersonne);    
		}
	  @GetMapping(path="/Assure/numeroAssure/{numeroAssure}")
		public List<Assure> rechercherNumeroAssure(@PathVariable Long numeroAssure) {
			return assureRepository.findByNumeroAssure(numeroAssure);    
		}

	
		@GetMapping(path="/rechercherAssureNomPrenom/{nom}/{prenom}")
		public List<Assure> rechercherAssureNomPrenom(@PathVariable String nom, @PathVariable String prenom) {
			List<Assure> assuresList  = assureRepository.findByNomAndPrenom(nom, prenom);
			return assuresList;

		}

		
		@GetMapping(path="/Assure/nomLike/{chaine}")
		public List<Assure>  rechercherAssureContientChaine(@PathVariable String chaine) {

			return  assureRepository.findByNomLike("%"+chaine+"%");

		}

		
		@GetMapping(path="/Assure/avantDateNaissance/{sdate}")
		public List<Assure> rechercherAssureAvantDateNaissance
		(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate sdate) {

			return assureRepository.findByDateNaissanceBefore(sdate);

		}
		@GetMapping(path="/Assure/nomContient/{chaine}")
		public List<Assure>  rechercherAssureContientChaine2(@PathVariable String chaine) {

			return  assureRepository.findByNomContaining(chaine);

		}
		
		  @ApiOperation(value = "Recherche un assuré grâce à son ID à condition que celui-ci existe.")    
		    @GetMapping(path="/Assure/{id}")
		  public MappingJacksonValue rechercherAssureId(@PathVariable Integer id) throws AssureIntrouvableException {
			 // return  assureRepository.findById(id);
			  Optional<Assure> assure =  assureRepository.findById(id);

				//J2- 14
				if (!assure.isPresent()) throw new AssureIntrouvableException("L'assure avec l'id " + id + " n'existe pas !"); 

				FilterProvider listeFiltres = creerFiltre("filtreDynamiqueJson", "dossierMedical");

				Assure a = assure.get();
				ArrayList<Assure> ar = new ArrayList<Assure>();
				ar.add(assure.get());

				return filtrerAssures(ar, listeFiltres);
		  }
		
		@DeleteMapping (path="/Assure/{id}")     
	       public void supprimerAssurer(@PathVariable Integer id) {
	        assureRepository.deleteById(id);        
	       }
		
		@PutMapping (path="/modifierAssure")    
	      public void modifierAssure(@RequestBody Assure assure) {

	        assureRepository.save(assure);
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

		//J2- 13
		public MappingJacksonValue filtrerAssures(List<Assure> assures, FilterProvider listeFiltres) {

			MappingJacksonValue assuresFiltres = new MappingJacksonValue(assures);

			assuresFiltres.setFilters(listeFiltres);

			return assuresFiltres;
		}

}