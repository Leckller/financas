package com.ruyCorp.dot;

import com.ruyCorp.dot.controller.dto.TokenDto;
import com.ruyCorp.dot.controller.dto.Transaction.TransactionDto;
import com.ruyCorp.dot.controller.dto.User.UserCreationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DotApplicationTests {

	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	MockMvc mockMvc;

  @Test
  public void listTransactionsTest() throws Exception {

    String token = this.getToken();

    MvcResult request = mockMvc.perform(MockMvcRequestBuilders
            .get("/transaction")
            .contentType("application/json")
            .header("Authorization", token))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

  }

	@Test
	public void createTransaction() throws Exception {

		TransactionDto mockedTransact = new TransactionDto("despesa", 100d);

		String body = this.objectMapper.writeValueAsString(mockedTransact);

    String token = this.getToken();

		MvcResult request = mockMvc.perform(MockMvcRequestBuilders
				.post("/transaction")
						.contentType("application/json")
            .header("Authorization", token)
						.content(body))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn();

    String json = request.getResponse().getContentAsString();
    TransactionDto transaction = objectMapper.readValue(json, TransactionDto.class);

    assertEquals(mockedTransact.amount(), transaction.amount());
    assertEquals(mockedTransact.type(), transaction.type());

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
				MockMvcRequestBuilders.post("/user")
            .contentType("application/json")
            .content(body))
				.andReturn();

		String json = request.getResponse().getContentAsString();
		TokenDto token = this.objectMapper.readValue(json, TokenDto.class);

		return token.token();

	}

}
