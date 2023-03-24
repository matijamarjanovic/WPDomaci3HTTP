package http;

import app.Quote;
import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {

    public static final int PORT = 8080;
    public static final int PORT2 = 8081;

    private static BufferedReader in;
    private static PrintWriter out;

    public static List<Quote> quotes;
    public static Quote qod;

    public static void main(String[] args) throws IOException {


        ServerSocket ss = new ServerSocket(PORT);

        quotes = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 10; i++){
            quotes.add(new Quote("author" + i, "quote" + i));
        }

        Gson gson = new Gson();
        String jsonList = "";


        while (true){

            Socket s2 = new Socket("127.0.0.1", PORT2);
            in = new BufferedReader(new InputStreamReader(s2.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s2.getOutputStream())), true);

            for (Quote q: quotes){
                jsonList += gson.toJson(q) + " ";
            }

            out.println(jsonList);
            qod = gson.fromJson(in.readLine(), Quote.class);


            Socket s = ss.accept();
            new Thread(new ServerThread(s)).start();


        }
    }
}
