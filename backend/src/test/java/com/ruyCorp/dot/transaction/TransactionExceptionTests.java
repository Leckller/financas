package com.ruyCorp.dot.transaction;

import com.ruyCorp.dot.controller.dto.TokenDto;
import com.ruyCorp.dot.controller.dto.Transaction.TransactionCreationDto;
import com.ruyCorp.dot.controller.dto.Transaction.TransactionDto;
import com.ruyCorp.dot.controller.dto.User.UserCreationDto;
import com.ruyCorp.dot.service.exception.MessageDto;
import com.ruyCorp.dot.utils.CreateUser;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static com.ruyCorp.dot.utils.CreateUser.createUserRequest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Transaction Exceptions Tests")
public class TransactionExceptionTests {

  ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  MockMvc mockMvc;

  @DisplayName("Deve retornar uma exception caso seja passado um tipo inválido")
  @Test
  public void InvalidTypeTransactionTest() throws Exception {

    String exception = "A transação só pode ser do tipo despesa ou receita.";
    TransactionCreationDto transactionDto = new TransactionCreationDto("invalid", 100d);
    String body = objectMapper.writeValueAsString(transactionDto);
    String token = createUserRequest(mockMvc);

    MvcResult result = mockMvc.perform(
        MockMvcRequestBuilders.post("/transaction")
            .contentType("application/json")
            .header("Authorization", token)
            .content(body))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andReturn();

    String json = result.getResponse().getContentAsString();
    MessageDto message = objectMapper.readValue(json, MessageDto.class);

    assertEquals(exception, message.message());

  }

}
