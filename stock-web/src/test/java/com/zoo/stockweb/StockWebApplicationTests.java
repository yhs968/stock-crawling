package com.zoo.stockweb;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import com.zoo.stockcommon.domain.Stock;
import com.zoo.stockcommon.repository.StockRepository;
import com.zoo.stockweb.controller.FormController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FormController.class)
class StockWebApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  /**
   * Spring ApplicationContext가 제대로 로딩되는지 테스트
   */
  @Test
  void contextLoads() {
  }

  /**
   * Mastering Spring 5.0, Chapter 3, Flow 3 - Controller redirecting to a View with Model
   *
   * @throws Exception
   */
  @Test
  void testGreetingForm() throws Exception {
    mockMvc.perform(get("/stock")
        .accept(MediaType.parseMediaType("application/html;charset=UTF-8"))
    )
        .andExpect(model().attributeExists("stock"))
        .andExpect(view().name("stock"));
  }

  /**
   * Mastering Spring 5.0, Chapter 3, Flow 5 - Controller redirecting to a View with a Form
   *
   * @throws Exception
   */
  @Test
  void testGreetingSubmit() throws Exception {
    String mockStockId = "035720";  // 카카오 주식 종목코드
    String mockStockForm = String.format("{\"stockId\": \"%s\"}", mockStockId);

    mockMvc.perform(
        post("/stock")
            .content(mockStockForm)
            .contentType(MediaType.APPLICATION_JSON)
    )
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("stock"))
        .andExpect(header().string("location", containsString("/stock/" + mockStockId)));
  }

  /**
   * Mastering Spring 5.0, Chapter 5, First REST service, Get method with path variables Integration
   * Testing in Spring: https://www.baeldung.com/integration-testing-in-spring
   *
   * @throws Exception
   */
  @Test
  void testShowStockData() throws Exception {
    String mockStockId = "035720";  // 카카오 주식 종목코드
    String mockStockUrl = String.format("/stock/%s", mockStockId);

    mockMvc.perform(get(mockStockUrl)
        .accept(MediaType.APPLICATION_JSON)
    )
        .andExpect(status().isOk());
  }
}
