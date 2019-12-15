package com.gdt.servlet;

import com.gdt.utils.JDBCUtils;
import com.gdt.utils.ReadExeclUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@WebServlet(name = "FileUploadServlet01")
public class FileUploadServlet01 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // 1.创建磁盘项工厂对象 DiskFileItemFactory
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 2.创建核心解析类 ServletFileUpload (传递 磁盘项工厂对象)
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 3.核心解析类解析request对象,调用parseRequest(request)===> List<FileItem>
            List<FileItem> list = upload.parseRequest(request);
            // 4.循环List<FileItem>
            for (FileItem item : list) {
                // 4.1判断是否为普通项  isFormFiled  true
                if (item.isFormField()) {
                    //  获取name属性值getFormFiled()    获取value值  getString()
                    String fieldName = item.getFieldName();
                    String value = item.getString();
                    System.out.println(fieldName + " , " + value);
                } else {
                    //获取上传项文件的名称打印
                    String fileName = item.getName();
                    System.out.println(fileName);
                    //把文件通过io写到G:developer/upload目录下
                    //获取输入流 (流中有上传项的数据)
                    InputStream inputStream = item.getInputStream();
                    String path="D:\\developer7/fileupload/"+fileName;
                    //创建一个输出流
                    File filePath = new File(path);
                    FileOutputStream outputStream =
                            new FileOutputStream(filePath);
                    //io的读写操作
                    int len = 0;
                    byte[] arr = new byte[1024 << 2];  // 1024*2^2
                    while ((len = inputStream.read(arr)) != -1) {
                        outputStream.write(arr, 0, len);
                    }
                    String exceInfo = ReadExeclUtil.getExceInfo(path);
                    String[] split = exceInfo.split("\r\n");
                    DataSource dataSource = JDBCUtils.getDataSource();
                    Connection connection=null;
                    Statement statement = null;
                    try {
                         connection = dataSource.getConnection();
                         statement = connection.createStatement();

                        for (String row : split) {

                       String[] cell = row.split("\t");

                        String system = cell[0];
                        String tblName = cell[1];
                        String field="";
                        for (int i = 2; i < cell.length; i++) {
                            field=field+cell[i]+" ";
                        }

                        String sql ="insert into tbl_execl values ('"+system+"','"+tblName+"','"+field+"');";
                        statement.execute(sql);
                       System.out.println(system+", "+tblName+" ,"+field);
                    }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        statement.close();
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    //关闭资源
                    outputStream.close();
                    inputStream.close();
                   // response.sendRedirect("show.html");
                    response.getWriter().write("<script language='javascript'>alert('添加成功！');</script>");
                    request.getRequestDispatcher("fileupload.html").forward(request,response);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);

    }
}