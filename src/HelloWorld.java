import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/apicomp")
public class HelloWorld {
    public HelloWorld() throws IOException {
    }

    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("text/plain")
    public String getClichedMessage() throws IOException {
        // Return some cliched textual content
        return readAPI();
    }


    public String readAPI() throws IOException {
        URL url = new URL("http://18.212.190.57:8082/pedirInfo");
        HttpURLConnection conection = (HttpURLConnection) url.openConnection();
        conection.setRequestMethod("GET");
        conection.setConnectTimeout(5000);
        conection.setReadTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        String datos = content.toString();
        String[] datosPartidos = datos.split("///");
        int[] arreglito = new int[datosPartidos.length];
        for(int i=0; i<datosPartidos.length; i++)
        {
           int producto = Integer.parseInt(datosPartidos[i].split(";;")[2]);
           arreglito[i]= producto;

        }

        int masFrecuente = mostFrequent(arreglito,arreglito.length);

        URL url2 = new URL("http://54.89.175.67:8081/pedirInfo");
        HttpURLConnection conection2 = (HttpURLConnection) url2.openConnection();
        conection2.setRequestMethod("GET");
        conection2.setConnectTimeout(5000);
        conection2.setReadTimeout(5000);
        BufferedReader in2 = new BufferedReader(new InputStreamReader(conection2.getInputStream()));
        String inputLine2;
        StringBuffer content2 = new StringBuffer();
        while ((inputLine2 = in2.readLine()) != null) {
            content2.append(inputLine2);
        }

        String datos2 = content2.toString();
        String[] datosPartidos2 = datos2.split("///");
        String productoPopular = "";
        for(int i=0; i<datosPartidos2.length; i++)
        {
            if(datosPartidos2[i].split(";;")[0].equalsIgnoreCase(masFrecuente+""))
            {
                productoPopular = datosPartidos2[i].split(";;")[1];
            }

        }

        in.close();
        in2.close();

        return productoPopular;

    }


    static int mostFrequent(int arr[], int n)
    {

        // Sort the array
        Arrays.sort(arr);

        // find the max frequency using linear
        // traversal
        int max_count = 1, res = arr[0];
        int curr_count = 1;

        for (int i = 1; i < n; i++)
        {
            if (arr[i] == arr[i - 1])
                curr_count++;
            else
            {
                if (curr_count > max_count)
                {
                    max_count = curr_count;
                    res = arr[i - 1];
                }
                curr_count = 1;
            }
        }

        // If last element is most frequent
        if (curr_count > max_count)
        {
            max_count = curr_count;
            res = arr[n - 1];
        }

        return res;
    }

}