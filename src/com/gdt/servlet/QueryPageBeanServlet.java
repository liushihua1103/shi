package com.gdt.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdt.bean.PageBean;
import com.gdt.bean.QueryVo;
import com.gdt.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@WebServlet(name = "QueryPageBeanServlet")
public class QueryPageBeanServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        /**
         * 1.接收请求的参数
         * 2.调用service获取pageBean对象
         * 3.把pageBean转成json
         * 4.把json给回调函数
         */
        //1.接收请求的参数
        long currentPage;
        String currentPageStr = request.getParameter("currentPage");
        if (currentPageStr != null && !"".equals(currentPageStr.trim())) {
            currentPage = Long.parseLong(currentPageStr);
        } else {
            currentPage = 1;
        }
        //每页显示条数固定为5
        int rows = 5;
        long total=0;
        Connection conn = JDBCUtils.getConnection();
        String sql ="select count(*) as count from tbl_execl;";
        try {
            Statement stat = conn.createStatement();
            ResultSet resultSet = stat.executeQuery(sql);
            while (resultSet.next()){
                 total = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

       /* List<QueryVo> list = new ArrayList<>();
        QueryVo queryVo1 = new QueryVo(1,"tbl1");
        QueryVo queryVo2 = new QueryVo(2,"tbl2");
        QueryVo queryVo3 = new QueryVo(3,"tbl3");
        list.add(queryVo1);
        list.add(queryVo2);
        list.add(queryVo3);
        PageBean pageBean = new PageBean(6,1,3,2,list);*/

        PageBean pageBean = new PageBean();
        pageBean.setRows(rows);
        pageBean.setCurrentPage(currentPage);
        pageBean.setTotal(total);
        long totalPage = total%rows == 0 ? total/rows : total/rows+1;
        pageBean.setTotalPage(totalPage);
        long index = (currentPage - 1)*rows;
        String sql2="SELECT tbl_name FROM tbl_execl limit ? , ?";

        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        try {
            List<QueryVo> list = queryRunner.query(sql2 , new BeanListHandler<QueryVo>(QueryVo.class),index,rows);
            System.out.println(sql2+","+index+","+rows);
            QueryVo queryVo = list.get(2);
            System.out.println(queryVo.toString());
            pageBean.setList(list);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("异常");
        }


        ObjectMapper om = new ObjectMapper();
        String jsonPageBean = om.writeValueAsString(pageBean);
        //4.把json给回调函数
        System.out.println(jsonPageBean);
        response.getWriter().print(jsonPageBean);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        doGet(request, response);
    }
}
