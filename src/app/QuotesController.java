package app;

import http.Request;
import http.Server;
import http.response.HtmlResponse;
import http.response.RedirectResponse;
import http.response.Response;

public class QuotesController extends Controller{

    public QuotesController(Request request){
        super(request);
    }

    @Override
    public Response doGet() {

        String htmlBody = "" +
                "<form method = \"POST\" action=\"/save-quote\">" +
                "<label>Author: </label> <input name=\"author\" type=\"text\"><br><br>" +
                "<label>Quote: </label> <input name=\"quote\" type=\"text\"><br><br>" +
                "<button>Save Quote</button>" +
                "</form>";

        String htmlList = "<h1>Quotes</h1><ol>";

        for (Quote q : Server.quotes){
            htmlList += "<li>";
            htmlList += q.toString();
            htmlList += "</li>";
        }

        htmlList += "</ol>";

        String htmlQod = "<h1>Quote of the day</h1>" + "<div>" + Server.qod.toString() + "</div>";

        String content = "<html><head><title> Odgovor Servera </title></head>\n";
        content += "<body>" + htmlBody + htmlQod + htmlList + "</body></html>";

        return new HtmlResponse(content);
    }

    @Override
    public Response doPost() {

        System.out.println("Quote saved");
        return new RedirectResponse("/quotes");
    }
}
