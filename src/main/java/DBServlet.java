import DBFunctions.DBInterface;
import org.json.JSONObject;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.stream.Collectors;

@WebServlet(urlPatterns={"/patients"},loadOnStartup = 1)
public class DBServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String message = "Hello, World!";
        resp.getWriter().write(message);

        //create our database interface class and try to connect to heroku postgresql db
        DBInterface dbInterface = new DBInterface();

        /*ResultSet rset = null;
        try {
            rset = getPatient("Gutierrez");
            String message1 = "Hello, World!";
            resp.getWriter().write(message1);
            while(rset.next()){
                resp.getWriter().write(rset.getInt("id")+" "+ rset.getString("lastName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        dbInterface.closeConnection();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String reqBody= req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        //System.out.println(reqBody);
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
            dbInterface.closeConnection();


        }catch(Exception e){
            System.out.println("Exception occured while parsing JSON.");
        };
    }
}