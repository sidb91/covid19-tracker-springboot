package com.opolo.coronatrackerapp.controller;

import java.util.List;

import com.opolo.coronatrackerapp.model.CovidTrackerModel;
import com.opolo.coronatrackerapp.services.CoronaDataFetchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

    @Autowired
    CoronaDataFetchService coronaDataFetchService;

    @RequestMapping(value = "/")
    public String homePage(Model model) {

        List<CovidTrackerModel> geoStatsList = coronaDataFetchService.getGeoCovidDataList();

        // total reported confirmed covid-19 cases
        int totalReportedCases = geoStatsList.stream().mapToInt
        (totalCaseStats -> totalCaseStats.getLatestTotalCases()).sum();

        // number of new reported confirmed covid-19 cases since previous day
        int newReportedCases = geoStatsList.stream().mapToInt
        (newCaseStats -> newCaseStats.getDiffFromPrevious()).sum();

        // total reported confirmed covid-19 death cases
        int totalReportedDeathCases = geoStatsList.stream().mapToInt
        (totalDeathCaseStats -> totalDeathCaseStats.getTotalDeathCases()).sum();

        // number of new reported confirmed covid-19 death cases since previous day
        int newReportedDeathCases = geoStatsList.stream().mapToInt
        (newDeathCaseStats -> newDeathCaseStats.getDiffInDeathFrPrev()).sum();

        // total reported confirmed covid-19 recovered cases
        int totalReportedRecovCases = geoStatsList.stream().mapToInt
        (totalRecovCaseStats -> totalRecovCaseStats.getTotalRecovCases()).sum();

         // number of new reported confirmed covid-19 recovered cases since previous day
         int newReportedRecovCases = geoStatsList.stream().mapToInt
         (newRecovCaseStats -> newRecovCaseStats.getDiffInRecovCases()).sum();

        model.addAttribute("geoCovidStats", geoStatsList);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("newCasesSincePrevious", newReportedCases);
        model.addAttribute("totalReportedDeathCases", totalReportedDeathCases);
        model.addAttribute("newReportedDeathCases", newReportedDeathCases);
        model.addAttribute("totalReportedRecovCases", totalReportedRecovCases);
        model.addAttribute("newReportedRecovCases", newReportedRecovCases);

        return "home";

    }

}