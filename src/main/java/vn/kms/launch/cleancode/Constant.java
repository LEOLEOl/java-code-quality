package vn.kms.launch.cleancode;

import java.time.Year;
import java.util.*;

/**
 * Created by vietha on 9/1/2017.
 */
public class Constant {
    private Constant() {
    }

    protected static final String REPORT_CONTACTS_PER_STATE = "report-contacts-per-state";
    protected static final String REPORT_CONTACTS_PER_AGE_GROUPS_AND_PERCENTAGE = "report-contacts-per-age-groups-and-percentage";
    protected static final String REPORT_INVALID_CONTACTS_PER_COLUMN_NAMES = "report-invalid-contacts-per-column-names";
    protected static final String STORE_VALID_CONTACTS = "store-valid-contacts";
    protected static final String STORE_INVALID_CONTACTS = "store-invalid-contacts";

    protected static final int MAX_CONTACT_FILE_SIZE = 16 * 1024 * 1024;

    protected static final int YEAR_OF_REPORT = Year.now().getValue();
    protected static Map<String, String> outFileHeaders = new HashMap<String, String>() {{
        put(STORE_INVALID_CONTACTS, "contact_id\terror_field\terror_message\r\n");
        put(STORE_VALID_CONTACTS, "id\tfirst_name\tlast_name\tday_of_birth\taddress\tcity\tstate\tzip_code\tmobile_phone\temail\r\n");
        put(REPORT_INVALID_CONTACTS_PER_COLUMN_NAMES, "field_name\tnumber_of_invalid_contact\r\n");
        put(REPORT_CONTACTS_PER_STATE, "state_code\tnumber_of_contact\r\n");
        put(REPORT_CONTACTS_PER_AGE_GROUPS_AND_PERCENTAGE, "group\tnumber_of_contact\tpercentage_of_contact\r\n");
    }};

    protected static final String INPUT_DATA = "data/big-contacts.tsv";
    protected static final String PATH_OUTPUT = "output";
    protected static final String[] AGE_GROUPS = {"Children", "Adolescent", "Adult", "Middle Age", "Senior"};

    protected static final Set<String> VALID_STATE_CODES = new HashSet<>(Arrays.asList("AL", "AK", "AS", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "GU", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MH", "MA", "MI", "FM", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "MP", "OH", "OK", "OR", "PW", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "VI", "WA", "WV", "WI", "WY"));
}
