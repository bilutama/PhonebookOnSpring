package phonebook.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PrintMessageScheduler {
	private static final Logger logger = LoggerFactory.getLogger(PrintMessageScheduler.class);

	@Scheduled(fixedRate = 100000)
	public void logMessage() {
		logger.info("Scheduler is triggered");
	}
}