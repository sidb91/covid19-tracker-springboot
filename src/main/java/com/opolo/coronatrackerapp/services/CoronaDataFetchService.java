package com.opolo.coronatrackerapp.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.opolo.coronatrackerapp.model.CovidTrackerModel;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CoronaDataFetchService {

    private static final String datatURL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

    private List<CovidTrackerModel> geoCovidDataList = new ArrayList<CovidTrackerModel>();

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchData() throws IOException, InterruptedException {

        CovidTrackerModel covidTrackerObj;
        final List<CovidTrackerModel> localGeoCovidDataList = new ArrayList<CovidTrackerModel>();

        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(datatURL)).build();

        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
       
        final StringReader csvReader = new StringReader(response.body());
        
        final Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvReader);

    
        for (final CSVRecord record : records) {

            covidTrackerObj = new CovidTrackerModel();

            int diffFromPrevious = Integer.parseInt(record.get(record.size() - 1)) - 
            Integer.parseInt(record.get(record.size() - 2));

            String stateName = record.get("Province/State").equals("") ? "<Country-wide-data>" : 
            record.get("Province/State");
            
            covidTrackerObj.setState(stateName);
            covidTrackerObj.setCountry(record.get("Country/Region"));
            covidTrackerObj.setLatestTotalCases(Integer.parseInt(record.get(record.size() - 1)));
            covidTrackerObj.setDiffFromPrevious(diffFromPrevious);

            System.out.println(covidTrackerObj);
            localGeoCovidDataList.add(covidTrackerObj);
            
        }

        this.geoCovidDataList = localGeoCovidDataList;
         
        csvReader.close();
    }

    public List<CovidTrackerModel> getGeoCovidDataList() {
        return geoCovidDataList;
    }

    public void setGeoCovidDataList(List<CovidTrackerModel> geoCovidDataList) {
        this.geoCovidDataList = geoCovidDataList;
    }

}