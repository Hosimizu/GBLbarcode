package com;

import java.sql.*;

public class MysqlJdbcUtil {
    //数据库连接对象
    private static Connection conn = null;

    private static String driver = "com.mysql.jdbc.Driver"; //驱动

    private static String url = "jdbc:mysql://120.79.72.226:3306/sm_ems?useUnicode=true&amp;characterEncoding=UTF-8"; //连接字符串

    private static String username = "root"; //用户名

    private static String password = "Gbl#12345"; //密码

    public static void main(String[] args) throws Exception {
    	/*MysqlJdbcUtil. test = new MysqlJdbcUtil.();
    	while(true){
    		  System.out.println("线程开始....................");
    		  test.getConn();
    	      test.query("select VINCODE from gamcsales.v_vehicle_stock WHERE CREATETIME >= to_date('2019-08-01 14:33:36','yyyy-MM-dd hh24:mi:ss')", true);       
    	      System.out.println("线程开始....................");
    	      Thread.sleep(2000);
    	} */
    }


    private static MysqlJdbcUtil mysqlJdbcUtil = null;

    public static MysqlJdbcUtil instance() {
        if (null == mysqlJdbcUtil) {
            mysqlJdbcUtil = new MysqlJdbcUtil();
        }
        return mysqlJdbcUtil;
    }

    // 获得连接对象
    private synchronized Connection getConn() {
        if (conn == null) {
            try {
                Class.forName(driver);
                DriverManager.setLoginTimeout(6);
                conn = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    //执行查询语句
    public void query(String sql, boolean isSelect) throws SQLException {
        PreparedStatement pstmt;

        try {
            pstmt = getConn().prepareStatement(sql);
            //建立一个结果集，用来保存查询出来的结果
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String VINCODE = rs.getString("VINCODE");
                System.out.println("VINCODE = " + VINCODE);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void query(String sql) throws SQLException {
        PreparedStatement pstmt;
        pstmt = getConn().prepareStatement(sql);
        pstmt.execute();
        pstmt.close();
    }


    //关闭连接
    public void close() {
        try {
            getConn().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //下线
    public PrintBean getPringBean(String vincode, String type) {
        PrintBean printBean = new PrintBean();
        printBean.setVincode(vincode);
        String sql = "";
        if ("下线".equals(type)) {
            sql = "SELECT " +
                    "rd.vincode, " +
                    "b.name AS binCode, " +
                    "DATE_FORMAT(rd.receive_date, '%Y-%m-%d %H:%i:%s') AS receiveDate, " +
                    "i.name AS productName " +
                    "FROM wms_f_receive_detail rd " +
                    "INNER JOIN wms_f_vehicle v ON (rd.vincode = v.vincode) " +
                    "LEFT JOIN basic_item_b i ON (v.material_code = i.xid) " +
                    "LEFT JOIN wms_d_bin b ON (rd.dest_bin_id = b.id) " +
                    "WHERE  " +
                    "rd.vincode = '" + vincode + "' LIMIT 0,1";
        } else if ("在库".equals(type)) {
            sql = "SELECT " +
                    "inv.vincode, " +
                    "b.name AS binCode, " +
                    "DATE_FORMAT(inv.receive_in_date, '%Y-%m-%d %H:%i:%s') AS receiveDate, " +
                    "i.name AS productName " +
                    "FROM wms_f_inventory inv " +
                    "INNER JOIN wms_f_vehicle v ON (inv.vincode = v.vincode) " +
                    "LEFT JOIN basic_item_b i ON (v.material_code = i.xid) " +
                    "LEFT JOIN wms_d_bin b ON (inv.bin_id = b.id) " +
                    "WHERE  " +
                    "inv.vincode = '" + vincode + "' LIMIT 0,1";
        } else if ("出库".equals(type)) {
            sql = "SELECT  " +
                    "inv.vincode, " +
                    "b.name AS binCode, " +
                    "i.name AS productName, " +
                    "ss.dest_name AS destName " +
                    "FROM wms_f_inventory inv " +
                    "INNER JOIN wms_f_vehicle v ON (inv.vincode = v.vincode) " +
                    "INNER JOIN wms_f_ship_sn ss ON (inv.vincode = ss.vincode) " +
                    "LEFT JOIN basic_item_b i ON (v.material_code = i.xid) " +
                    "LEFT JOIN wms_f_move m ON (m.move_type ='D' AND inv.vincode = m.vincode) " +
                    "LEFT JOIN wms_d_bin b ON (m.dest_bin_id = b.id) " +
                    "WHERE  " +
                    "ss.state_flag < '03' AND " +
                    "inv.vincode = '" + vincode +
                    "' ORDER BY ss.id DESC LIMIT 0,1 ";
        }

        PreparedStatement pstmt;
        try {
            pstmt = getConn().prepareStatement(sql);
            //建立一个结果集，用来保存查询出来的结果
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String binCode = rs.getString("binCode");

                String productName = rs.getString("productName");
                printBean.setBinCode(binCode);
                printBean.setProductName(productName);
                if ("下线".equals(type) || "在库".equals(type)) {
                    String receiveDate = rs.getString("receiveDate");
                    printBean.setReceiveDate(receiveDate);
                } else if ("出库".equals(type)) {
                    String destName = rs.getString("destName");
                    printBean.setDestName(destName);
                }
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            conn = null;
        }
        return printBean;
    }
}
