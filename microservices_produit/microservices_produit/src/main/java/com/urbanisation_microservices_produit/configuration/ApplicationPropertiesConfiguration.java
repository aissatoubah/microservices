package com.urbanisation_microservices_produit.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component    
@ConfigurationProperties("urbanisation-si.clairprev") 
@RefreshScope 

public class ApplicationPropertiesConfiguration {
	 private int limiteNombreProduit;

	 public int getLimiteNombreProduit() {
	        return limiteNombreProduit;
	    }

	    public void setLimiteNombreProduit(int limiteNombreAssure) {
	        this.limiteNombreProduit = limiteNombreProduit;
	    }

		}



