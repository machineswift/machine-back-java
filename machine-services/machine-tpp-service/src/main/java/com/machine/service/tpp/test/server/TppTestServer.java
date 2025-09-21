package com.machine.service.tpp.test.server;

import com.machine.client.tpp.test.ITppTestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/tpp/test")
public class TppTestServer implements ITppTestClient {

}
