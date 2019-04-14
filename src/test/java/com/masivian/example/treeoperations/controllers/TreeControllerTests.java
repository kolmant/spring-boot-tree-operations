package com.masivian.example.treeoperations.controllers;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class,
		MockitoTestExecutionListener.class
})
@SpringBootTest
public class TreeControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void invalidAlphabeticTreeStructure() throws Exception {

		JSONObject request = new JSONObject();
		request.put("structure", "a");

		this.mockMvc.perform(post("/tree")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request.toString()))
				.andExpect(status().is4xxClientError());
	}
	@Test
	public void invalidMultilineAlphabeticTreeStructure() throws Exception {

		JSONObject request = new JSONObject();
		request.put("structure", "1,2,3\na,b");

		this.mockMvc.perform(post("/tree")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request.toString()))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void validSingleLineTreeStructure() throws Exception {

		JSONObject request = new JSONObject();
		request.put("structure", "1,2,3");

		this.mockMvc.perform(post("/tree")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request.toString()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}

	@Test
	public void validMultilineTreeStructure() throws Exception {

		JSONObject request = new JSONObject();
		request.put("structure", "1,2,3\n1,2,4");

		this.mockMvc.perform(post("/tree")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request.toString()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}

}
