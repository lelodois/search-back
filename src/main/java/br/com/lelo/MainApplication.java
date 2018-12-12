package br.com.lelo;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import br.com.lelo.common.MyProperties;

@SpringBootApplication
@ComponentScan({ "br.com.lelo.*" })
@EntityScan("br.com.lelo.*")
@EnableScheduling
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@Bean
	public HttpSolrClient getSolrClient(MyProperties properties) {
		HttpSolrClient solr = new HttpSolrClient(properties.getSolrClient());
		solr.setParser(new XMLResponseParser());
		return solr;
	}
}