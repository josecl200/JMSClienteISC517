package jms;

import controladoras.Clientes;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consoomer {
    ActiveMQConnectionFactory factory;
    Connection connection;
    Session session;
    Topic topic;
    MessageConsumer consoomer;
    String topicName;

    public Consoomer(String topicName) {
        this.topicName = topicName;
    }

    public void connect() throws JMSException {
        factory = new ActiveMQConnectionFactory("root", "toor", "failover:"+System.getenv("sensoresURL"));
        connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        topic = session.createTopic(topicName);
        consoomer = session.createConsumer(topic);
        System.out.println("Listo para recibir.");
        consoomer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println(textMessage.getText());
                Clientes.enviarMensaje(textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }
}