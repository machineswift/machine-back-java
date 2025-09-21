package com.machine.service.data.leaf.server;

import com.machine.client.data.leaf.IDataLeaf4ScmCodeClient;
import com.machine.service.data.leaf.service.IDataLeafService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/data/leaf_4_scm")
public class DataLeaf4ScmCodeServer implements IDataLeaf4ScmCodeClient {

    private static final int DEFAULT_LENGTH = 4;
    private static final int DEFAULT_STEP = 8;

    @Autowired
    private IDataLeafService leafService;

    @Override
    @GetMapping("supplier_code")
    public String supplierCode() {
        return generateCode(CodePrefix.SUPPLIER_CODE);
    }

    @Override
    @GetMapping("franchisee_code")
    public String franchiseeCode() {
        return generateCode(CodePrefix.FRANCHISEE_CODE);
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
        SUPPLIER_CODE("SCM_SUPPLIER_CODE_", "GYS", "供应商编码"),
        FRANCHISEE_CODE("SCM_FRANCHISEE_CODE_", "JMS", "加盟商编码");

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
