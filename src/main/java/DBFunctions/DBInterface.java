package DBFunctions;

import DBClasses.Doctor;
import DBClasses.medicalCentre;
import DBClasses.Patient;
import DBClasses.medicalCentre;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBInterface {

    static Statement s = null;

    public DBInterface(){
        String dbUrl = "jdbc:postgresql://localhost:5432/postgres";

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(dbUrl, "postgres", "Chhoopnege2k22");
            s = conn.createStatement();
        }catch (Exception e) {
            System.out.println(e);
            String l=e.getMessage();
        }
    }

    public static ArrayList<Patient> getPatient(JSONObject data) throws SQLException {
        new DBInterface();
        ArrayList<Patient> output= new ArrayList();
        try {
            Gson gson = new Gson();
            String patientData = data.toString();
            Patient p = gson.fromJson(patientData, Patient.class);
            String message = "select * from patients where \"lastname\" = '" + p.lastName +
                    "' and \"firstname\" = '"+p.firstName+"';";
            s.execute(message);

            ResultSet rset = s.executeQuery(message);
            while(rset.next()) {
                Patient newP = new Patient(rset.getString("firstname"), rset.getString("lastname"), rset.getString("phonenum"), rset.getString("address"), rset.getString("dob"), rset.getString("email"));
                System.out.println(newP.firstName);
                System.out.println(newP.lastName);
                output.add(newP);
            }
        }
        catch(SQLException e) {
            System.out.println("Error while executing SQL function in getPatient");
            e.printStackTrace();
            }
        return output;
    }

    public static void getDoctor(JSONObject data) throws SQLException {
        new DBInterface();
        try {
            Gson gson = new Gson();
            String doctorData = data.toString();
            Doctor d = gson.fromJson(doctorData, Doctor.class);
            String message = "select * from doctors where \"lastName\" = '" + d.lastName +
                   "'and \"firstName\" = '"+d.firstName+"';";
            s.execute(message);

            ResultSet rset = s.executeQuery(message);
            while(rset.next()) {
                Doctor newD = new Doctor(rset.getString("firstName"), rset.getString("lastName"),
                        rset.getString("pagerNumber"), rset.getString("email"));
                System.out.println(newD.firstName);
                System.out.println(newD.lastName);
            }
        }
        catch(SQLException e) {
            System.out.println("Error while executing SQL function in addPatient");
            e.printStackTrace();
        }
    }

    public static void getMC(JSONObject data) throws SQLException {
        System.out.println("i got here");
        new DBInterface();
        try {
            Gson gson = new Gson();
            String MCData = data.toString();
            medicalCentre mc = gson.fromJson(MCData, medicalCentre.class);
            String message = "select * from medicalcentre where \"name\" = '" + mc.name +"';";
            s.execute(message);

            ResultSet rset = s.executeQuery(message);
            while(rset.next()) {
                medicalCentre newMC = new medicalCentre(rset.getString("name"), rset.getString("address"));
                System.out.println(newMC.name);
                System.out.println(newMC.address);
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
            message = "INSERT INTO patients (\"firstName\", \"lastName\", \"phonenumber\", \"address\", \"dob\", \"email\") " +
                    "values ('"+p.firstName+"', '"+p.lastName+"','"+p.phoneNum+"','"+p.address+"','"+p.dob+"' ,'"+p.email+"');";
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
            message = "INSERT INTO doctors (\"firstName\", \"lastName\", \"pagerNumber\", \"email\") values " +
                    "('"+d.firstName+"', '"+d.lastName+"','"+d.pagerNum+"', '"+d.email+"');";
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
            medicalCentre mc = gson.fromJson(MCData, medicalCentre.class);
            System.out.println(MCData);

            String message;
            message = "INSERT INTO medicalcentre (name, address) values ('"+mc.name+"', '"+mc.address+"');";
            System.out.println(mc.name);
            s.execute(message);

        }catch(SQLException e){
            System.out.println("Error while executing SQL function in addMC");
            e.printStackTrace();
        }
    }

}