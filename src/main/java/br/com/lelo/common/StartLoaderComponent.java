package br.com.lelo.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import br.com.lelo.domain.User;
import br.com.lelo.service.PriorityService;
import br.com.lelo.service.UsersService;

@Component
public class StartLoaderComponent {

	private final UsersService usersService;
	private final MyProperties properties;
	private final PriorityService priorityService;
	private List<User> users = Lists.newArrayList();
	private final Log logger = LogFactory.getLog(StartLoaderComponent.class);

	@Autowired
	public StartLoaderComponent(UsersService usersService, MyProperties properties, PriorityService priorityService) {
		this.usersService = usersService;
		this.properties = properties;
		this.priorityService = priorityService;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void start() throws Exception {
		logger.info("Loading priority");
		this.readerPriorityFile(properties.getFilePriority1Path(), 1);
		this.readerPriorityFile(properties.getFilePriority2Path(), 2);

		logger.info("Load users");
		this.loadUsers();
	}

	private void readerPriorityFile(String path, int priority) throws IOException {
		this.readerFile(path).parallel().forEach(lineId -> {
			priorityService.add(lineId, priority);
		});
	}

	private void loadUsers() throws Exception {
		this.readerFile(properties.getFileUsersPath()).parallel().forEach(line -> {
			try {
				users.add(new User(line.split(",")));
			} catch (Exception e) {
				logger.error(e + " invalid user: " + line);
			}
		});
		logger.info("saving users");
		usersService.saveUsers(users.stream());
	}

	private Stream<String> readerFile(String urlOrPath) throws IOException {
		try {
			logger.info("Reading from path: " + urlOrPath);
			String resource = this.getClass().getClassLoader().getResource(".").getPath();
			return Files.lines(Paths.get(resource + urlOrPath), Charset.forName("UTF-8"));
		} catch (Exception e) {
			logger.info("Reading from url: " + urlOrPath);
			InputStream in = new URL(urlOrPath).openStream();
			return IOUtils.readLines(in, Charset.forName("UTF-8")).stream();
		}
	}

	public List<User> getUsers() {
		return Collections.unmodifiableList(users);
	}
}
