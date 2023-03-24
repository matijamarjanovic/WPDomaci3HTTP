package helperservis;

import app.Quote;
import com.google.gson.Gson;
import http.Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelperThread implements Runnable{

    private static Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public HelperThread(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

    }

    @Override
    public void run() {
        try {

            String jsonList = in.readLine();
            Gson gson = new Gson();
            ArrayList<Quote> quotes = new ArrayList<>();
            Quote q;

            String[] str = jsonList.split(" ");
            for (String s : str){
                q = gson.fromJson(s, Quote.class);
                quotes.add(q);
            }

            Random rand = new Random();
            int random = rand.nextInt(quotes.size());

            out.println(gson.toJson(quotes.get(random)));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
