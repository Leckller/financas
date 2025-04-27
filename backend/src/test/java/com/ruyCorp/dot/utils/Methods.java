package com.ruyCorp.dot.utils;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class Methods {

  public static MvcResult postRequest(MockMvc mockMvc, String url, String body, int status) throws Exception {
    return mockMvc.perform(
            MockMvcRequestBuilders.post(url)
                .contentType("application/json")
                .content(body))
        .andExpect(MockMvcResultMatchers.status().is(status))
        .andReturn();
  }

  public static MvcResult getRequest(MockMvc mockMvc, String url, int status) throws Exception {
    return mockMvc.perform(
            MockMvcRequestBuilders.get(url))
        .andExpect(MockMvcResultMatchers.status().is(status))
        .andReturn();
  }

  public static MvcResult patchRequest(MockMvc mockMvc, String url, String body, int status) throws Exception {
    return mockMvc.perform(
            MockMvcRequestBuilders.patch(url)
                .contentType("application/json")
                .content(body))
        .andExpect(MockMvcResultMatchers.status().is(status))
        .andReturn();
  }

  public static MvcResult deleteRequest(MockMvc mockMvc, String url, String body, int status) throws Exception {
    return mockMvc.perform(
            MockMvcRequestBuilders.delete(url)
                .contentType("application/json")
                .content(body))
        .andExpect(MockMvcResultMatchers.status().is(status))
        .andReturn();
  }

}
