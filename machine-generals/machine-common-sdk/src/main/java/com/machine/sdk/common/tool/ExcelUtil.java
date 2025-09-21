package com.machine.sdk.common.tool;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import cn.idev.excel.read.metadata.ReadSheet;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelUtil {

    /**
     * 读取Excel多个sheet页的数据
     * @param inputStream Excel文件输入流
     * @param sheetClasses 每个sheet页对应的类对象, key为sheet索引(从0开始), value为对应的类
     * @return 返回每个sheet页读取的对象列表, key为sheet索引, value为对应的对象列表
     */
    public static Map<Integer, List<?>> readMultiSheet(InputStream inputStream,
                                                       Map<Integer, Class<?>> sheetClasses) {
        Map<Integer, List<?>> result = new HashMap<>();

        try {
            // 创建ExcelReader对象
            EasyExcel.read(inputStream)
                    .registerReadListener(new MultiSheetListener(result))
                    .build()
                    .read(createSheets(sheetClasses));
        } catch (Exception e) {
            log.error("读取Excel失败", e);
            throw new RuntimeException("读取Excel失败", e);
        }

        return result;
    }

    private static List<ReadSheet> createSheets(Map<Integer, Class<?>> sheetClasses) {
        List<ReadSheet> sheets = new ArrayList<>();

        sheetClasses.forEach((sheetNo, clazz) -> {
            ReadSheet readSheet = EasyExcel.readSheet(sheetNo)
                    .head(clazz)
                    .build();
            sheets.add(readSheet);
        });

        return sheets;
    }

    private static class MultiSheetListener extends AnalysisEventListener<Object> {
        private final Map<Integer, List<?>> sheetData;
        private List<Object> currentSheetData;

        public MultiSheetListener(Map<Integer, List<?>> sheetData) {
            this.sheetData = sheetData;
        }

        @Override
        public void invoke(Object data, AnalysisContext context) {
            if (currentSheetData == null) {
                currentSheetData = new ArrayList<>();
            }
            currentSheetData.add(data);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            if (currentSheetData != null) {
                sheetData.put(context.readSheetHolder().getSheetNo(), currentSheetData);
                currentSheetData = null;
            }
        }
    }
}
