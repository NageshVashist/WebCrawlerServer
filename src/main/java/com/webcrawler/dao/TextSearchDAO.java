package com.webcrawler.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(value = UrlSearchText.class)
public class TextSearchDAO {

	@Id
	private String url;
	@Id
	private String searchText;
	private String result;
	private String status;

	public TextSearchDAO() {
	}

	public TextSearchDAO(String url, String searchText, String result, String status) {
		this.url = url;
		this.searchText = searchText;
		this.result = result;
		this.status = status;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
