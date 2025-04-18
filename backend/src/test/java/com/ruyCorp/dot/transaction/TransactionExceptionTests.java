package com.ruyCorp.dot.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Transaction Exceptions Tests")
public class TransactionExceptionTests {

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  MockMvc mockMvc;

//  @DisplayName("Deve retornar uma exception caso seja passado um tipo inválido")
//  @Test
//  public void InvalidTypeTransactionTest() throws Exception {
//
//    String exception = "A transação só pode ser do tipo despesa ou receita.";
//    TransactionCreationDto transactionDto = new TransactionCreationDto("invalid", 100d);
//    String body = objectMapper.writeValueAsString(transactionDto);
//    String token = createUserRequest(mockMvc);
//
//    MvcResult result = mockMvc.perform(
//        MockMvcRequestBuilders.post("/transaction")
//            .contentType("application/json")
//            .header("Authorization", token)
//            .content(body))
//        .andExpect(MockMvcResultMatchers.status().isBadRequest())
//        .andReturn();
//
//    String json = result.getResponse().getContentAsString();
//    MessageDto message = objectMapper.readValue(json, MessageDto.class);
//
//    assertEquals(exception, message.message());
//
//  }

}
