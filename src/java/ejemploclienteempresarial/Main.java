/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemploclienteempresarial;

import dominio.Persona;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 *
 * @author Alfonso Felix
 */
public class Main {

    @Resource(mappedName = "resDestino")
    private static Queue resDestino;

    @Resource(mappedName = "miConexion")
    private static ConnectionFactory miConexion;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            sendJMSMessageToResDestino("Hola mundo.");
            sendJMSPersonaMessageToResDestino(new Persona("Alfonso", new Date()));
            
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Message createJMSMessageForresDestino(Session session, Object messageData) throws JMSException {
        // TODO create and populate message to send
        TextMessage tm = session.createTextMessage();
        tm.setText(messageData.toString());
        return tm;
    }

    private static void sendJMSMessageToResDestino(Object messageData) throws JMSException {
        Connection connection = null;
        Session session = null;
        try {
            connection = miConexion.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(resDestino);
            messageProducer.send(createJMSMessageForresDestino(session, messageData));
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    private static Message createJMSPersonaMessageForresDestino(Session session, Persona messageData) throws JMSException {
        // TODO create and populate message to send
        ObjectMessage tm = session.createObjectMessage();
        tm.setObject(messageData);
        return tm;
    }

    private static void sendJMSPersonaMessageToResDestino(Persona messageData) throws JMSException {
        Connection connection = null;
        Session session = null;
        try {
            connection = miConexion.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(resDestino);
            messageProducer.send(createJMSPersonaMessageForresDestino(session, messageData));
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
    
}
