package phonebook.api.v1.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import phonebook.model.ErrorInfo;

@ControllerAdvice
public class ErrorController {
	private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ErrorInfo processException(Exception e) {
		logger.info("error happened", e);
		return new ErrorInfo(e.getMessage());
	}
}