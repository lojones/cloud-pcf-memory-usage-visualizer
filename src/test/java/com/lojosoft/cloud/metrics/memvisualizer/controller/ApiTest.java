package com.lojosoft.cloud.metrics.memvisualizer.controller;

import com.lojosoft.cloud.metrics.memvisualizer.service.DataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(Api.class)
public class ApiTest {

    @MockBean
    private DataService dataService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getZoomableCirclePackingData() throws Exception {

        //jsonpath docn - https://goessner.net/articles/JsonPath
        mockMvc.perform(
                get("/api/zcp")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void getAvailableFoundations() {
    }

    @Test
    public void getFoundation() {
    }
}