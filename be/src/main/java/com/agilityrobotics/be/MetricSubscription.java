package com.agilityrobotics.be;

public record MetricSubscription(
	String metricName,
	String workcellFilter,
	String robotFilter,
	String workflowFilter
) {
	public MetricSubscription() {
		this(null, null, null, null);
	}
}
