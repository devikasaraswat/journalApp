package net.project.journalApp.scheduler;


import net.project.journalApp.cache.AppCache;
import net.project.journalApp.entity.JournalEntry;
import net.project.journalApp.entity.User;
import net.project.journalApp.enums.Sentiment;
import net.project.journalApp.model.SentimentData;
import net.project.journalApp.repository.UserRepositoryImpl;
import net.project.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

   // @Scheduled(cron = "0 0 9 * * SUN")
    //@Scheduled(cron = "0 * * ? * *")
    public void fetchUsersAndSendSAEmail() {
        List<User> users = userRepository.getUsersForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(entry ->
                    entry.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null)
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null) {
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for previous week "+ mostFrequentSentiment.toString()).build();
                //emailService.sendEmail(user.getEmail(), "Sentiment for previous week", mostFrequentSentiment.toString());
                kafkaTemplate.send("weekly-sentiments",sentimentData.getEmail(),sentimentData);
            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache(){
        appCache.init();
    }

}
