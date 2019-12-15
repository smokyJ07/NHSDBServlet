import DBFunctions.DBInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jdk.internal.jline.internal.Log;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Assert;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.logging.Logger;
import static DBFunctions.DBInterface.getPatients;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static junit.framework.TestCase.assertEquals;


public class TestDatabase {

    @Test
    public void testGetPatient() throws SQLException, JSONException {
        DBInterface dbInterface = new DBInterface();

        JSONObject person = new JSONObject();
        person.put("name", "Alejandra");

        try {
            dbInterface.getPatients(person);
        } catch (SQLException | URISyntaxException e) {
            e.printStackTrace();
        }
        dbInterface.closeConnection();

    }

    /*@Test
    public void testgetDoctor() throws SQLException, JSONException {
        DBInterface dbInterface = new DBInterface();
        JSONObject doctor = new JSONObject();
        doctor.put("name", "Martin");
        try {
            dbInterface.getDoctor(doctor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    /*@Test
    public void testgetMC() throws JSONException {
        JSONObject mc = new JSONObject();
        mc.put("name", "Chelsea Hospital");
        try {
            DBInterface.getMC(mc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    /*
    @Test
    public void testaddMC() throws SQLException, JSONException {
        DBInterface dbInterface = new DBInterface();
        JSONObject mc = new JSONObject();
        mc.put("address", "Kings Road");
        mc.put("name", "Chelsea Hospital");

        try {
            dbInterface.addMC(mc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    @Test
    public void testAddCases() throws SQLException, JSONException {
        // VERY USEFUL LINK
        // http://www.objgen.com/json
        DBInterface dbInterface = new DBInterface();
        String cases = "{\n" +
                "  \"casereport\": {\n" +
                "    \"patientid\": \"23\",\n" +
                "    \"doctorid\": \"7\",\n" +
                "    \"casenotes\": \"broken leg\",\n" +
                "    \"chronic_condition\": \"true\",\n" +
                "    \"datetime\": \"12-12-2019\"\n" +
                "  },\n" +
                "  \"medications\": [\n" +
                "    {\n" +
                "      \"casereportid\": \"1\",\n" +
                "      \"starttime\": \"01-12-2012\",\n" +
                "      \"endtime\": \"01-12-2012\",\n" +
                "      \"type\": \"cast for leg\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"casereportid\": \"1\",\n" +
                "      \"starttime\": \"01-12-2012\",\n" +
                "      \"endtime\": \"01-12-2012\",\n" +
                "      \"type\": \"anti-inflamation\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        JSONObject Object = new JSONObject(cases);
        dbInterface.addCase(Object);
    }

    @Test
    public void testGetCases() throws JSONException, SQLException {
        DBInterface dbInterface = new DBInterface();
        String message = "{\n" +
        "  \"function\": \"getCaseReports\",\n" +
        "  \"data\": \"23\"\n" +
        "}";
        JSONObject Object = new JSONObject(message);
        dbInterface.getCaseReport(Object);
    }

    @Test
    public void testcheckLogIn() throws JSONException, SQLException {

        DBInterface dbInterface = new DBInterface();
        String message = "{\n" +
                "  \"name\": \"Jonas\",\n" +
                "  \"password\": \"passdword\"\n" +
                "}";
        JSONObject Object = new JSONObject(message);
        dbInterface.checkLogIn(Object);
    }

}

