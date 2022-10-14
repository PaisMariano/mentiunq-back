package com.unq.edu.li.pdesa.mentiUnq.exceptions;

public class UnauthorizedException extends Exception
{
	private String status;

	public static UnauthorizedException createWith(String status) {
		return new UnauthorizedException(status);
	}
	private UnauthorizedException(String status) {
		this.status = status;
	}

	@Override
	public String getMessage() {
		return status;
	}
}
