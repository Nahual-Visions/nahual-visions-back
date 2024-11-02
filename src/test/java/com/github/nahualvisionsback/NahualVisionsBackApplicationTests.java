package com.github.nahualvisionsback;

import com.github.nahualvisionsback.controller.AboutController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AboutController.class)
@WebAppConfiguration
class NahualVisionsBackApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void returnOk() throws Exception {
		mockMvc.perform(get("/"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("OK"));
	}
}
