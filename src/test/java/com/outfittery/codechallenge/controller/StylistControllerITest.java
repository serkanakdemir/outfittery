
package com.outfittery.codechallenge.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class StylistControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreateStylist() throws Exception {
        LocalDate localDate = LocalDate.now();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v1.0/stylist/add").content("{\n"
                + "\t\"name\": \"name\",\n"
                + "\t\"email\": \"sample@sample.com\"\n"
                + "}").contentType(MediaType.APPLICATION_JSON);
        ;
        mockMvc.perform(requestBuilder)
            .andExpect(status().isCreated())
            .andReturn();


    }
}