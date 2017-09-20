package vn.kms.launch.cleancode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static vn.kms.launch.cleancode.Constant.*;

/**
 * Created by vietha on 9/1/2017.
 */
public class Write {
    private static final Logger LOGGER = Logger.getLogger( Write.class.getName() );

    private Write() {
    }

    public static void storeAndReportToFileSystem(List<Contact> allContacts, Map<Integer, Map<String, String>> invalidContacts, Map reports) throws IOException {
        File outputFile = getOutputDirectory();
        for (Map.Entry<String, String> entry : outFileHeaders.entrySet()) {
            String fileName = entry.getKey();
            try (Writer writer = new FileWriter(new File(outputFile, fileName + ".tab"))) {
                writer.write(entry.getValue()); // write header

                Map<String, Integer> report = (Map<String, Integer>) reports.get(fileName);
                switch (fileName) {
                    case STORE_INVALID_CONTACTS:
                        storeInvalidContacts(invalidContacts, writer);
                        break;
                    case STORE_VALID_CONTACTS:
                        storeValidContacts(allContacts, invalidContacts, writer);
                        break;
                    case REPORT_INVALID_CONTACTS_PER_COLUMN_NAMES:
                        reportInvalidContactsPerColumnNames(report, writer);
                        break;
                    case REPORT_CONTACTS_PER_STATE:
                        reportContactsPerState(report, writer);
                        break;
                    case REPORT_CONTACTS_PER_AGE_GROUPS_AND_PERCENTAGE: default:
                        reportContactsPerAgeGroupsAndPercentage(report, writer);
                        break;
                }
                writer.flush();
                System.out.println("[Generated stored and reported files] at: " + "/output/" + fileName + ".tab");
            } catch (IOException e) {
                LOGGER.log(Level.FINER, "Cannot open " + fileName, e);
            }
        }
    }

    public static File getOutputDirectory() {
        File outputFile = new File(PATH_OUTPUT);
        if (!outputFile.exists())
            outputFile.mkdirs();
        return outputFile;
    }

    public static void storeValidContacts(List<Contact> allContacts, Map<Integer, Map<String, String>> invalidContacts, Writer writer) throws IOException {
        for (Contact contact : allContacts)
            if (!invalidContacts.containsKey(contact.getId()))
                writer.write(contact.toLine() + "\r\n");
    }

    private static void storeInvalidContacts(Map<Integer, Map<String, String>> invalidContacts, Writer writer) throws IOException {
        Set<Map.Entry<Integer, Map<String, String>>> entrySetI = invalidContacts.entrySet();
        for (Map.Entry<Integer, Map<String, String>> entryI : entrySetI) {
            Set<Map.Entry<String, String>> entrySetJ = entryI.getValue().entrySet();
            for (Map.Entry<String, String> entryJ : entrySetJ)
                writer.write(entryI.getKey() + "\t" + entryJ.getKey() + "\t" + entryJ.getValue() + "\r\n");
        }
    }

    private static void reportInvalidContactsPerColumnNames(Map<String, Integer> fieldErrorCounts, Writer writer) throws IOException {
        for (Map.Entry entry : fieldErrorCounts.entrySet())
            writer.write(entry.getKey() + "\t" + entry.getValue() + "\r\n");
    }

    private static void reportContactsPerState(Map<String, Integer> report, Writer writer) throws IOException {
        Map<String, Integer> newReport = new TreeMap<>(report); // I want to sort by state
        for (Map.Entry<String, Integer> entry : newReport.entrySet())
            writer.write(entry.getKey() + "\t" + entry.getValue()+ "\r\n");
    }

    private static void reportContactsPerAgeGroupsAndPercentage(Map<String, Integer> report, Writer writer) throws IOException {
        int total = 0;
        for (Integer v : report.values())
            total += v;

        if (total == 0) return;

        // I want to sort by age-group followed the requirement
        for (String item : AGE_GROUPS)
            writer.write(item + "\t" + report.get(item) + "\t" + Math.round(((report.get(item) * 100.0f) / total)) + "%\r\n");
    }
}
