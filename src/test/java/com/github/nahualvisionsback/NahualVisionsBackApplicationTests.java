package com.github.nahualvisionsback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nahualvisionsback.controller.AboutController;
import com.github.nahualvisionsback.controller.AuthController;
import com.github.nahualvisionsback.controller.UserController;
import com.github.nahualvisionsback.custexception.AuthException;
import com.github.nahualvisionsback.dto.JwtRequest;
import com.github.nahualvisionsback.dto.JwtResponse;
import com.github.nahualvisionsback.repository.UserRepository;
import com.github.nahualvisionsback.service.AuthService;
import com.github.nahualvisionsback.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AboutController.class, AuthController.class, UserController.class,})
@SpringBootTest
@WebAppConfiguration
class NahualVisionsBackApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	@MockBean
	private AuthService authService;
	@MockBean
	private UserService userService;


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

//	@Test
//	void loginAsUsername() throws Exception {
//		JwtRequest user = new JwtRequest("Ivan", "", "ivan");
//		String access = "accessToken";
//		String refresh = "refreshToken";
//		JwtResponse res = new JwtResponse(access, refresh);
//		String js = "{\"username\":\"Ivan\",\"email\":\"\",\"password\":\"ivan\"}";
//		Mockito.when(this.authService.login(Mockito.eq(user))).thenReturn(res);
//
//		mockMvc.perform(post("/auth/login")
//						.accept(MediaType.APPLICATION_JSON_VALUE)
//						.contentType(MediaType.APPLICATION_JSON_VALUE)
//						.content(js))
//				.andDo(print())
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.accessToken").value(access))
//				.andExpect(jsonPath("$.refreshToken").value(refresh))
//				.andExpect(jsonPath("$.type").value("Bearer "));
//	}
//
//	void loginAsEmail() throws Exception {
//		JwtRequest user = new JwtRequest("", "aa@aa.com", "ivan");
//
//
//		Mockito.when(this.authService.login(user)).thenThrow(AuthException.class);
//
//		mockMvc.perform(post("/auth/login")
//						.content(new ObjectMapper().writeValueAsString(user)))
//				.andDo(print())
//				.andExpect(status().isOk());
//	}

	@Test
	void anton() throws Exception {
		mockMvc.perform(get("/api/user/anton"))
				.andDo(print())
				.andExpect(status().isOk());
	}
}
