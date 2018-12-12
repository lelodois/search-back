package br.com.lelo.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyProperties {

	@Value("${solr.query-page-size}")
	private int solrQueryPageSize;

	@Value("${solr.client}")
	private String solrClient;

	@Value("${file.users}")
	private String fileUsersPath;

	@Value("${file.priority1}")
	private String filePriority1Path;

	@Value("${file.priority2}")
	private String filePriority2Path;

	public String getFilePriority1Path() {
		return filePriority1Path;
	}

	public void setFilePriority1Path(String filePathPriority1) {
		this.filePriority1Path = filePathPriority1;
	}

	public String getFilePriority2Path() {
		return filePriority2Path;
	}

	public void setFilePriority2Path(String filePathPriority2) {
		this.filePriority2Path = filePathPriority2;
	}

	public String getFileUsersPath() {
		return fileUsersPath;
	}

	public void setFileUsersPath(String fileUsersPath) {
		this.fileUsersPath = fileUsersPath;
	}

	public int getSolrQueryPageSize() {
		return solrQueryPageSize;
	}

	public void setSolrQueryPageSize(int solrQueryPageSize) {
		this.solrQueryPageSize = solrQueryPageSize;
	}

	public String getSolrClient() {
		return solrClient;
	}

	public void setSolrClient(String solrClient) {
		this.solrClient = solrClient;
	}

}
