import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static junit.framework.TestCase.assertEquals;

import org.mockito.Mock;
import org.mockito.Mockito;

import DBClasses.*;
import DBFunctions.DBInterface;
/*
This unit testing code focuses on the earliest DBinterface functions.
It only works with the local postgres databases which were originally used for development and testing.
Some later changes in the code also create discrepancies between the tests and the current methods.

For each method we create a test input which has the same format as the string received from the Client
(which was originally a JSONObject converted to string)
 */

/*
public class TestDatabaseFunctions {

    @Test
    public void TestaddPatient() {
        //create new JSON Object = simulate the one we would receive from server
        String receivedString="{\"name\":\"Alexandre2\"," +
                "\"address\":\"11 Avenue Auguste Renoir\",\"dob\":\"10/01/2005\"," +
                "\"phoneNum\":\"+33645464748\",\"email\":\"alex.orsini@gmail.com\"}";
        try{
            JSONObject receivedJsondata = new JSONObject(receivedString);
            DBInterface dbInterface = new DBInterface();
            dbInterface.addPatient(receivedJsondata);
        }catch(Exception e){
            System.out.println("Exception occured while parsing JSON.");
            e.printStackTrace();
        }
    }
    /*
    @Test
    public void TestgetPatient(){
        try{
            JSONObject testjsondata = new JSONObject();
            testjsondata.put("firstName", "Alexandre");
            testjsondata.put("lastName", "Orsini");
            testjsondata.put("phoneNum", "+33645464748");
            testjsondata.put("address", "11 Avenue Auguste Renoir");
            testjsondata.put("email", "alex.orsini@gmail.com");
            testjsondata.put("dob", "10/10/2005");
            DBInterface dbinterface= new DBInterface();
            dbinterface.addPatient(testjsondata);
            System.out.println(dbinterface.getPatients(testjsondata));
            Assert.assertEquals(dbinterface.getPatients(testjsondata).get(0).firstName,"Alexandre");
        }
        catch(SQLException | JSONException | URISyntaxException e) {
            System.out.println("Error while executing SQL function in getPatient");
            e.printStackTrace();
        }
    }
    @Test
    public void TestaddDoctor() {
        //create new JSON Object = simulate the one we would receive from server
        String receivedString="{\"firstName\":\"Martin\",\"lastName\":\"Holloway\"," +
                "\"pagerNumber\":\"12345678\",\"email\":\"holloway@gmail.com\"}";
        try{
            JSONObject receivedJsondata = new JSONObject(receivedString);
            DBInterface dbInterface = new DBInterface();
            dbInterface.addPatient(receivedJsondata);
        }catch(Exception e){
            System.out.println("Exception occured while parsing JSON.");
            e.printStackTrace();
        }
    }
    @Test
    public void TestaddMC() {
        //create new JSON Object = simulate the one we would receive from server
        String receivedString="{\"name\":\"Fulham MC\", \"address\":\"Lillie Road\"}";
        try{
            JSONObject receivedJsondata = new JSONObject(receivedString);
            DBInterface dbInterface = new DBInterface();
            dbInterface.addMC(receivedJsondata);
        }catch(Exception e){
            System.out.println("Exception occured while parsing JSON.");
            e.printStackTrace();
        }
    }
    @Test
    public void TestgetDoctor(){
        try{
            JSONObject testjsondata = new JSONObject();
            testjsondata.put("firstName", "Martin");
            testjsondata.put("lastName", "Holloway");
            testjsondata.put("pagerNumber", "12345678");
            testjsondata.put("email", "holloway@gmail.com");
            DBInterface dbinterface= new DBInterface();
            dbinterface.addDoctor(testjsondata); //in case it doesn't already exist in the database
            System.out.println(dbinterface.getDoctor(testjsondata));
            Assert.assertEquals(dbinterface.getDoctor(testjsondata).get(0).firstName,"Martin");
        }
        catch(SQLException | JSONException e) {
            System.out.println("Error while executing SQL function in getPatient");
            e.printStackTrace();
        }
    }
    @Test
    public void TestgetMC(){
        try{
            JSONObject testjsondata = new JSONObject();
            testjsondata.put("name", "Fulham MC");
            testjsondata.put("address", "Lillie Road");
            DBInterface dbinterface= new DBInterface();
            dbinterface.addPatient(testjsondata);
            System.out.println(dbinterface.getMC(testjsondata));
            Assert.assertEquals(dbinterface.getMC(testjsondata).get(0).name,"Fulham MC");
        }
        catch(SQLException | JSONException e) {
            System.out.println("Error while executing SQL function in getPatient");
            e.printStackTrace();
        }
    }


}

*/