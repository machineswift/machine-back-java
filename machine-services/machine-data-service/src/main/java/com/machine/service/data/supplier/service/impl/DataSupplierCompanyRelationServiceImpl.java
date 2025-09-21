package com.machine.service.data.supplier.service.impl;

import com.machine.service.data.supplier.dao.IDataSupplierCompanyRelationDao;
import com.machine.service.data.supplier.service.IDataSupplierCompanyRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DataSupplierCompanyRelationServiceImpl implements IDataSupplierCompanyRelationService {

    @Autowired
    private IDataSupplierCompanyRelationDao supplierCompanyRelationDao;

}
