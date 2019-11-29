import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.stream.Collectors;
import java.sql.Statement;

@WebServlet(urlPatterns={"/patients"},loadOnStartup = 1)
public class DBServlet extends HttpServlet {
    static Statement s = null;

    protected static void initDatabaseConnection() {
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String message = "Hello, World!";

        resp.getWriter().write(message);

        ResultSet rset = null;
        try {
            rset = getPatient("Gutierrez");
            String message1 = "Hello, World!";
            resp.getWriter().write(message1);
            while(rset.next()){
                resp.getWriter().write(rset.getInt("id")+" "+ rset.getString("lastName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqBody= req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Gson gson = new Gson();
        Patient p=gson.fromJson(reqBody,Patient.class);
        System.out.println(reqBody);
    }

    public static void addPatient(String name, String lastname, String phonenumber) throws SQLException {
        initDatabaseConnection();
        String message;
        message = "INSERT INTO patients (\"firstName\", \"lastName\", phonenumber) values ('"+name+"', '"+lastname+"','"+phonenumber+"');";
        s.execute(message);
    }

    public static void addDoctor(String name, String lastname,String pagernumber) throws SQLException {
        initDatabaseConnection();
        String message;
        message = "INSERT INTO doctors (\"firstName\", \"lastName\", pagernumber) values ('"+name+"', '"+lastname+"','"+pagernumber+"');";
        s.execute(message);
    }

    public static ResultSet getPatient(String lastname) throws SQLException {
        initDatabaseConnection();
        String message;
        message = "select * from patients where \"lastName\" = '"+lastname+"';";
        s.execute(message);

        ResultSet rset=s.executeQuery(message);
        while(rset.next()){
            System.out.println(rset.getInt("id")+" "+ rset.getString("lastName"));
        }
        return rset;
    }

    public static void modPatient(int id, String parameter, String newvalue) throws SQLException {
        initDatabaseConnection();
        String message;
        message = "update patients set "+parameter+"='"+newvalue+"' where id="+id+";";
        s.execute(message);
    }
}