package com.github.nahualvisionsback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nahualvisionsback.controller.AboutController;
import com.github.nahualvisionsback.controller.AuthController;
import com.github.nahualvisionsback.controller.UserController;
import com.github.nahualvisionsback.custexception.AuthException;
import com.github.nahualvisionsback.dto.JwtRequest;
import com.github.nahualvisionsback.repository.UserRepository;
import com.github.nahualvisionsback.service.AuthService;
import com.github.nahualvisionsback.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AboutController.class, AuthController.class, UserController.class,})
@WebAppConfiguration
//@WebMvcTest(AuthController.class)
class NahualVisionsBackApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	@MockBean
	private AuthService authService;


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

	@Test
	void login() throws Exception {
		JwtRequest user = new JwtRequest("Ivan", "Ivan");

		Mockito.when(this.authService.login(user)).thenThrow(AuthException.class);

		mockMvc.perform(post("/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(user)))
				.andDo(print())
				.andExpect(status().is4xxClientError());
	}

	@Test
	void anton() throws Exception {
		mockMvc.perform(get("/anton"))
				.andDo(print())
				.andExpect(status().isOk());
	}
}
