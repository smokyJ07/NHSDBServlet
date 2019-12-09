import DBFunctions.DBInterface;
import org.json.JSONObject;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.stream.Collectors;

@WebServlet(urlPatterns={"/patients"},loadOnStartup = 1)
public class DBServlet extends HttpServlet {
    static Statement s = null;

    protected static void initDatabaseConnection() {
        String dbUrl = "jdbc:postgresql://localhost:5432/postgres";

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(dbUrl, "postgres", "Chhoopnege2k22");
            // CHANGED POSTGRES PASSWORD FROM ALEX'S TO MINE (CHLOE)
            s = conn.createStatement();
        }catch (Exception e) {
            System.out.println(e);
            String l=e.getMessage();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String message = "Hello, World!";
        resp.getWriter().write(message);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String reqBody= req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        try {
            //parse and decompose json received
            JSONObject reqBodyJson = new JSONObject(reqBody);
            String function = (String) reqBodyJson.get("function");
            JSONObject data = (JSONObject) reqBodyJson.get("data");
            //execute correct function
            DBInterface dbInterface = new DBInterface();
            if (function.equals("addPatient")){
                dbInterface.addPatient(data);
            }
            else if(function.equals("addDoctor")){
                dbInterface.addDoctor(data);
            }
            else if(function.equals("addMC")){
                dbInterface.addMC(data);
            }
            else if(function.equals("getPatient")){
                dbInterface.getPatient(data);
            }
            else if(function.equals("getMC")){
                dbInterface.getMC(data);
            }
            else if(function.equals("getMC")){
                dbInterface.getDoctor(data);
            }
            else{
                System.out.println("No matching function found!");
            }
        }catch(Exception e){
            System.out.println("Exception occured while parsing JSON.");
        }
    }
}