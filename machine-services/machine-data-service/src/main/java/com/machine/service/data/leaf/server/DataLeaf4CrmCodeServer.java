package com.machine.service.data.leaf.server;

import com.machine.client.data.leaf.IDataLeaf4CrmCodeClient;
import com.machine.client.data.leaf.IDataLeaf4IamCodeClient;
import com.machine.service.data.leaf.service.IDataLeafService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/data/leaf_4_crm")
public class DataLeaf4CrmCodeServer implements IDataLeaf4CrmCodeClient {

    private static final int DEFAULT_LENGTH = 4;
    private static final int DEFAULT_STEP = 8;

    @Autowired
    private IDataLeafService leafService;

    @Override
    @GetMapping("customer_code")
    public String customerCode() {
        return generateCode(CodePrefix.CUSTOMER_CODE);
    }

    @Override
    @GetMapping("member_code")
    public String memberCode() {
        return generateCode(CodePrefix.MEMBER_CODE);
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
        CUSTOMER_CODE("CRM_CUSTOMER_CODE_", "C", "客户编码"),
        MEMBER_CODE("CRM_MEMBER_CODE_", "M", "会员编码");

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
