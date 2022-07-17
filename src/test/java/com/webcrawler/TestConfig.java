package com.webcrawler;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.webcrawler.services.CrawlerService;
import com.webcrawler.worker.CrawlerExecutor;

@Configuration
public class TestConfig {

	@Bean
	public CrawlerService getService() {
		return Mockito.mock(CrawlerService.class);
	}
	
	@Bean
	public CrawlerExecutor getExecutor() {
		return Mockito.mock(CrawlerExecutor.class);
	}
}
