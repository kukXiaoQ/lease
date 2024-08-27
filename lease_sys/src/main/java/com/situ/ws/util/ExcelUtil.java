package com.situ.ws.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.LinkedList;
import java.util.List;

public class ExcelUtil {

    /**
     *  批量导出数据
     *  pathName 文件路径（需指定文件名）
     *  clazz 实体类字节码文件（表头根据实体类字段生成）
     *  sheetName 工作表名称
     *  list 传入的数据
     */
    public static void exportData(String pathName, Class clazz, String sheetName, List list){
        EasyExcel.write(pathName).head(clazz).sheet(sheetName).doWrite(list);
    }


    /*
     * 批量插入数据的方法
     *
     * object 实体类
     * filePath 文件路径
     * clazz 实体类字节码文件
     * sheetName 读取的工作表名称
     */
    public static <object> List<object> importData(Object object, String filePath, Class clazz, String sheetName) {
        List<object> list = new LinkedList<>();
        EasyExcel.read(filePath).head(clazz)
                .sheet(sheetName).registerReadListener(new AnalysisEventListener<object>() {
                    @Override
                    public void invoke(Object object, AnalysisContext analysisContext) {
                        //读取一行数据触发的方法
                        list.add((object) object);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                        //数据读取完毕触发的方法
                        System.out.println("数据读取完毕");
                    }
                }).doRead();

        return list;
    }
}
