
package com.outfittery.codechallenge.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class SlotControllerITest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        jdbcTemplate.execute("DELETE FROM STYLIST_SLOT");
        jdbcTemplate.execute("DELETE FROM SLOT");
        jdbcTemplate.execute("DELETE FROM STYLIST");
        jdbcTemplate.execute("DELETE FROM SLOT_DEFINITION");
        jdbcTemplate.update("insert into SLOT_DEFINITION values (1,30,null)");

    }

    @Test
    public void shouldReturnAvailableSlot() throws Exception {
        LocalDate localDate = LocalDate.now();

        jdbcTemplate.getDataSource().getConnection();

        jdbcTemplate
            .update("insert into STYLIST (id, name, email,state ) values (1,'name','sample@sample.com','ACTIVE')");
        jdbcTemplate.update(
            "insert into SLOT (id,slot_definition_id, start, finish, date ) values (?,?,?,?,?)", 1, 1, "10:30", "11:00",
            localDate);
        jdbcTemplate
            .update("insert into STYLIST_SLOT (id,slot_id, stylist_id,  customer_id, version) values (1,1,1,null ,1 )");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/api/v1.0/slot/available-slots?date=" + localDate.format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        final MvcResult mvcResult = mockMvc.perform(requestBuilder)
            .andExpect(status().isOk())
            .andExpect(content().json(
                "[{\"id\":1,\"slotDefinition\":{\"id\":1,\"duration\":30,\"validUntil\":null},\"start\":\"10:30:00\",\"finish\":\"11:00:00\",\"date\":\""
                    + localDate + "\"}]"))
            .andReturn();

    }


    @Test
    public void shouldMakeAnAppointment() throws Exception {
        LocalDate localDate = LocalDate.now();

        jdbcTemplate.getDataSource().getConnection();

        jdbcTemplate
            .update("insert into STYLIST (id, name, email,state ) values (1,'name','sample@sample.com','ACTIVE')");
        jdbcTemplate.update(
            "insert into SLOT (id,slot_definition_id, start, finish, date ) values (?,?,?,?,?)", 1, 1, "10:30", "11:00",
            localDate);
        jdbcTemplate
            .update("insert into STYLIST_SLOT (id,slot_id, stylist_id,  customer_id, version) values (1,1,1,null ,1 )");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/api/v1.0/slot/appointment?customerId=1&slotId=1");
        final MvcResult mvcResult = mockMvc.perform(requestBuilder)
            .andExpect(status().isCreated())
            .andExpect(content().json(
                "{{\"id\":1,\"slot\":{\"id\":1,\"slotDefinition\":{\"id\":1,\"duration\":30,\"validUntil\":null},\"start\":\"10:30:00\",\"finish\":\"11:00:00\",\"date\":\""
                    + localDate
                    + "\"},\"stylist\":{\"id\":1,\"name\":\"name\",\"email\":\"sample@sample.com\",\"state\":\"ACTIVE\"},\"customerId\":1,\"version\":2}}"))
            .andReturn();


    }
}