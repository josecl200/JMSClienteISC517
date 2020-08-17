package controladoras;

import jms.Consoomer;
import org.eclipse.jetty.websocket.api.Session;
import org.thymeleaf.Configuration;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import websockets.WebSocketSessionManager;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class Clientes {
    public static List<Session> sesiones=new ArrayList<>();
    public static void main(String[] args) throws JMSException{
        String queue = "sensores";
        webSocket("/sensores", WebSocketSessionManager.class);
        get("/",(request, response) -> new ModelAndView(new HashMap<String,Object>(),"index"),new ThymeleafTemplateEngine());
        Consoomer consoomer = new Consoomer(queue);
        consoomer.connect();
    }
    public static void enviarMensaje(String mensaje) {
        for (Session sesion : sesiones) {
            try {
                sesion.getRemote().sendString(mensaje);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
