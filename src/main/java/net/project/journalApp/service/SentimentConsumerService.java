package net.project.journalApp.service;


import net.project.journalApp.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class SentimentConsumerService {

    @Autowired
    private  EmailService emailService;

    @KafkaListener(topics="weekly-sentiments", groupId = "weekly-sentiments-group")
    public void consume(SentimentData sentimentData){
        sendEmail(sentimentData);
    }

    public void sendEmail(SentimentData sentimentData){
        emailService.sendEmail(sentimentData.getEmail(),"Sentiment for previous week ", sentimentData.getSentiment());
    }
}
