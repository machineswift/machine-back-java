package com.machine.service.data.supplier.service.impl;

import com.machine.service.data.supplier.dao.ISupplierCompanyRelationDao;
import com.machine.service.data.supplier.service.ISupplierCompanyRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SupplierCompanyRelationServiceImpl implements ISupplierCompanyRelationService {

    @Autowired
    private ISupplierCompanyRelationDao supplierCompanyRelationDao;

}
