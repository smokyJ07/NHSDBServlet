import org.json.JSONObject;
import org.junit.Test;
import org.junit.Assert;

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

public class TestDatabaseFunctions {


    @Test
    public void TestaddPatient() {
        //create new JSON Object = simulate the one we would receive from server
        String receivedString="{\"data\":{\"firstName\":\"Alexandre\",\"lastName\":\"Orsini\",\"address\":\"11 Avenue Auguste Renoir\",\"dob\":\"10/01/2005\",\"phoneNum\":\"+33645464748\",\"email\":\"alex.orsini@gmail.com\"},\"function\":\"addPatient\"}";
        try{
        JSONObject receivedJson = new JSONObject(receivedString);

        String function = (String) receivedJson.get("function");
        JSONObject data = (JSONObject) receivedJson.get("data");
        //execute correct function
        DBInterface dbInterface = new DBInterface();
        if (function.equals("addPatient")){
            dbInterface.addPatient(data);
            System.out.println("addPatient called");
        }
        else if(function.equals("addDoctor")){
            dbInterface.addDoctor(data);
            System.out.println("addDoctor called");
        }
        else if(function.equals("addMC")){
            dbInterface.addMC(data);
            System.out.println("addMC called");
        }
        else{
            System.out.println("No matching function found!");
        }
        }catch(Exception e){
        System.out.println("Exception occured while parsing JSON.");
        }



    }

}
