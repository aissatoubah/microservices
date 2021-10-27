package com.urbanisation_si.microservices_contrat.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component    
@ConfigurationProperties("urbanisation-si.clairprev") 


public class ApplicationPropertiesConfiguration {
	 private int limiteNombreContrat;

	    public int getLimiteNombreContrat() {
	        return limiteNombreContrat;
	    }

	    public void setLimiteNombreAssure(int limiteNombreContrat) {
	        this.limiteNombreContrat = limiteNombreContrat;
	    }

}
