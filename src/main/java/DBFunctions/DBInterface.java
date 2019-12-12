package DBFunctions;
import DBClasses.CustomJson;
import DBClasses.Doctor;
import DBClasses.medicalCentre;
import DBClasses.Patient;
import DBClasses.medicalCentre;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBInterface {

    static Connection conn = null;
    static Statement s = null;
    public DBInterface(){

        //connection to heroku online postgresql DB
        /*try{
            conn = getConnection();
            Statement s = conn.createStatement();
            System.out.println("Connection open");

            createTables();
            s.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Connection failed");
        }*/

        //use this to connect to your local db: note to change password and username to whatever you use

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

    private static Connection getConnection() throws URISyntaxException, SQLException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        return DriverManager.getConnection(dbUrl);
    }

    public void closeConnection() {
        try {
            conn.close();
        }catch(SQLException e){
            System.out.println("Connection could'nt be closed.");
            e.printStackTrace();
        }
    }

    private boolean tableExists(String tableName){
        try {
            String queryMessage = String.format("SELECT EXISTS(SELECT 1 FROM information_schema.tables WHERE table_schema = 'public' AND table_name = '%s');", tableName);
            Statement s = conn.createStatement();
            ResultSet rset = s.executeQuery(queryMessage);
            boolean result = false;
            while(rset.next()){
                result = rset.getBoolean("exists");
                System.out.println(result);
            }
            rset.close();
            s.close();
            return result;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static String getPatients(JSONObject data) throws SQLException, URISyntaxException {
        new DBInterface();
        //getConnection();
        //ArrayList<Patient> list = new ArrayList<>();
        JSONArray myArray = new JSONArray();
        try {
            String Name = data.getString("name");


            String message = "select * from patients where \"name\" = '" + Name + "';";

            s.execute(message);
            ResultSet rset = s.executeQuery(message);
            System.out.println("hello");
            while (rset.next()) {
                JSONObject patient = new JSONObject();
                patient.put("name", rset.getString("name"));
                patient.put("phonenumber", rset.getString("phonenumber"));
                patient.put("address", rset.getString("address"));
                patient.put("dob", rset.getString("dob"));
                patient.put("email", rset.getString("email"));
                myArray.put(patient);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        CustomJson instruction = new CustomJson("getPatients", myArray);
        String message = instruction.toString();
        System.out.println(message);
        System.out.println("hello");
        return message;
    }

    public static void getDoctor(JSONObject data) throws SQLException {
        try {
            Gson gson = new Gson();
            String doctorData = data.toString();
            Doctor d = gson.fromJson(doctorData, Doctor.class);
            String message = "select * from doctors where \"lastName\" = '" + d.lastName +
                   "'and \"firstName\" = '"+d.firstName+"';";
            Statement s = conn.createStatement();
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
        try {
            Gson gson = new Gson();
            String MCData = data.toString();
            medicalCentre mc = gson.fromJson(MCData, medicalCentre.class);
            String message = "select * from medicalcentre where \"name\" = '" + mc.name +"';";
            Statement s = conn.createStatement();
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

    private void createTables(){
        System.out.println("Creating tables...");
        if(!tableExists("patients")){
            System.out.println("Creating patients table...");
            try {
                Statement s = conn.createStatement();
                String sql = "create table patients(id SERIAL PRIMARY KEY, firstname varchar(128) NOT NULL, " +
                        "lastname varchar(128) NOT NULL, phonenum varchar(32), address varchar (128), " +
                        "dob varchar(128), email varchar(128))";
                s.execute(sql);
                s.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(!tableExists("doctors")){
            System.out.println("Creating doctors table...");
            try {
                Statement s = conn.createStatement();
                String sql = "create table doctors(id SERIAL PRIMARY KEY, firstname varchar(128) NOT NULL, " +
                        "lastname varchar(128) NOT NULL, pagernum varchar(32), email varchar(128))";
                s.execute(sql);
                s.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(!tableExists("medicalcentres")){
            System.out.println("Creating medicalcentres table...");
            try {
                Statement s = conn.createStatement();
                String sql = "create table medicalcentres(id SERIAL PRIMARY KEY, name varchar(128) NOT NULL, " +
                        "address varchar (128))";
                s.execute(sql);
                s.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        System.out.println("All required tables exist.");
    }

    public void addPatient(JSONObject data){
        try {
            Gson gson = new Gson();
            String patientData = data.toString();
            Patient p = gson.fromJson(patientData, Patient.class);
            System.out.println(patientData);

            String message;
            message = "INSERT INTO patients (name, \"lastname\", \"phonenum\", \"address\", \"dob\", \"email\") values ('"+p.firstName+"', '"+p.lastName+"','"+p.phoneNum+"','"+p.address+"','"+p.dob+"' ,'"+p.email+"');";
            Statement s = conn.createStatement();
            s.execute(message);
            s.close();
            System.out.println("Added patient with data:" + patientData);
        }catch(SQLException e){
            System.out.println("Error while executing SQL function in addPatient");
            e.printStackTrace();
        }
    }

    public void addDoctor(JSONObject data){
        try {
            Gson gson = new Gson();
            String doctorData = data.toString();
            Doctor d = gson.fromJson(doctorData, Doctor.class);

            String message;
            Statement s = conn.createStatement();
            message = "INSERT INTO doctors (\"firstName\", \"lastName\", \"pagerNumber\", \"email\") values " +
                    "('"+d.firstName+"', '"+d.lastName+"','"+d.pagerNum+"', '"+d.email+"');";
            s.execute(message);
            s.close();
            System.out.println("Added doctor with data:" + doctorData);

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
            message = "INSERT INTO medicalcentres (name, address) values ('"+mc.name+"', '"+mc.address+"');";
            //  INSERT INTO medicalCentre (name, address) values ('hello', 'medicalcentre');
            Statement s = conn.createStatement();
            s.execute(message);
            s.close();
            System.out.println("Added MC with data:" + MCData);

        }catch(SQLException e){
            System.out.println("Error while executing SQL function in addMC");
            e.printStackTrace();
        }
    }

}