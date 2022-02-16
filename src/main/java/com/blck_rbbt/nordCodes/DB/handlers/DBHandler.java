package com.blck_rbbt.nordCodes.DB.handlers;

import com.blck_rbbt.nordCodes.DB.DBConfig;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class DBHandler extends DBConfig {
    private Connection dBConnection;
    private Statement statement;

    public void createDB() {
        try {
            Class.forName(JDBC_DRIVER);
            dBConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            statement = dBConnection.createStatement();
            String SQL = "CREATE DATABASE IF NOT EXISTS `nordcodes_DB` DEFAULT CHARACTER SET utf8";
            statement.executeUpdate(SQL);
            String mysqlUrl = String.format("jdbc:mysql://%s:%s/%s", DB_HOST, DB_PORT, DB_NAME);
            Flyway flyway = Flyway.configure()
                    .dataSource(mysqlUrl, DB_USER, DB_PASS)
                    .load();
            flyway.migrate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement!=null){
                    statement.close();
                }
                if(dBConnection!=null){
                    dBConnection.close();
                }
            } catch (SQLException t) {
                t.printStackTrace();
            }
        }
    }
}
