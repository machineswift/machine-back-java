package com.machine.test.temp.hello;

import com.machine.sdk.base.tool.UUIDv7;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("temp/hello")
public class HelloController {

    @GetMapping("UUIDv7")
    public void UUIDv7() {
        String id = UUIDv7.generateWithoutDashes();
        log.info(id);
    }
}