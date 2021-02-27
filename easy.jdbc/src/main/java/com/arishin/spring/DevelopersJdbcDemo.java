package com.arishin.spring;

import java.sql.*;

public class DevelopersJdbcDemo {
   
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/db_denis?autoReconnect=true&useSSL=false";
    static final String USER = "root";
    static final String PASSWORD = "qweasd";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Statement statement = null;

        System.out.println("Registering JDBC driver...");

        Class.forName("com.mysql.cj.jdbc.Driver");

        System.out.println("Creating database connection...");
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        connection.setAutoCommit(false);
        System.out.println("Executing statement...");
        statement = connection.createStatement();

        String sql;
        sql = "SELECT * FROM developer";

        ResultSet resultSet = statement.executeQuery(sql);

        System.out.println("Retrieving data from database...");
        System.out.println("\nDevelopers:");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String specialty = resultSet.getString("specialty");
            int salary = resultSet.getInt("salary");

            System.out.println("\n================\n");
            System.out.println("id: " + id);
            System.out.println("Name: " + name);
            System.out.println("Specialty: " + specialty);
            System.out.println("Salary: $" + salary);
        }

        System.out.println("\n===========================\n");
        System.out.println("Creating savepoint...");
        Savepoint savepointOne = connection.setSavepoint("SavepointOne");

        try {
            sql = "INSERT INTO developer VALUES (6, 'John','C#', 2200)";
            statement.executeUpdate(sql);

            sql = "INSERT INTO developer VALUES (7, 'Ron', 'Ruby', 1900)";
            statement.executeUpdate(sql);

            connection.commit();
        } catch (SQLException e) {
            System.out.println("SQLException. Executing rollback to savepoint...");
            connection.rollback(savepointOne);
        }
        sql = "SELECT * FROM developer";
        resultSet = statement.executeQuery(sql);
        System.out.println("Retrieving data from database...");
        System.out.println("\nDeveloper:");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String specialty = resultSet.getString("specialty");
            int salary = resultSet.getInt("salary");

            System.out.println("id: " + id);
            System.out.println("Name: " + name);
            System.out.println("Specialty: " + specialty);
            System.out.println("Salary: $" + salary);
            System.out.println("\n================\n");
        }
        
        
        
        sql = "INSERT INTO developer VALUES (11, 'Miro', 'Java', 3000)";
        statement.addBatch(sql);
        sql = "INSERT INTO developer VALUES (12, 'Christian', 'UI/UX', 2500)";
        statement.addBatch(sql);
        sql = "INSERT INTO developer VALUES (13, 'Philip', 'C++', 3000)";
        statement.addBatch(sql);
        
        
        try {
        	
        	statement.executeBatch();
        	connection.commit();
        	
        	sql = "SELECT * FROM developer";
        	resultSet = statement.executeQuery(sql);
        	System.out.println("Final developer's table content:");
        	
        	while(resultSet.next()) {
        	
        		 int id = resultSet.getInt("id");
                 String name = resultSet.getString("name");
                 String specialty = resultSet.getString("specialty");
                 int salary = resultSet.getInt("salary");
        		
                 System.out.println("\n====================");
                 System.out.println("id: " + id);
                 System.out.println("Name: " + name);
                 System.out.println("Specialty: " + specialty);
                 System.out.println("Salary : $" + salary);
                 System.out.println("====================\n");
        		
        	}
        	
        System.out.println("Closing connection and releasing resources...");
        resultSet.close();
        statement.close();
        connection.close();
    } finally {
    	
    	 if (statement != null) {
             statement.close();
    	}
    	 if (connection != null) {
             connection.close();
    }
}
        
        
        System.out.println("Thank You.");
        
        
    }}     
        
        