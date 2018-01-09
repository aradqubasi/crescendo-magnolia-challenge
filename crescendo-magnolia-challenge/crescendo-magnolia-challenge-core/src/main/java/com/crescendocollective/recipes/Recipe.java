package com.crescendocollective.recipes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {
	 private String title;
	 private String description;
	 private String prepTime;
	 private String cookTime;
	 private String servingSize;
	 
	public String getTitle() {

		return title;
	}
	public void setTitle(String title) {

		this.title = title;
	}
	public String getDescription() {

		return description;
	}
	public void setDescription(String description) {

		this.description = description;
	}
	public String getPrepTime() {

		return prepTime;
	}
	public void setPrepTime(String prepTime) {

		this.prepTime = prepTime;
	}
	public String getCookTime() {

		return cookTime;
	}
	public void setCookTime(String cookTime) {

		this.cookTime = cookTime;
	}
	public String getServingSize() {

		return servingSize;
	}
	public void setServingSize(String servingSize) {

		this.servingSize = servingSize;
	}
	
	@Override
	public String toString() {
		return "Recipe [title=" + title + ", description=" + description + ", prepTime=" + prepTime + ", cookTime="
				+ cookTime + ", servingSize=" + servingSize + "]";
	}
	
	
	 
	 
	

}
