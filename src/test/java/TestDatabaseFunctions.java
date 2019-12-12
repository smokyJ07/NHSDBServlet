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

//So far (05/12/19): testing DBInterface functions from stage where we have received Json as a string (so no simulation of Httprequest/ response)
// 9/12/19 : changed it to stage where servlet has extracted 'data' string from the customjson string: inout argument for all functions = data string
// in TestdoPost : will test the extracting part later

public class TestDatabaseFunctions {


    @Test
    public void TestaddPatient() {
        //create new JSON Object = simulate the one we would receive from server
        String receivedString="{\"firstName\":\"Alexandre\",\"lastName\":\"Orsini\"," +
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
    @Test
    public void TestgetPatient(){
        try{
        JSONObject testjsondata = new JSONObject();
            testjsondata.put("firstname", "Alexandre");
            testjsondata.put("lastname", "Orsini");
            testjsondata.put("phonenum", "+33645464748");
            testjsondata.put("address", "11 Avenue Auguste Renoir");
            testjsondata.put("email", "alex.orsini@gmail.com");
            testjsondata.put("dob", "10/10/2005");
            DBInterface dbinterface= new DBInterface();
            System.out.println(dbinterface.getPatient(testjsondata));
            Assert.assertEquals(dbinterface.getPatient(testjsondata).get(0).firstName,"Alexandre");
        }
        catch(SQLException | JSONException e) {
            System.out.println("Error while executing SQL function in getPatient");
            e.printStackTrace();
        }
    }
}
