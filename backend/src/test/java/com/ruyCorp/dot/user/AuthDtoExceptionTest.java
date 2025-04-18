package com.ruyCorp.dot.user;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("User exception tests")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthDtoExceptionTest {

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("AUTH-USER-NOT-FOUND: Testa se retorna uma mensagem caso não encontre ou a senha esteja errada")
  public void authUserNotFoundTest() throws Exception {

    String body = objectMapper.writeValueAsString(CreateUser.validUser());

    MvcResult request = mockMvc.perform(
        MockMvcRequestBuilders
            .post("/auth/login")
            .contentType("application/json")
            .content(body))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andReturn();

    String json = request.getResponse().getContentAsString();
    MessageDto response = objectMapper.readValue(json, MessageDto.class);

    MessageDto message = new MessageDto("Usuário ou senha inválidos!");
    assertEquals(message, response);

  }

}
