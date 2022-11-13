package com.unq.edu.li.pdesa.mentiUnq.controllers.fixtures;

import com.unq.edu.li.pdesa.mentiUnq.models.MentiOption;
import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.protocols.Status;
import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;
import java.util.List;

public class ResponseUnitFixture
{
	private final Status status = Status.SUCCESS;
	private final String message = Strings.EMPTY;
	private final String payload = Strings.EMPTY;

	public static ResponseUnit withOkResponseFormsUser()
	{
		String payload = "[{\\\"id\\\":2,\\\"code\\\":\\\"c673272d-90ce-4b02-83be-681e1efc2ebf\\\",\\\"codeShare\\\":\\\"YZY3MZI3\\\",\\\"questions\\\":[]}]";
		ResponseUnit responseUnit = ResponseUnitFixture.withDefaults();
		responseUnit.setPayload(payload);
		return responseUnit;
	}

	public static ResponseUnit withOkResponseCreateForm()
	{
		String payload = "{\"id\":1,\"form\":{\"id\":2,\"code\":\"c673272d-90ce-4b02-83be-681e1efc2ebf\",\"codeShare\":\"YZY3MZI3\"},\"userName\":\"pepito\"}";
		ResponseUnit responseUnit = ResponseUnitFixture.withDefaults();
		responseUnit.setPayload(payload);
		return responseUnit;
	}

	public static ResponseUnit withOkResponseSlides()
	{
		String payload =  "[{\"id\":1,\"nombre\":\"Multiple Choice\"}]";
		ResponseUnit responseUnit = ResponseUnitFixture.withDefaults();
		responseUnit.setPayload(payload);
		return responseUnit;
	}

	public static ResponseUnit withDefaults()
	{
		return new ResponseUnitFixture().build();
	}

	public static ResponseUnit withOkResponseSlide()
	{
		String payload =  "{\"id\":1,\"nombre\":\"Multiple Choice\"}";
		ResponseUnit responseUnit = ResponseUnitFixture.withDefaults();
		responseUnit.setPayload(payload);
		return responseUnit;
	}

	public static ResponseUnit withOkAuthenticated()
	{
		String payload = "{\"accessToken\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmcmFzZXNwdXJhc0BnbWFpbC5jb20iLCJpc3MiOiJPQVVUSCIsImV4cCI6MTY2NDA1ODgzMSwiZW1haWwiOiJmcmFzZXNwdXJhc0BnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6WyJVU0VSIl19.S3tpgSkldhP4-aGE8EV-4oJ1xGPlR26iNalnA9DKfS4\"}";
		ResponseUnit responseUnit = ResponseUnitFixture.withDefaults();
		responseUnit.setPayload(payload);
		return responseUnit;
	}

	public static ResponseUnit withOkGetFormByCode()
	{
		String payload = "{\"id\":1,\"form\":{\"id\":2,\"code\":\"c673272d-90ce-4b02-83be-681e1efc2ebf\",\"codeShare\":\"YZY3MZI3\"},\"userName\":\"pepito\"}";
		ResponseUnit responseUnit = ResponseUnitFixture.withDefaults();
		responseUnit.setPayload(payload);
		return responseUnit;
	}

    public static ResponseUnit withOkGetAnswersByQuestionId() {
		String payload = "[{\"id\":1,\"name\":\"pepito\",\"score\":1}]";
		ResponseUnit responseUnit = ResponseUnitFixture.withDefaults();
		responseUnit.setPayload(payload);
		return responseUnit;
    }

	public static ResponseUnit withOkDeleteQuestionById() {
		String payload = "Question with id 1 from Form with id 1 deleted successful";
		ResponseUnit responseUnit = ResponseUnitFixture.withDefaults();
		responseUnit.setPayload(payload);
		return responseUnit;
	}

    public static ResponseUnit withOkDeleteOptionById() {
		String payload = "Option with id 1 from Form with id 1 deleted successful";
		ResponseUnit responseUnit = ResponseUnitFixture.withDefaults();
		responseUnit.setPayload(payload);
		return responseUnit;
    }

	public static ResponseUnit withOkGetQuestionsById() {
		String payload = "[{\"id\":1,\"question\":\"pepito\",\"isCurrent\":\"true\",\"mentiOptions\":\"[]\"}]";
		ResponseUnit responseUnit = ResponseUnitFixture.withDefaults();
		responseUnit.setPayload(payload);
		return responseUnit;
	}

	public static ResponseUnit withOkResultsFormByCode()
	{
		String payload = "{\"votes\":1,\"slides\":\"1\",\"contentSlides\":\"0\",\"openSlides\":\"0\",\"closeSlides\":\"1\"}";
		ResponseUnit responseUnit = ResponseUnitFixture.withDefaults();
		responseUnit.setPayload(payload);
		return responseUnit;
	}

	private ResponseUnit build()
	{
		return new ResponseUnit(status, message, payload);
	}
}
