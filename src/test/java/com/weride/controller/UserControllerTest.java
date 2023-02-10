package com.weride.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegister() throws Exception{
        String user = "{\"email\":\"123@ucsd.edu\",\"password\":\"123456\"}";
        var response = this.mockMvc.perform(post("/api/user-service/register").contentType(MediaType.APPLICATION_JSON).content(user)).andReturn().getResponse();
                assertEquals(HttpStatus.OK.value(),response.getStatus());
    }

}