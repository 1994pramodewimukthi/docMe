package edu.esoft.finalproject.DocMe.service.imp;
import edu.esoft.finalproject.DocMe.entity.Message;
import edu.esoft.finalproject.DocMe.repository.MessageRepository;
import edu.esoft.finalproject.DocMe.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;
    private Map<Long, Message> messageMap = null;


    @PostConstruct
    private void initMessage() {
        Iterable<Message> messages;
        try {
            messageMap = new HashMap<>();
            messages = messageRepository.findAll();
            for (Message message : messages) {
                messageMap.put(message.getMessageId(), message);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public String getSystemMessage(Long messageId) {
        return messageMap.containsKey(messageId) ? messageMap.get(messageId).getMessage() : "NOT AVAILABLE";
    }
}
