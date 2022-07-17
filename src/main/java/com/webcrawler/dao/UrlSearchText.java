package com.webcrawler.dao;

import java.io.Serializable;

public class UrlSearchText implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	private String searchText;

	public UrlSearchText() {
	}

	public UrlSearchText(String url, String searchText) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((searchText == null) ? 0 : searchText.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UrlSearchText other = (UrlSearchText) obj;
		if (searchText == null) {
			if (other.searchText != null)
				return false;
		} else if (!searchText.equals(other.searchText))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

}
