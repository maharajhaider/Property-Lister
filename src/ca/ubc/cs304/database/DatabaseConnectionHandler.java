package ca.ubc.cs304.database;

import ca.ubc.cs304.model.EntityModel;
import ca.ubc.cs304.model.Listing;
import ca.ubc.cs304.model.enums.ListingType;
import ca.ubc.cs304.model.enums.Province;
import ca.ubc.cs304.util.PrintablePreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static ca.ubc.cs304.sql.scripts.InitialData.INITIAL_DATA;
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

    private static final Map<String, String> CREATE_TABLE_DDL;

    static {
        CREATE_TABLE_DDL = new LinkedHashMap<>();
        CREATE_TABLE_DDL.put("person", CREATE_TABLE_PERSON);
        CREATE_TABLE_DDL.put("homeowner", CREATE_TABLE_HOMEOWNER);
        CREATE_TABLE_DDL.put("realestateagency", CREATE_TABLE_REAL_ESTATE_AGENCY);
        CREATE_TABLE_DDL.put("realestateagent", CREATE_TABLE_REAL_ESTATE_AGENT);
        CREATE_TABLE_DDL.put("developer", CREATE_TABLE_DEVELOPER);
        CREATE_TABLE_DDL.put("contractorcompany", CREATE_TABLE_CONTRACTOR_COMPANY);
        CREATE_TABLE_DDL.put("strata", CREATE_TABLE_STRATA);
        CREATE_TABLE_DDL.put("city", CREATE_TABLE_CITY);
        CREATE_TABLE_DDL.put("property", CREATE_TABLE_PROPERTY);
        CREATE_TABLE_DDL.put("listing", CREATE_TABLE_LISTING);
        CREATE_TABLE_DDL.put("hiresrea", CREATE_TABLE_HIRES_REA);
        CREATE_TABLE_DDL.put("hirescontractor", CREATE_TABLE_HIRES_CONTRACTOR);
        CREATE_TABLE_DDL.put("pays", CREATE_TABLE_PAYS);
        CREATE_TABLE_DDL.put("maintains", CREATE_TABLE_MAINTAINS);
        CREATE_TABLE_DDL.put("manageslisting", CREATE_TABLE_MANAGES_LISTING);
    }

    private Connection connection = null;

    public DatabaseConnectionHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            login("ora_bansal21","a67617654");
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
        createTables();
        fillInitialData();
    }

    private void dropTablesIfExist() {
        try {
            String query = "select table_name from user_tables";
            PrintablePreparedStatement ps =
                    new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            Set<String> tableNames = CREATE_TABLE_DDL.keySet();
            while (rs.next()) {
                String tableFound = rs.getString(1).toLowerCase();
                if (tableNames.contains(tableFound)) {
                    ps.execute("DELETE FROM " + tableFound);
                    ps.execute("DROP TABLE " + tableFound);
                }
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    private void createTables() {
        try {
            for (String table : CREATE_TABLE_DDL.keySet()) {
                String query = CREATE_TABLE_DDL.get(table);
                PrintablePreparedStatement ps = getPS(query);
                ps.executeUpdate();
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void fillInitialData() {
        for (EntityModel data : INITIAL_DATA) {
            insertData(data);
        }
    }

    public void insertData(EntityModel data) {
        try {
            String insertStatement = data.insertStatement();
            PrintablePreparedStatement ps = getPS(insertStatement);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    private PrintablePreparedStatement getPS(String script) throws SQLException {

        return new PrintablePreparedStatement(connection.prepareStatement(script), script, false);
    }

    public Listing[] getListingInfo() {
        List<Listing> result = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(ORACLE_URL, "ora_bansal21", "a67617654");
            connection.setAutoCommit(false);
            String query = "SELECT * FROM Listing";
            PrintablePreparedStatement ps = getPS(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Listing listing = new Listing(
                        rs.getInt("listingID"),
                        rs.getString("streetAddress").trim(),
                        Province.fromLabel(rs.getString("province").trim()),
                        rs.getString("cityName").trim(),
                        ListingType.fromLabel(rs.getString("type").trim()),
                        rs.getInt("price"),
                        rs.getInt("active"));
                result.add(listing);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result.toArray(new Listing[0]);
    }

    public void deleteListing(int listingId) {
        //
    }
}
