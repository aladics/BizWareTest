package com.bizware.bizware;


import com.bizware.bizware.model.Visitor;
import com.bizware.bizware.repository.VisitorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class BizwareApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private VisitorRepository visitorRepository;

	@AfterEach
	public void tearDown() {
		visitorRepository.deleteAll();
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void testIndexPage() throws Exception {
		this.mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("index")); //
	}

	@Test
	public void testHomePage() throws Exception {
		this.mockMvc.perform(get("/home"))
				.andExpect(status().isOk())
				.andExpect(view().name("home")); // Verify the correct view name
	}

	@Test
	public void testGreet() throws Exception {
		String testName = "Test User";

		long initialCount = visitorRepository.count();

		this.mockMvc.perform(post("/greet")
						.param("name", testName))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello <strong>" + testName + "!</strong>")));

		long finalCount = visitorRepository.count();
		assertThat(finalCount).isEqualTo(initialCount + 1);
	}

	@Test
	public void testGreetings() throws Exception {
		Visitor visitor1 = new Visitor("Alice");
		Visitor visitor2 = new Visitor("Bob");
		visitorRepository.saveAll(List.of(visitor1, visitor2));

		MvcResult result = this.mockMvc.perform(get("/greetings"))
				.andExpect(status().isOk())
				.andExpect(view().name("greetings"))
				.andReturn();

		List<Visitor> visitorsList = (List<Visitor>) result.getModelAndView().getModel().get("visitorsList");

		assertThat(visitorsList).hasSize(2);
		assertThat(visitorsList).containsExactlyInAnyOrder(visitor1, visitor2);
	}

	@Test
	public void testDeleteVisitor() throws Exception {
		Visitor visitor1 = new Visitor("Alice");
		Visitor visitor2 = new Visitor("Bob");
		visitorRepository.saveAll(List.of(visitor1, visitor2));


		Integer idToDelete = visitor1.getId();

		MvcResult result = this.mockMvc.perform(delete("/deleteVisitor/" + idToDelete))
				.andExpect(status().isOk())
				.andExpect(view().name("visitors_table_fragment"))
				.andReturn();

		Optional<Visitor> deletedVisitor = visitorRepository.findById(idToDelete);
		assertThat(deletedVisitor).isNotPresent();


		List<Visitor> visitorsList = (List<Visitor>) result.getModelAndView().getModel().get("visitorsList");

		assertThat(visitorsList).hasSize(1);
		assertThat(visitorsList).containsExactly(visitor2);
	}

}
