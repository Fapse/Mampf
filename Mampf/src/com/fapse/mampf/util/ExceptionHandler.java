package com.fapse.mampf.util;

public interface ExceptionHandler {
	   public void handle(String contextCode, String errorCode,
               String errorText, Throwable t);

	   public void raise(String contextCode, String errorCode,
              String errorText);
}
