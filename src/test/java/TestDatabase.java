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

    @Test
    public void testgetDoctor() throws SQLException, JSONException {

        JSONObject doctor = new JSONObject();
        doctor.put("name", "Martin");
        try {
            DBInterface.getDoctor(doctor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testgetMC() throws JSONException {
        JSONObject mc = new JSONObject();
        mc.put("name", "Chelsea Hospital");
        try {
            DBInterface.getMC(mc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testaddMC() throws SQLException, JSONException {

        JSONObject mc = new JSONObject();
        mc.put("address", "Kings Road");
        mc.put("name", "Chelsea Hospital");

        try {
            DBInterface.addMC(mc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCases() throws SQLException, JSONException {
        String cases = "{\n" +
                "  \"case\": {\n" +
                    "    \"casereport\": {\n" +
                        "      \"patient_id\": \"1\",\n" +
                        "      \"doctor_id\": \"1\",\n" +
                        "      \"casenotes\": \"Mr. is sick\",\n" +
                        "      \"chronic_condition\": \"0\",\n" +
                        "      \"datetime\": \"1999-01-08\"\n" +
                        "    },\n" +
                    "    \"medication\": {\n" +
                        "      \"casereportid\": \"1\",\n" +
                        "      \"starttime\": \"12.12.12\",\n" +
                        "      \"endtime\": \"13.13.13\",\n" +
                        "      \"type\": \"ibuprofene\"\n" +
                        "    }\n" +
                "  }\n" +
                "}";

        JSONObject Object = new JSONObject(cases);
        System.out.println(Object);
        DBInterface.addCase(Object);

    }

}

