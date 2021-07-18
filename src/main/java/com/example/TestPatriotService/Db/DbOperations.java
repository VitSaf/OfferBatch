package com.example.TestPatriotService.Db;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DbOperations {


    private static final String DB_PATH = "DSSK_DB.s3db";

    private static Connection connection;

    private static int sfidSimCounter;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = createConnection();
                System.out.println("con created" + LocalDateTime.now());
                connection.setAutoCommit(true);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }


    public static ArrayList<Person> selectPersonBySfid(String sfid) {
        PreparedStatement stmt;
        ArrayList<Person> records = new ArrayList<>();
        String selectSql = "SELECT * FROM persons WHERE sfId = ?";
        try {
            stmt = getConnection().prepareStatement(selectSql);
            stmt.setString(1, sfid);
            ResultSet set = stmt.executeQuery();
            while (set.next()){
                records.add(new Person(set.getInt("id"), set.getString("firstName"), set.getString("lastName"),
                        set.getString("midName"), set.getString("phone"), set.getString("sfId"),
                        set.getString("email")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(records.size());
        return records;
    }

    public static void tryDbConnection() {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            createTablePerson(conn);
            sfidSimCounter = getCountOfRowsInDb() + 1;
            System.out.println(sfidSimCounter - 1);
            if (conn != null && getCountOfRowsInDb() < 5) {
                String sqlInsert = "insert into persons (lastName, phone, sfid) values ('Petrov', '88005552525', '0x000000000" + sfidSimCounter + "')";
                stmt.execute(sqlInsert);
            }
            ResultSet set = stmt.executeQuery("SELECT * FROM persons");
            ArrayList<Person> persons = new ArrayList<>();
            while (set.next()){
                persons.add(new Person(set.getInt("id"), set.getString("firstName"), set.getString("lastName"),
                        set.getString("midName"), set.getString("phone"), set.getString("sfId"),
                        set.getString("email")));
            }
            System.out.println(persons.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTablePerson(Connection con) {
        String sql = "CREATE TABLE IF NOT EXISTS persons (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	lastName text NOT NULL,\n"
                + "	firstName text,\n"
                + "	midName text,\n"
                + "	phone text,\n"
                + "	sfId text,\n"
                + "	email text\n"
                + ");";
        try {
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static Connection createConnection() throws SQLException {
        String url = "jdbc:sqlite:" + DB_PATH;
        return DriverManager.getConnection(url);
    }

    //Person(int id, String firstName, String lastName, String midName, String phone, String sfId, String email)
    public static boolean insert(Person r) {
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            pstmt = getConnection().prepareStatement("insert into persons (firstName, lastName, midName, phone, sfId, email) " +
                    "                                           values (?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, r.getFirstName());
            pstmt.setString(2, r.getLastName());
            pstmt.setString(3, r.getMidName());
            pstmt.setString(4, r.getPhone());
            pstmt.setString(5, r.getSfId());
            pstmt.setString(6, r.getEmail());
            result = pstmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static Person[] selectAll() {
        Statement stmt = null;
        Person[] records;
        try {
            stmt = getConnection().createStatement();
            ResultSet set = stmt.executeQuery("SELECT * FROM persons");
            records = new Person[DbOperations.getCountOfRowsInDb()];
            int i = 0;
            while (set.next()) {
                records[i] = new Person(set.getInt("id"), set.getString("firstName"), set.getString("lastName"),
                        set.getString("midName"), set.getString("phone"), set.getString("sfId"),
                        set.getString("email"));
                i++;
            }
        } catch (SQLException throwables) {
            records = null;
            throwables.printStackTrace();
        }
        return records;
    }

    public static int getCountOfRowsInDb() throws SQLException {
        Statement stmt = getConnection().createStatement();
        return stmt.executeQuery("SELECT COUNT(*) FROM persons").getInt(1);
    }
}
