package DBFunctions;
import DBClasses.*;
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

/*
This class:
 - establishes connection to the database
 - checks if all relevant tables exist and creates them if they don't
 - contains all methods used to pass info between servlet and database
 - note: input for interface functions (add/get Patient/Doctor/MC...) is a JSON Object received from the Client application,
 converted into the appropriate class with all relevant fields and passed into appropriate table
 */

public class DBInterface {

    static Connection conn = null;

    public DBInterface(){
        //connection to heroku online postgresql DB

        try{
            conn = getConnection();
            Statement s = conn.createStatement();
            System.out.println("Connection open");

            createTables();
            s.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Connection failed");
        }
        /*
        //use this to connect to your local db: note to change password and username to whatever you use
        String dbUrl = "jdbc:postgresql://localhost:5432/postgres";

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(dbUrl, "postgres", "12345678");
            Statement s = conn.createStatement();
         //   System.out.println("Local connection open");

            createTables();
            s.close();
        }catch (Exception e) {
            System.out.println("Local connection failed");
            e.printStackTrace();
        }*/
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

    // CHECKING ALL NECESSARY TABLES EXIST, CREATING THEM IF NOT

    private boolean tableExists(String tableName){
        try {
            String queryMessage = String.format("SELECT EXISTS(SELECT 1 FROM information_schema.tables WHERE table_schema = 'public' AND table_name = '%s');", tableName);
            Statement s = conn.createStatement();
            ResultSet rset = s.executeQuery(queryMessage);
            boolean result = false;
            while(rset.next()){
                result = rset.getBoolean("exists");
        //        System.out.println(result);
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
                        "pagernum varchar(32), email varchar(128), username varchar(128), password varchar (128))";
                s.execute(sql);
                s.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

        if(!tableExists("gplogin")){
            System.out.println("Creating GP Login table...");
            try {
                Statement s = conn.createStatement();
                String sql = "create table gplogin (id SERIAL PRIMARY KEY, name varchar(128) NOT NULL, " +
                        "password varchar(32))";
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
        if(!tableExists("medication")){
            System.out.println("Creating medication table...");
            try {
                Statement s = conn.createStatement();
                String sql = "create table medication(id SERIAL PRIMARY KEY, casereportid INT NOT NULL, " +
                        "starttime TIMESTAMP, endtime TIMESTAMP, type varchar(128))";
                s.execute(sql);
                s.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(!tableExists("casereports")){
            System.out.println("Creating casereports table...");
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
        if(!tableExists("patienttodoctor")){
            System.out.println("Creating patienttodoctor table...");
            try{
                Statement s = conn.createStatement();
                String sql = "CREATE TABLE patienttodoctor(id SERIAL PRIMARY KEY, patientid INT NOT NULL, " +
                        "doctorid INT NOT NULL);";
                s.execute(sql);
                s.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        System.out.println("All required tables exist.");
    }

    // METHODS TO PASS NEW OBJECTS TO THE DATABASE

    public String addPatient(JSONObject data){
        String returnString = "";
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

            //adding patient and gp to patienttodoctor table
            String gpName = data.getString("gp");
            //get doctor ID
            String getGPID = "SELECT * FROM doctors WHERE name = '" + gpName + "';";
            Statement s1 = conn.createStatement();
            ResultSet rset = s1.executeQuery(getGPID);
            int gpID = 0;
            if (rset.next()){
                gpID = rset.getInt("id");
            }
            s1.close();
            //get patient id
            String getPatientID = "SELECT MAX(id) FROM patients;";
            Statement s2 = conn.createStatement();
            ResultSet rset2 = s2.executeQuery(getPatientID);
            int patientID = 0;
            if(rset2.next()){
                patientID = rset2.getInt(1);
            }
            s2.close();
            //put 2 ids into patienttodoctor and construct response
            if(patientID != 0 && gpID != 0) {
                String put = "INSERT INTO patienttodoctor (patientid, doctorid) VALUES('" + patientID + "','" + gpID + "');";
                Statement s3 = conn.createStatement();
                s3.execute(put);
                s3.close();

                JSONObject responseData = new JSONObject();
                responseData.put("gpid_found", true);
                responseData.put("patientid_found", true);
                CustomJson resp = new CustomJson("addPatient", responseData);
                returnString = resp.toString();

            }
            else if (patientID == 0 && gpID != 0){
                System.out.println("Error: wasn't able to retrieve patientID while inserting patient.");

                JSONObject responseData = new JSONObject();
                responseData.put("gpid_found", true);
                responseData.put("patientid_found", false);
                CustomJson resp = new CustomJson("addPatient", responseData);
                returnString = resp.toString();
            }
            else if (gpID == 0 && patientID != 0){
                System.out.println("Error: no GP found with the given name.");

                JSONObject responseData = new JSONObject();
                responseData.put("gpid_found", false);
                responseData.put("patientid_found", true);
                CustomJson resp = new CustomJson("addPatient", responseData);
                returnString = resp.toString();
            }
            else{
                JSONObject responseData = new JSONObject();
                responseData.put("gpid_found", false);
                responseData.put("patientid_found", false);
                CustomJson resp = new CustomJson("addPatient", responseData);
                returnString = resp.toString();
            }
        }catch(Exception e){
            System.out.println("Error while executing SQL function in addPatient");
            e.printStackTrace();
        }
        return returnString;
    }

    public String addDoctor(JSONObject data){
        String returnString = "";
        try {
            Gson gson = new Gson();
            String doctorData = data.toString();
            Doctor d = gson.fromJson(doctorData, Doctor.class);

            //check whether username already exists
            JSONObject responseData = new JSONObject();
            String checkNames;
            Statement s0 = conn.createStatement();
            checkNames = "SELECT * FROM doctors WHERE username = '" + d.username + "';";
            ResultSet rset1 = s0.executeQuery(checkNames);
            if (rset1.next()){
                try {
                    responseData.put("username_exists", true);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else{
                responseData.put("username_exists", false);
            }
            //check whether name already exists
            checkNames = "SELECT * FROM doctors WHERE name = '" + d.name + "';";
            ResultSet rset2 = s0.executeQuery(checkNames);
            if(rset2.next()){
                try {
                    responseData.put("name_exists", true);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else{
                responseData.put("name_exists", false);
            }
            if(!responseData.getBoolean("name_exists") && !responseData.getBoolean("username_exists")){
                String message;
                Statement s = conn.createStatement();
                message = "INSERT INTO doctors (name, pagernum, email, username, password) values " +
                        "('"+d.name+"','"+d.pagerNum+"','"+d.email+"','"+d.username+"','"+d.password + "');";
                s.execute(message);
                s.close();
                System.out.println("Added doctor with data:" + doctorData);
            }
            CustomJson response = new CustomJson("addDoctor", responseData);
            returnString = response.toString();
            s0.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return returnString;
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

    public void addCaseReport(JSONObject data) throws SQLException {
        try{
            // --- FOR CASE REPORTS ---//
            Statement s = conn.createStatement();
            String cas = data.getString("casereport");
            Gson gson = new Gson();
            CaseReport cases = gson.fromJson(cas, CaseReport.class);
            cases.updateDatetime();

            String message = "INSERT INTO casereports (patientid, doctorid , casenotes, chronic_condition, datetime)"+
                    " values ('"+cases.patient_id+"', '"+cases.doctor_id+"', '" +cases.casenotes+"', '"+
                    cases.chronic_condition+"', '"+cases.datetime+"');";

            s.execute(message);

            // --- FOR MEDICATIONS --- //
            JSONArray med = data.getJSONArray("medications");
            String message1="";

            String query = "SELECT MAX(id) FROM casereports";

            ResultSet idMax = s.executeQuery(query);
            int id2 = 0;
            if ( idMax.next() ){
                id2 = idMax.getInt(1);
            }

            for (int i = 0; i < med.length(); i++) {
                JSONObject newmed = med.getJSONObject(i);
                Medication mediS = gson.fromJson(newmed.toString(), Medication.class);
                message1 = "INSERT INTO medication (casereportid, starttime, endtime, type)"+
                    " values ( '"+id2+"', '"+mediS.starttime+"', '"+mediS.endtime+"', '"
                    +mediS.type+"');";
                s.execute(message1);
            }
            s.close();

            //debugging messages
            System.out.println("For casereport id: " + id2 + " added casereport " + cas +
                    " and medications " + med.toString());

        }catch(JSONException e){
            System.out.println("Error while executing SQL function in addMC");
            e.printStackTrace();
        }
    }

    public void changeCaseReport(JSONObject data){
        try{
            // --- FOR CASE REPORTS ---//
            Statement s = conn.createStatement();
            String cas = data.getString("casereport");
            Gson gson = new Gson();
            CaseReport cases = gson.fromJson(cas, CaseReport.class);
            cases.updateDatetime();

            // --- FOR CASEREPORT --- //
            //delete old casereport
            String deleteMessage = "DELETE FROM casereports WHERE id = " + cases.id + ";";
            s.execute(deleteMessage);
            //insert new casereport
            String caseMessage = "INSERT INTO casereports (id, patientid, doctorid , casenotes, chronic_condition, datetime)"+
                    " values ('" + cases.id + "', '" +cases.patient_id+"', '"+cases.doctor_id+"', '" +cases.casenotes+"', '"+
                    cases.chronic_condition+"', '"+cases.datetime+"');";
            s.execute(caseMessage);

            // --- FOR MEDICATIONS --- //
            //delete old medication
            String deleteMedication = "DELETE FROM medication WHERE casereportid = " + cases.id + ";";
            s.execute(deleteMedication);
            //insert new medication
            JSONArray med = data.getJSONArray("medications");
            String medicationMessage="";

            int id2 = cases.id;

            for (int i = 0; i < med.length(); i++) {
                JSONObject newmed = med.getJSONObject(i);
                Medication mediS = gson.fromJson(newmed.toString(), Medication.class);
                medicationMessage = "INSERT INTO medication (casereportid, starttime, endtime, type)"+
                        " values ( '"+id2+"', '"+mediS.starttime+"', '"+mediS.endtime+"', '"
                        +mediS.type+"');";
                s.execute(medicationMessage);
            }
            s.close();

            //debugging messages
            System.out.println("For casereport id: " + id2 + " added casereport " + cas +
                    " and medications " + med.toString());

        }catch(Exception e){
            System.out.println("Error while executing SQL function in addMC");
            e.printStackTrace();
        }
    }

    // METHODS TO RETRIEVE INFO FROM THE DATABASE

    public String getCaseReports(JSONObject data) throws JSONException, SQLException {
        JSONArray Data = new JSONArray();
        try {
            int patientID = data.getInt("patient_id");
            Statement s = conn.createStatement();
            Statement s1 = conn.createStatement();
            int caseID;

            String message = "SELECT * FROM casereports WHERE patientid = '" + patientID + "';";
            String message1 = "";
            s.execute(message);
            ResultSet rset = s.executeQuery(message);

            while (rset.next()) {       // LOOP THROUGH CASES
                JSONObject singleCase = new JSONObject();
                JSONArray medArray = new JSONArray();

                caseID = rset.getInt("id");
                message1 = "SELECT * FROM medication WHERE casereportid = " + caseID + ";";
                System.out.println("case report id");
                System.out.println(caseID);

                s1.execute(message1);
                ResultSet rset1 = s1.executeQuery(message1);

                singleCase.put("id", rset.getInt("id"));
                singleCase.put("datetime", rset.getString("datetime"));
                singleCase.put("casenotes", rset.getString("casenotes"));
                singleCase.put("chronic_condition", rset.getBoolean("chronic_condition"));
                JSONObject combination=  new JSONObject();
                while (rset1.next()) {      // LOOP THROUGH MEDICATIONS

                    JSONObject medObj=  new JSONObject();
                    medObj.put("type", rset1.getString("type"));
                    medObj.put("starttime", rset1.getString("starttime"));
                    medObj.put("endtime", rset1.getString("endtime"));
                    //  medID = rset1.getInt("id");
                    //  System.out.println("medication  id");
                    //  System.out.println(medID);
                    medArray.put(medObj);
                } //System.out.println(medArray);
                combination.put("casereport", singleCase);
                combination.put("medications", medArray);
                Data.put(combination);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CustomJson instruction = new CustomJson("getCaseReport", Data);
        System.out.println(instruction);
        String message = instruction.toString();
        return message;
        }

    public String getAllPatients(JSONObject data) throws SQLException{
        JSONArray myArray = new JSONArray();
        try {
            String gpID = data.getString("gpid");
            //get all patient ids
            String message = "SELECT * FROM patients WHERE (patients.id IN (" +
                    "SELECT patientid from patienttodoctor WHERE patienttodoctor.doctorid = " + gpID +"));";
            Statement s = conn.createStatement();
            s.execute(message);
            ResultSet rset = s.executeQuery(message);
            while (rset.next()) {
                JSONObject patient = new JSONObject();
                patient.put("name", rset.getString("name"));
                patient.put("phoneNum", rset.getString("phonenum"));
                patient.put("address", rset.getString("address"));
                patient.put("dob", rset.getString("dob"));
                patient.put("email", rset.getString("email"));
                patient.put("id", rset.getString("id"));
                myArray.put(patient);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        CustomJson instruction = new CustomJson("getPatients", myArray);
        String message = instruction.toString();
        System.out.println(message);

        return message;
    }

    public String getPatients(JSONObject data) throws SQLException, URISyntaxException {
        JSONArray myArray = new JSONArray();
        try {
            String name = data.getString("name");
            int gpID = data.getInt("gpid");
            String message = "SELECT * FROM patients WHERE (patients.name = '" + name + "')  AND (patients.id IN (" +
                    "SELECT patientid from patienttodoctor WHERE patienttodoctor.doctorid = " + gpID +"));";
            Statement s = conn.createStatement();
            s.execute(message);
            ResultSet rset = s.executeQuery(message);
            while (rset.next()) {
                JSONObject patient = new JSONObject();
                patient.put("name", rset.getString("name"));
                patient.put("phoneNum", rset.getString("phonenum"));
                patient.put("address", rset.getString("address"));
                patient.put("dob", rset.getString("dob"));
                patient.put("email", rset.getString("email"));
                patient.put("id", rset.getString("id"));
                myArray.put(patient);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        CustomJson instruction = new CustomJson("getPatients", myArray);
        String message = instruction.toString();
        System.out.println(message);

        return message;
    }

    public String getDoctor(JSONObject data) throws SQLException {
        JSONObject responseData = new JSONObject();
        try {
            String name = data.getString("name");
            String id = "";
            String message = "SELECT id FROM doctors WHERE name = '"+name+"';";
            Statement s = conn.createStatement();

            ResultSet rset = s.executeQuery(message);
            while(rset.next()) {
                id = rset.getString("id");
            }
            responseData.put("gpid", id);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        CustomJson instruction = new CustomJson("getDoctor", responseData);
        String message = instruction.toString();
        System.out.println(message);
        return message;
    }

    public String getMC(JSONObject data) throws SQLException {
        JSONArray myArray = new JSONArray();
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

        CustomJson instruction = new CustomJson("getMC", myArray);
        String message = instruction.toString();
        System.out.println(message);

        return message;
    }

    public String checkLogIn(JSONObject data) throws JSONException, SQLException {
        String username = data.getString("username");
        String password = data.getString("password");
        JSONObject info = new JSONObject();
        int id;
        try{
            Statement s = conn.createStatement();
            //SELECT EXISTS (
            String command= "SELECT * FROM doctors WHERE username = '"+username
                    +"' AND password = '"+password+"'";
            s.execute(command);
            ResultSet result = s.executeQuery(command);
            if(!result.next()) {
                System.out.println("Login unsuccesful");
                info.put("id", "");
                info.put("login", false);
                info.put("name", "");
            }
            else{
                System.out.println("Login Succesful");
                info.put("id", result.getInt("id"));
                info.put("login", true);
                info.put("name", result.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        CustomJson instruction = new CustomJson("checkLogIn", info);
        String message = instruction.toString();
        System.out.println(message);

        return message;
    }

}


