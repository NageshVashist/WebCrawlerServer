package com.webcrawler;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webcrawler.dao.TextSearchDAO;

@Repository
public interface TextSearchRepository extends JpaRepository<TextSearchDAO, Integer> {

	public List<TextSearchDAO> findAllByUrlAndSearchText(String url, String searchText);
}
