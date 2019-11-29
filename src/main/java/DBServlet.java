
import com.google.gson.Gson;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.stream.Collectors;

@WebServlet(urlPatterns={"/patients"},loadOnStartup = 1)
public class DBServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String message = "Hello, World!";
        resp.getWriter().write(message);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqBody= req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Gson gson = new Gson();
        Patient p=gson.fromJson(reqBody,Patient.class);
        System.out.println(reqBody);

    }

    public void createDB() {
        String dbUrl = "jdbc:postgresql://localhost:5432/postgres";

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(dbUrl, "postgres", "aleseb99");

            Statement s=conn.createStatement();

            String sqlStr;
            // sqlStr = addPatient("Cristian", "Gutierrez", "222222222");
            // s.execute(sqlStr);

            sqlStr = addDoctor("Angela", "Kedgley", "11111111");
            s.execute(sqlStr);

            //String sqlStr = getPatient("Gutierrez");
            // ResultSet rset=s.executeQuery(sqlStr);
            // while(rset.next()){
            //     System.out.println(rset.getInt("id")+" "+ rset.getString("name")+" "+ rset.getString("lastname"));
            //  }
            //(int id, String parameter, String newvalue){
            sqlStr = modPatient(1, "lastname", "Gutierrez");
            s.execute(sqlStr);

            //  rset.close();
            s.close();
            conn.close();

        } catch (Exception e) {
            String l=e.getMessage();
        }
    }

    public static String addPatient(String name, String lastname,String phonenumber) {
        String message;
        message = "INSERT INTO patients (name,lastname, phonenumber) values ('"+name+"', '"+lastname+"','"+phonenumber+"');";
        return message;
    }

    public static String addDoctor(String name, String lastname,String pagernumber) {
        String message;
        message = "INSERT INTO doctors (name,lastname, pagernumber) values ('"+name+"', '"+lastname+"','"+pagernumber+"');";
        return message;
    }

    public static String getPatient(String lastname) {
        String message;
        message = "select * from patients where lastname = '"+lastname+"';";
        return message;
    }

    public static String modPatient(int id, String parameter, String newvalue){
        String message;
        message = "update patients set "+parameter+"='"+newvalue+"' where id="+id+";";
        System.out.println(message);
        return message;
    }

}