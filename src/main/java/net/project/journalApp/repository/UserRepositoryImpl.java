package net.project.journalApp.repository;


import net.project.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;


    public List<User> getUsersForSA(){
        Query query = new Query();
   //     Criteria criteria = new Criteria();
      //  query.addCriteria(Criteria.where("email").exists(true));
        // query.addCriteria(Criteria.where("email").ne(null).ne(""));

        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$/"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        query.addCriteria(Criteria.where("username").nin("Shanu", "Manu", "Sheelu"));

//        query.addCriteria(criteria.orOperator(Criteria.where("email").exists(true), Criteria.where("sentimentAnalysis").is(true)));
        List<User> users = mongoTemplate.find(query, User.class);
        return  users;

    }
}
