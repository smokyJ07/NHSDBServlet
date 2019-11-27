
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String dbUrl = "jdbc:postgresql://localhost:5432/postgres";

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(dbUrl, "postgres", "aleseb99");

            Statement s=conn.createStatement();

            String sqlStr;
            // sqlStr = addPatient("Cristian", "Gutierrez", "222222222");
            // s.execute(sqlStr);

            sqlStr = addDoctor("Anil", "Bharath", "11111111");
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