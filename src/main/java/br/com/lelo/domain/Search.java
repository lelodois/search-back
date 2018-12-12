package br.com.lelo.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

public class Search<T> {

	private List<T> results = Lists.newArrayList();
	private String text;
	private int page;
	@JsonIgnore
	private int startRow;

	public Search(String text, int page, int pageSize) {
		this.text = text;
		this.page = page;
		int realPage = this.page <= 0 ? 0 : this.page -1;
		this.startRow = realPage == 0 ? 0 : realPage * pageSize;
	}

	public int getPage() {
		return page;
	}

	public String getText() {
		return text;
	}

	public List<T> getResults() {
		return results;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

}
