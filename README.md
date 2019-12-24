# MS3Challenge

## Purpose
The purpose of this repo is to parse data from a CSV file into valid records and bad records. Good records are stored into a SQLite database, while bad records are written to a new CSV file. Statistics (the total number of records, number of good records, number of bad records) are logged as well.

## Instructions for Developers
This is a Maven project. Developers must download Maven to run this application. The application relies on the JDBC API, and OpenCSV, an open-source library for parsing CSV files. The application is split into four classes: *Driver*, *RecordValidator*, *RecordWriter*, *StatsLogger*, and *Driver*.

The *RecordValidator* class determines if a record is valid or bad.

The *RecordWriter* class creates a table in the database, and writes records to the database.

The *StatsLogger* class logs statistics on CSV records to a log file.

The *Driver* class uses the above classes to validate records, write the valid records to a database, and log statistics to a log file. *Driver* implements CSVReader and CSVWriter (from the OpenCSV library) to read records from a CSV file, and to write bad records to a new CSV file, respectively.

## Approach
OpenCSV's CSVReader class is used to read records iteratively from a CSV file. In each iteration, statistics are calculated, the record is classified as valid or bad, and then written to the database if valid, or written to a CSV file if bad.

## Design choices
The application is split into microservices as opposed to a monolith. This approach was chosen for the purposes of efficiency and simplicity. The CSV records read into the application were not stored into an in-memory data structure, because storing a large data set in memory would harm efficiency. 

## Assumptions
It was assumed that the valid and bad records written to the database and bad CSV file, respectively, are correct. Proper testing can be done with JUnit in the future to verify the results.
