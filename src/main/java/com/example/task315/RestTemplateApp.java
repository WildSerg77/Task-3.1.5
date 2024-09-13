package com.example.task315;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RestTemplateApp {
    static final String URL = "http://94.198.50.185:7081/api/users";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(URL,
                HttpMethod.GET, entity, String.class);
        List<String> cookies = response.getHeaders().get("Set-Cookie");
        String result = response.getBody();
        System.out.println(result);
        System.out.println("Cookies: " + cookies.toString());

        //Add user
        User user = new User(3L, "James", "Brown", (byte) 55);
        headers.add("Cookie", cookies.get(0));
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL,
                HttpMethod.POST, requestEntity, String.class);
        String code = responseEntity.getBody();
        System.out.println("Add Response: " + code);

        // Update user
        user.setName("Thomas");
        user.setLastName("Shelby");
        requestEntity = new HttpEntity<>(user, headers);
        responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, requestEntity, String.class);
        System.out.println("Update Response: " + responseEntity.getBody());
        code += responseEntity.getBody();

        // Delete user
        responseEntity = restTemplate.exchange(URL + "/" + user.getId(),
                HttpMethod.DELETE, requestEntity, String.class);
        System.out.println("Delete Response: " + responseEntity.getBody());
        code += responseEntity.getBody();
        System.out.println("Total Response Code: " + code);

    }

}
