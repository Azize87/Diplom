package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {


    private QueryRunner runner;
    private Connection connection;

    @SneakyThrows
    public Database(String dbUrl, String user, String pass) {
        connection = DriverManager.getConnection(dbUrl, user, pass);
        runner = new QueryRunner();
    }

    @SneakyThrows
    public String getDebitCardTransactionStatus(){
        return runner.query(connection, "select status from payment_entity", new ScalarHandler<>());
    }

    @SneakyThrows
    public String getDebitCardOrderEntityPaymentIdStatus(){
        return runner.query(connection, "select payment_id from order_entity", new ScalarHandler<>());
    }

    @SneakyThrows
    public String getCreditCardTransactionStatus(){
        return runner.query(connection, "select status from credit_request_entity", new ScalarHandler<>());
    }

    @SneakyThrows
    public String getCreditCardOrderEntityCreditIdStatus(){
        return runner.query(connection, "select credit_id from order_entity", new ScalarHandler<>());
    }

    @SneakyThrows
    public void clearDB() {
        var cleanCreditRequest = "delete from credit_request_entity;";
        var cleanOrder = "delete from order_entity;";
        var cleanPayment = "delete from payment_entity;";

        runner.update(connection, cleanCreditRequest);
        runner.update(connection, cleanOrder);
        runner.update(connection, cleanPayment);

    }
}
