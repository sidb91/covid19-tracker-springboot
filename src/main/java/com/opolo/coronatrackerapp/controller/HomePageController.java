package com.opolo.coronatrackerapp.controller;

import java.util.List;

import com.opolo.coronatrackerapp.model.CovidTrackerModel;
import com.opolo.coronatrackerapp.services.CoronaDataFetchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomePageController{

    @Autowired
    CoronaDataFetchService coronaDataFetchService;

    @RequestMapping(value="/")
    public String homePage(Model model){

        List<CovidTrackerModel> geoStatsList =  coronaDataFetchService.getGeoCovidDataList();
        
        int totalReportedCases = geoStatsList.stream().mapToInt
        (totalCaseStats -> totalCaseStats.getLatestTotalCases()).sum();

        int newReportedCases = geoStatsList.stream().mapToInt
        (newCaseStats -> newCaseStats.getDiffFromPrevious()).sum();

        model.addAttribute("geoCovidStats",geoStatsList);
        model.addAttribute("totalReportedCases",totalReportedCases);
        model.addAttribute("newCasesSincePrevious", newReportedCases);

        return "home";

    }

}