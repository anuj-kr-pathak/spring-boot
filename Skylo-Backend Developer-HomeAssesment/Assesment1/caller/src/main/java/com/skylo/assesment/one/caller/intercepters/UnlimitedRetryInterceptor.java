package com.skylo.assesment.one.caller.intercepters;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class UnlimitedRetryInterceptor implements ClientHttpRequestInterceptor {

    private final long backOff;

    public UnlimitedRetryInterceptor(long backOff) {
        this.backOff = backOff;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        while (true) {
            try {
                return execution.execute(request, body);
            } catch (IOException e) {
                try {
                    Thread.sleep(backOff); // Wait before the next attempt
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Interrupted while waiting to retry", interruptedException);
                }
            }
        }
    }

}
