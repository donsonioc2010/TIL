package com.jong1.advanced.trace.hellotrace;

import com.jong1.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HelloTraceV2Test {

    @Test
    void begin_end() {
        HelloTraceV2 trace = new HelloTraceV2();
        TraceStatus status = trace.begin("hello");
        TraceStatus status2 = trace.beginSync(status.getTraceId(), "hello2");
        TraceStatus status3 = trace.beginSync(status2.getTraceId(), "hello3");
        trace.end(status3);
        trace.end(status2);
        trace.end(status);
    }

    @Test
    void begin_Exception() {
        HelloTraceV2 trace = new HelloTraceV2();
        TraceStatus status = trace.begin("hello");
        TraceStatus status2 = trace.beginSync(status.getTraceId(), "hello2");
        TraceStatus status3 = trace.beginSync(status2.getTraceId(), "hello3");
        Exception e = new IllegalStateException();
        trace.exception(status3, e);
        trace.exception(status2, e);
        trace.exception(status, e);
    }

}