CREATE TABLE Person
(
    phone CHAR(20) PRIMARY KEY,
    name  CHAR(255),
    email CHAR(255)
);

CREATE TABLE Homeowner
(
    phone CHAR(20) PRIMARY KEY,
    FOREIGN KEY (phone) REFERENCES Person (phone)
        ON DELETE CASCADE
);

CREATE TABLE RealEstateAgency
(
    agencyID INTEGER PRIMARY KEY,
    name     CHAR(255),
    rating   DOUBLE PRECISION
);

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
);

CREATE TABLE Developer
(
    developerLicenseID INTEGER PRIMARY KEY,
    name               CHAR(255)
);

CREATE TABLE ContractorCompany
(
    contractorID   INTEGER PRIMARY KEY,
    name           CHAR(255),
    chargeSchedule CHAR(255)
);

CREATE TABLE Strata
(
    strataID INTEGER PRIMARY KEY,
    name     VARCHAR(255)
);

CREATE TABLE City
(
    province CHAR(255),
    name     CHAR(255),
    taxRate  DOUBLE PRECISION,
    PRIMARY KEY (province, name)
);

CREATE TABLE Property
(
    streetAddress      CHAR(255),
    province           CHAR(255),
    cityName           CHAR(255),
    developerLicenseID INTEGER NOT NULL,
    strataID           INTEGER,
    homeownerPhone     CHAR(20),
    bedrooms           INTEGER,
    bathrooms          INTEGER,
    sizeInSqft         INTEGER,
    hasAC              Number(1, 0),
    PRIMARY KEY (streetAddress, province, cityName),
    FOREIGN KEY (province, cityName) REFERENCES City (province, name)
        ON DELETE CASCADE,
    FOREIGN KEY (strataID) REFERENCES Strata (strataID),
    FOREIGN KEY (homeownerPhone) REFERENCES Homeowner (phone),
    FOREIGN KEY (developerLicenseID) REFERENCES Developer (developerLicenseID)
        ON DELETE CASCADE
);

CREATE TABLE Listing
(
    listingID            INTEGER PRIMARY KEY,
    realEstateAgentPhone CHAR(20) NOT NULL,
    streetAddress        CHAR(255),
    province             CHAR(255),
    cityName             CHAR(255),
    type                 CHAR(255),
    price                INTEGER,
    active               NUMBER(1, 0),
    FOREIGN KEY (streetAddress, cityName, province) REFERENCES Property (streetAddress, cityName, province)
        ON DELETE CASCADE,
    FOREIGN KEY (realEstateAgentPhone) REFERENCES RealEstateAgent (phone),
    UNIQUE (streetAddress, cityName, province)
);

CREATE TABLE HiresREA
(
    homeownerPhone       CHAR(20),
    realEstateAgentPhone CHAR(20),
    PRIMARY KEY (homeownerPhone, realEstateAgentPhone),
    FOREIGN KEY (homeownerPhone) REFERENCES Homeowner (phone)
        ON DELETE CASCADE,
    FOREIGN KEY (realEstateAgentPhone) REFERENCES RealEstateAgent (phone)
        ON DELETE CASCADE
);

CREATE TABLE HiresContractor
(
    homeownerPhone CHAR(20),
    contractorID   INTEGER,
    PRIMARY KEY (homeownerPhone, contractorID),
    FOREIGN KEY (homeownerPhone) REFERENCES Homeowner (phone)
        ON DELETE CASCADE,
    FOREIGN KEY (contractorID) REFERENCES ContractorCompany (contractorID)
        ON DELETE CASCADE
);

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
);

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
);


INSERT INTO Person (phone, name, email)
VALUES ('123 456 7890', 'John Doe', 'john.doe@email.com');
INSERT INTO Person (phone, name, email)
VALUES ('234 567 8901', 'Jane Smith', 'jane.smith@email.com');
INSERT INTO Person (phone, name, email)
VALUES ('345 678 9012', 'Bob Johnson', 'bob.johnson@email.com');
INSERT INTO Person (phone, name, email)
VALUES ('456 789 0123', 'Alice Brown', 'alice.brown@email.com');
INSERT INTO Person (phone, name, email)
VALUES ('164 551 5313', 'Alice Brown', 'alice23@name.com');
INSERT INTO Person (phone, name, email)
VALUES ('703 582 1740', 'Danny Brown', 'dan56@name.com');
INSERT INTO Person (phone, name, email)
VALUES ('849 284 0582', 'John Smith', 'josh@name.com');
INSERT INTO Person (phone, name, email)
VALUES ('195 588 7561', 'Russel Johnson', 'brussel@name.com');

