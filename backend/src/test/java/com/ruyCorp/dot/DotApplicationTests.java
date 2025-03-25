package com.ruyCorp.dot;

import com.ruyCorp.dot.controller.dto.TokenDto;
import com.ruyCorp.dot.controller.dto.Transaction.TransactionDto;
import com.ruyCorp.dot.controller.dto.User.UserCreationDto;
import com.ruyCorp.dot.repository.entity.User;
import com.ruyCorp.dot.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DotApplicationTests {

	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	MockMvc mockMvc;

	@Test
	public void createTransaction() throws Exception {

		TransactionDto mockedTransact = new TransactionDto("despesa", 100d);

		String body = this.objectMapper.writeValueAsString(mockedTransact);

		MvcResult request = this.mockMvc.perform(MockMvcRequestBuilders
				.post("/transaction")
						.contentType("application/json")
						.header("Authorization", getToken())
						.content(body))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn();

	}

	public String getToken() throws Exception {

		UserCreationDto mockedUserCreate = new UserCreationDto(
				"teste",
				"user-test",
				"teste@teste.com",
				"goodPassword"
		);

		String body = objectMapper.writeValueAsString(mockedUserCreate);

		MvcResult request = this.mockMvc.perform(
				MockMvcRequestBuilders.post("/user"))
				.andReturn();

		String json = request.getResponse().getContentAsString();
		TokenDto token = this.objectMapper.readValue(json, TokenDto.class);

		return token.token();

	}

}
