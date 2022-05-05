package ru.academits.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "Call")
@Table(name="calls")
public class Call {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long callContactId;

    @Column
    private Timestamp callTime;

    @Column
    private boolean isDeleted;

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCallContactId() {
        return callContactId;
    }

    public void setCallContactId(Long callContactId) {
        this.callContactId = callContactId;
    }

    public Timestamp getCallTime() {
        return callTime;
    }

    public void setCallTime(Timestamp callTime) {
        this.callTime = callTime;
    }
}