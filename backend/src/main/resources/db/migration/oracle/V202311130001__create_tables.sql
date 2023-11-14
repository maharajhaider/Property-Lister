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
);

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

CREATE TABLE ManagesListing
(
    realEstateAgentPhone CHAR(20),
    listingID            INTEGER,
    PRIMARY KEY (listingID, realEstateAgentPhone),
    FOREIGN KEY (realEstateAgentPhone) REFERENCES RealEstateAgent (phone)
        ON DELETE CASCADE,
    FOREIGN KEY (listingID) REFERENCES Listing (listingID)
        ON DELETE CASCADE
);
