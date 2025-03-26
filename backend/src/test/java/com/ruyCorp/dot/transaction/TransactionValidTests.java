package com.ruyCorp.dot.transaction;

import com.ruyCorp.dot.controller.dto.TokenDto;
import com.ruyCorp.dot.controller.dto.Transaction.TransactionDto;
import com.ruyCorp.dot.controller.dto.User.UserCreationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Transaction Valid Tests")
class TransactionValidTests {

	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	MockMvc mockMvc;

  @Test
	@DisplayName("Testa se retorna uma lista de transactions")
  public void listTransactionsTest() throws Exception {

    String token = this.createUser();
		TransactionDto transaction = this.createTransaction(token);

    MvcResult request = mockMvc.perform(MockMvcRequestBuilders
            .get("/transaction")
            .contentType("application/json")
            .header("Authorization", token))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

		String json = request.getResponse().getContentAsString();
		List<TransactionDto> transactions = objectMapper
				.readValue(json, new TypeReference<List<TransactionDto>>() {});

		assertThat(transactions).isNotNull().isInstanceOf(List.class);
		assertThat(transactions).containsExactly(transaction);

  }

	@DisplayName("Cria uma transaction")
	public TransactionDto createTransaction(String token) throws Exception {

		TransactionDto mockedTransact = new TransactionDto("despesa", 100d);

		String body = this.objectMapper.writeValueAsString(mockedTransact);

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

		return transaction;

	}

	@DisplayName("Cria um novo usuário")
	public String createUser() throws Exception {

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
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn();

		String json = request.getResponse().getContentAsString();
		TokenDto token = this.objectMapper.readValue(json, TokenDto.class);

		return token.token();

	}

}
