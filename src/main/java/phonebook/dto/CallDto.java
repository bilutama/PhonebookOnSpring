package phonebook.dto;

import java.sql.Timestamp;

public class CallDto {
	private Long id;

	private Long callContactId;

	private Timestamp callTime;

	public CallDto() {
	}

	public CallDto(
		Long callContactId,
		Timestamp callTime
	) {
		this.callContactId = callContactId;
		this.callTime = callTime;
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