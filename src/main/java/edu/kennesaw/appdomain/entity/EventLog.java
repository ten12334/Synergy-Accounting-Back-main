package edu.kennesaw.appdomain.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Calendar;

//Eventlog added at 9:23 on 10/11/24
public class EventLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String eventType;
    private String beforeImage;
    private String afterImage;
    private LocalDateTime timestamp;
    private String userID;

    public Long getId() {
        return id;
    }

    public String getEventType() {
        return eventType;
    }

    public String getBeforeImage() {
        return beforeImage;
    }

    public String getAfterImage() {
        return afterImage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUserID() {
        return userID;
    }

    public void setID(Long id) {
        this.id = id;
    }
    public void setTimestamp(LocalDateTime timestamp){
        this.timestamp =timestamp;
    }
    public void setId(String userID){
        this.userID = userID;
    }
    public void setEventType(String eventType){
        this.eventType = eventType;
    }
    public void setBeforeImage(String beforeImage){
        this.beforeImage = beforeImage;
    }
    public void setAfterImage(String afterImage) {
        this.afterImage = afterImage;
    }
}