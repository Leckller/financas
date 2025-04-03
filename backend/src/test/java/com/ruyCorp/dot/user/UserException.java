package com.ruyCorp.dot.user;

import com.ruyCorp.dot.controller.dto.User.UserCreationDto;
import com.ruyCorp.dot.service.exception.MessageDto;
import com.ruyCorp.dot.utils.CreateUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
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
@DisplayName("User exception tests")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserException {

  ObjectMapper  objectMapper = new ObjectMapper();

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("USER-ALREADY-EXISTS: Testa se retorna um bad request" +
      " ao tentar cadastrar um usuário já existente ")
  public void userAlreadyExistsTest() throws Exception {
    // Cria um usuário
    String token = CreateUser.createUserRequest(mockMvc);

    // Cria um novo usuário com os mesmos dados
    UserCreationDto mockedUserCreate = CreateUser.validUser();

    String body = objectMapper.writeValueAsString(mockedUserCreate);

    // Espera que retorne um status 403 e uma mensagem
    MvcResult request = mockMvc.perform(
            MockMvcRequestBuilders.post("/user")
                .contentType("application/json")
                .content(body))
        .andExpect(MockMvcResultMatchers.status().isForbidden())
        .andReturn();

    String json = request.getResponse().getContentAsString();
    MessageDto message = objectMapper.readValue(json, MessageDto.class);

    assertEquals("Este usuário já existe!", message.message());

  }

  @Test
  @DisplayName("USER-INVALID-FIELDS: Testa as validações do json enviado na hora da criação")
  public void userCreateInvalidJsonFields() throws Exception {
    String badPassword = objectMapper.writeValueAsString(CreateUser.badPassword());
    String emptyJsonValues = objectMapper.writeValueAsString(CreateUser.emptyJsonValues());
    String badBudget = objectMapper.writeValueAsString(CreateUser.badBudget());
    String badEmail = objectMapper.writeValueAsString(CreateUser.badEmail());

    MvcResult passwordRequest = this.UserPostRequest(badPassword);
    MvcResult emptyRequest = this.UserPostRequest(emptyJsonValues);
    MvcResult budgetRequest = this.UserPostRequest(badBudget);
    MvcResult emailRequest = this.UserPostRequest(badEmail);

    // Só preciso verificar o status, as mensagens são da biblioteca e já são testadas
    assertEquals(400, passwordRequest.getResponse().getStatus());
    assertEquals(400, emptyRequest.getResponse().getStatus());
    assertEquals(400, budgetRequest.getResponse().getStatus());
    assertEquals(400, emailRequest.getResponse().getStatus());

  }

  @DisplayName("User request")
  public MvcResult UserPostRequest(String body) throws Exception {
    return mockMvc.perform(
            MockMvcRequestBuilders.post("/user")
                .contentType("application/json")
                .content(body))
        .andReturn();
  }

}
