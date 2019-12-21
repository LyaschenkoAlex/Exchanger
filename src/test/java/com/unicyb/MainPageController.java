package com.unicyb;

import com.unicyb.repositories.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(secure=false)
public class MainPageController {
    @Autowired
    private MockMvc mockMvc;
    //       Most of these mocks are here to avoid autowiring issues. They aren't
    //       actually used in the course of the home page test, so their behavior
    //       isn't important. They just need to exist so autowiring can take place.
    @MockBean
    private CourseRepository courseRepository;
    @MockBean
    private BankRepository bankRepository;
    @MockBean
    private ExchangeHistoryRepository exchangeHistoryRepository;
    @MockBean
    private MonthlyRatingRepository monthlyRatingRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/main_page"))
                .andExpect(status().isOk())
                .andExpect(view().name("mainPage"))
                .andExpect(content().string(
                        containsString("Exchanger")));
    }
}
