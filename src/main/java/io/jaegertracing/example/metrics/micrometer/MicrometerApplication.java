package io.jaegertracing.example.metrics.micrometer;

import io.jaegertracing.Configuration;
import io.jaegertracing.Tracer;
import io.jaegertracing.micrometer.MicrometerMetricsFactory;
import io.jaegertracing.samplers.ConstSampler;
import io.opentracing.util.GlobalTracer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicrometerApplication {

	public static void main(String[] args) {
		MicrometerMetricsFactory metricsFactory = new MicrometerMetricsFactory();
		Configuration configuration = new Configuration("jaeger-client-java-tester");
		Tracer tracer = configuration
				.withReporter(
						new Configuration.ReporterConfiguration()
								.withLogSpans(true)
				)
				.withSampler(
						new Configuration.SamplerConfiguration()
								.withType(ConstSampler.TYPE)
								.withParam(1)
				)
				.getTracerBuilder()
				.withMetricsFactory(metricsFactory)
				.build();

		GlobalTracer.register(tracer);

		SpringApplication.run(MicrometerApplication.class, args);
	}
}
