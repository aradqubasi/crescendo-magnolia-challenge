package com.crescendocollective.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RecipeClient {
	
	public List<Recipe> getRecipes(){
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new TextHtmlJsonConverter());
		restTemplate.setMessageConverters(messageConverters);
		ResponseEntity<Recipe[]> response = restTemplate.getForEntity("http://www.johnsonville.com/recipes.top-rated.json", Recipe[].class);
		return Arrays.asList(response.getBody());
		
	}

}
