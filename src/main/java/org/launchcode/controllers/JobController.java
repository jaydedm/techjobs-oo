package org.launchcode.controllers;
import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, @RequestParam int id) {


        // TODO #1 - get the Job with the given ID and pass it into the view
        Job c = jobData.findById(id);
        model.addAttribute("job", c);;
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.


        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            return "new-job";
        }
        //model.addAttribute("jobForm", new Job());
        int emp = jobForm.getEmployerId();
        int loc = jobForm.getLocationId();
        int core = jobForm.getCoreCompetenciesId();
        int pos = jobForm.getPositionTypesId();
        String name = jobForm.getName();

        Employer employer = jobData.getEmployers().findById(emp);
        Location location = jobData.getLocations().findById(loc);
        PositionType positionType = jobData.getPositionTypes().findById(pos);
        CoreCompetency coreCompetency = jobData.getCoreCompetencies().findById(core);

        Job newJob = new Job(name, employer, location, positionType, coreCompetency);



        jobData.add(newJob);
        int id = newJob.getId();

        return "redirect:?id=" + id ;

    }
}
