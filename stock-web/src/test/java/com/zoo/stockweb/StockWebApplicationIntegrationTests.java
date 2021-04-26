package com.zoo.stockweb;


import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Mastering Spring 5.0, Chapter 5, Creating a todo resource, Retrieving a Todo list
 */
@SpringBootTest(classes = StockWebApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class StockWebApplicationIntegrationTests {

  private static final String LOCALHOST = "http://localhost:";

  @LocalServerPort
  private int port;

  private TestRestTemplate template = new TestRestTemplate();

  /**
   * https://www.baeldung.com/rest-template#exchange_post-1
   * @throws Exception
   */
  @Test
  void testStockApplication() throws Exception {
    // "/stock" form 접속 테스팅
    String homeUrl = LOCALHOST + port + "/stock";
    ResponseEntity<String> connectionResponse = template
        .getForEntity(homeUrl, String.class);
    assertThat(connectionResponse.getStatusCode(), equalTo(HttpStatus.OK));

    // "/stock" form submit -> "/stock/{code}"로 redirect해서 response 받기
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    String stockId = "035720";  // 카카오 주식 종목코드
    map.add("code", stockId);
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
    ResponseEntity<String> submitResponse = template.postForEntity(homeUrl, request, String.class);
    assertThat(submitResponse.getStatusCode(), is(HttpStatus.CREATED));

  }

}
