package vn.kms.launch.cleancode;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static vn.kms.launch.cleancode.Constant.INPUT_DATA;
import static vn.kms.launch.cleancode.Read.readContactDataFromFile;
import static vn.kms.launch.cleancode.Report.generateReports;
import static vn.kms.launch.cleancode.Sort.qSortContact;
import static vn.kms.launch.cleancode.Write.storeAndReportToFileSystem;

public class Application {

    private static final Logger LOGGER = Logger.getLogger( Application.class.getName() );

    public static void main(String[] args) throws Exception {
        try {
            List<Contact> allContacts = new ArrayList<>(); // ArrayList to store all contact data
            Map<String, Integer> reportFieldErrorCounts = new TreeMap<>(); // I want to sort by key

            // 1. Load data from file
            readContactDataFromFile(allContacts, INPUT_DATA);

            // 2. Validate contact data
            // TODO: I will use refection & annotations to isValid contact data later
            Map invalidContacts = new ValidateAll(allContacts).validateContactsAndErrorStatsTo(reportFieldErrorCounts);

            // 3. Sort contact data by zipCode.
            qSortContact(invalidContacts, allContacts, (contactA, contactB) -> Integer.parseInt(contactA.getZipCode()) - Integer.parseInt(contactB.getZipCode()));

            // 4. Get all reports
            Map reports = generateReports(allContacts, invalidContacts, reportFieldErrorCounts);

            // 5. Write valid & invalid contacts, reports to file system
            storeAndReportToFileSystem(allContacts, invalidContacts, reports);

        } catch (FileNotFoundException e) {
            LOGGER.log(Level.FINER, "Error processing data", e);
        }
    }
}
