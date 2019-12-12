package DBFunctions;

import DBClasses.Doctor;
import DBClasses.medicalCentre;
import DBClasses.Patient;
import DBClasses.medicalCentre;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBInterface {

    static Connection conn = null;

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
            conn = DriverManager.getConnection(dbUrl, "postgres", "12345678");
            Statement s = conn.createStatement();
            System.out.println("Local connection open");

            createTables();
            s.close();
            conn.close();
        }catch (Exception e) {
            System.out.println("Local connection failed");
            e.printStackTrace();
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

    public static String getPatients(JSONObject data) throws SQLException {
        ArrayList<Patient> output= new ArrayList();
        try {
            Gson gson = new Gson();
            String patientData = data.toString();
            Patient p = gson.fromJson(patientData, Patient.class);
            String message = "select * from patients where \"name\" = '" + p.name + "';";
            Statement s = conn.createStatement();
            s.execute(message);

            ResultSet rset = s.executeQuery(message);
            while(rset.next()) {
                Patient newP = new Patient(rset.getString("name"),
                        rset.getString("phonenum"), rset.getString("address"), rset.getString("dob"),
                        rset.getString("email"));
                System.out.println(newP.name);
                output.add(newP);
            }
        }
        catch(SQLException e) {
            System.out.println("Error while executing SQL function in getPatient");
            e.printStackTrace();
            }
        return "hi";
    }

    public static void getDoctor(JSONObject data) throws SQLException {
        try {
            Gson gson = new Gson();
            String doctorData = data.toString();
            Doctor d = gson.fromJson(doctorData, Doctor.class);
            String message = "select * from doctors where \"name\" = '"+d.name+"';";
            Statement s = conn.createStatement();
            s.execute(message);

            ResultSet rset = s.executeQuery(message);
            while(rset.next()) {
                Doctor newD = new Doctor(rset.getString("nae"), rset.getString("pagerNumber"), rset.getString("email"));
                System.out.println(newD.name);
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
                String sql = "create table patients(id SERIAL PRIMARY KEY, name varchar(128) NOT NULL, " +
                        "phonenum varchar(32), address varchar (128), " +
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
                String sql = "create table doctors(id SERIAL PRIMARY KEY, name varchar(128) NOT NULL, " +
                        "pagernum varchar(32), email varchar(128))";
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
            message = "INSERT INTO patients (\"name\", \"phonenum\", \"address\", \"dob\", \"email\") values ('"+p.name+"','"+p.phoneNum+"','"+p.address+"','"+p.dob+"' ,'"+p.email+"');";
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
            message = "INSERT INTO doctors (\"name\", \"pagerNumber\", \"email\") values " +
                    "('"+d.name+"','"+d.pagerNum+"', '"+d.email+"');";
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