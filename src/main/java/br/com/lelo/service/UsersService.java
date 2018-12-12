package br.com.lelo.service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lelo.common.MyProperties;
import br.com.lelo.domain.Search;
import br.com.lelo.domain.User;
import br.com.lelo.repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository repository;

	@Autowired
	private PriorityService priorityService;

	@Autowired
	private MyProperties myProperties;

	private final Log logger = LogFactory.getLog(UsersService.class);

	public Search<User> findByName(String name, int page) throws Exception {
		return repository.findByName(new Search<User>(name, page, this.getPageSize()));
	}

	public Search<User> findByUsername(String userName, int page) throws Exception {
		return repository.findByUsername(new Search<User>(userName, page, this.getPageSize()));
	}

	public void saveUsers(Stream<User> users) {
		new Thread(new Runnable() {
			public void run() {
				final AtomicInteger count = new AtomicInteger();
				users.parallel().forEach(user -> {
					priorityService.setUserPriority(user);
					repository.addBean(user);
					if (count.incrementAndGet() % 100 == 0) {
						repository.commit();
						logger.info("solr more 100 itens committed");
					}
				});
				repository.commit();
				logger.info("solr final itens loaded");

			}
		}).start();
	}

	private int getPageSize() {
		return myProperties.getSolrQueryPageSize();
	}

}
