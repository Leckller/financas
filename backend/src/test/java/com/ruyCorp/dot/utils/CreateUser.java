package com.ruyCorp.dot.utils;

import com.ruyCorp.dot.controller.dto.TokenDto;
import com.ruyCorp.dot.controller.dto.User.UserCreationDto;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class CreateUser {

  @DisplayName("Cria um novo usuário")
  public static String createUserRequest(MockMvc mockMvc) throws Exception {
    ObjectMapper  objectMapper = new ObjectMapper();
    UserCreationDto mockedUserCreate = new UserCreationDto(
        "teste",
        "user-test",
        "teste@teste.com",
        1500d,
        "goodPassword"
    );

    String body = objectMapper.writeValueAsString(mockedUserCreate);

    MvcResult request = mockMvc.perform(
            MockMvcRequestBuilders.post("/user")
                .contentType("application/json")
                .content(body))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andReturn();

    String json = request.getResponse().getContentAsString();
    TokenDto token = objectMapper.readValue(json, TokenDto.class);

    return token.token();

  }

}
