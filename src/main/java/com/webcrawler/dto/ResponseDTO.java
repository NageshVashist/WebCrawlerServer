package com.webcrawler.dto;

public class ResponseDTO {

	private String url;
	private String searchText;
	private String result;
	private String status;

	public ResponseDTO() {
	}

	public ResponseDTO(String url, String searchText, String result, String status) {
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
