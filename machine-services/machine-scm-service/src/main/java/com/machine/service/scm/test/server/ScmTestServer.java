package com.machine.service.scm.test.server;

import com.machine.client.scm.test.IScmTestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/scm/test")
public class ScmTestServer implements IScmTestClient {

}
