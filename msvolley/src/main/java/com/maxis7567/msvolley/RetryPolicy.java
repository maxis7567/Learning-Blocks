package com.maxis7567.msvolley;

public class RetryPolicy {
    private int timeoutMs = 2500;

    private int maxRetries = 1;

    private float backoffMulti=0;

    public int getTimeoutMs() {
        return timeoutMs;
    }

    public RetryPolicy setTimeoutMs(int timeoutMs) {
        this.timeoutMs = timeoutMs;
        return this;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public RetryPolicy setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
        return this;
    }

    public float getBackoffMulti() {
        return backoffMulti;
    }

    public RetryPolicy setBackoffMulti(float backoffMulti) {
        this.backoffMulti = backoffMulti;
        return this;
    }
}
