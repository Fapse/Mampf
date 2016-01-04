package com.fapse.mampf.util;

public class ExceptionHandlerImpl {
    public void handle(String errorContext, String errorCode, String errorText, Throwable t){
		if(! (t instanceof EnrichableException)){
			throw new EnrichableException(errorContext, errorCode, errorText, t);
		} else {
			((EnrichableException) t).addInfo(errorContext, errorCode, errorText);
		}
    }

	public void raise(String errorContext, String errorCode, String errorText){
		throw new EnrichableException(errorContext, errorCode, errorText);
	}
}
