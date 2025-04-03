package com.ruyCorp.dot.transaction;

import com.ruyCorp.dot.controller.dto.TokenDto;
import com.ruyCorp.dot.controller.dto.Transaction.TransactionCreationDto;
import com.ruyCorp.dot.controller.dto.Transaction.TransactionDto;
import com.ruyCorp.dot.controller.dto.Transaction.TransactionListDto;
import com.ruyCorp.dot.controller.dto.User.UserCreationDto;
import com.ruyCorp.dot.repository.entity.Transaction;
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

import static com.ruyCorp.dot.utils.CreateUser.createUserRequest;
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

    String token = createUserRequest(mockMvc);
		TransactionDto transaction = this.createTransaction(token);

    MvcResult request = mockMvc.perform(MockMvcRequestBuilders
            .get("/transaction")
            .contentType("application/json")
            .header("Authorization", token))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

		String json = request.getResponse().getContentAsString();
		TransactionListDto transactions = objectMapper
				.readValue(json, TransactionListDto.class);

		Transaction transactionListed = new Transaction();

		assertThat(transactions.balance()).isEqualTo(1400d);
		assertThat(transactions.transactions()).isNotNull().isInstanceOf(List.class);

  }

	@DisplayName("Cria uma transaction")
	public TransactionDto createTransaction(String token) throws Exception {

		TransactionCreationDto mockedTransact = new
				TransactionCreationDto("despesa", 100d);

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
		assertEquals(1400d, transaction.balance());

		return transaction;

	}

}
