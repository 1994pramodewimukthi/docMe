package edu.esoft.finalproject.DocMe.utility;

import edu.esoft.finalproject.DocMe.dto.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;
import java.io.InputStream;

@Component
public class ActiveMQEmail {

    private static final String EMAIL_LOG_ID = "EMAIL_LOG_ID";
    private static final String FROM_ADDRESS_KEY = "FROM_ADDRESS";
    private static final String TO_ADDRESS_KEY = "TO_ADDRESS";
    private static final String CC_ADDRESS_KEY = "CC_ADDRESS";
    private static final String SUBJECT_KEY = "SUBJECT";
    private static final String BODY_KEY = "BODY";
    private static final String DOC_NAME = "DOC_NAME";
    private static final String DOC_INPUT_STREAM = "DOC_INPUT_STREAM";
    private static final String TYPE = "TYPE";

    private String jmsQueueName = "Esoft-Email-Queue";

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendFromEmail(Email email) throws JMSException {
        try {
            MessageCreator messageCreator = (Session session) -> {

                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setObject(EMAIL_LOG_ID, email.getEmailLogId());
                mapMessage.setObject(FROM_ADDRESS_KEY, email.getFromAddress());
                mapMessage.setObject(TO_ADDRESS_KEY, email.getToAddress());
                mapMessage.setObject(CC_ADDRESS_KEY, email.getCcAddress());// save CC Address
                mapMessage.setObject(SUBJECT_KEY, email.getSubject());// save Subject
                mapMessage.setObject(BODY_KEY, email.getBody());// save Body
                mapMessage.setObject(DOC_NAME, email.getDocumentNAme());// save Body
                mapMessage.setObject(DOC_INPUT_STREAM, email.getDocInputStream());// save Body
                mapMessage.setObject(TYPE, email.getType());// save Body
                return mapMessage;
            };
            jmsTemplate.send(jmsQueueName, messageCreator);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
