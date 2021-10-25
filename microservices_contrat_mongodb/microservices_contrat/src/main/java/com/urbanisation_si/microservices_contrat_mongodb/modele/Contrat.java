package com.urbanisation_si.microservices_contrat_mongodb.modele;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import com.fasterxml.jackson.annotation.JsonFormat;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of= {"id", "numeroContrat"})
@ToString(of= {"id", "numeroContrat", "dateDebut", "numeroAssure", "numeroProduit"})
@Document(collection ="gestionprevdb" ) 
public class Contrat {
	
	@Id     
	private String id;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dateDebut;
	
	
	@Indexed(unique=true, direction= IndexDirection.DESCENDING)
	@NotNull
    private Long numeroContrat;
    
	@NotNull
    private Long numeroAssure;
    
	@NotNull
    private Long numeroProduit;

	public String getId() {
		return id;
	}
    
	
}
