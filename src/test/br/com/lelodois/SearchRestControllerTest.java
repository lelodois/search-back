package br.com.lelodois;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.solr.common.SolrDocument;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.common.base.MoreObjects;

import br.com.lelo.controller.UsersController;
import br.com.lelo.domain.Search;
import br.com.lelo.domain.User;
import br.com.lelo.service.UsersService;

@WebAppConfiguration
@RunWith(MockitoJUnitRunner.class)
public class SearchRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsersService service;

    @InjectMocks
    private UsersController controller;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void findByName_Ok() throws Exception {
        String name = "Test";
		int page = 1;
		when(service.findByName(name, page))
			.thenReturn(this.mockSearchUser(name, null, page));

        mockMvc.perform(get("/users/name/" + name + "/" + page))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.page").value(page))
                .andExpect(jsonPath("$.text").value(name))
                .andExpect(jsonPath("$.results").isArray())
                .andExpect(jsonPath("$.results[0].id").isNotEmpty())
        		.andExpect(jsonPath("$.results[0].name").value(name));
    }

    @Test
    public void findByUserName_Ok() throws Exception {
        String username = "Test";
		int page = 1;
		when(service.findByUsername(username, page))
			.thenReturn(this.mockSearchUser(null, username, page));

        mockMvc.perform(get("/users/user-name/" + username + "/" + page))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.page").value(page))
                .andExpect(jsonPath("$.text").value(username))
                .andExpect(jsonPath("$.results").isArray())
                .andExpect(jsonPath("$.results[0].id").isNotEmpty())
        		.andExpect(jsonPath("$.results[0].userName").value(username));
    }
    
    @Test
    public void findByUserName_notFound() throws Exception {
         mockMvc.perform(get("/users/user-name/Test/1"))
                 .andExpect(status().isNotFound());
      }

    @Test
    public void findByName_notFound() throws Exception {
        mockMvc.perform(get("/users/name/Test/1"))
        .andExpect(status().isNotFound());
    }
    
    private Search<User> mockSearchUser(String name, String username, int page) {
		Search<User> value = new Search<User>(MoreObjects.firstNonNull(name, username), page, 15);
		SolrDocument item = new SolrDocument();
		item.setField("id", "any");
		item.setField("name", name == null ? "any" : name);
		item.setField("userName", username == null ? "any" : username);
		item.setField("priority", 1);
		value.getResults().add(new User(item));
		return value;
	}
}