package com.github.losevod.calculator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.FlashMap;

import java.util.HashMap;
import java.util.Objects;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CalculatorMVCTests {

    @Autowired
    Calculator calculator;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldProperlyExecuteGETRequest() throws Exception{
        mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(content().string(containsString("<form method=\"POST\" id=\"calcForm\" action=\"/\">\n" +
                        "            <input type=\"text\" style=\"height: 20px; width: 90%; margin-bottom: 10px\"\n" +
                        "                   name=\"textValue\" id=\"calcString\" value=\"\"/>\n" +
                        "        </form>")));
    }

    @Test
    public void shouldProperlyExecutePOSTRequest() throws Exception{

        calculator.setTextValue("1+1");

        HashMap<String, Object> sessionAttrs = new HashMap<>();
        sessionAttrs.put("calculator", calculator);

        FlashMap flashMap = mockMvc.perform(
                post("/")
                        .sessionAttrs(sessionAttrs))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getFlashMap();

        assertEquals("2.0", calculator.getTextValue());
    }

}
