package phonebook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "calls")
public class Call {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Long callContactId;

	@Column
	private Timestamp callTime;

	@Column
	private boolean isDeleted;

	public Call() {
	}

	public Call(
		Long callContactId,
		Timestamp callTime
	) {
		this.callContactId = callContactId;
		this.callTime = callTime;
	}

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