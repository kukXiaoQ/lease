package com.situ.ws.config.listener;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.situ.ws.bean.entries.EasyObj;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EasyXlsxListener implements ReadListener<EasyObj> {

    /**
     * 读取到每一个对象，都会调用invoke方法
     */
    @Override
    public void invoke(EasyObj easyObj, AnalysisContext analysisContext) {
        System.out.println(easyObj);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("读取完毕");
    }

    public static void main(String[] args) {
        ExcelReaderBuilder builder = EasyExcel.read("D:\\WPS Office\\py\\xlsx\\easy.xlsx", EasyObj.class, new EasyXlsxListener());
        builder.sheet(0).doRead();
    }
}
