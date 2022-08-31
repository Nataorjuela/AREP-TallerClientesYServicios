package arep.MultipleServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase realiza una conexiOn http por medio de servidor
 * @author Natalia Orjuela
 * @author Daniel Benavides
 *
 */
public class MultipleServer {


    /**
     *
     * @param args son los argumentos que entra en el main
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        try {
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        processReques(clientSocket);

        serverSocket.close();
    }

    public static void processReques(Socket clientSocket) throws IOException {

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine;

        String method = "";
        String path = "";
        String version = "";
        List<String> headers = new ArrayList<String>();
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received: " + inputLine);
            if (method.isEmpty()) {
                String[] requestString = inputLine.split(" ");
                method = requestString[0];
                path = requestString[1];
                version = requestString[2];
                System.out.println("Request: " + method + " " + path + " " + version);
            } else {
                System.out.println("Header: " + inputLine);
                headers.add(inputLine);
            }
            if (!in.ready()) {
                break;
            }
        }
        String responseMsg = htmlResponse(path);
        out.println(responseMsg);
        out.close();

        in.close();

        clientSocket.close();
    }

    /**
     * @return outmsg  pagina html;
     */
        public static String htmlResponse(String path) {

            Path file = Paths.get("C:\\Users\\natalia.orjuela\\Documents\\AREP-TallerClientesYServicios\\WWW\\prueba.html");
            Charset charset = Charset.forName("ISO-8859-1");
            String outmsg = "";
            try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    outmsg = outmsg +"\r\n" +line;
                }
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }

            return "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n"
                    + outmsg;
        }
}
