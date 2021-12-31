package com.scraper.api.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scraper.api.model.ResponseDTO;
import com.scraper.api.service.ScraperService;

@RestController
@RequestMapping("/api")
public class ScraperController {

	@Autowired
	ScraperService scraperService;
	
	/**
	 * Funcion que recibe como parametro el nombre del periodico
	 * 
	 * @param newsName
	 * @return
	 */
	@GetMapping("/{newsName}")
	public Set<ResponseDTO> getNewsByName(@PathVariable String newsName) {
		return scraperService.getNewsByName(newsName);
	}
	
}
