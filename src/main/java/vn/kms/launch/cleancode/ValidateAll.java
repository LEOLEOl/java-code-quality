package vn.kms.launch.cleancode;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static vn.kms.launch.cleancode.Constant.YEAR_OF_REPORT;

/**
 * Created by vietha on 9/1/2017.
 */
public class ValidateAll {
    private List<Contact> contacts;

    public ValidateAll(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Map validateContactsAndErrorStatsTo(Map<String, Integer> countErrors) {
        Map<Integer, Map<String, String>> invalidContacts = new LinkedHashMap<>(); // invalidContacts order by ID

        for (Contact contact : contacts) {
            Map<String, String> errors = new TreeMap<>(); // errors order by field name

            // Validate contact
            new Validate(contact).validateContactAndErrorStatsTo(countErrors, errors);

            if (!errors.isEmpty()) { // if contact is invalid, store it and errors into invalidContacts
                invalidContacts.put(contact.getId(), errors);
            } else { // populate other fields from raw fields
                contact.setAge(calculateAgeByDOB(contact.getDayOfBirth())); // Reset to real age
            }
        }
        return invalidContacts;
    }

    private static int calculateAgeByDOB(String dateOfBirth) {
        String yearStr = dateOfBirth.split("/")[2];
        int year = Integer.parseInt(yearStr);
        return YEAR_OF_REPORT - year;
    }
}
