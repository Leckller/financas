package com.ruyCorp.dot.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruyCorp.dot.controller.dto.Transaction.CreateTransactionDto;
import com.ruyCorp.dot.controller.dto.Transaction.EditTransactionDto;
import com.ruyCorp.dot.controller.dto.Transaction.TransactionDto;
import com.ruyCorp.dot.controller.dto.Transaction.TransactionListDto;
import com.ruyCorp.dot.repository.entity.Transaction;
import jakarta.transaction.Transactional;
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

import static com.ruyCorp.dot.utils.CreateUser.createUserRequest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@DisplayName("Transaction Valid Tests")
class TransactionValidTests {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	MockMvc mockMvc;

  @Test
	@DisplayName("GET /transaction - Testa se retorna uma lista de transactions")
  public void listTransactionsTest() throws Exception {

    String token = createUserRequest(mockMvc);

		this.createTransaction(token, 100d, "Bet365");
		this.createTransaction(token, 600d, "Salário - primeira parcela");
		this.createTransaction(token, -600d, "Trybe");
		this.createTransaction(token, -45d, "Manicure da minha esposa");
		this.createTransaction(token, -220d, "Remédio para acne");
		this.createTransaction(token, -35d, "Cartão de crédito");
		this.createTransaction(token, 900d, "Salário - segunda parcela");

    MvcResult request = mockMvc.perform(MockMvcRequestBuilders
            .get("/transaction")
            .contentType("application/json")
            .header("Authorization", token))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

		String json = request.getResponse().getContentAsString();
		TransactionListDto transactions = objectMapper.readValue(json, TransactionListDto.class);

		Transaction transactionListed = new Transaction();

		assertThat(transactions.balance()).isEqualTo(700d);
		assertThat(transactions.transactions()).isNotNull().isInstanceOf(List.class);

  }

	@Test
	@DisplayName("DELETE /transaction/{id} - Apaga uma transação e verifica se retorna OK")
	public void deleteTransactionTest() throws Exception {

		String token = createUserRequest(mockMvc);

		MvcResult resultTransaction = this.createTransaction(token, 150d, "SmartWatch");

		String json = resultTransaction.getResponse().getContentAsString();

		TransactionDto transaction = objectMapper.readValue(json, TransactionDto.class);

		String transactionId = Integer.toString(transaction.id());

		mockMvc.perform(MockMvcRequestBuilders
						.delete("/transaction/" + transactionId)
						.header("Authorization", token))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		mockMvc.perform(MockMvcRequestBuilders
						.get("/transaction/" + transactionId)
						.header("Authorization", token))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andReturn();

	}

	@Test
	@DisplayName("POST /transaction - Testa a criação de uma transaction")
	public void createTransactionTest() throws Exception {

		String token = createUserRequest(mockMvc);

		MvcResult response = this.createTransaction(token, 100d, "5 Choppinhos");

		String json = response.getResponse().getContentAsString();
		TransactionDto transaction = objectMapper.readValue(json, TransactionDto.class);

		assertEquals(100d, transaction.amount());
		assertEquals("5 Choppinhos", transaction.name());
		assertEquals(201, response.getResponse().getStatus());

	}

	@Test
	@DisplayName("PATCH /transaction - Testa aedição de uma transação")
	public void editTransactionTest() throws Exception {

		String token = createUserRequest(mockMvc);

		MvcResult createResultTransaction = this.createTransaction(token, 200d, "cachaça");
		String jsonCreateTransaction = createResultTransaction.getResponse().getContentAsString();
		TransactionDto createdTransaction = objectMapper.readValue(jsonCreateTransaction, TransactionDto.class);
		String transactionId = Integer.toString(createdTransaction.id());

		EditTransactionDto editTransaction = new EditTransactionDto(
				createdTransaction.id(), "refrigerante", 100d
		);
		String body = objectMapper.writeValueAsString(editTransaction);


		mockMvc.perform(MockMvcRequestBuilders
						.patch("/transaction")
						.contentType("application/json")
						.header("Authorization", token)
						.content(body))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		MvcResult request = mockMvc.perform(MockMvcRequestBuilders
						.get("/transaction/" + transactionId)
						.header("Authorization", token))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String json = request.getResponse().getContentAsString();
		TransactionDto transaction = objectMapper.readValue(json, TransactionDto.class);

		assertEquals(createdTransaction.id(), transaction.id());
		assertEquals(100d, transaction.amount());
		assertEquals("refrigerante", transaction.name());

	}

	public MvcResult createTransaction(String token, Double amount, String name) throws Exception {

		CreateTransactionDto mockedTransact = new
				CreateTransactionDto(amount, name);

		String body = this.objectMapper.writeValueAsString(mockedTransact);

		return mockMvc.perform(MockMvcRequestBuilders
				.post("/transaction")
						.contentType("application/json")
            .header("Authorization", token)
						.content(body))
				.andReturn();

	}

}
