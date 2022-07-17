package com.webcrawler.model;

public class TextSearch {
	private String url;
	private String searchText;

	public TextSearch() {
	}

	public TextSearch(String url, String searchText) {
		super();
		this.url = url;
		this.searchText = searchText;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

}
