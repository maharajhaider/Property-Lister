package ca.ubc.cs304.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import ca.ubc.cs304.model.Listing;
import ca.ubc.cs304.util.PrintablePreparedStatement;

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
            Map.entry(
                    "person",
                    """
                            CREATE TABLE Person
                            (
                                phone CHAR(20) PRIMARY KEY,
                                name  CHAR(255),
                                email CHAR(255)
                            );"""),
            Map.entry(
                    "homeowner",
                    """
                            CREATE TABLE Homeowner
                            (
                                phone CHAR(20) PRIMARY KEY,
                                FOREIGN KEY (phone) REFERENCES Person (phone)
                                    ON DELETE CASCADE
                            );
                            """),
            Map.entry(
                    "realestateagency",
                    """
                            CREATE TABLE RealEstateAgency
                            (
                                agencyID INTEGER PRIMARY KEY,
                                name     CHAR(255),
                                rating   DOUBLE PRECISION
                            );"""),
            Map.entry(
                    "realestateagent",
                    """
                            CREATE TABLE RealEstateAgent
                            (
                                phone          CHAR(20) PRIMARY KEY,
                                agentLicenseId INTEGER UNIQUE,
                                yearsOfExp     INTEGER,
                                agencyID       INTEGER NOT NULL,
                                FOREIGN KEY (phone) REFERENCES Person (phone)
                                    ON DELETE CASCADE,
                                FOREIGN KEY (agencyID) REFERENCES RealEstateAgency (agencyID)
                                    ON DELETE CASCADE
                            );"""),
            Map.entry(
                    "developer",
                    """
                            CREATE TABLE Developer
                            (
                                developerLicenseID INTEGER PRIMARY KEY,
                                name               CHAR(255)
                            );"""),
            Map.entry(
                    "contractorcompany",
                    """
                            CREATE TABLE ContractorCompany
                            (
                                contractorID   INTEGER PRIMARY KEY,
                                name           CHAR(255),
                                chargeSchedule CHAR(255)
                            );"""),
            Map.entry(
                    "strata",
                    """
                            CREATE TABLE Strata
                            (
                                strataID INTEGER PRIMARY KEY,
                                name     VARCHAR(255)
                            );"""),
            Map.entry(
                    "city",
                    """
                            CREATE TABLE City
                            (
                                province CHAR(255),
                                name     CHAR(255),
                                taxRate  DOUBLE PRECISION,
                                PRIMARY KEY (province, name)
                            );"""),
            Map.entry(
                    "property",
                    """
                            CREATE TABLE Property
                            (
                                streetAddress      CHAR(255),
                                province           CHAR(255),
                                cityName           CHAR(255),
                                developerLicenseID INTEGER NOT NULL,
                                strataID           INTEGER,
                                phone              CHAR(20),
                                bedrooms           INTEGER,
                                bathrooms          INTEGER,
                                sizeInSqft         INTEGER,
                                hasAC              Number(1,0),
                                PRIMARY KEY (streetAddress, province, cityName),
                                FOREIGN KEY (province, cityName) REFERENCES City (province, name)
                                    ON DELETE CASCADE,
                                FOREIGN KEY (strataID) REFERENCES Strata (strataID),
                                FOREIGN KEY (phone) REFERENCES Homeowner (phone),
                                FOREIGN KEY (developerLicenseID) REFERENCES Developer (developerLicenseID)
                                    ON DELETE CASCADE
                            );"""),
            Map.entry(
                    "listing",
                    """
                            CREATE TABLE Listing
                            (
                                listingID     INTEGER PRIMARY KEY,
                                streetAddress CHAR(255),
                                province      CHAR(255),
                                cityName      CHAR(255),
                                type          CHAR(255),
                                price         INTEGER,
                                active        NUMBER(1,0),
                                FOREIGN KEY (streetAddress, cityName, province) REFERENCES Property (streetAddress, cityName, province)
                                    ON DELETE CASCADE,
                                UNIQUE (streetAddress, cityName, province)
                            );"""),
            Map.entry(
                    "hiresrea",
                    """
                            CREATE TABLE HiresREA
                            (
                                homeownerPhone       CHAR(20),
                                realEstateAgentPhone CHAR(20),
                                PRIMARY KEY (homeownerPhone, realEstateAgentPhone),
                                FOREIGN KEY (homeownerPhone) REFERENCES Homeowner (phone)
                                    ON DELETE CASCADE,
                                FOREIGN KEY (realEstateAgentPhone) REFERENCES RealEstateAgent (phone)
                                    ON DELETE CASCADE
                            );"""),
            Map.entry(
                    "hirescontractor",
                    """
                            CREATE TABLE HiresContractor
                            (
                                homeownerPhone CHAR(20),
                                contractorID   INTEGER,
                                PRIMARY KEY (homeownerPhone, contractorID),
                                FOREIGN KEY (homeownerPhone) REFERENCES Homeowner (phone)
                                    ON DELETE CASCADE,
                                FOREIGN KEY (contractorID) REFERENCES ContractorCompany (contractorID)
                                    ON DELETE CASCADE
                            );"""),
            Map.entry(
                    "pays",
                    """
                            CREATE TABLE Pays
                            (
                                homeownerPhone CHAR(20),
                                strataID       INTEGER,
                                fee            INTEGER,
                                PRIMARY KEY (homeownerPhone, strataID),
                                FOREIGN KEY (homeownerPhone) REFERENCES Homeowner (phone)
                                    ON DELETE CASCADE,
                                FOREIGN KEY (strataID) REFERENCES Strata (strataID)
                                    ON DELETE CASCADE
                            );"""),
            Map.entry(
                    "maintains",
                    """
                            CREATE TABLE Maintains
                            (
                                contractorID         INTEGER,
                                streetAddress        CHAR(255),
                                province             CHAR(255),
                                cityName             CHAR(255),
                                areaOfResponsibility CHAR(255),
                                PRIMARY KEY (contractorID, streetAddress, province, cityName),
                                FOREIGN KEY (streetAddress, cityName, province) REFERENCES Property (streetAddress, cityName, province)
                                    ON DELETE CASCADE,
                                FOREIGN KEY (contractorID) REFERENCES ContractorCompany (contractorID)
                                    ON DELETE CASCADE,
                                UNIQUE (streetAddress, province, cityName, areaOfResponsibility)
                            );"""),
            Map.entry(
                    "manageslisting",
                    """
                            CREATE TABLE ManagesListing
                            (
                                realEstateAgentPhone CHAR(20),
                                listingID            INTEGER,
                                PRIMARY KEY (listingID, realEstateAgentPhone),
                                FOREIGN KEY (realEstateAgentPhone) REFERENCES RealEstateAgent (phone)
                                    ON DELETE CASCADE,
                                FOREIGN KEY (listingID) REFERENCES Listing (listingID)
                                    ON DELETE CASCADE
                            );""")
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
