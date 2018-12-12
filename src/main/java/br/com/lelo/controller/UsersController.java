package br.com.lelo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lelo.domain.Search;
import br.com.lelo.domain.User;
import br.com.lelo.service.UsersService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.OPTIONS })
public class UsersController {

	@Autowired
	private UsersService service;

	@GetMapping("/name/{name}/{page}")
	public ResponseEntity<Search<User>> findByName(@PathVariable String name, @PathVariable Integer page)
			throws Exception {
		Search<User> search = service.findByName(name, page);
		return this.createResponse(search);
	}

	@GetMapping("/user-name/{userName}/{page}")
	public ResponseEntity<Search<User>> findByUsername(@PathVariable String userName, @PathVariable Integer page)
			throws Exception {
		Search<User> search = service.findByUsername(userName, page);
		return this.createResponse(search);
	}

	private ResponseEntity<Search<User>> createResponse(Search<User> search) {
		if (search == null || search.getResults().isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(search);
	}

}
