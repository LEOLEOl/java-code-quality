package vn.kms.launch.cleancode;

import java.util.List;
import java.util.Map;

/**
 * Created by vietha on 9/1/2017.
 */
public abstract class Sort {
    private Sort() {
    }

    public static void sortContact(Map<Integer, Map<String, String>> invalidContacts, List<Contact> allContacts, Contact.CompareContactFunction cmpFunc) {
        for (int i = 0; i < allContacts.size(); i++) {
            for (int j = allContacts.size() - 1; j > i; j--) {
                Contact contactA = allContacts.get(i);
                Contact contactB = allContacts.get(j);
                if (!invalidContacts.containsKey(contactA.getId())
                        && !invalidContacts.containsKey(contactB.getId())
                        && cmpFunc.compare(contactA, contactB) > 0) {
                    allContacts.set(i, contactB);
                    allContacts.set(j, contactA);
                }
            }
        }
    }

    public static void qSortContact(Map<Integer, Map<String, String>> invalidContacts, List<Contact> allContacts, Contact.CompareContactFunction cmpFunc) {
        qSortContact(invalidContacts, allContacts, 0, allContacts.size() - 1, cmpFunc);
    }

    private static Contact choosePivot(Map<Integer, Map<String, String>> invalidContacts, List<Contact> allContacts, int l, int r) {
        int i = l;
        while (invalidContacts.containsKey(allContacts.get(i).getId()) && i < r)
            ++i;

        if (i <= r) return allContacts.get(i);
        else return null;
    }

    private static void qSortContact(Map<Integer, Map<String, String>> invalidContacts, List<Contact> allContacts, int l, int r, Contact.CompareContactFunction cmpFunc) {
        int i = l;
        int j = r;
        Contact contactKey = choosePivot(invalidContacts, allContacts, l, r);
        if (contactKey == null) return;

        while (i <= j) {
            while (i < r
                    && (invalidContacts.containsKey(allContacts.get(i).getId())
                    || cmpFunc.compare(allContacts.get(i), contactKey) < 0)) ++i;
            while (j > l
                    && (invalidContacts.containsKey(allContacts.get(j).getId())
                    || cmpFunc.compare(allContacts.get(j), contactKey) > 0)) --j;

            /** NOTE:
             * The above code working correctly because the optimization of compiler, it is not fully logic at all.
             * The following code is fully logic, pay attention.
             while (i < r) {
             if (invalidContacts.containsKey(allContacts.get(i).getId()))
             ++i;
             else if (cmpFunc.compare(allContacts.get(i), contactKey) < 0)
             ++i;
             else break;
             }

             while (j > l) {
             if (invalidContacts.containsKey(allContacts.get(j).getId()))
             --j;
             else if (cmpFunc.compare(allContacts.get(j), contactKey) > 0)
             --j;
             else break;
             }
             */

            if (i <= j) {
                if (i < j) {
                    // Swap
                    Contact contactAi = allContacts.get(i);
                    Contact contactAj = allContacts.get(j);
                    allContacts.set(i, contactAj);
                    allContacts.set(j, contactAi);
                }
                ++i;
                --j;
            }
        }
        if (l < j) qSortContact(invalidContacts, allContacts, l, j, cmpFunc);
        if (i < r) qSortContact(invalidContacts, allContacts, i, r, cmpFunc);
    }

}
