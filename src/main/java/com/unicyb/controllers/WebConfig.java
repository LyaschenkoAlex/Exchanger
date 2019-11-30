package com.unicyb.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  // tag::customLoginViewController[]
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("mainPage");
    registry.addViewController("/main_page").setViewName("mainPage");
    registry.addViewController("/login");
  }

}
