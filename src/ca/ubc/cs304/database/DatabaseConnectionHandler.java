package ca.ubc.cs304.database;

import ca.ubc.cs304.model.AgencyInfo;
import ca.ubc.cs304.model.CityPropertyCount;
import ca.ubc.cs304.model.ListingInfo;
import ca.ubc.cs304.model.CityRealEstatePrice;
import ca.ubc.cs304.model.entity.*;
import ca.ubc.cs304.model.enums.ListingType;
import ca.ubc.cs304.model.enums.Province;
import ca.ubc.cs304.util.PrintablePreparedStatement;
import ca.ubc.cs304.util.SimpleResultSet;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import static ca.ubc.cs304.sql.scripts.InitialData.INITIAL_DATA;
import static ca.ubc.cs304.sql.scripts.SQLScripts.CREATE_TABLES;

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

    private static final Map<String, Class<? extends EntityModel>> TABLES_AND_ENTITIES;

    static {
        TABLES_AND_ENTITIES = new LinkedHashMap<>();
        TABLES_AND_ENTITIES.put("Person", Person.class);
        TABLES_AND_ENTITIES.put("Homeowner", Homeowner.class);
        TABLES_AND_ENTITIES.put("RealEstateAgency", RealEstateAgency.class);
        TABLES_AND_ENTITIES.put("RealEstateAgent", RealEstateAgent.class);
        TABLES_AND_ENTITIES.put("Developer", Developer.class);
        TABLES_AND_ENTITIES.put("ContractorCompany", ContractorCompany.class);
        TABLES_AND_ENTITIES.put("Strata", Strata.class);
        TABLES_AND_ENTITIES.put("City", City.class);
        TABLES_AND_ENTITIES.put("Property", Property.class);
        TABLES_AND_ENTITIES.put("Listing", Listing.class);
        TABLES_AND_ENTITIES.put("HiresREA", HiresREA.class);
        TABLES_AND_ENTITIES.put("HiresContractor", HiresContractor.class);
        TABLES_AND_ENTITIES.put("Pays", Pays.class);
        TABLES_AND_ENTITIES.put("Maintains", Maintains.class);
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
            PrintablePreparedStatement ps = getPS("placeholder");

            List<String> tables = getTables();
            for (String table: tables) {
                try {
                    ps.execute("DROP TABLE " + table + " CASCADE CONSTRAINTS");
                    System.out.println("Dropped " + table);
                } catch (SQLException e) {
                    // Do nothing
                }
            }
            
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    private void createTables() {
        try {
            PrintablePreparedStatement ps = getPS("placeholder");
            for (String query : CREATE_TABLES) {
                ps.executeUpdate(query);
                System.out.println("Running Query: " + query);
            }

            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void fillInitialData() {
        for (EntityModel data : INITIAL_DATA) {
            insertData(data, null);
        }
    }

    public void insertData(EntityModel data, Integer id) {
        try {
            String insertStatement = data.insertStatement(id);
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

    public List<Listing> getListings(String startOfAddress) {
        List<Listing> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM Listing" +
                    " WHERE streetAddress LIKE '%"+ startOfAddress+"%'";
            PrintablePreparedStatement ps = getPS(query);
            SimpleResultSet rs = new SimpleResultSet(ps.executeQuery());

            while(rs.next()) {
                Listing listing = new Listing(
                        rs.getInt("listingId"),
                        rs.getString("realEstateAgentPhone"),
                        rs.getString("streetAddress"),
                        rs.getString("province") == null? null: Province.fromLabel(rs.getString("province")),
                        rs.getString("cityName"),
                        rs.getString("type") == null? null: ListingType.fromLabel(rs.getString("type")),
                        rs.getInt("price"),
                        rs.getInt("active"));
                result.add(listing);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result;
    }

    public List<Listing> getListings(List<String> parameters) {
        List<Listing> result = new ArrayList<>();
        String streetAddressEnabled ="";
        String streetAddressEquality= "";
        String streetAddressParam= "";
        String addressStarting = "";
        String addressEnding ="";

        String listingIDEnabled= "";
        String listingEquality= "";
        String listingIDParam= "";
        String listingIDOperator = "";

        String typeEnabled= "";
        String typeEquality= "";
        String typeParam= "";
        String typeOperator = "";

        String priceEnabled= "";
        String pricingEquality= "";
        String priceParam= "";
        String priceOperator = "";

        String activeEnabled= "";
        String activeEquality= "";
        String activeParam= "";
        String activeOperator = "";

        String where = "WHERE ";

        if (isEnabled(parameters.get(0))) {
            streetAddressEnabled= "streetAddress ";
            streetAddressEquality= "LIKE ";
            streetAddressParam= parameters.get(1);
            addressStarting = "'%";
            addressEnding =  "%'";

        }

        if (isEnabled(parameters.get(2))) {
            listingIDEnabled= "listingId ";
            listingEquality= "= ";
            listingIDParam= parameters.get(3);
            if (isEnabled(parameters.get(0))){
                listingIDOperator = parameters.get(2);
            }
        }
        if (isEnabled(parameters.get(4))) {
            typeEnabled= "active ";
            typeEquality= "= ";
            typeParam= processBool(parameters.get(5));
            if (isEnabled(parameters.get(0)) | isEnabled(parameters.get(2))) {
                typeOperator = parameters.get(4);

            }

        }
        if (isEnabled(parameters.get(6))) {
            priceEnabled= "price ";
            pricingEquality= "= ";
            priceParam= parameters.get(7);
            if (isEnabled(parameters.get(0)) | isEnabled(parameters.get(2)) | isEnabled(parameters.get(4))) {
                priceOperator = parameters.get(6);

            }
        }
        if (isEnabled(parameters.get(8))) {
            activeEnabled= "type ";
            activeEquality= "= ";
            activeParam= "'"+ parameters.get(9) + "'";
            if (isEnabled(parameters.get(0)) | isEnabled(parameters.get(2)) | isEnabled(parameters.get(4)) | isEnabled(parameters.get(6))) {
                activeOperator = parameters.get(8);
            }

        }
        if (!(isEnabled(parameters.get(0)) | isEnabled(parameters.get(2)) | isEnabled(parameters.get(4)) | isEnabled(parameters.get(6)) | isEnabled(parameters.get(8)))) {
            where ="";
        }



        try {
            String query =
                    "SELECT * FROM Listing" +
                    where+ streetAddressEnabled + streetAddressEquality + addressStarting+ streetAddressParam+ addressEnding
                            + listingIDOperator+" " + listingIDEnabled + listingEquality + listingIDParam
                            + typeOperator+" " + typeEnabled  + typeEquality + typeParam
                            + priceOperator+" " + priceEnabled + pricingEquality + priceParam
                            + activeOperator+" " + activeEnabled+ activeEquality +activeParam;

            PrintablePreparedStatement ps = getPS(query);
            SimpleResultSet rs = new SimpleResultSet(ps.executeQuery());

            while(rs.next()) {
                Listing listing = new Listing(
                        rs.getInt("listingId"),
                        rs.getString("realEstateAgentPhone"),
                        rs.getString("streetAddress"),
                        rs.getString("province") == null? null: Province.fromLabel(rs.getString("province")),
                        rs.getString("cityName"),
                        rs.getString("type") == null? null: ListingType.fromLabel(rs.getString("type")),
                        rs.getInt("price"),
                        rs.getInt("active"));
                result.add(listing);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result;
    }

    private String processBool(String s) {
        if (s.equals("true") ){
            return "1";
        }else {
            return "0";
        }
    }

    public Boolean isEnabled(String marker) {
        return !(marker.equalsIgnoreCase("disable filter ->"));
    }

    public Integer generateId(HasID model) {
        try {
            String query = model.getIdSQL();
            PrintablePreparedStatement ps = getPS(query);
            SimpleResultSet rs = new SimpleResultSet(ps.executeQuery());
            int id;
            if (rs.next()) {
                int largestId = rs.getInt(1);
                id = largestId + 1;
            } else {
                id = model.defaultId();
            }

            rs.close();
            ps.close();
            return id;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return null;
    }

    public <T extends EntityModel> List<T> getAllTuples(Class<T> type) {
        List<T> result = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + type.getSimpleName();
            PrintablePreparedStatement ps = getPS(query);
            SimpleResultSet rs = new SimpleResultSet(ps.executeQuery());

            while(rs.next()) {
                T newTuple = type.getConstructor(SimpleResultSet.class).newInstance(rs);
                result.add(newTuple);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result;
    }

    public ListingInfo getListingInfo(Integer listingID) {
        try {
            String query = "SELECT " +
                    "Listing.listingID, Listing.type, Listing.price, Listing.active, Property.streetAddress, " +
                    "Property.province, Property.cityName, Property.developerLicenseID, Property.strataID, " +
                    "Property.homeownerPhone, Listing.realEstateAgentPhone, Property.bedrooms, Property.bathrooms, " +
                    "Property.sizeInSqft, Property.hasAC " +
                    "FROM Listing " +
                    "INNER JOIN Property " +
                    "ON Listing.streetAddress = Property.streetAddress " +
                    "AND Listing.province = Property.province " +
                    "AND Listing.cityName = Property.cityName " +
                    "WHERE Listing.listingID = " + listingID;
            PrintablePreparedStatement ps = getPS(query);
            SimpleResultSet rs = new SimpleResultSet(ps.executeQuery());

            rs.next();
            Listing listing = new Listing(rs);
            Property property = new Property(rs);
            ListingInfo listingInfo = new ListingInfo(listing, property);

            rs.close();
            ps.close();
            return listingInfo;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return null;
    }

    public Boolean updateListing(int active, ListingType type, int price, int listingID) {
        try {
            String query = "UPDATE Listing" +
                    " SET "+
                    "type = '"  + type.name().toLowerCase() + "'," +
                    "price ="  + price + "," +
                    "active ="  + active + "\n" +
                    "WHERE listingID =" + listingID;
            PrintablePreparedStatement ps = getPS(query);
            ps.executeQuery();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
        return true;
    }

    public List<String> getTables() {
        List<String> tablesFound = new ArrayList<>();
        try {
            String query = "SELECT table_name FROM user_tables";
            PrintablePreparedStatement ps = getPS(query);
            SimpleResultSet rs = new SimpleResultSet(ps.executeQuery());

            List<String> tables = new ArrayList<>(TABLES_AND_ENTITIES.keySet());
            List<String> tableLookup = tables
                    .stream()
                    .map(String::toLowerCase)
                    .toList();
            while(rs.next()) {
                String table = rs.getString(1).toLowerCase();
                if(tableLookup.contains(table)) {
                    tablesFound.add(tables.get(tableLookup.indexOf(table)));
                }
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return tablesFound.stream()
                .map(table -> table.substring(0, 1).toUpperCase() + table.substring(1))
                .toList();
    }

    public List<String> getColumns(String table) {
        List<String> columns = new ArrayList<>();
        try {
            columns = Arrays
                    .stream(TABLES_AND_ENTITIES.get(table).getDeclaredFields())
                    .map(Field::getName)
                    .toList();
        } catch (SecurityException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return columns;
    }

    public <T extends EntityModel> List<T> projectData(String table, List<String> columns) {
        List<T> result = new ArrayList<>();
        if (columns == null || columns.size() == 0) {
            return result;
        }
        try {
            String columnList = columns
                    .stream()
                    .reduce((c1, c2) -> c1 + ", " + c2)
                    .get();
            String query = "SELECT " + columnList + " FROM " + table;
            PrintablePreparedStatement ps = getPS(query);
            SimpleResultSet rs = new SimpleResultSet(ps.executeQuery());

            while(rs.next()) {
                T newTuple = (T) TABLES_AND_ENTITIES.get(table).getConstructor(SimpleResultSet.class).newInstance(rs);
                result.add(newTuple);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return result;
    }

    public List<CityPropertyCount> getPropertyCountByCity() {
        List<CityPropertyCount> propertyCountList = new ArrayList<>();
        try {
            String query =
                    "SELECT City.province, City.name, COUNT(*) " +
                            "FROM City " +
                            "RIGHT JOIN Property " +
                            "ON City.province = Property.province " +
                            "AND City.name = Property.cityName " +
                            "GROUP BY City.province, CIty.name";
            PrintablePreparedStatement ps = getPS(query);
            SimpleResultSet rs = new SimpleResultSet(ps.executeQuery());

            while(rs.next()) {
                CityPropertyCount count = new CityPropertyCount(
                        Province.fromLabel(rs.getString(1)),
                        rs.getString(2),
                        rs.getInt(3)
                );
                propertyCountList.add(count);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return propertyCountList;
    }

    public List<AgencyInfo> getReputableAgencies() {
        List<AgencyInfo> agencies = new ArrayList<>();
        try {
            String query =
                    "SELECT RealEstateAgency.agencyID, RealEstateAgency.name, RealEstateAgency.rating, " +
                            "COUNT(RealEstateAgent.agentLicenseID), AVG(RealEstateAgent.yearsOfExp) " +
                            "FROM RealEstateAgency " +
                            "RIGHT JOIN RealEstateAgent " +
                            "ON RealEstateAgency.agencyID = RealEstateAgent.agencyID " +
                            "WHERE RealEstateAgency.rating > 4.0 " +
                            "GROUP BY RealEstateAgency.agencyID, RealEstateAgency.name, RealEstateAgency.rating " +
                            "HAVING COUNT(*) > 1";
            PrintablePreparedStatement ps = getPS(query);
            SimpleResultSet rs = new SimpleResultSet(ps.executeQuery());

            while(rs.next()) {
                AgencyInfo agency = new AgencyInfo(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getInt(4),
                        rs.getDouble(5)
                );
                agencies.add(agency);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return agencies;
    }

    /**
     *
     * @return the city with properties of the lowest price per sqft, rent/sale specified by the caller
     */
    public CityRealEstatePrice findCheapestCity(ListingType listingType) {
        try {
            String joinedTable = "Listing " +
                    "INNER JOIN Property " +
                    "ON Listing.province = Property.province " +
                    "AND Listing.cityName = Property.cityName " +
                    "AND Listing.streetAddress = Property.streetAddress ";
            String query =
                    "SELECT Property.province, Property.cityName, AVG(Listing.price / Property.sizeInSqft) " +
                            "FROM " + joinedTable +
                            "WHERE Listing.type = '" + listingType.label + "' " +
                            "GROUP BY Property.province, Property.cityName " +
                            "HAVING AVG(Listing.price / Property.sizeInSqft) <= ALL(" +
                            "SELECT AVG(Listing.price / Property.sizeInSqft) " +
                            "FROM " + joinedTable +
                            "WHERE Listing.type = '" + listingType.label + "' " +
                            "GROUP BY Property.province, Property.cityName)";
            PrintablePreparedStatement ps = getPS(query);
            SimpleResultSet rs = new SimpleResultSet(ps.executeQuery());

            rs.next();
            CityRealEstatePrice cityRealEstatePrice = new CityRealEstatePrice(
                    Province.fromLabel(rs.getString(1)),
                    rs.getString(2),
                    listingType,
                    rs.getDouble(3)
            );

            rs.close();
            ps.close();
            return cityRealEstatePrice;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return null;
    }

    public boolean deleteProperty(Province province, String cityName, String streetAddress) {
        try {
            String query = "DELETE FROM Property " +
                    "WHERE StreetAddress = '" + streetAddress + "' " +
                    "AND Province = '" + province.label + "' " +
                    "AND CityName = '" + cityName + "'";
            PrintablePreparedStatement ps = getPS(query);
            ps.executeUpdate();

            connection.commit();
            ps.close();
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }
}