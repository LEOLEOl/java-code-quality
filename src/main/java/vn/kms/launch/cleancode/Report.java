package vn.kms.launch.cleancode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static vn.kms.launch.cleancode.Constant.*;
import static vn.kms.launch.cleancode.Validate.addCounts;

/**
 * Created by vietha on 9/1/2017.
 */
public class Report {
    private Report() {
    }

    public static Map generateReports(List<Contact> allContacts, Map<Integer, Map<String, String>> invalidContacts, Map<String, Integer> reportFieldErrorCounts) {
        Map reports = new HashMap();
        Map<String, Integer> stateCounts = new HashMap<>();
        Map<String, Integer> ageGroupCounts = new HashMap<>();

        for (Contact contact : allContacts) {
            if (!invalidContacts.containsKey(contact.getId())) {
                addCounts(stateCounts, contact.getState());
                addCounts(ageGroupCounts, calculateAgeGroup(contact.getAge()));
            }
        }

        reports.put(REPORT_INVALID_CONTACTS_PER_COLUMN_NAMES, reportFieldErrorCounts);
        reports.put(REPORT_CONTACTS_PER_STATE, stateCounts);
        reports.put(REPORT_CONTACTS_PER_AGE_GROUPS_AND_PERCENTAGE, ageGroupCounts);
        return reports;
    }

    private static String calculateAgeGroup(int age) {
        if (age <= 9) {
            return AGE_GROUPS[0];
        } else if (age < 19) {
            return AGE_GROUPS[1];
        } else if (age <= 45) {
            return AGE_GROUPS[2];
        } else if (age <= 60) {
            return AGE_GROUPS[3];
        } else {
            return AGE_GROUPS[4];
        }
    }
}
