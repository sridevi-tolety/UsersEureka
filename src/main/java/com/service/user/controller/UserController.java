package com.service.user.controller;
​
import java.util.HashMap;​
import java.util.Map;​
​
import org.json.JSONException;​
import org.json.JSONObject;​
import org.springframework.beans.factory.annotation.Autowired;​
import org.springframework.context.annotation.Bean;​
import org.springframework.http.HttpEntity;​
import org.springframework.http.HttpHeaders;​
import org.springframework.http.HttpMethod;​
import org.springframework.http.MediaType;​
import org.springframework.http.ResponseEntity;​
import org.springframework.util.LinkedMultiValueMap;​
import org.springframework.util.MultiValueMap;​
import org.springframework.web.bind.annotation.GetMapping;​
import org.springframework.web.bind.annotation.PathVariable;​
import org.springframework.web.bind.annotation.RequestMapping;​
import org.springframework.web.bind.annotation.RequestParam;​
import org.springframework.web.bind.annotation.RestController;​
import org.springframework.web.client.RestTemplate;​
import org.springframework.web.util.UriComponentsBuilder;​
​
@RestController​
@RequestMapping("/users")​
public class UserController {​
​
@Autowired​
RestTemplate restTemplate;​
​
@GetMapping("")​
public String getUserOrders() {​
​
final String uri = "http://localhost:8081/demo/orders";​
​
String result = restTemplate.getForObject(uri, String.class);​
​
return result;​
​
}​
​
@GetMapping("/{userId}")​
public String getUserOrdersById(@PathVariable String userId) {​
​
//final String uri = "http://localhost:8081/demo/orders/" + userId;​
final String uri = "http://orders-service/demo/orders/" + userId;​
​
String result = restTemplate.getForObject(uri, String.class);​
​
return result;​
​
}​
​
@GetMapping("/byparam")​
public String getUserOrdersByReqParam(@RequestParam String userId) {​
​
final String uri = "http://localhost:8081/demo/orders/byparam";​
​
HttpHeaders headers = new HttpHeaders();​
headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);​
​
Map<String, String> params = new HashMap<String, String>();​
params.put("userId", userId);​
​
UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);​
for (Map.Entry<String, String> entry : params.entrySet()) {​
builder.queryParam(entry.getKey(), entry.getValue());​
}​
​
String result = restTemplate.getForObject(builder.toUriString(), String.class);​
​
return result;​
​
}​
​
@GetMapping("/postparam")​
public ResponseEntity<String> testPostWithParam(@RequestParam String userId) {​
​
String uri = "http://localhost:8081/demo/orders/byparam";​
​
HttpHeaders headers = new HttpHeaders();​
headers.setContentType(MediaType.APPLICATION_JSON);​
​
MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();​
// map.add("userId", userId);​
​
HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(map, headers);​
​
ResponseEntity<String> response = restTemplate.postForEntity(uri, httpEntity, String.class);​
​
System.out.println(response);​
return response;​
}​
​
@GetMapping("/bybody")​
public ResponseEntity<String> testReqBody() throws JSONException {​
​
String uri = "http://localhost:8081/demo/orders/bybody";​
​
HttpHeaders headers = new HttpHeaders();​
headers.setContentType(MediaType.APPLICATION_JSON);​
​
JSONObject request = new JSONObject();​
request.put("id", 110);​
request.put("des", "test10");​
​
HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);​
​
ResponseEntity<String> response = restTemplate.postForEntity(uri, entity, String.class);​
​
System.out.println(response);​
return response;​
​
}​
​
@GetMapping("/bybody2")​
public ResponseEntity<String> testReqBody2() throws JSONException {​
​
String uri = "http://localhost:8081/demo/orders/bybody";​
​
HttpHeaders headers = new HttpHeaders();​
headers.setContentType(MediaType.APPLICATION_JSON);​
​
​
JSONObject request = new JSONObject();​
request.put("id", 1122);​
request.put("des", "usk1");​
​
​
HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);​
​
ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);​
return response;​
​
}​
​
@Bean​
public RestTemplate restTemplate() {​
return new RestTemplate();​
}​
​
}​