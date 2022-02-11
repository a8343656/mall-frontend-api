package com.mall.client.exception;

public class CantBuyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CantBuyException() {}


    public CantBuyException(String message)
    {
       super(message);
    }
}
