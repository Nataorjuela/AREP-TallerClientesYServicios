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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Esta clase realiza una conexiOn http por medio de servidor
 * @author Natalia Orjuela
 * @author Daniel Benavides
 *
 */
public class HttpServer {

    private Boolean running;

    private static HttpServer _instance = new HttpServer();

    private HttpServer() {
    }

    private static HttpServer getInstance() {
        return _instance;
    }


    public String createTextResponse(String path) {
        String type = "text/html";
        if (path.endsWith(".css")) {
            type = "text/css";
        } else if (path.endsWith(".js")) {
            type = "text/javascript";
        } else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
            type = "image/jpeg";
        } else if (path.endsWith(".png")) {
            type = "image/png";
        }
        Path file = Paths.get("./WWW/" + path);
        Charset charset = Charset.forName("UTF-8");
        String outmsg = "";
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                outmsg = outmsg + "\r\n" + line;
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        return "HTTP/1.1 200 OK\r\n" + "Content-Type: " + type + "\r\n" + outmsg;
    }

    public Runnable processResponse(final Socket clientSocket) throws IOException {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String inputLine;
                    String method = "";
                    String path = "";
                    String version = "";
                    List<String> headers = new ArrayList<String>();
                    while ((inputLine = in.readLine()) != null) {
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
                    String responseMessage = createTextResponse(path);

                    out.println(responseMessage);

                    out.close();

                    in.close();

                    clientSocket.close();
                } catch (IOException e) {
                    System.out.print("Error en processResponse");
                }

            }
        };
    }

    public void startServer(String[] args) throws IOException {
        int port = getPort();
        ServerSocket serverSocket = null;
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(1);
        }
        Socket clientSocket = null;
        running = true;
        while (running) {
            try {
                System.out.println("Listo para recibir en puerto: " + port);
                clientSocket = serverSocket.accept();
                threadPool.submit(processResponse(clientSocket));
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        }
        threadPool.shutdown();
        serverSocket.close();
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set(i.e. on localhost)
    }

    public static void main(String[] args) throws IOException {
        HttpServer.getInstance().startServer(args);
    }
}

