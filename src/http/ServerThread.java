package http;

import app.Quote;
import app.RequestHandler;
import http.response.Response;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerThread implements Runnable{

    private static Socket socket;

    private BufferedReader in;
    private PrintWriter out;

    public ServerThread(Socket s) throws IOException {
        this.socket = s;

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

    }

    @Override
    public void run() {
        try {

            String  requestLine = in.readLine();
            StringTokenizer stringTokenizer = new StringTokenizer(requestLine);
            int contentLength = 0;

            String method = stringTokenizer.nextToken();
            String path = stringTokenizer.nextToken();

            System.out.println("\nHTTP ZAHTEV KLIJENTA\n");
            do{
                System.out.println(requestLine);

                //citanje duzine content length-a
                if (requestLine.contains("Content-Length")){
                    System.out.println(requestLine);
                    String[] str = requestLine.split(" ");
                    contentLength = Integer.parseInt(str[1]);
                }

                requestLine = in.readLine();
            }while (!requestLine.trim().equals(""));


            //dodvanje u quotes
            if(method.equals(HttpMethod.POST.toString())){
                char[] buffer = new char[contentLength];
                in.read(buffer);
                String s = new String(buffer);

                int i = 0;
                String author = "",  quote = "";
                String[] str = s.split("&");
                for (String st: str){
                    String[] string = st.split("=");

                    if (i == 0){
                        author = string[1];
                        i++;
                    }else{
                        quote = string[1];
                        i--;
                    }
                }
                Server.quotes.add(new Quote(author, quote));
            }

            Request request = new Request(HttpMethod.valueOf(method), path);

            RequestHandler requestHandler = new RequestHandler();
            Response response = requestHandler.handle(request);

            out.println(response.getResponseString());

            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            //throw new RuntimeException(e);
        } catch (Exception e) {
            //throw new RuntimeException(e);
        }


    }


}
