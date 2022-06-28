package org.example.cbrrate.controller;

import org.example.cbrrate.config.CbrConfig;
import org.example.cbrrate.requester.CbrRequester;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {org.example.cbrrate.CbrRateApplication.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@AutoConfigureMockMvc
public class CurrencyRateControllerConnectToCbr {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Autowired
    MockMvc mockMvc;

    @Test
    @DirtiesContext
    void getCurrencyRateTest() throws Exception {
        //given
        var currency = "EUR";
        var date = "25-06-2022";

        //when
        var result = mockMvc.perform(get(String.format("/api/currencyRate/%s/%s", currency, date)))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/currencyRate/%s/%s", currency, date))
                )
                .andDo(print())
                .andExpect(status().isOk());

        System.out.println(result);
        assertThat(result).isEqualTo("{\"numCode\":\"978\",\"charCode\":\"EUR\",\"nominal\":\"1\",\"name\":\"Евро\",\"value\":\"55,9640\"}");
    }
}
