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
                    new RealEstateAgency(1000001, "ABC Realty", 4.5),
                    new RealEstateAgency(1000002, "XYZ Realty", 4.2),
                    new RealEstateAgency(1000003, "PQR Realty", 4.0),
                    new RealEstateAgency(1000004, "Best Realtor", 4.2),
                    new RealEstateAgency(1000005, "Dream Inc", 3.5));

    private static final List<EntityModel> REAL_ESTATE_AGENT_DATA =
            List.of(
                    new RealEstateAgent("123 456 7890", 6000001, 5, 1000001),
                    new RealEstateAgent("234 567 8901", 6000002, 3, 1000002),
                    new RealEstateAgent("345 678 9012", 6000003, 7, 1000003),
                    new RealEstateAgent("456 789 0123", 6000004, 0, 1000002),
                    new RealEstateAgent("164 551 5313", 6000005, 12, 1000005));

    private static final List<EntityModel> DEVELOPER_DATA =
            List.of(
                    new Developer(2000001, "ABC Developers"),
                    new Developer(2000002, "XYZ Builders"),
                    new Developer(2000003, "UBC Builders"),
                    new Developer(2000004, "Thunder Developers"),
                    new Developer(2000005, "Blue Inc"));

    private static final List<EntityModel> CONTRACTOR_COMPANY_DATA =
            List.of(
                    new ContractorCompany(3000001, "FixIt Inc.", ChargeSchedule.HOURLY),
                    new ContractorCompany(3000002, "FixAll Construction", ChargeSchedule.FIXED_PRICE),
                    new ContractorCompany(3000003, "Fix Roofing Inc", ChargeSchedule.HOURLY),
                    new ContractorCompany(3000004, "New Maintenance Company", ChargeSchedule.FIXED_PRICE),
                    new ContractorCompany(3000005, "FixAll Construction", ChargeSchedule.FIXED_PRICE));

    private static final List<EntityModel> STRATA_DATA =
            List.of(
                    new Strata(4000001, "Sunny Heights Condos"),
                    new Strata(4000002, "Pineview Estates"),
                    new Strata(4000003, "Thunderbird Condos"),
                    new Strata(4000004, "KWTQ Estates"),
                    new Strata(4000005, "Marine Condos"));

    private static final List<EntityModel> CITY_DATA =
            List.of(
                    new City(Province.ALBERTA, "Calgary", 0.11),
                    new City(Province.QUEBEC, "Montreal", 0.14),
                    new City(Province.MANITOBA, "Winnipeg", 0.10),
                    new City(Province.BRITISH_COLUMBIA, "Vancouver", 0.08),
                    new City(Province.ONTARIO, "Toronto", 0.10));

    private static final List<EntityModel> PROPERTY_DATA =
            List.of(
                    new Property("123 Main St", Province.BRITISH_COLUMBIA, "Vancouver", 2000001, 4000001, "123 456 7890", 2, 2, 1200, 1),
                    new Property("456 Elm St", Province.ONTARIO, "Toronto", 2000002, 4000001, "234 567 8901", 3, 2, 1500, 0),
                    new Property("2525 West Mall", Province.ONTARIO, "Toronto", 2000001, 4000005, "345 678 9012", 3, 2, 1500, 0),
                    new Property("6331 Thunderbird Cres", Province.ALBERTA, "Calgary", 2000001, 4000004, "456 789 0123", 3, 2, 1500, 1),
                    new Property("202 Birch St", Province.MANITOBA, "Winnipeg", 2000003, 4000002, "164 551 5313", 3, 2, 1500, 1));

    private static final List<EntityModel> LISTING_DATA =
            List.of(
                    new Listing(5000001, "123 Main St", Province.BRITISH_COLUMBIA, "Vancouver", ListingType.SALE, 500000, 1),
                    new Listing(5000002, "456 Elm St", Province.ONTARIO, "Toronto", ListingType.SALE, 700000, 1),
                    new Listing(5000003, "2525 West Mall", Province.ONTARIO, "Toronto", ListingType.RENT, 2500, 1),
                    new Listing(5000004, "6331 Thunderbird Cres", Province.ALBERTA, "Calgary", ListingType.SALE, 1000000, 1),
                    new Listing(5000005, "202 Birch St", Province.MANITOBA, "Winnipeg", ListingType.RENT, 3000, 1));

    private static final List<EntityModel> HIRES_REA_DATA =
            List.of(
                    new HiresREA("123 456 7890", "234 567 8901"),
                    new HiresREA("164 551 5313", "345 678 9012"),
                    new HiresREA("345 678 9012", "123 456 7890"),
                    new HiresREA("234 567 8901", "456 789 0123"),
                    new HiresREA("456 789 0123", "164 551 5313"));

    private static final List<EntityModel> HIRES_CONTRACTOR_DATA =
            List.of(
                    new HiresContractor("123 456 7890", 3000001),
                    new HiresContractor("234 567 8901", 3000002),
                    new HiresContractor("345 678 9012", 3000003),
                    new HiresContractor("456 789 0123", 3000004),
                    new HiresContractor("164 551 5313", 3000005));

    private static final List<EntityModel> PAYS_DATA =
            List.of(
                    new Pays("123 456 7890", 4000001, 243),
                    new Pays("234 567 8901", 4000002, 542),
                    new Pays("345 678 9012", 4000003, 340),
                    new Pays("456 789 0123", 4000004, 459),
                    new Pays("164 551 5313", 4000005, 321));

    private static final List<EntityModel> MAINTAINS_DATA =
            List.of(
                    new Maintains(3000001, "123 Main St", Province.BRITISH_COLUMBIA, "Vancouver", "Landscaping"),
                    new Maintains(3000002, "456 Elm St", Province.ONTARIO, "Toronto", "Plumbing"),
                    new Maintains(3000003, "2525 West Mall", Province.ONTARIO, "Toronto", "Electrical"),
                    new Maintains(3000004, "6331 Thunderbird Cres", Province.ALBERTA, "Calgary", "HVAC"),
                    new Maintains(3000005, "202 Birch St", Province.MANITOBA, "Winnipeg", "Roofing"));

    private static final List<EntityModel> MANAGES_LISTING_DATA =
            List.of(
                    new ManagesListing("123 456 7890", 5000001),
                    new ManagesListing("234 567 8901", 5000002),
                    new ManagesListing("345 678 9012", 5000003),
                    new ManagesListing("456 789 0123", 5000004),
                    new ManagesListing("164 551 5313", 5000005));

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