INSERT INTO Homeowner (phone)
VALUES ('123 456 7890');
INSERT INTO Homeowner (phone)
VALUES ('234 567 8901');
INSERT INTO Homeowner (phone)
VALUES ('345 678 9012');
INSERT INTO Homeowner (phone)
VALUES ('456 789 0123');
INSERT INTO Homeowner (phone)
VALUES ('164 551 5313');
INSERT INTO Homeowner (phone)
VALUES ('703 582 1740');
INSERT INTO Homeowner (phone)
VALUES ('849 284 0582');

INSERT INTO RealEstateAgency (agencyID, name, rating)
VALUES (1000001, 'ABC Realty', 4.5);
INSERT INTO RealEstateAgency (agencyID, name, rating)
VALUES (1000002, 'XYZ Realty', 4.2);
INSERT INTO RealEstateAgency (agencyID, name, rating)
VALUES (1000003, 'PQR Realty', 4.0);
INSERT INTO RealEstateAgency (agencyID, name, rating)
VALUES (1000004, 'Best Realtor', 4.2);
INSERT INTO RealEstateAgency (agencyID, name, rating)
VALUES (1000005, 'Dream Inc', 3.5);

INSERT INTO RealEstateAgent (phone, agentLicenseId, yearsOfExp, agencyID)
VALUES ('123 456 7890', 6000001, 5, 1000001);
INSERT INTO RealEstateAgent (phone, agentLicenseId, yearsOfExp, agencyID)
VALUES ('234 567 8901', 6000002, 3, 1000002);
INSERT INTO RealEstateAgent (phone, agentLicenseId, yearsOfExp, agencyID)
VALUES ('345 678 9012', 6000003, 7, 1000003);
INSERT INTO RealEstateAgent (phone, agentLicenseId, yearsOfExp, agencyID)
VALUES ('456 789 0123', 6000004, 0, 1000002);
INSERT INTO RealEstateAgent (phone, agentLicenseId, yearsOfExp, agencyID)
VALUES ('164 551 5313', 6000005, 12, 1000005);
INSERT INTO RealEstateAgent (phone, agentLicenseId, yearsOfExp, agencyID)
VALUES ('703 582 1740', 6000006, 3, 1000001);
INSERT INTO RealEstateAgent (phone, agentLicenseId, yearsOfExp, agencyID)
VALUES ('849 284 0582', 6000007, 4, 1000005);
INSERT INTO RealEstateAgent (phone, agentLicenseId, yearsOfExp, agencyID)
VALUES ('195 588 7561', 6000008, 6, 1000001);

INSERT INTO Developer (developerLicenseID, name)
VALUES (2000001, 'ABC Developers');
INSERT INTO Developer (developerLicenseID, name)
VALUES (2000002, 'XYZ Builders');
INSERT INTO Developer (developerLicenseID, name)
VALUES (2000003, 'UBC Builders');
INSERT INTO Developer (developerLicenseID, name)
VALUES (2000004, 'Thunder Developers');
INSERT INTO Developer (developerLicenseID, name)
VALUES (2000005, 'Blue Inc');

INSERT INTO ContractorCompany (contractorID, name, chargeSchedule)
VALUES (3000001, 'FixIt Inc.', 'Hourly');
INSERT INTO ContractorCompany (contractorID, name, chargeSchedule)
VALUES (3000002, 'FixAll Construction', 'Fixed Price');
INSERT INTO ContractorCompany (contractorID, name, chargeSchedule)
VALUES (3000003, 'Fix Roofing Inc', 'Hourly');
INSERT INTO ContractorCompany (contractorID, name, chargeSchedule)
VALUES (3000004, 'New Maintenance Company', 'Hourly');
INSERT INTO ContractorCompany (contractorID, name, chargeSchedule)
VALUES (3000005, 'FixAll Construction', 'Fixed Price');

INSERT INTO Strata (strataID, name)
VALUES (4000001, 'Sunny Heights Condos');
INSERT INTO Strata (strataID, name)
VALUES (4000002, 'Pineview Estates');
INSERT INTO Strata (strataID, name)
VALUES (4000003, 'Thunderbird Condos');
INSERT INTO Strata (strataID, name)
VALUES (4000004, 'KWTQ Estates');
INSERT INTO Strata (strataID, name)
VALUES (4000005, 'Marine Condos');

