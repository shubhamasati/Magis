package com.hank.speechengine.recognizer;

/**
 * Response listeners for URL connections.
 * @author Skylion
 *
 */
public interface GSpeechResponseListener {
	
	void onResponse(com.hank.speechengine.recognizer.GoogleResponse gr);
	
}
