package com.vharia.springbootrest.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.vharia.springbootrest.exceptions.BadRequestException;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestController
 */
@RestController
@RequestMapping("/api")
public class Controller {
    @GetMapping
    public ResponseEntity<String> getRequest(){
        return ResponseEntity.status(HttpStatus.OK).body("Hello world!!");
    }

    @GetMapping("/queryparams")
    public ResponseEntity<Map<String, Properties>> getRequestQueryParams(HttpMethod requestType, @RequestParam Map<String, String> requestParams) {
        if(requestParams.size() == 0){
            throw (new BadRequestException("send at least 1 query parameter."));            
        }

        Map<String, Properties> resultMap = new HashMap<>(1);
        resultMap.put("queryParams", getPropertiesObjectfromMap(requestParams));        
        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Map<String, Properties>> getFormParams(@RequestParam Map<String, String> requestParams){
        if(requestParams.size() == 0){
            throw (new BadRequestException("send at least 1 form parameter."));            
        }

        Map<String, Properties> resultMap = new HashMap<>(1);
        resultMap.put("formParams", getPropertiesObjectfromMap(requestParams));

        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }

    private Properties getPropertiesObjectfromMap(Map<String,String> input){
        Properties props = new Properties();
        input.forEach((k,v) -> props.setProperty(k, v));
        return props;
    }

/*     @GetMapping("/pathparams/{pathParamName}/{optional1 : (/optional1)?}")
    public ResponseEntity<String> getPathParams(@PathVariable("pathParamName") String pathVariable,
                                                @PathVariable("optional1") String optional1){
        return ResponseEntity.status(HttpStatus.OK).body("Path parameter 1 - "+pathVariable+", optional path param - "+optional1);
    } */

    @GetMapping("/pathparams/{pathParamName}/")
    public ResponseEntity<String> getPathParams(@PathVariable("pathParamName") String pathVariable){
        return ResponseEntity.status(HttpStatus.OK).body("Path parameter 1 - "+pathVariable);
    }

    @GetMapping("/requestheaders")
    public ResponseEntity<Map<String, Properties>> getRequestHeaders(@RequestHeader Map<String,String> requestHeaders){
        Map<String, Properties> resultMap = new HashMap<>(1);
        Properties props = new Properties();
        requestHeaders.forEach((k,v) -> props.setProperty(k, v));
        resultMap.put("headers", props);
        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getRequestBodyJson(@RequestBody Object requestBody){
        Map<String, Object> resultMap = new HashMap<>(1);
        resultMap.put("requestBody", requestBody);
        return ResponseEntity.status(HttpStatus.OK).body(resultMap);
    }

}