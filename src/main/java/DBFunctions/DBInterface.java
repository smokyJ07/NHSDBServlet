package DBFunctions;
import DBClasses.*;
import DBClasses.medicalCentre;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.plaf.synth.SynthScrollBarUI;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBInterface {
    Statement s = null;
    static Connection conn = null;

    public DBInterface() throws SQLException {
//        //connection to heroku online postgresql DB
//        try{
//            conn = getConnection();
//            Statement s = conn.createStatement();
//            System.out.println("Connection open");
//
//            createTables();
//            s.close();
//        }catch(Exception e){
//            e.printStackTrace();
//            System.out.println("Connection failed");
//        }

        //use this to connect to your local db: note to change password and username to whatever you use
        String dbUrl = "jdbc:postgresql://localhost:5432/postgres";

        try {
           // conn.createStatement();
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(dbUrl, "postgres", "aleseb99");
            Statement s = conn.createStatement();
            System.out.println("Local connection open");

            createTables();
            s.close();
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
        if(!tableExists("medicalcentre")){
            System.out.println("Creating medicalcentre table...");
            try {
                Statement s = conn.createStatement();
                String sql = "create table medicalcentre(id SERIAL PRIMARY KEY, name varchar(128) NOT NULL, " +
                        "address varchar (128))";
                s.execute(sql);
                s.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

        if(!tableExists("medication")){
            System.out.println("Creating medication table...");
            try {
                Statement s = conn.createStatement();
                String sql = "create table medication(id SERIAL PRIMARY KEY, casereportid INT NOT NULL, " +
                        "start TIMESTAMP, end TIMESTAMP, type varchar(128))";
                s.execute(sql);
                s.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

        if(!tableExists("casereports")){
            System.out.println("Creating case reports table...");
            try {
                Statement s = conn.createStatement();
                String sql = "create table casereports(id SERIAL PRIMARY KEY, patientid INT NOT NULL, " +
                        "doctorid INT NOT NULL, datetime TIMESTAMP, casenotes TEXT, chronic_condition BOOLEAN NOT NULL)";
                s.execute(sql);
                s.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

        System.out.println("All required tables exist.");
    }

    public static String getPatients(JSONObject data) throws SQLException, URISyntaxException {
        new DBInterface();
        JSONArray myArray = new JSONArray();

        try {
            String Name = data.getString("name");
            String message = "select * from patients where \"name\" = '" + Name + "';";
            Statement s = conn.createStatement();
            s.execute(message);

            ResultSet rset = s.executeQuery(message);
            while (rset.next()) {
                JSONObject patient = new JSONObject();
                patient.put("name", rset.getString("name"));
                patient.put("phoneNum", rset.getString("phonenumber"));
                patient.put("address", rset.getString("address"));
                patient.put("dob", rset.getString("dob"));
                patient.put("email", rset.getString("email"));
                patient.put("id", rset.getString("id"));
                myArray.put(patient);
            }
          // String test = "SELECT id FROM patients WHERE id = (SELECT MAX(id) FROM patients)";

        }catch(JSONException e){
            e.printStackTrace();
        }
        CustomJson instruction = new CustomJson("getPatients", myArray);
        String message = instruction.toString();
        System.out.println(message);

        return message;
    }

    public static String getDoctor(JSONObject data) throws SQLException {
        new DBInterface();
        JSONArray myArray = new JSONArray();
        try {
            String Name = data.getString("name");
            String message = "select * from doctors where \"name\" = '" + Name + "';";
            Statement s = conn.createStatement();
            s.execute(message);
            ResultSet rset = s.executeQuery(message);
            while (rset.next()) {
                JSONObject doctor = new JSONObject();
                doctor.put("name", rset.getString("name"));
                doctor.put("pagerNumber", rset.getString("pagernumber"));
                doctor.put("email", rset.getString("email"));
                myArray.put(doctor);
            }
        }catch(SQLException e) {
            System.out.println("Error while executing SQL function in addPatient");
            e.printStackTrace();
        }catch(JSONException e){
            e.printStackTrace();
        }
        CustomJson instruction = new CustomJson("getDoctor", myArray);
        String message = instruction.toString();
        System.out.println(message);

        return message;
    }

    public static String getMC(JSONObject data) throws SQLException {
        new DBInterface();
        JSONArray myArray = new JSONArray();
        try {
            String Name = data.getString("name");
            String message = "select * from medicalcentre where \"name\" = '" + Name + "';";
            Statement s = conn.createStatement();
            s.execute(message);
            ResultSet rset = s.executeQuery(message);
            while (rset.next()) {
                JSONObject mc = new JSONObject();
                mc.put("name", rset.getString("name"));

                myArray.put(mc);
            }
        }catch(SQLException e) {
            System.out.println("Error while executing SQL function in addPatient");
            e.printStackTrace();
        }catch(JSONException e){
            e.printStackTrace();
        }

        CustomJson instruction = new CustomJson("getMC", myArray);
        String message = instruction.toString();
        System.out.println(message);

        return message;
    }

    public void addPatient(JSONObject data) throws SQLException {
        new DBInterface();
        try {
            Gson gson = new Gson();
            String patientData = data.toString();
            Patient p = gson.fromJson(patientData, Patient.class);
            System.out.println(patientData);

            String message;
            message = "INSERT INTO patients (\"name\", \"phonenumber\", \"address\", \"dob\", \"email\") values ('"+p.name+"','"+p.phoneNum+"','"+p.address+"','"+p.dob+"' ,'"+p.email+"');";
            Statement s = conn.createStatement();
            s.execute(message);
            s.close();
            System.out.println("Added patient with data:" + patientData);
        }catch(SQLException e){
            System.out.println("Error while executing SQL function in addPatient");
            e.printStackTrace();
        }
    }

    public void addDoctor(JSONObject data) throws SQLException {
        new DBInterface();
        try {
            Gson gson = new Gson();
            String doctorData = data.toString();
            Doctor d = gson.fromJson(doctorData, Doctor.class);

            String message;
            Statement s = conn.createStatement();
            message = "INSERT INTO doctors (\"name\", \"pagernum\", \"email\") values " +
                    "('"+d.name+"','"+d.pagerNum+"', '"+d.email+"');";
            s.execute(message);
            s.close();
            System.out.println("Added doctor with data:" + doctorData);

        }catch(SQLException e){
            System.out.println("Error while executing SQL function in addDoctor");
            e.printStackTrace();
        }
    }

    public static void addMC(JSONObject data) throws SQLException {
        new DBInterface();
        try {
            Gson gson = new Gson();
            String MCData = data.toString();
            medicalCentre mc = gson.fromJson(MCData, medicalCentre.class);
            System.out.println(MCData);

            String message = "INSERT INTO medicalcentre (name, address) values ('"+mc.name+"', '"+mc.address+"');";
            Statement s = conn.createStatement();
            s.execute(message);
            s.close();
            System.out.println("Added MC with data:" + MCData);

        }catch(SQLException e){
            System.out.println("Error while executing SQL function in addMC");
            e.printStackTrace();
        }
    }

    public static void addCase(JSONObject data) throws SQLException {
        new DBInterface();
        try{
            // --- FOR CASE REPORTS ---//
            Statement s = conn.createStatement();
            String cas = data.getString("casereport");
            Gson gson = new Gson();
            CaseReport cases = gson.fromJson(cas, CaseReport.class);

            String message = "INSERT INTO casereports (patientid, doctorid , casenotes, chronic_condition, datetime)"+
                    " values ('"+cases.patientid+"', '"+cases.doctorid+"', '" +cases.casenotes+"', '"+
                    cases.chronic_condition+"', '"+cases.datetime+"');";

            s.execute(message);

            // --- FOR CASE MEDICATIONS --- //
            JSONArray med = data.getJSONArray("medications");
            String message1="";

            String query = "SELECT MAX(id) FROM casereports";

            ResultSet idMax = s.executeQuery(query);
            int id2 = 0;
            if ( idMax.next() ){
                id2 = idMax.getInt(1);
            }
            System.out.println(id2);

            for (int i = 0; i < med.length(); i++) {
                JSONObject newmed = med.getJSONObject(i);
                Medication mediS = gson.fromJson(String.valueOf(newmed), Medication.class);
                message1 = "INSERT INTO medication (casereportid, starttime, endtime, type)"+
                    " values ( '"+id2+"', '"+mediS.starttime+"', '"+mediS.endtime+"', '"
                    +mediS.type+"');";
                s.execute(message1);
            }

            s.close();

    }catch(JSONException e){
        System.out.println("Error while executing SQL function in addMC");
        e.printStackTrace();
    }
    }
}