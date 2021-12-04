package com.hank.process.jobs;

import com.hank.process.jobs.jobprocessor.JobProcessor;
import com.hank.process.jobs.jobrequest.JobRequest;

public class Browser implements JobProcessor {
    private final String tabCount = "--pinned-tab-count=";
    private final String browser = "Google Chrome";
    private final Runtime runtime = Runtime.getRuntime();
    @Override
    public void excute(JobRequest jobRequest) {

    }
}
