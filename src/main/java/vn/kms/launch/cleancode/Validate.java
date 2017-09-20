package vn.kms.launch.cleancode;

import java.util.*;

import static vn.kms.launch.cleancode.Constant.VALID_STATE_CODES;

/**
 * Created by vietha on 9/1/2017.
 */
public class Validate {

    private Contact contact;

    public Validate(Contact contact) {
        this.contact = contact;
    }

    public void validateContactAndErrorStatsTo(Map<String, Integer> countErrors, Map<String, String> errors) {
        validateFirstName(countErrors, errors);
        validateLastName(countErrors, errors);
        validateDOB(countErrors, errors);
        validateAddress(countErrors, errors);
        validateCity(countErrors, errors);
        validateState(countErrors, errors);
        validateZipCode(countErrors, errors);
        validateMobilePhone(countErrors, errors);
        validateEmail(countErrors, errors);
    }

    private void validateEmail(Map<String, Integer> countErrors, Map<String, String> errors) {
        if (!contact.getEmail().matches("^.+@.+\\..+$")) {
            errors.put("email", "'" + contact.getEmail() + "' is invalid email format");
            addCounts(countErrors, "email");
        }
    }

    private void validateMobilePhone(Map<String, Integer> countErrors, Map<String, String> errors) {
        if (!contact.getMobilePhone().matches("^\\d{3}\\-\\d{3}\\-\\d{4}$")) {
            errors.put("mobilePhone", "'" + contact.getMobilePhone() + "' is invalid format XXX-XXX-XXXX");
            addCounts(countErrors, "mobile_phone");
        }
    }

    private void validateZipCode(Map<String, Integer> countErrors, Map<String, String> errors) {
        if (!contact.getZipCode().matches("^\\d{4,5}$")) {
            errors.put("zipCode", "'" + contact.getZipCode() + "' is not four or five digits");
            addCounts(countErrors, "zip_code");
        }
    }

    private void validateState(Map<String, Integer> countErrors, Map<String, String> errors) {
        if (!VALID_STATE_CODES.contains(contact.getState())) {
            errors.put("state", "'" + contact.getState() + "' is incorrect state code");
            addCounts(countErrors, "state");
        }
    }

    private void validateCity(Map<String, Integer> countErrors, Map<String, String> errors) {
        if (contact.getCity().length() > 15) {
            errors.put("city", "'" + contact.getCity() + "''s length is over 15");
            addCounts(countErrors, "city");
        }
    }

    private void validateAddress(Map<String, Integer> countErrors, Map<String, String> errors) {
        if (contact.getAddress().length() > 20) {
            errors.put("address", "'" + contact.getAddress() + "''s length is over 20");
            addCounts(countErrors, "address");
        }
    }

    private void validateDOB(Map<String, Integer> countErrors, Map<String, String> errors) {
        if (contact.getDayOfBirth() == null || contact.getDayOfBirth().trim().length() != 10) {
            errors.put("dayOfBirth", "'" + contact.getDayOfBirth() + "' is invalid");
            addCounts(countErrors, "dayOfBirth");
        }
    }

    private void validateLastName(Map<String, Integer> countErrors, Map<String, String> errors) {
        if (contact.getLastName().trim().length() == 0) {
            errors.put("lastName", "is empty");
            addCounts(countErrors, "last_name");
        }
        if (contact.getLastName().length() > 10) {
            errors.put("lastName", "'" + contact.getLastName() + "''s length is over 10");
            addCounts(countErrors, "last_name");
        }
    }

    private void validateFirstName(Map<String, Integer> countErrors, Map<String, String> errors) {
        if (contact.getFirstName().trim().length() == 0) {
            errors.put("firstName", "is empty");
            addCounts(countErrors, "first_name");
        }
        if (contact.getFirstName().length() > 10) {
            errors.put("firstName", "'" + contact.getFirstName() + "''s length is over 10");
            addCounts(countErrors, "first_name");
        }
    }

    protected static void addCounts(Map<String, Integer> counts, String fieldName) {
        Integer count = counts.get(fieldName);
        if (count == null)
            count = 0;
        counts.put(fieldName, count + 1);
    }
}
