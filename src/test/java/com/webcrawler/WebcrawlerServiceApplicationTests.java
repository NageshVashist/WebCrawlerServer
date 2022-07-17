package com.webcrawler;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.webcrawler.controller.CrawlerController;
import com.webcrawler.dto.ResponseDTO;
import com.webcrawler.services.CrawlerService;

//@WebMvcTest(CrawlerController.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {TestConfig.class})

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
class WebcrawlerServiceApplicationTests {

	@InjectMocks
	private CrawlerController crawlercontroller;
	@Mock
	private CrawlerService service;

//	@Autowired
	private MockMvc mvc;

	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@BeforeEach
	public void setup() {

		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.standaloneSetup(this.crawlercontroller).build();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void searchTextInUrls() {
		try {
			ResponseDTO res = new ResponseDTO("https://en.wikipedia.org/wiki/Text#Computing_and_telecommunications",
					"which",
					"<li><a href=\"/wiki/Formatted_text\" title=\"Formatted text\">Formatted text</a>&nbsp;– Digital text which has styling information beyond minimal semantic elements</li>",
					"FOUND");
			List<ResponseDTO> l = new LinkedList();
			l.add(res);

			when(service.searchText(anyList())).thenReturn(l);

			String requestBody = "{\"urls\":	[\n"
					+ "		\"https://en.wikipedia.org/wiki/Text#Computing_and_telecommunications\"\n" + "	],\n"
					+ "\"text\": \"which\"	\n" + "}";

			ResultActions resultActions = mvc
					.perform(post("/search").contentType(APPLICATION_JSON_UTF8).content(requestBody))
					.andExpect(status().isOk());

//			ResultActions resultActions = mvc.perform(get("/getData")).andExpect(status().isOk());
			assertNotNull(resultActions);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
