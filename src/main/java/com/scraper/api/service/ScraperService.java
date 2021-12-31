package com.scraper.api.service;

import java.util.Set;

import com.scraper.api.model.ResponseDTO;

public interface ScraperService {

	Set<ResponseDTO> getNewsByName(String newsName);
	
}
