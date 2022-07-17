package com.webcrawler.worker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.webcrawler.TextSearchRepository;
import com.webcrawler.dao.TextSearchDAO;
import com.webcrawler.dto.ResponseDTO;
import com.webcrawler.model.TextSearch;

@Component
public class CrawlerExecutor {
	private static final int N_THREADS = Runtime.getRuntime().availableProcessors() + 1;

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private int queueSize = 100;
	private BlockingQueue<TextSearch> queue = new LinkedBlockingQueue<>(queueSize);
	private ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);
	private TextSearchRepository repo;

	public CrawlerExecutor(TextSearchRepository repo) {
		this.repo = repo;
	}

	public List<ResponseDTO> addAll(List<TextSearch> textSearchList) {
		queue.addAll(textSearchList);
		return startExecutor();
	}

	private List<ResponseDTO> startExecutor() {
		logger.info("CrawlerExecutor started");
		int threadCount = queue.size() - 1;
		ArrayList<CrawlerWorker> workerlist = new ArrayList<CrawlerWorker>();
		while (threadCount >= 0) {
			workerlist.add(new CrawlerWorker(queue, repo));
			threadCount--;
		}

		List<ResponseDTO> searchResult = new LinkedList<>();
		try {
			List<Future<TextSearchDAO>> resultsFuture = executor.invokeAll(workerlist);

			for (Future<TextSearchDAO> result : resultsFuture) {
				TextSearchDAO textSearchDAO = result.get();
				searchResult.add(new ResponseDTO(textSearchDAO.getUrl(), textSearchDAO.getSearchText(),
						textSearchDAO.getResult(), textSearchDAO.getStatus()));

			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			logger.error("Error :" + e.getMessage());
		}
		return searchResult;

	}

	@Override
	protected void finalize() throws Throwable {
		logger.info("CrawlerExecutor shutting down");
		executor.shutdown();
	}

}
