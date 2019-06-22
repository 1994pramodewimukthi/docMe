package edu.esoft.finalproject.DocMe.utility;

import edu.esoft.finalproject.DocMe.dto.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;

@Component
public class ActiveMQEmail {

    private static final String EMAIL_LOG_ID = "EMAIL_LOG_ID";
    private static final String FROM_ADDRESS_KEY = "FROM_ADDRESS";
    private static final String TO_ADDRESS_KEY = "TO_ADDRESS";
    private static final String CC_ADDRESS_KEY = "CC_ADDRESS";
    private static final String SUBJECT_KEY = "SUBJECT";
    private static final String BODY_KEY = "BODY";

    private String jmsQueueName = "Esoft-Email-Queue";

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendFromEmail(Email email) throws JMSException {
        MessageCreator messageCreator = (Session session) -> {

            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setObject(EMAIL_LOG_ID, email.getEmailLogId());
            mapMessage.setObject(FROM_ADDRESS_KEY, email.getFromAddress());
            mapMessage.setObject(TO_ADDRESS_KEY, email.getToAddress());
            mapMessage.setObject(CC_ADDRESS_KEY, email.getCcAddress());// save CC Address
            mapMessage.setObject(SUBJECT_KEY, email.getSubject());// save Subject
            mapMessage.setObject(BODY_KEY, email.getEmailMessage());// save Body
            return mapMessage;
        };
        jmsTemplate.send(jmsQueueName, messageCreator);
    }
}
