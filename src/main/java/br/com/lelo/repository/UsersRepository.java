package br.com.lelo.repository;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.lelo.common.MyProperties;
import br.com.lelo.domain.Search;
import br.com.lelo.domain.User;

@Repository
public class UsersRepository {

	@Autowired
	private HttpSolrClient solrClient;

	@Autowired
	private MyProperties properties;

	public Search<User> findByName(Search<User> search) throws Exception {
		return this.findByQueryParams(search, "name:" + search.getText() + "*");
	}

	public Search<User> findByUsername(Search<User> search) throws Exception {
		return this.findByQueryParams(search, "userName:" + search.getText());
	}

	public Search<User> findByQueryParams(Search<User> search, String queryParams) throws Exception {
		SolrQuery query = new SolrQuery(queryParams);
		query.setStart(search.getStartRow());
		query.addSort("priority", ORDER.asc);
		query.setRows(properties.getSolrQueryPageSize());

		solrClient.query(query).getResults().forEach(result -> {
			search.getResults().add(new User(result));
		});
		return search;
	}

	public void addBean(User user) {
		try {
			solrClient.addBean(user);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void commit() {
		try {
			solrClient.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
