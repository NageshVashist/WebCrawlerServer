package com.webcrawler.worker;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import org.jsoup.Connection;
import org.jsoup.Connection.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webcrawler.TextSearchRepository;
import com.webcrawler.dao.TextSearchDAO;
import com.webcrawler.model.TextSearch;

public class CrawlerWorker implements Callable<TextSearchDAO> {
	private TextSearchRepository repo;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private BlockingQueue<TextSearch> queue;

	public CrawlerWorker(BlockingQueue<TextSearch> queue, TextSearchRepository repo) {
		this.queue = queue;
		this.repo = repo;
	}

	@Override
	public TextSearchDAO call() throws Exception {

		TextSearch textSearch = null;
		TextSearchDAO dao = null;
		try {
			textSearch = queue.take();
			logger.info("Searching text '" + textSearch.getSearchText() + "' in url '" + textSearch.getUrl() + "'");
			if (textSearch != null) {
				Document document = Jsoup.connect(textSearch.getUrl())
						.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
						.referrer("http://www.google.com").timeout(1000 * 5).get();

				Elements elementsContainingText = document.getElementsContainingText(textSearch.getSearchText());

				Element element = elementsContainingText.last();
				logger.info("Text '" + textSearch.getSearchText() + "' found in website '" + textSearch.getUrl()
						+ "' at following line" + element);

				dao = new TextSearchDAO(textSearch.getUrl(), textSearch.getSearchText(),
						element != null
								? element.toString().substring(0,
										element.toString().length() > 255 ? 255 : element.toString().length())
								: "",
						element != null ? "FOUND" : "NOT_FOUND");
				repo.save(dao);
			}
		} catch (IOException | InterruptedException e) {
			logger.error(e.getMessage());
			dao = new TextSearchDAO(textSearch.getUrl(), textSearch.getSearchText(), e.getMessage(), "ERROR");
			repo.save(dao);
		}
		return dao;

	}
}
