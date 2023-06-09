package com.linseven.config;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import metrics_influxdb.HttpInfluxdbProtocol;
import metrics_influxdb.InfluxdbReporter;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
//@EnableMetrics
public class MetricsConfig extends MetricsConfigurerAdapter {

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        initInfluxdbReporter(metricRegistry);
    }

    private void initInfluxdbReporter(MetricRegistry metricRegistry) {

        InfluxdbReporter.Builder builder = InfluxdbReporter.forRegistry(metricRegistry);
        builder.protocol(new HttpInfluxdbProtocol("127.0.0.1", 8086, "root","root234"));
        builder.convertRatesTo(TimeUnit.SECONDS);
        builder.convertDurationsTo(TimeUnit.MILLISECONDS);
        builder.filter(MetricFilter.ALL);
        builder.skipIdleMetrics(false);
        ScheduledReporter influxdbReporter = builder.build();
        influxdbReporter.start(5, TimeUnit.SECONDS);

        super.registerReporter(influxdbReporter);
    }
}
