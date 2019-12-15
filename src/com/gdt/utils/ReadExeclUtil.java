package com.gdt.utils;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ReadExeclUtil {
    public static String  getExceInfo(  String filePath) throws IOException {
        int lastRowNum = 0;

        //String filePath = "C:\\Users\\shi\\Desktop\\source_tbl.xlsx";
        //String filePath="C:\\Users\\shi\\Desktop\\1231.xls";
        FileInputStream fileInputStream = new FileInputStream(filePath);
        //读取xls文件
      /*  if(filePath.substring(filePath.length())=="s"){
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet sheetAt1 = hssfWorkbook.getSheetAt(1);
        }*/

        //获取我们解析excel表格的对象
        XSSFWorkbook xssfSheets = new XSSFWorkbook(fileInputStream);
        //获取excel的第一个sheet页
        XSSFSheet sheetAt = xssfSheets.getSheetAt(1);
        //获取我们sheet页的最后一行的数字之，说白了就是看这个excel一共有多少行
        lastRowNum = sheetAt.getLastRowNum();


        //  List<Article> articleList = new ArrayList<Article>();
        String source_tbl = "";
        for(int i =0;i<lastRowNum+1;i++) {
            // Article article = new Article();
            //获取我们一行 行的数据
            XSSFRow row = sheetAt.getRow(i);
            //通过我们的row对象，解析里面一个个的字段
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {

                if (j != lastCellNum - 1) {
                    source_tbl = source_tbl+row.getCell(j) + "\t";
                } else {
                    source_tbl = source_tbl+ row.getCell(j) + "\r\n";
                }
            }
            // Article article = new Article();
            //获取我们一行 行的数据
            //   XSSFRow row = sheetAt.getRow(2);
            //通过我们的row对象，解析里面一个个的字段
            // short lastCellNum = row.getLastCellNum();

        }

        fileInputStream.close();
        return source_tbl;
    }
}
