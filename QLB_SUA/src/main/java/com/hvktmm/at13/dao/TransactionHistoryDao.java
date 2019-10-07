package com.hvktmm.at13.dao;

import com.hvktmm.at13.config.MySqlDao;
import com.hvktmm.at13.model.TransactionHistory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Date;

public class TransactionHistoryDao {
    PreparedStatement ptmt=null;
    ResultSet resultSet=null;
    Connection connection=null;
    Date dateTime = new Date();
    long time = dateTime.getTime();
    Timestamp timestamp = new Timestamp(time);
    private Connection getConnection() throws SQLException {
        Connection con;
        con= MySqlDao.getInstance().getConnection();
        return con;
    }
    // 1 history import
    // 0 history export
    public Boolean insertImport(TransactionHistory transactionHistory, int idx){
        Boolean check = false;
        try {
            connection=getConnection();
            if(idx == 1){
                String sql="insert into transaction_history(amount,date_import,note,product_id,user_id) values (?,?,?,?,?)";
                ptmt=connection.prepareStatement(sql);
                ptmt.setInt(1,transactionHistory.getAumount());
                ptmt.setTimestamp(2, timestamp);
                ptmt.setString(3,transactionHistory.getNote());
                ptmt.setInt(4,transactionHistory.getProductId());
                ptmt.setInt(5,transactionHistory.getUserId());
                int row=0;
                row=ptmt.executeUpdate();
                if (row!=0){
                    check=true;
                }
            }else {
                String sql="insert into transaction_history(amount,date_export,note,product_id,user_id) values (?,?,?,?,?)";
                ptmt=connection.prepareStatement(sql);
                ptmt.setInt(1,transactionHistory.getAumount());
                ptmt.setTimestamp(2, timestamp);
                ptmt.setString(3,transactionHistory.getNote());
                ptmt.setInt(4,transactionHistory.getProductId());
                ptmt.setInt(5,transactionHistory.getUserId());
                int row=0;
                row=ptmt.executeUpdate();
                if (row!=0){
                    check=true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (ptmt != null) {
                    ptmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return check;
    }
}