INSERT INTO City (province, name, taxRate)
VALUES ('AB', 'Calgary', 0.11);
INSERT INTO City (province, name, taxRate)
VALUES ('AB', 'Edmonton', 0.10);
INSERT INTO City (province, name, taxRate)
VALUES ('QC', 'Montreal', 0.14);
INSERT INTO City (province, name, taxRate)
VALUES ('MB', 'Winnipeg', 0.10);
INSERT INTO City (province, name, taxRate)
VALUES ('BC', 'Vancouver', 0.08);
INSERT INTO City (province, name, taxRate)
VALUES ('BC', 'Kelowna', 0.08);
INSERT INTO City (province, name, taxRate)
VALUES ('ON', 'Toronto', 0.10);
INSERT INTO City (province, name, taxRate)
VALUES ('ON', 'Ottawa', 0.10);

INSERT INTO Property
(streetAddress, province, cityName, developerLicenseID, strataID, homeownerPhone, bedrooms, bathrooms, sizeInSqft, hasAC)
VALUES ('123 Main St', 'BC', 'Vancouver', 2000001, 4000001, '123 456 7890', 2, 2, 1200, 1);
INSERT INTO Property
(streetAddress, province, cityName, developerLicenseID, strataID, homeownerPhone, bedrooms, bathrooms, sizeInSqft, hasAC)
VALUES ('456 Elm St', 'ON', 'Toronto', 2000002, 4000001, '234 567 8901', 3, 2, 1500, 0);
INSERT INTO Property
(streetAddress, province, cityName, developerLicenseID, strataID, homeownerPhone, bedrooms, bathrooms, sizeInSqft, hasAC)
VALUES ('2525 West Mall', 'ON', 'Toronto', 2000001, 4000005, '345 678 9012', 3, 2, 1500, 0);
INSERT INTO Property
(streetAddress, province, cityName, developerLicenseID, strataID, homeownerPhone, bedrooms, bathrooms, sizeInSqft, hasAC)
VALUES ('6331 Thunderbird Cres', 'AB', 'Calgary', 2000001, 4000004, '456 789 0123', 3, 2, 1500, 1);
INSERT INTO Property
(streetAddress, province, cityName, developerLicenseID, strataID, homeownerPhone, bedrooms, bathrooms, sizeInSqft, hasAC)
VALUES ('202 Birch St', 'MB', 'Winnipeg', 2000003, 4000002, '164 551 5313', 3, 2, 1500, 1);
INSERT INTO Property
(streetAddress, province, cityName, developerLicenseID, strataID, homeownerPhone, bedrooms, bathrooms, sizeInSqft, hasAC)
VALUES ('3001 Oak St', 'AB', 'Edmonton', 2000001, 4000004, '703 582 1740', 3, 2, 1500, 1);
INSERT INTO Property
(streetAddress, province, cityName, developerLicenseID, strataID, homeownerPhone, bedrooms, bathrooms, sizeInSqft, hasAC)
VALUES ('204 Birch St', 'MB', 'Winnipeg', 2000003, 4000002, '123 456 7890', 3, 2, 1500, 1);
INSERT INTO Property
(streetAddress, province, cityName, developerLicenseID, strataID, homeownerPhone, bedrooms, bathrooms, sizeInSqft, hasAC)
VALUES ('120 Reis Road', 'ON', 'Ottawa', 2000001, 4000005, '123 456 7890', 3, 2, 1500, 1);
INSERT INTO Property
(streetAddress, province, cityName, developerLicenseID, strataID, homeownerPhone, bedrooms, bathrooms, sizeInSqft, hasAC)
VALUES ('845 Cawston Ave', 'BC', 'Kelowna', 2000001, 4000001, '849 284 0582', 3, 2, 1500, 1);

