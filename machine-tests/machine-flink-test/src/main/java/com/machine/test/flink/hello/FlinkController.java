package com.machine.test.flink.hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("base/flink")
public class FlinkController {

    @GetMapping("test")
    public void employeeTime() {
      log.info("================");

    }
}