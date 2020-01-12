import DBFunctions.DBInterface;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns={"/patients"},loadOnStartup = 1)
public class DBServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*String reqBody= req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println(reqBody);
        resp.setContentType("text/html"); //set type for response
        try {
            //parse and decompose json received
            JSONObject reqBodyJson = new JSONObject(reqBody);
            String function = (String) reqBodyJson.get("function");
            JSONObject data = (JSONObject) reqBodyJson.get("data");
            //execute correct function
            DBInterface dbInterface = new DBInterface();
            if (function.equals("getPatients")){
                resp.getWriter().write(dbInterface.getPatients(data));
            }
            else{
                System.out.println("No matching function found!");
            }
            dbInterface.closeConnection();
        }catch(Exception e){
            System.out.println("Exception occured while parsing JSON.");
            e.printStackTrace();
        };
        */
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String reqBody= req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println(reqBody);
        try {
            //Parse and decompose json received
            JSONObject reqBodyJson = new JSONObject(reqBody);
            String function = (String) reqBodyJson.get("function");
            JSONObject data = (JSONObject) reqBodyJson.get("data");

            //Execute correct function
            DBInterface dbInterface = new DBInterface();
            if (function.equals("addPatient")){
                resp.setContentType("text/html");
                resp.getWriter().write(dbInterface.addPatient(data));
            }
            else if(function.equals("addDoctor")) {
                resp.setContentType("text/html");
                resp.getWriter().write(dbInterface.addDoctor(data));
            }
            else if(function.equals("addMC")){
                dbInterface.addMC(data);
            }
            else if(function.equals("addCaseReport")){
                dbInterface.addCaseReport(data);
            }
            else if(function.equals("changeCaseReport")){
                dbInterface.changeCaseReport(data);
            }
            else if(function.equals("getPatients")){
                resp.setContentType("text/html");
                resp.getWriter().write(dbInterface.getPatients(data));
            }
            else if(function.equals("getAllPatients")){
                resp.setContentType("text/html");
                resp.getWriter().write(dbInterface.getAllPatients(data));
            }
            else if (function.equals("getCaseReports")){
                resp.setContentType("text/html");
                resp.getWriter().write(dbInterface.getCaseReports(data));
            }
            else if(function.equals("checkLogIn")){
                resp.setContentType("text/html");
                resp.getWriter().write(dbInterface.checkLogIn(data));
            }
            else if(function.equals("getDoctor")){
                resp.setContentType("text/html");
                resp.getWriter().write(dbInterface.getDoctor(data));
            }
            else{
                System.out.println("No matching function found!");
            }
            dbInterface.closeConnection();

        }catch(Exception e){
            System.out.println("Exception occurred while parsing JSON.");
            e.printStackTrace();
        };
    }
}