package com.machine.service.data.leaf.server;

import com.machine.client.data.leaf.IDataLeaf4IamCodeClient;
import com.machine.service.data.leaf.service.IDataLeafService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/data/leaf_4_iam")
public class DataLeaf4IamCodeServer implements IDataLeaf4IamCodeClient {

    private static final int DEFAULT_LENGTH = 4;
    private static final int DEFAULT_STEP = 8;

    @Autowired
    private IDataLeafService leafService;

    @Override
    @GetMapping("organization_code")
    public String organizationCode() {
        return generateCode(CodePrefix.ORGANIZATION_CODE);
    }

    @Override
    @GetMapping("role_code")
    public String roleCode() {
        return generateCode(CodePrefix.ROLE_CODE);
    }

    @Override
    @GetMapping("user_code")
    public String userCode() {
        return generateCode(CodePrefix.USER_CODE);
    }

    private String generateCode(CodePrefix codePrefix) {
        return leafService.getKqBatchNo(
                codePrefix.code,
                codePrefix.prefix,
                DEFAULT_LENGTH,
                DEFAULT_STEP,
                codePrefix.description
        );
    }

    private enum CodePrefix {
        ORGANIZATION_CODE("IAM_ORGANIZATION_CODE_", "ORG", "业务组织编码"),
        ROLE_CODE("IAM_COMPANY_ROLE_CODE_", "R", "角色编码"),
        USER_CODE("IAM_USER_CODE_", "USER", "用户编码");

        private final String code;
        private final String prefix;
        private final String description;

        CodePrefix(String code, String prefix,
                   String description) {
            this.code = code;
            this.prefix = prefix;
            this.description = description;
        }
    }
}
