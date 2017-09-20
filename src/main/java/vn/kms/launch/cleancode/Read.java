package vn.kms.launch.cleancode;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static vn.kms.launch.cleancode.Constant.MAX_CONTACT_FILE_SIZE;


/**
 * Created by vietha on 9/1/2017.
 */
public class Read {
    private static final Logger LOGGER = Logger.getLogger( Read.class.getName() );

    private Read() {
    }

    protected static void readContactDataFromFile(List<Contact> allContacts, String filePath) throws IOException {
        String s = readAllTextToString(filePath);

        String[] lines = s.split("\r"); // get all lines
        Map<String, Integer> map = new HashMap<>();

        // read header to a map
        String[] data = lines[0].split("\t"); // get data of a line
        for (int k = 0; k < data.length; ++k)
            map.put(data[k], k);

        for (int i = 1; i < lines.length; i++) {
            data = lines[i].split("\t"); // get data of a line
            if (data.length != map.size()) { // invalid line format
                continue;
            }

            // TODO: I will use refection & annotations to build contact object later
            Contact contact = new Contact();
            try {
                // FIXME: Don't forget to change this code if first column is not ID
                contact.setId(Integer.parseInt(data[map.get("id")]));
            } catch (Exception ex) {
                LOGGER.log(Level.FINE, "Not ID format", ex);
                continue;
            }

            contact.setFirstName(data[map.get("first_name")]); // first_name
            contact.setLastName(data[map.get("last_name")]); // last_name
            contact.setDayOfBirth(data[map.get("date_of_birth")]); // dayOfBirth
            contact.setAddress(data[map.get("address")]); // address
            contact.setCity(data[map.get("city")]); // city
            contact.setState(data[map.get("state")]); // state
            contact.setZipCode(data[map.get("zip")]); // zip
            contact.setMobilePhone(data[map.get("phone1")]); // phone1
            contact.setEmail(data[map.get("email")]); // email

            allContacts.add(contact);
        }
    }

    private static String readAllTextToString(InputStream is) throws IOException {
        char[] buff = new char[MAX_CONTACT_FILE_SIZE]; // guest file size is not greater than 100000 chars
        int b; // read one character
        int c = 0; // count total characters in file
        while ((b = is.read()) != -1) {
            buff[c] = (char) b;
            c++;
        }
        return new String(buff, 0, c);
    }

    private static String readAllTextToString(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
    }
}
