import DBFunctions.DBInterface;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Assert;

import java.sql.SQLException;
import java.util.logging.Logger;

import static DBFunctions.DBInterface.getPatient;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static junit.framework.TestCase.assertEquals;


public class TestDatabase {

    /*
      //  private static final Logger log= Logger.getLogger(DBClasses.Patient.class.getName());
      /*  @Test
        public void TestaddPatient(){
            DBClasses.Patient p1=new DBClasses.Patient("Martin", " Holloway", "888888888");

            Assert.assertThat(p1.firstName, is(equalTo("Martin")));
          //  System.out.println("Hi");
        }*/

        /*
        @Test
        public void TestaddDB() throws SQLException {
            // DBServlet info= new DBServlet();
            DBServlet.addPatient("Christine", "Speybrook", "888888888");
            DBServlet.getPatient("Speybrook");

          //  Assert.assertThat(s, is(equalTo("select * from patients where lastname = 'Speybrook';")));
    }
     */
/*
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
        @Test
    public void testGetPatient() throws SQLException, JSONException {
        //  addPatient(JSONObject data)
        JSONObject person = new JSONObject();
        person.put("lastName", "Gutierrez");
        person.put("firstName", "Alejandra");
        try {
            DBInterface.getPatient(person);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
/*
    @Test
    public void testgetMC() throws SQLException, JSONException {
        //  addPatient(JSONObject data)
        JSONObject mc = new JSONObject();
        mc.put("address", "Kings Road");
        mc.put("name", "Chelsea Hospital");
        try {
            DBInterface.getMC(mc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */

}

