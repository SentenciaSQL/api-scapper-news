package com.scraper.api.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.scraper.api.model.ResponseDTO;

@Service
public class ScraperServiceImpl implements ScraperService{
	
	/**
	 * Obtiene las lista del periodicos del application.properties
	 */
	@Value("#{'${website.urls}'.split(',')}")
	List<String> urls;

	@Override
	public Set<ResponseDTO> getNewsByName(String newsName) {
		
		Set<ResponseDTO> responseDTOs = new HashSet<>();
		
		System.out.println(urls);
		
		for(String url: urls) {
			if(url.contains(newsName)) {
				if(newsName.equals("diariolibre")) {
					extractDataFromDiarioLibre(responseDTOs, url);
				}
				
				if(newsName.equals("listindiario")) {
					extractDataFromListinDiario(responseDTOs, url);
				}
				
				if(newsName.equals("elnacional")) {
					extractDataFromElNacional(responseDTOs, url);
				}
				
				if(newsName.equals("elnuevodiario")) {
					extractDataFromNuevoDiario(responseDTOs, url);
				}
				
				if(newsName.equals("remolacha")) {
					extractDataFromRemolacha(responseDTOs, url);
				}
			} 
		}
		
		return responseDTOs;
	}
	
	/**
	 * Función para obtener datos de Diario Libre
	 * 
	 * @param responseDTOs
	 * @param url
	 */
	private void extractDataFromDiarioLibre(Set<ResponseDTO> responseDTOs, String url) {
		try {
			Document document = Jsoup.connect(url).get();
			Elements elements = document.getElementsByTag("article");
			
			System.out.println(url);
			
			for (Element element : elements) {
				ResponseDTO responseDTO = new ResponseDTO();
				
				String title = element.getElementsByTag("h3").text();
				String mediaUrl = element.getElementsByTag("a").attr("href");
				String imgUrl = element.getElementsByTag("img").attr("data-src");
				
				System.out.println(imgUrl);
				
				if(title.isEmpty()) {
					title = element.getElementsByTag("h2").text();
				} 
				
				if(imgUrl.isEmpty()) {
					imgUrl = null;
				}
				
				responseDTO.setTitle(title);
				responseDTO.setExtract(null);
				responseDTO.setMediaUrl(url.concat(mediaUrl));
				responseDTO.setImg(imgUrl);
				
				responseDTOs.add(responseDTO);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Función para obtener datos de Listin Diario
	 * 
	 * @param responseDTOs
	 * @param url
	 */
	private void extractDataFromListinDiario(Set<ResponseDTO> responseDTOs, String url) {
		try {
			Document document = Jsoup.connect(url).get();
			Elements elements = document.getElementsByClass("row_item");
			
			for (Element element : elements) {
				ResponseDTO responseDTO = new ResponseDTO();
				
				String title = element.getElementsByTag("h2").text();
				String extract = element.getElementsByClass("topleftmain_sumario").text();
				String mediaUrl = element.getElementsByTag("a").attr("href");
				String imgUrl = element.getElementsByTag("img").attr("src");
				
				responseDTO.setTitle(title);
				responseDTO.setExtract(extract);
				responseDTO.setMediaUrl(url.concat(mediaUrl));
				responseDTO.setImg(imgUrl);
				
				responseDTOs.add(responseDTO);
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Función para obtener datos de Nuevo Diario
	 * 
	 * @param responseDTOs
	 * @param url
	 */
	private void extractDataFromNuevoDiario(Set<ResponseDTO> responseDTOs, String url) {
		try {
			Document document = Jsoup.connect(url).get();
			Elements elements = document.getElementsByClass("image-news");
			
			for (Element element : elements) {
				ResponseDTO responseDTO = new ResponseDTO();
				
				String title = element.getElementsByTag("a").attr("title");
				String mediaUrl = element.getElementsByTag("a").attr("href");
				String imgUrl = element.getElementsByTag("div").attr("data-src");
				
				responseDTO.setTitle(title);
				responseDTO.setExtract(null);
				responseDTO.setMediaUrl(url.concat(mediaUrl));
				responseDTO.setImg(imgUrl);
				
				responseDTOs.add(responseDTO);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Función para obtener datos de El Nacional
	 * 
	 * @param responseDTOs
	 * @param url
	 */
	private void extractDataFromElNacional(Set<ResponseDTO> responseDTOs, String url) {
		try {
			Document document = Jsoup.connect(url).get();
			Elements elements = document.getElementsByClass("utf_post_block_style");
			
			for (Element element : elements) {
				ResponseDTO responseDTO = new ResponseDTO();
				
				String title = element.getElementsByTag("a").text();
				String extract = element.getElementsByTag("p").text();
				String mediaUrl = element.getElementsByTag("a").attr("href");
				String imgUrl = element.getElementsByTag("img").attr("src");
				
				if(imgUrl.isEmpty()) {
					imgUrl = null;
				}
				
				if(extract.isEmpty()) {
					extract = null;
				}
				
				responseDTO.setTitle(title);
				responseDTO.setExtract(extract);
				responseDTO.setMediaUrl(url.concat(mediaUrl));
				responseDTO.setImg(imgUrl);
				
				responseDTOs.add(responseDTO);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Función para obtener datos de Remolacha
	 * 
	 * @param responseDTOs
	 * @param url
	 */
	private void extractDataFromRemolacha(Set<ResponseDTO> responseDTOs, String url) {
		try {
			Document document = Jsoup.connect(url).get();
			Elements elements = document.getElementsByClass("post");
			
			for (Element element : elements) {
				ResponseDTO responseDTO = new ResponseDTO();
				
				String title = element.getElementsByTag("h1").text();
				String extract = element.getElementsByTag("p").text();
				String mediaUrl = element.getElementsByTag("a").attr("href");
				String imgUrl = element.getElementsByTag("img").attr("data-orig-file");
				
				if(imgUrl.isEmpty()) {
					imgUrl = null;
				}
				
				responseDTO.setTitle(title);
				responseDTO.setExtract(extract);
				responseDTO.setMediaUrl(url.concat(mediaUrl));
				responseDTO.setImg(imgUrl);
				
				responseDTOs.add(responseDTO);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
