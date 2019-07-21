package com.github.phillipkruger.user.backend;

import com.github.javafaker.Faker;
import com.github.phillipkruger.user.Person;
import com.github.phillipkruger.user.Score;
import com.github.phillipkruger.user.ScoreType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class ScoreDB {
    
    private final Map<String,List<Score>> DB = new ConcurrentHashMap<>();
    
    public List<Score> getScores(String idNumber){
        return DB.get(idNumber);
    }
    
    public Score getScore(ScoreType type,String idNumber){
        List<Score> scores = getScores(idNumber);
        for(Score score:scores){
            if(score.getName().equals(type))return score;
        }
        return null;
    }
    
    public void init(@Observes Person person){
        DB.put(person.getIdNumber(), createRandomScores());
    }
    
    private List<Score> createRandomScores() {
        Faker faker = new Faker();
        List<Score> scores = new ArrayList<>();
        scores.add(new Score(ScoreType.Driving, Long.valueOf(faker.number().numberBetween(0, 100))));
        scores.add(new Score(ScoreType.Fitness, Long.valueOf(faker.number().numberBetween(0, 100))));
        scores.add(new Score(ScoreType.Activity, Long.valueOf(faker.number().numberBetween(0, 100))));
        scores.add(new Score(ScoreType.Financial, Long.valueOf(faker.number().numberBetween(0, 100))));
        return scores;
    }
}