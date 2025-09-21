package com.machine.service.data.leaf.server;

import com.machine.client.data.leaf.IDataLeaf4HrmCodeClient;
import com.machine.service.data.leaf.service.IDataLeafService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/data/leaf_4_hrm")
public class DataLeaf4HrmCodeServer implements IDataLeaf4HrmCodeClient {

    private static final int DEFAULT_LENGTH = 4;
    private static final int DEFAULT_STEP = 8;

    @Autowired
    private IDataLeafService leafService;

    @Override
    @GetMapping("leaf_4_hrm")
    public String shopEmployeeCode() {
        return generateCode(CodePrefix.SHOP_EMPLOYEE_CODE);
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
        SHOP_EMPLOYEE_CODE("HRM_SHOP_EMPLOYEE_CODE_", "SE", "门店员工编码");

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
