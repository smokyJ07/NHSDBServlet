import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.json.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;

// test code for testing doGet and do Post by simulating receiving an actual Http resp/req
// not working so far : unknown functions + missing Spring Framework (needed to run test code : https://stackoverflow.com/questions/41542703/how-to-mock-http-post-with-applicationtype-json-with-mockito-java)
public class TestdoPost {
/*
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;

    String json = "{\"id\":213213213, \"amount\":222}";
    when(request.getInputStream()).thenReturn(
    new DelegatingServletInputStream(
            new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))));
    when(request.getReader()).thenReturn(
    new BufferedReader(new StringReader(json)));
    when(request.getContentType()).thenReturn("application/json");
    when(request.getCharacterEncoding()).thenReturn("UTF-8");

    @Test
    public void testDoGet() throws IOException, ServletException {
        StringWriter stringwriter = new StringWriter();
        PrintWriter printwriter = new PrintWriter(stringwriter);
        Mockito.when(response.getWriter()).thenReturn(printwriter);

        DBServlet test1 = new DBServlet();
        test1.doGet(request, response);

        String output = stringwriter.getBuffer().toString();
        Assert.assertEquals("Hello, World!", output);
    }
    @Test
    public void testDoPost() throws IOException, ServletException {
        DBServlet test2= new DBServlet();
        test2.doPost(request,response);
    }
*/
}

