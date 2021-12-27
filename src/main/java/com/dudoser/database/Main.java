package com.dudoser.database;

public class Main {

    public static void main(String[] args) {
        WorkWithDB wwdb = new WorkWithDB();
        // запись данных в БД
        wwdb.addRecords();
        // запрос к БД с выводом информации
        wwdb.getRecords();
    }
}
