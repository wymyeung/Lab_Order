package com.ibm.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.jaegertracing.Configuration;
import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.jaegertracing.internal.samplers.ConstSampler;

@SpringBootApplication
@EnableCircuitBreaker
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

	@Bean // Tracer bean
	public io.opentracing.Tracer jaegerTracer() {
		SamplerConfiguration samplerConfig = SamplerConfiguration.fromEnv().withType(ConstSampler.TYPE).withParam(1);
		ReporterConfiguration reporterConfig = ReporterConfiguration.fromEnv().withLogSpans(true);
		return new Configuration("Menu-service").withSampler(samplerConfig).withReporter(reporterConfig).getTracer();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
