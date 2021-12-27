package com.example.democrud;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductoRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testfindByName() throws Exception {

		mockMvc.perform(get("/api/v1/products/findByName/{nombre}", "enterizo"))
				.andExpect(status().is(HttpStatus.OK.value())).andExpect(jsonPath("$.size()").value(1)).andReturn()
				.getResponse().getContentAsString();

	}

	@Test
	public void testMostWanted() throws Exception {

		mockMvc.perform(get("/api/v1/products/mostWanted")).andExpect(status().is(HttpStatus.OK.value()))
				.andExpect(jsonPath("$.size()").value(4)).andReturn().getResponse().getContentAsString();
	}


}
