package com.springbatch.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1")
public class JobLauncherController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;


    @GetMapping(value = "/start")
    public String startJob(){
        try{

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("nombre", "test1")
                .toJobParameters();

        jobLauncher.run(job, jobParameters);
        }catch (Exception e){
            log.error("ERROR: " + e);
        }

        return "200";
    }

}
