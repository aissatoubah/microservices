package com.urbanisation_si.microservices_assure.modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity  
public class Assure extends Personne {
    

    private String dossierMedical;

    private Long numeroAssure;

	public String getDossierMedical() {
		return dossierMedical;
	}

	public void setDossierMedical(String dossierMedical) {
		this.dossierMedical = dossierMedical;
	}

	public Long getNumeroAssure() {
		return numeroAssure;
	}

	public void setNumeroAssure(Long numeroAssure) {
		this.numeroAssure = numeroAssure;
	}

	@Override
	public String toString() {
		return super.toString()+" Assure [dossierMedical=" + dossierMedical + ", numeroAssure=" + numeroAssure + "]";
		
	}

	



	
	
}
