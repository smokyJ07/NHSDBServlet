import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class TestdoPost {
    @Mock
    HttpServletRequest req;
    @Mock
    HttpServletResponse resp;

    /*
    @Test
    public void testDoGet() throws IOException, ServletException {
        StringWriter stringwriter = new StringWriter();
        PrintWriter printwriter = new PrintWriter(stringwriter);
        Mockito.when(resp.getWriter()).thenReturn(printwriter);

        DBServlet test1 = new DBServlet();
        test1.doGet(req, resp);

        String output = stringwriter.getBuffer().toString();
        Assert.assertEquals("Hello, World!", output);
    }
    @Test
    public void testDoPost() throws IOException, ServletException {
        DBServlet test2= new DBServlet();
        test2.doPost(req,resp);
    }
     */

}

