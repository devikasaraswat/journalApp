package net.project.journalApp.repository;

import net.project.journalApp.entity.ConfigJournalAppEntity;
import net.project.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
