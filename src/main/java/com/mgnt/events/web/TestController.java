package com.mgnt.events.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

import com.mgnt.events.constants.Routes;

@RestController
public class TestController {
  @GetMapping(Routes.TEST)
  public Map<String, String> test() {
    return Map.of("message", "this is a test");
  }  
}
