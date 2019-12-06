package DBFunctions;

import DBClasses.Doctor;
import DBClasses.medicalCentre;
import DBClasses.Patient;
import DBClasses.medicalCentre;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;

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

   // public static void getPatient(String toString) {
  //  }


    public static void getPatient(JSONObject data) throws SQLException {
        new DBInterface();
        try {
            Gson gson = new Gson();
            String patientData = data.toString();

            Patient p = gson.fromJson(patientData, Patient.class);
            System.out.println(data);
            String message = "select * from patients where \"lastName\" = '" + p.lastName + "';";
            s.execute(message);

            ResultSet rset = s.executeQuery(message);
            while(rset.next()) {
             //   System.out.println(rset.getInt("id") + " " + rset.getString("lastName"));
                Patient newP = new Patient(rset.getString("firstName"), rset.getString("lastName"),
                        rset.getString("phonenumber"), rset.getString("address"), rset.getString("email"));
                System.out.println(newP.firstName);
                System.out.println(newP.lastName);
                System.out.println(" ");
            }
        }
        catch(SQLException e) {
            System.out.println("Error while executing SQL function in addPatient");
            e.printStackTrace();
        }

    }

    public void addPatient(JSONObject data){
        try {
            Gson gson = new Gson();
            String patientData = data.toString();
            Patient p = gson.fromJson(patientData, Patient.class);
            System.out.println(patientData);

            String message;
            message = "INSERT INTO patients (\"firstName\", \"lastName\", \"phonenumber\", \"address\", \"dob\", \"email\") values ('"+p.firstName+"', '"+p.lastName+"','"+p.phoneNum+"','"+p.address+"','"+p.dob+"' ,'"+p.email+"');";
            s.execute(message);


        }catch(SQLException e){
            System.out.println("Error while executing SQL function in addPatient");
        }
    }

    public void addDoctor(JSONObject data){
        try {
            Gson gson = new Gson();
            String doctorData = data.toString();
            Doctor d = gson.fromJson(doctorData, Doctor.class);
            System.out.println(doctorData);

            String message;
            message = "INSERT INTO doctors (\"firstName\", \"lastName\", \"pagerNumber\", \"email\") values ('"+d.firstName+"', '"+d.lastName+"','"+d.pagerNum+"', '"+d.email+"');";
            s.execute(message);

        }catch(SQLException e){
            System.out.println("Error while executing SQL function in addDoctor");
            e.printStackTrace();
        }
    }

    public void addMC(JSONObject data){
        try {
            Gson gson = new Gson();
            String MCData = data.toString();
            medicalCentre m = gson.fromJson(MCData, medicalCentre.class);
            System.out.println(MCData);

            String message;
            message = "INSERT INTO medicalcentre (name, address) values ('"+m.name+"', '"+m.address+"');";
          //  INSERT INTO medicalCentre (name, address) values ('hello', 'medicalcentre');
            s.execute(message);

        }catch(SQLException e){
            System.out.println("Error while executing SQL function in addMC");
            e.printStackTrace();
        }
    }

}