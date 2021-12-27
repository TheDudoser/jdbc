package com.dudoser.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class WorkWithDB {
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;

    private static final String INSERT_NEW = "insert into films (name, description) VALUES (?,?)";
    private static final String GET_ALL = "select * from films";

    WorkWithDB() {
        setConfig();
    }

    private void setConfig () {
        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);

            URL = property.getProperty("db.host");
            USERNAME = property.getProperty("db.login");
            PASSWORD = property.getProperty("db.password");

            //System.out.println("HOST: " + URL
            //        + ", LOGIN: " + USERNAME
            //        + ", PASSWORD: " + PASSWORD);

        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
    }

    public Driver createDriver(Driver driver) {
        try {
            driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return driver;
    }

    public void addRecords() {
        Driver driver = null;
        //UserUI uui = new UserUI();
        driver = createDriver(driver);
        // получили записи
        ArrayList<UserUI.rec> records = new UserUI().getRecords();
        PreparedStatement preparedStatement = null;

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            // добавляем введённую пользователем запись
            for (UserUI.rec record : records) {
                preparedStatement = connection.prepareStatement(INSERT_NEW);

                preparedStatement.setString(1, record.Name);
                preparedStatement.setString(2, record.Desc);

                preparedStatement.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getRecords() {
        PreparedStatement preparedStatement = null;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            // считаем количество записей на данный момент
            String query = "select count(*) from films";
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            int count = rs.getInt(1);
            System.out.println("Всего в БД " + count + " записей");

            // вывод таблицы
            System.out.println("====films====");

            preparedStatement = connection.prepareStatement(GET_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                System.out.println("id: " + id + ", name: " + name + ", description: " + description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
