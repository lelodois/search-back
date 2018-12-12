package br.com.lelodois;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.lelo.MainApplication;
import br.com.lelo.common.StartLoaderComponent;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@ContextConfiguration(classes = { MainApplication.class })
public class IntegrationApplicationTests {

	@Autowired
	private StartLoaderComponent component;

	@Test
	public void start() throws Exception {
		Assert.assertTrue(component.getUsers().isEmpty());
		component.start();
		Assert.assertFalse(component.getUsers().isEmpty());
	}

}
