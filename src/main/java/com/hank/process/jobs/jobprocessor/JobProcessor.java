package com.hank.process.jobs.jobprocessor;

import com.hank.process.jobs.jobrequest.JobRequest;

public interface JobProcessor {
    void excute(JobRequest jobRequest);
}
