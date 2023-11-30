package ca.ubc.cs304.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SimpleResultSet {
    private ResultSet rs;

    public SimpleResultSet(ResultSet rs) {
        this.rs = rs;
    }

    public boolean next() throws SQLException {
        return this.rs.next();
    }

    public Integer getInt(int columnIndex) {
        try {
            return rs.getInt(columnIndex);
        } catch (SQLException e) {
            return null;
        }
    }

    public Integer getInt(String columnLabel) {
        try {
            return rs.getInt(columnLabel);
        } catch (SQLException e) {
            return null;
        }
    }

    public String getString(int columnIndex) {
        try {
            return rs.getString(columnIndex).trim();
        } catch (SQLException e) {
            return null;
        }
    }

    public String getString(String columnLabel) {
        try {
            return rs.getString(columnLabel).trim();
        } catch (SQLException e) {
            return null;
        }
    }

    public Double getDouble(int columnIndex) {
        try {
            return rs.getDouble(columnIndex);
        } catch (SQLException e) {
            return null;
        }
    }

    public Double getDouble(String columnLabel) {
        try {
            return rs.getDouble(columnLabel);
        } catch (SQLException e) {
            return null;
        }
    }

    public void close() {
        try {
            this.rs.close();
        } catch (SQLException e) {
            System.out.println("Unable to close ResultSet");
        }
    }
}
