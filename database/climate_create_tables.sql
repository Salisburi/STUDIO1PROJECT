-- Create tables for Semester 1 2023 CTG example ER Model
PRAGMA foreign_keys = OFF;
drop table if exists Country;
drop table if exists Date;
PRAGMA foreign_keys = ON;

CREATE TABLE Country (
   CountryCode TEXT NOT NULL,
   CountryName Text,
   PRIMARY KEY (CountryCode)
);

CREATE TABLE Date (
   Year NOT NULL,
   PRIMARY KEY (Year)
);

CREATE TABLE CityTempObservation (
    CountryCode    TEXT,
    City           TEXT,
    Year           TEXT,
    Latitude       TEXT,
    Longitude      TEXT,
    AvgTemperature REAL,
    MinTemperature REAL,
    MaxTemperature REAL,
    PRIMARY KEY (
        CountryCode,
        City,
        Year,
        Latitude,
        Longitude
    ),
    FOREIGN KEY (
        CountryCode
    )
    REFERENCES Country (CountryCode),
    FOREIGN KEY (
        Year
    )
    REFERENCES Date (Year) 
);

CREATE TABLE CountryTempPopulation (
    CountryCode TEXT    NOT NULL,
    Year        INT     NOT NULL,
    AvgTemp     REAL,
    MinTemp     REAL,
    MaxTemp     REAL,
    Population  INTEGER,
    PRIMARY KEY (
        CountryCode,
        Year
    ),
    FOREIGN KEY (
        CountryCode
    )
    REFERENCES Country (CountryCode),
    FOREIGN KEY (
        Year
    )
    REFERENCES Date (Year) 
);

CREATE TABLE GlobalTempObservation (
    Year                    INTEGER,
    AvgTemperature          REAL,
    MinTemperature          REAL,
    MaxTemperature          REAL,
    LandOceanAvgTemperature REAL,
    LandOceanMinTemperature REAL,
    LandOceanMaxTemperature REAL,
    PRIMARY KEY (
        Year
    )
);

CREATE TABLE StateTempObservation (
    State              TEXT,
    Country            TEXT,
    Year               INTEGER,
    AvgTemperature     REAL,
    MinTemperature     REAL,
    MaximumTemperature REAL,
    PRIMARY KEY (
        State,
        Country,
        Year
    ),
    FOREIGN KEY (
        Year
    )
    REFERENCES Date (Year) 
);

CREATE TABLE WorldPopulation (
    Year         NOT NULL,
    CountryCode  NOT NULL,
    Population,
    PRIMARY KEY (
        Year,
        CountryCode
    ),
    FOREIGN KEY (
        CountryCode
    )
    REFERENCES Country (CountryCode),
    FOREIGN KEY (
        Year
    )
    REFERENCES Date (Year) 
);