INSERT INTO Listing (listingID, realEstateAgentPhone, streetAddress, province, cityName, type, price, active)
VALUES (5000001, '123 456 7890', '123 Main St', 'BC', 'Vancouver', 'sale', 500000, 1);
INSERT INTO Listing (listingID, realEstateAgentPhone, streetAddress, province, cityName, type, price, active)
VALUES (5000002, '234 567 8901', '456 Elm St', 'ON', 'Toronto', 'sale', 700000, 1);
INSERT INTO Listing (listingID, realEstateAgentPhone, streetAddress, province, cityName, type, price, active)
VALUES (5000003, '345 678 9012', '2525 West Mall', 'ON', 'Toronto', 'rent', 500000, 1);
INSERT INTO Listing (listingID, realEstateAgentPhone, streetAddress, province, cityName, type, price, active)
VALUES (5000004, '456 789 0123', '6331 Thunderbird Cres', 'AB', 'Calgary', 'sale', 100000, 1);
INSERT INTO Listing (listingID, realEstateAgentPhone, streetAddress, province, cityName, type, price, active)
VALUES (5000005, '164 551 5313', '202 Birch St', 'MB', 'Winnipeg', 'rent', 250000, 1);

INSERT INTO HiresREA (homeownerPhone, realEstateAgentPhone)
VALUES ('123 456 7890', '234 567 8901');
INSERT INTO HiresREA (homeownerPhone, realEstateAgentPhone)
VALUES ('164 551 5313', '345 678 9012');
INSERT INTO HiresREA (homeownerPhone, realEstateAgentPhone)
VALUES ('345 678 9012', '123 456 7890');
INSERT INTO HiresREA (homeownerPhone, realEstateAgentPhone)
VALUES ('234 567 8901', '456 789 0123');
INSERT INTO HiresREA (homeownerPhone, realEstateAgentPhone)
VALUES ('456 789 0123', '164 551 5313');

INSERT INTO HiresContractor (homeownerPhone, contractorID)
VALUES ('123 456 7890', 3000001);
INSERT INTO HiresContractor (homeownerPhone, contractorID)
VALUES ('234 567 8901', 3000002);
INSERT INTO HiresContractor (homeownerPhone, contractorID)
VALUES ('345 678 9012', 3000003);
INSERT INTO HiresContractor (homeownerPhone, contractorID)
VALUES ('456 789 0123', 3000004);
INSERT INTO HiresContractor (homeownerPhone, contractorID)
VALUES ('164 551 5313', 3000005);

INSERT INTO Pays (homeownerPhone, strataID, fee)
VALUES ('123 456 7890', 4000001, 243);
INSERT INTO Pays (homeownerPhone, strataID, fee)
VALUES ('234 567 8901', 4000002, 542);
INSERT INTO Pays (homeownerPhone, strataID, fee)
VALUES ('345 678 9012', 4000003, 340);
INSERT INTO Pays (homeownerPhone, strataID, fee)
VALUES ('456 789 0123', 4000004, 459);
INSERT INTO Pays (homeownerPhone, strataID, fee)
VALUES ('164 551 5313', 4000005, 321);

INSERT INTO Maintains (contractorID, streetAddress, province, cityName, areaOfResponsibility)
VALUES (3000001, '123 Main St', 'BC', 'Vancouver', 'Landscaping');
INSERT INTO Maintains (contractorID, streetAddress, province, cityName, areaOfResponsibility)
VALUES (3000002, '456 Elm St', 'ON', 'Toronto', 'Plumbing');
INSERT INTO Maintains (contractorID, streetAddress, province, cityName, areaOfResponsibility)
VALUES (3000003, '2525 West Mall', 'ON', 'Toronto', 'Electrical');
INSERT INTO Maintains (contractorID, streetAddress, province, cityName, areaOfResponsibility)
VALUES (3000004, '6331 Thunderbird Cres', 'AB', 'Calgary', 'HVAC');
INSERT INTO Maintains (contractorID, streetAddress, province, cityName, areaOfResponsibility)
VALUES (3000005, '202 Birch St', 'MB', 'Winnipeg', 'Roofing');
INSERT INTO Maintains (contractorID, streetAddress, province, cityName, areaOfResponsibility)
VALUES (3000002, '3001 Oak St', 'AB', 'Edmonton', 'Plumbing');
INSERT INTO Maintains (contractorID, streetAddress, province, cityName, areaOfResponsibility)
VALUES (3000002, '204 Birch St', 'MB', 'Winnipeg', 'Plumbing');
INSERT INTO Maintains (contractorID, streetAddress, province, cityName, areaOfResponsibility)
VALUES (3000002, '120 Reis Road', 'ON', 'Ottawa', 'Plumbing');
INSERT INTO Maintains (contractorID, streetAddress, province, cityName, areaOfResponsibility)
VALUES (3000002, '845 Cawston Ave', 'BC', 'Kelowna', 'Plumbing');
