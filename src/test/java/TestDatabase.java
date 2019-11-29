import org.junit.Test;
import org.junit.Assert;

import java.sql.SQLException;
import java.util.logging.Logger;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static junit.framework.TestCase.assertEquals;


public class TestDatabase {

  //  private static final Logger log= Logger.getLogger(Patient.class.getName());
  /*  @Test
    public void TestaddPatient(){
        Patient p1=new Patient("Martin", " Holloway", "888888888");

        Assert.assertThat(p1.firstName, is(equalTo("Martin")));
      //  System.out.println("Hi");
    }*/

    @Test
    public void TestaddDB() throws SQLException {
        // DBServlet info= new DBServlet();
        DBServlet.addPatient("Christine", "Speybrook", "888888888");
        DBServlet.getPatient("Speybrook");

      //  Assert.assertThat(s, is(equalTo("select * from patients where lastname = 'Speybrook';")));


    }


}
