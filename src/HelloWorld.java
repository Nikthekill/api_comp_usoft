import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/helloworld")
public class HelloWorld {
    public HelloWorld() throws IOException {
    }

    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("text/plain")
    public String getClichedMessage() throws IOException {
        // Return some cliched textual content
        return readAPI().toString();
    }


    public StringBuffer readAPI() throws IOException {
        URL url = new URL("http://54.221.147.38:8000/pedirInfo");
        HttpURLConnection conection = (HttpURLConnection) url.openConnection();
        conection.setRequestMethod("GET");
        conection.setConnectTimeout(5000);
        conection.setReadTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine())!= null) {
            content.append(inputLine);
        }

        in.close();

        return content;

    }


}