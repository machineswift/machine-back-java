package com.machine.service.data.supplier.server;

import com.machine.client.data.supplier.IDataSupplierCompanyRelationClient;
import com.machine.service.data.supplier.service.IDataSupplierCompanyRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/data/supplier_company_relation")
public class DataSupplierCompanyRelationServer implements IDataSupplierCompanyRelationClient {

    @Autowired
    private IDataSupplierCompanyRelationService supplierCompanyRelationService;

}
