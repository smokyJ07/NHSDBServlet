package DBFunctions;

import DBClasses.Patient;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInterface {

    static Statement s = null;

    public DBInterface(){
        String dbUrl = "jdbc:postgresql://localhost:5432/postgres";

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(dbUrl, "postgres", "aleseb99");
            s = conn.createStatement();
        }catch (Exception e) {
            System.out.println(e);
            String l=e.getMessage();
        }
    }


    public void addPatient(JSONObject data){
        try {
            Gson gson = new Gson();
            String patientData = data.get("patient").toString();
            Patient p = gson.fromJson(patientData, Patient.class);
            System.out.println(patientData);

            String message;
            message = "INSERT INTO patients (\"firstName\", \"lastName\", phonenumber) values ('"+p.firstName+"', '"+p.lastName+"','"+p.phoneNum+"');";
            s.execute(message);

        }catch(JSONException e){
            System.out.println("Error while executing JSON function in AddPatient");
        }catch(SQLException e){
            System.out.println("Error while executing SQL function in Patient");
        }
    }
}
