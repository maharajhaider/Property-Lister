package ca.ubc.cs304.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import ca.ubc.cs304.model.Listing;
import ca.ubc.cs304.util.PrintablePreparedStatement;

import static ca.ubc.cs304.sql.scripts.SQLScripts.*;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
    // Use this version of the ORACLE_URL if you are running the code off of the server
//	private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
    // Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private static final Map<String, String> CREATE_TABLE_DDL = Map.ofEntries(
            Map.entry("person", CREATE_TABLE_PERSON),
            Map.entry("homeowner", CREATE_TABLE_HOMEOWNER),
            Map.entry("realestateagency", CREATE_TABLE_REAL_ESTATE_AGENCY),
            Map.entry("realestateagent", CREATE_TABLE_REAL_ESTATE_AGENT),
            Map.entry("developer", CREATE_TABLE_DEVELOPER),
            Map.entry("contractorcompany", CREATE_TABLE_CONTRACTOR_COMPANY),
            Map.entry("strata", CREATE_TABLE_STRATA),
            Map.entry("city", CREATE_TABLE_CITY),
            Map.entry("property", CREATE_TABLE_PROPERTY),
            Map.entry("listing", CREATE_TABLE_LISTING),
            Map.entry("hiresrea", CREATE_TABLE_HIRES_REA),
            Map.entry("hirescontractor", CREATE_TABLE_HIRES_CONTRACTOR),
            Map.entry("pays", CREATE_TABLE_PAYS),
            Map.entry("maintains", CREATE_TABLE_MAINTAINS),
            Map.entry("manageslisting", CREATE_TABLE_MANAGES_LISTING)
    );

    private Connection connection = null;

    public DatabaseConnectionHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void deleteListing(int listingId) {
        //
    }

    public void insertListing(Listing listing) {
        //
    }

    public Listing[] getListingInfo() {
        return new Listing[0];
    }

    public boolean login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

    private void rollbackConnection() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void databaseSetup() {
        dropTablesIfExist();

        try {
            for (String table: CREATE_TABLE_DDL.keySet()) {
                String query = CREATE_TABLE_DDL.get(table);
                PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    private void dropTablesIfExist() {
        try {
            String query = "select table_name from user_tables";
            PrintablePreparedStatement ps =
                    new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            Set<String> tableNames = CREATE_TABLE_DDL.keySet();
            while(rs.next()) {
                String tableFound = rs.getString(1).toLowerCase();
                if(tableNames.contains(tableFound)) {
                    ps.execute("DROP TABLE " + tableFound);
                }
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }
}
