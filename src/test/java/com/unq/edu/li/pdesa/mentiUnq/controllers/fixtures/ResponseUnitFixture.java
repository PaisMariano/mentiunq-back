package com.unq.edu.li.pdesa.mentiUnq.controllers.fixtures;

import com.unq.edu.li.pdesa.mentiUnq.protocols.ResponseUnit;
import com.unq.edu.li.pdesa.mentiUnq.protocols.Status;
import org.apache.logging.log4j.util.Strings;

public class ResponseUnitFixture
{
	private static final Status status = Status.SUCCESS;
	private static final  String message = Strings.EMPTY;
	private static final  String payload = Strings.EMPTY;

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
		String payload =  "[{\"id\":1,\"nombre\":\"Multiple Choice\"},{\"id\":2,\"nombre\":\"World Cloud\"},{\"id\":3,\"nombre\":\"Open Ended\"},{\"id\":4,\"nombre\":\"Scales\"},{\"id\":5,\"nombre\":\"Ranking\"},{\"id\":6,\"nombre\":\"Q\\u0026A\"},{\"id\":7,\"nombre\":\"Select Answer\"},{\"id\":8,\"nombre\":\"Type Answer\"},{\"id\":9,\"nombre\":\"Heading\"},{\"id\":10,\"nombre\":\"Paragraph\"},{\"id\":11,\"nombre\":\"Bullets\"}]";
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
		String payload =  "{\"accessToken\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmcmFzZXNwdXJhc0BnbWFpbC5jb20iLCJpc3MiOiJPQVVUSCIsImV4cCI6MTY2NDA1ODgzMSwiZW1haWwiOiJmcmFzZXNwdXJhc0BnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6WyJVU0VSIl19.S3tpgSkldhP4-aGE8EV-4oJ1xGPlR26iNalnA9DKfS4\"}";
		ResponseUnit responseUnit = ResponseUnitFixture.withDefaults();
		responseUnit.setPayload(payload);
		return responseUnit;
	}

	private ResponseUnit build()
	{
		return new ResponseUnit(status, message, payload);
	}
}
