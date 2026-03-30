package net.project.journalApp.service;

import net.project.journalApp.entity.JournalEntry;
import net.project.journalApp.entity.User;
import net.project.journalApp.repository.JournalEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    private final static Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username){
        try {
            User user = userService.findByUsername(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(savedEntry);
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
//        user.getJournalEntries().add(savedEntry);
//        userService.saveEntry(user);
    }


    public List<JournalEntry> getAll() {
       return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(String id) {
       return journalEntryRepository.findById(id);
    }

    @Transactional
    public void deleteById(String id, String username) {
        try{
            User user = userService.findByUsername(username);
            boolean removed = user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
          //  System.out.println(e);
            throw new RuntimeException("Error occured while deleting the user" , e);
        }
    }

    public List<JournalEntry> findByUserName(String username){
        return null;
    }

}
