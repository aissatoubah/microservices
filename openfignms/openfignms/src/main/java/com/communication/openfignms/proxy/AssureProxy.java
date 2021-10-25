/**
 * 
 */
package com.communication.openfignms.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.communication.openfignms.AssureDTO;


/**
 * @author PC
 *
 */

@FeignClient(name="microservice-assure",url="localhost:9999")
public interface AssureProxy {
	@GetMapping("previt/listerLesAssures")
	List<AssureDTO> listerAssures();
	@GetMapping("/previt/Assure/numeroAssure/{assure.numeroAssure")
	List<AssureDTO> detailAssures(@PathVariable Long numeroAssure);
	
	//... recherche nom prenom...
   
}
