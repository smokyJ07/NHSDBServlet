import DBFunctions.DBInterface;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Assert;

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
        //  addPatient(JSONObject data)
        JSONObject person = new JSONObject();
        person.put("name", "Alejandra");

        try {
            DBInterface.getPatients(person);
        } catch (SQLException | URISyntaxException e) {
            e.printStackTrace();
        }
    }


        /*
    @Test
    public void TestaddPatient() {
        DBClasses.Patient p1 = new DBClasses.Patient("Martin", " Holloway", "888888888",
                "London", "01.01.01", "m.ho@ic.ac.uk");
        Assert.assertThat(p1.firstName, is(equalTo("Martin")));
    }

    @Test
    public void testgetDoctor() throws SQLException, JSONException {
        //  addPatient(JSONObject data)
        JSONObject doctor = new JSONObject();
        doctor.put("lastname", "Holloway");
        doctor.put("firstname", "Martin");
        try {
            DBInterface.getDoctor(doctor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */

}

