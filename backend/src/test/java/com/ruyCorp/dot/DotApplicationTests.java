package com.ruyCorp.dot;

import com.ruyCorp.dot.controller.AuthController;
import com.ruyCorp.dot.controller.UserController;
import com.ruyCorp.dot.controller.dto.TokenDto;
import com.ruyCorp.dot.controller.dto.User.UserCreationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.org.yaml.snakeyaml.tokens.Token;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@ActiveProfiles("test")
class DotApplicationTests {

	@Autowired
	UserController userController;

	@Autowired
	MockMvc mockMvc;

	ObjectMapper objectMapper;

	@Test
	@DisplayName("Cria uma transaction")
	public void userTransactionTest() throws Exception {
		TokenDto token = this.register();
	}

	public TokenDto register() throws Exception {
		UserCreationDto userDto = new UserCreationDto(
				"teste",
				"user-test",
				"teste@teste.com",
				"password"
		);

		String body = objectMapper.writeValueAsString(userDto);

		MvcResult request = mockMvc
				.perform(
						MockMvcRequestBuilders.post("/user")
								.contentType("application/json")
								.content(body)
				).andReturn();

		String json = request.getResponse().getContentAsString();
		TokenDto token = objectMapper.readValue(json, TokenDto.class);

		return token;

	}

}
