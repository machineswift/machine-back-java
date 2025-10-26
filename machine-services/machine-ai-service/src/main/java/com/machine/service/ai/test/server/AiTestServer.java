package com.machine.service.ai.test.server;

import com.machine.client.ai.test.IAiTestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/ai/test")
public class AiTestServer implements IAiTestClient {

}
