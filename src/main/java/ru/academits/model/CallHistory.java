package ru.academits.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="call_history")
public class CallHistory {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long outgoingCallContactId;

    @Column
    private Long incomingCallContactId;

    @Column
    private Timestamp callTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOutgoingCallContactId() {
        return outgoingCallContactId;
    }

    public void setOutgoingCallContactId(Long outgoingCallContactId) {
        this.outgoingCallContactId = outgoingCallContactId;
    }

    public Long getIncomingCallContactId() {
        return incomingCallContactId;
    }

    public void setIncomingCallContactId(Long incomingCallContactId) {
        this.incomingCallContactId = incomingCallContactId;
    }

    public Timestamp getCallTime() {
        return callTime;
    }

    public void setCallTime(Timestamp callTime) {
        this.callTime = callTime;
    }
}
