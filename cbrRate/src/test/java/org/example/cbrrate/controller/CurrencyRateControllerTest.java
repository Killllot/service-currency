package org.example.cbrrate.controller;

import org.example.cbrrate.config.ApplicationConfig;
import org.example.cbrrate.config.CbrConfig;
import org.example.cbrrate.config.JsonConfig;
import org.example.cbrrate.parser.CurrencyRateParserXml;
import org.example.cbrrate.requester.CbrRequester;
import org.example.cbrrate.services.CurrencyRateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CurrencyRateController.class)
@Import({ApplicationConfig.class, JsonConfig.class, CurrencyRateService.class, CurrencyRateParserXml.class})
class CurrencyRateControllerTest {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CbrConfig cbrConfig;

    @MockBean
    CbrRequester cbrRequester;

    @Test
    @DirtiesContext
    void getCurrencyRateTest() throws Exception {
        //given
        var currency = "EUR";
        var date = "25-06-2022";
        prepareCbrRequesterMock(date);

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

    @Test
    @DirtiesContext
    void cacheUseTest() throws Exception {
        //given
        prepareCbrRequesterMock(null);

        var currency = "EUR";
        var date = "02-03-2022";

        //when
        mockMvc.perform(get(String.format("/api/currencyRate/%s/%s", currency, date))).andExpect(status().isOk());
        mockMvc.perform(get(String.format("/api/currencyRate/%s/%s", currency, date))).andExpect(status().isOk());

        currency = "USD";
        mockMvc.perform(get(String.format("/api/currencyRate/%s/%s", currency, date))).andExpect(status().isOk());

        date = "03-03-2022";
        mockMvc.perform(get(String.format("/api/currencyRate/%s/%s", currency, date))).andExpect(status().isOk());

        //then
        verify(cbrRequester, times(2)).getRatesAsXml(any());
    }

    private void prepareCbrRequesterMock(String date) throws IOException, URISyntaxException {
        var uri = ClassLoader.getSystemResource("cbr_response.xml").toURI();
        var ratesXml = Files.readString(Paths.get(uri), Charset.forName("Windows-1251"));

        if (date == null) {
            when(cbrRequester.getRatesAsXml(any())).thenReturn(ratesXml);
        } else {
            var dateParam = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            var cbrUrl =  String.format("%s?date_req=%s", cbrConfig.getUrl(), DATE_FORMATTER.format(dateParam));
            when(cbrRequester.getRatesAsXml(cbrUrl)).thenReturn(ratesXml);
        }
    }
}