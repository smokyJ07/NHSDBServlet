package DBFunctions;

import DBClasses.Patient;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

public class DBInterface {
    public DBInterface(){

    }

    public void addPatient(JSONObject data){
        try {
            Gson gson = new Gson();
            String patientData = data.get("patient").toString();
            Patient p = gson.fromJson(patientData, Patient.class);
            System.out.println(patientData);

        }catch(JSONException e){
            System.out.println("Error while executing addPatient function");
        }
    }
}
