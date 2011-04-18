package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Customer;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lucky
 */
@Stateless
public class NotificationSessionBean {

    private static Logger LOG = LoggerFactory.getLogger(NotificationSessionBean.class);
    
    @Resource(mappedName = "jms/IBMailQueue")
    private Queue mailQueue;
    @Resource(mappedName = "jms/IBConnectionFactory")
    private ConnectionFactory connFactory;

    public void sendMessage(Customer c, String subject, String body) {
        try {
            this.sendMessageToQueue(c, subject, body);
            LOG.info("Mail for customer added into query.", c.getUsername());
        } catch (JMSException ex) {
            LOG.error("Error during sending mail! {}", ex.getMessage());
        }
    }

    private void sendMessageToQueue(Customer c, String subject, String body) throws JMSException {
        Connection conn = this.connFactory.createConnection();
        try {
            Session session = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);
            try {
                MessageProducer mp = session.createProducer(mailQueue);
                try {
                    MapMessage message = session.createMapMessage();

                    message.setString("mailto", c.getEmail());
                    message.setString("subject", subject);
                    message.setString("body", body);

                    mp.send(message);
                } finally {
                    mp.close();
                }
            } finally {
                session.close();
            }
        } finally {
            conn.close();
        }
    }
}
