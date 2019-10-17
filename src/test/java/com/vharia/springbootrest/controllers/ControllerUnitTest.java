package com.vharia.springbootrest.controllers;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
/**
 * ControllerUnitTest
 */
@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
public class ControllerUnitTest {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testSimpleGetRequest() throws Exception {
        mvc.perform(get("/api/"))
            .andExpect(status().isOk());
    }

    @Test
    public void testRequestQueryParams() throws Exception {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("param1", "value1");
        queryParams.put("param2", "value2");

        StringBuilder queryString = new StringBuilder();
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
            queryString.append(entry.getKey()).append("=").append(entry.getValue());
            queryString.append("&");
        }

        MvcResult mvcResult = mvc.perform(get("/api/queryparams?"+queryString.toString()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.queryParams.[*]", hasSize(queryParams.size()))) 
                    .andExpect(jsonPath("$.queryParams.[*]", hasItem(queryParams.values().iterator().next())))                   
                    .andReturn();

        /* String jsonString = mvcResult.getResponse().getContentAsString();

        Object dataObject = JsonPath.parse(jsonString).read("$.[*].[*]");
        String dataString = dataObject.toString();
        System.out.println(dataString); */            
    }

    @Test
    public void testEmptyQueryParams() throws Exception{
        MvcResult mvcResult = mvc.perform(get("/api/queryparams"))
                    .andDo(print())
                    .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                    .andReturn();
    }

    @Test
    public void testRequestHeaders() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("HeaderName1","HeaderValue1");

        MvcResult mvcResult = mvc.perform(get("/api/requestheaders")
                                .headers(httpHeaders))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.headers.[*]", hasItem("HeaderValue1")))
                                .andReturn();        
    }

    @Test
    public void testFormParams() throws Exception{

        MvcResult mvcResult = mvc.perform(post("/api")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .param("name", "Viral")
            .param("description", "java-developer"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.formParams.[*]", hasItem("Viral")))
            .andExpect(jsonPath("$.formParams.[*]", hasItem("java-developer")))
            .andReturn();               
    }
    
    @Test
    public void testPostRequest() throws Exception{
        Properties prop = new Properties();
        prop.setProperty("name", "viral");
        prop.setProperty("description", "java-developer");

        MvcResult mvcResult = mvc.perform(post("/api")
            .contentType(MediaType.APPLICATION_JSON_VALUE)  
            .characterEncoding("utf-8")          
            .content(objectMapper.writeValueAsString(prop)))           
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.requestBody.[*]", hasItem("viral")))
            .andExpect(jsonPath("$.requestBody.[*]", hasItem("java-developer")))
            .andReturn();               
    }
}