package com.jong1.advanced.trace.template;

import com.jong1.advanced.trace.TraceStatus;
import com.jong1.advanced.trace.logtrace.LogTrace;


public abstract class AbstarctTemplate<T> {
    private final LogTrace trace;

    public AbstarctTemplate(LogTrace trace) {
        this.trace = trace;
    }

    public T execute(String message) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);
            T result = call();
            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    protected abstract T call();
}
