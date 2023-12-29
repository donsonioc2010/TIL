package org.example;

import java.lang.instrument.Instrumentation;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * Hello world!
 *
 */
public class JavaAgentSample {
	public static void premain(String agentArgs, Instrumentation inst) {
		new AgentBuilder.Default()
			.type(ElementMatchers.any())
			.transform((builder, typeDescription, classLoader, javaModule, protectionDomain) ->
				builder.method(ElementMatchers.named("pullOut")).intercept(FixedValue.value("Rabbit!")))
			.installOn(inst);
	}
}
