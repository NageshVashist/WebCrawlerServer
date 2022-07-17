package com.webcrawler.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.webcrawler.model.TextSearch;

@Component
public class RequestDTO {
	private List<String> urls;
	private String text;

	public RequestDTO() {
	}

	public RequestDTO(List<String> urls, String text) {
		this.urls = urls;
		this.text = text;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<TextSearch> toTextSearch() {
		

		List<TextSearch> list = urls.stream().distinct().map(s -> new TextSearch(s, text))
				.collect(Collectors.toList());
		
		return list;

	}

}
