package ca.ubc.cs304.sql.scripts;

import ca.ubc.cs304.model.*;
import ca.ubc.cs304.model.enums.ChargeSchedule;
import ca.ubc.cs304.model.enums.ListingType;
import ca.ubc.cs304.model.enums.Province;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public final class InitialData {
    private static final List<EntityModel> PERSON_DATA =
            List.of(
                    new Person("123 456 7890", "John Doe", "john.doe@email.com"),
                    new Person("234 567 8901", "Jane Smith", "jane.smith@email.com"),
                    new Person("345 678 9012", "Bob Johnson", "bob.johnson@email.com"),
                    new Person("456 789 0123", "Alice Brown", "alice.brown@email.com"),
                    new Person("164 551 5313", "Alice Brown", "alice23@name.com"));

    private static final List<EntityModel> HOMEOWNER_DATA =
            List.of(
                    new Homeowner("123 456 7890"),
                    new Homeowner("234 567 8901"),
                    new Homeowner("345 678 9012"),
                    new Homeowner("456 789 0123"),
                    new Homeowner("164 551 5313"));

    private static final List<EntityModel> REAL_ESTATE_AGENCY_DATA =
            List.of(
                    new RealEstateAgency(01000001, "ABC Realty", 4.5),
                    new RealEstateAgency(01000002, "XYZ Realty", 4.2),
                    new RealEstateAgency(01000003, "PQR Realty", 4.0),
                    new RealEstateAgency(01000004, "Best Realtor", 4.2),
                    new RealEstateAgency(01000005, "Dream Inc", 3.5));

    private static final List<EntityModel> REAL_ESTATE_AGENT_DATA =
            List.of(
                    new RealEstateAgent("123 456 7890", 06000001, 5, 01000001),
                    new RealEstateAgent("234 567 8901", 06000002, 3, 01000002),
                    new RealEstateAgent("345 678 9012", 06000003, 7, 01000003),
                    new RealEstateAgent("456 789 0123", 06000004, 0, 01000002),
                    new RealEstateAgent("164 551 5313", 06000005, 12, 01000005));

    private static final List<EntityModel> DEVELOPER_DATA =
            List.of(
                    new Developer(02000001, "ABC Developers"),
                    new Developer(02000002, "XYZ Builders"),
                    new Developer(02000003, "UBC Builders"),
                    new Developer(02000004, "Thunder Developers"),
                    new Developer(02000005, "Blue Inc"));

    private static final List<EntityModel> CONTRACTOR_COMPANY_DATA =
            List.of(
                    new ContractorCompany(03000001, "FixIt Inc.", ChargeSchedule.HOURLY),
                    new ContractorCompany(03000002, "FixAll Construction", ChargeSchedule.FIXED_PRICE),
                    new ContractorCompany(03000003, "Fix Roofing Inc", ChargeSchedule.HOURLY),
                    new ContractorCompany(03000004, "New Maintenance Company", ChargeSchedule.FIXED_PRICE),
                    new ContractorCompany(03000005, "FixAll Construction", ChargeSchedule.FIXED_PRICE));

    private static final List<EntityModel> STRATA_DATA =
            List.of(
                    new Strata(04000001, "Sunny Heights Condos"),
                    new Strata(04000002, "Pineview Estates"),
                    new Strata(04000003, "Thunderbird Condos"),
                    new Strata(04000004, "KWTQ Estates"),
                    new Strata(04000005, "Marine Condos"));

    private static final List<EntityModel> CITY_DATA =
            List.of(
                    new City(Province.ALBERTA, "Calgary", 0.11),
                    new City(Province.QUEBEC, "Montreal", 0.14),
                    new City(Province.MANITOBA, "Winnipeg", 0.10),
                    new City(Province.BRITISH_COLUMBIA, "Vancouver", 0.08),
                    new City(Province.ONTARIO, "Toronto", 0.10));

    private static final List<EntityModel> PROPERTY_DATA =
            List.of(
                    new Property("123 Main St", Province.BRITISH_COLUMBIA, "Vancouver", 02000001, 04000001, "123 456 7890", 2, 2, 1200, 1),
                    new Property("456 Elm St", Province.ONTARIO, "Toronto", 02000002, 04000001, "234 567 8901", 3, 2, 1500, 0),
                    new Property("2525 West Mall", Province.ONTARIO, "Toronto", 02000001, 04000005, "345 678 9012", 3, 2, 1500, 0),
                    new Property("6331 Thunderbird Cres", Province.ALBERTA, "Calgary", 02000001, 04000004, "456 789 0123", 3, 2, 1500, 1),
                    new Property("202 Birch St", Province.MANITOBA, "Winnipeg", 02000003, 04000002, "164 551 5313", 3, 2, 1500, 1));

    private static final List<EntityModel> LISTING_DATA =
            List.of(
                    new Listing(05000001, "123 Main St", Province.BRITISH_COLUMBIA, "Vancouver", ListingType.SALE, 500000, 1),
                    new Listing(05000002, "456 Elm St", Province.ONTARIO, "Toronto", ListingType.SALE, 700000, 1),
                    new Listing(05000003, "2525 West Mall", Province.ONTARIO, "Toronto", ListingType.RENT, 2500, 1),
                    new Listing(05000004, "6331 Thunderbird Cres", Province.ALBERTA, "Calgary", ListingType.SALE, 1000000, 1),
                    new Listing(05000005, "202 Birch St", Province.MANITOBA, "Winnipeg", ListingType.RENT, 3000, 1));

    private static final List<EntityModel> HIRES_REA_DATA =
            List.of(
                    new HiresREA("123 456 7890", "234 567 8901"),
                    new HiresREA("164 551 5313", "345 678 9012"),
                    new HiresREA("345 678 9012", "123 456 7890"),
                    new HiresREA("234 567 8901", "456 789 0123"),
                    new HiresREA("456 789 0123", "164 551 5313"));

    private static final List<EntityModel> HIRES_CONTRACTOR_DATA =
            List.of(
                    new HiresContractor("123 456 7890", 03000001),
                    new HiresContractor("234 567 8901", 03000002),
                    new HiresContractor("345 678 9012", 03000003),
                    new HiresContractor("456 789 0123", 03000004),
                    new HiresContractor("164 551 5313", 03000005));

    private static final List<EntityModel> PAYS_DATA =
            List.of(
                    new Pays("123 456 7890", 04000001, 243),
                    new Pays("234 567 8901", 04000002, 542),
                    new Pays("345 678 9012", 04000003, 340),
                    new Pays("456 789 0123", 04000004, 459),
                    new Pays("164 551 5313", 04000005, 321));

    private static final List<EntityModel> MAINTAINS_DATA =
            List.of(
                    new Maintains(03000001, "123 Main St", Province.BRITISH_COLUMBIA, "Vancouver", "Landscaping"),
                    new Maintains(03000002, "456 Elm St", Province.ONTARIO, "Toronto", "Plumbing"),
                    new Maintains(03000003, "2525 West Mall", Province.ONTARIO, "Toronto", "Electrical"),
                    new Maintains(03000004, "6331 Thunderbird Cres", Province.ALBERTA, "Calgary", "HVAC"),
                    new Maintains(03000005, "202 Birch St", Province.MANITOBA, "Winnipeg", "Roofing"));

    private static final List<EntityModel> MANAGES_LISTING_DATA =
            List.of(
                    new ManagesListing("123 456 7890", 05000001),
                    new ManagesListing("234 567 8901", 05000002),
                    new ManagesListing("345 678 9012", 05000003),
                    new ManagesListing("456 789 0123", 05000004),
                    new ManagesListing("164 551 5313", 05000005));

    public static final List<EntityModel> INITIAL_DATA =
            Stream.of(
                    PERSON_DATA,
                    HOMEOWNER_DATA,
                    REAL_ESTATE_AGENCY_DATA,
                    REAL_ESTATE_AGENT_DATA,
                    DEVELOPER_DATA,
                    CONTRACTOR_COMPANY_DATA,
                    STRATA_DATA,
                    CITY_DATA,
                    PROPERTY_DATA,
                    LISTING_DATA,
                    HIRES_REA_DATA,
                    HIRES_CONTRACTOR_DATA,
                    PAYS_DATA,
                    MAINTAINS_DATA,
                    MANAGES_LISTING_DATA)
                    .flatMap(Collection::stream)
                    .toList();
}
