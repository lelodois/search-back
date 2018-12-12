package br.com.lelo.domain;

import org.apache.solr.client.solrj.beans.Field;
import org.apache.solr.common.SolrDocument;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {

	private static final int COLUNS_SIZE = 3;
	@Field
	private String id;
	@Field
	private String name;
	@Field
	private String userName;
	@Field
	@JsonIgnore
	private int priority;

	public User(SolrDocument item) {
		if (item.getFieldNames().size() <= COLUNS_SIZE) {
			throw new IllegalArgumentException("Invalid saved user");
		}

		this.id = item.getFieldValue("id").toString();
		this.name = item.getFieldValue("name").toString();
		this.userName = item.getFieldValue("userName").toString();
		this.priority = Integer.parseInt(item.getFieldValue("priority").toString());
	}

	public User(String[] coluns) {
		if (coluns == null || coluns.length < COLUNS_SIZE) {
			throw new IllegalArgumentException("Invalid coluns size: " + coluns);
		}

		this.id = coluns[0];
		this.name = coluns[1];
		this.userName = coluns[2];
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
