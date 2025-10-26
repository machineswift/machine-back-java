package com.machine.service.doc.config;

import lombok.SneakyThrows;
import org.jodconverter.core.office.OfficeManager;
import org.jodconverter.local.LocalConverter;
import org.jodconverter.local.office.LocalOfficeManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JodConverterConfig {

    @Value("${jodconverter.local.office-home:/usr/lib/libreoffice}")
    private String officeHome;

    @SneakyThrows
    @Bean(destroyMethod = "stop")
    public OfficeManager officeManager() {
        OfficeManager officeManager = LocalOfficeManager.builder()
                .officeHome(officeHome)
                .portNumbers(2001, 2002, 2003, 2004)
                .maxTasksPerProcess(64)
                .taskExecutionTimeout(3600_000L)
                .taskQueueTimeout(30_000L)
                .build();
        officeManager.start();
        return officeManager;
    }

    @Bean
    public LocalConverter localConverter(OfficeManager officeManager) {
        return LocalConverter.builder()
                .officeManager(officeManager)
                .build();
    }

}
