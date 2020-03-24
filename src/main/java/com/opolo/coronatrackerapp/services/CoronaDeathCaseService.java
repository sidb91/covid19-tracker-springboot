package com.opolo.coronatrackerapp.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.opolo.coronatrackerapp.model.CovidDeathTrackerModel;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CoronaDeathCaseService {

    
    private static final String datatURL = 
    "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Deaths.csv";

    // fetch external timeseries covid confirmed death cases data...
    @PostConstruct
    public Map<String,CovidDeathTrackerModel> fetchDeathCasesData() throws IOException, InterruptedException {

        int diffFromPrevious = 0, lastRecord = 0, secondLastRecord = 0;
        CovidDeathTrackerModel covidDeathTrackerObj;

        Map<String, CovidDeathTrackerModel> fetchDeathCasesDataMap =
          new HashMap<String, CovidDeathTrackerModel>();

        try {

            final HttpClient client = HttpClient.newHttpClient();
            final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(datatURL)).build();
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final StringReader csvReader = new StringReader(response.body());

            final Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvReader);

            for (final CSVRecord record : records) {

                covidDeathTrackerObj = new CovidDeathTrackerModel();

                if (StringUtils.hasText(record.get(record.size() - 1)) &&

                        StringUtils.hasText(record.get(record.size() - 2))) {

                    lastRecord = Integer.parseInt(record.get(record.size() - 1));
                    secondLastRecord = Integer.parseInt(record.get(record.size() - 2));

                    diffFromPrevious = lastRecord - secondLastRecord;

                } else if (!StringUtils.hasText(record.get(record.size() - 1))
                        && StringUtils.hasText(record.get(record.size() - 2))) {

                    diffFromPrevious = 0;

                } else if (StringUtils.hasText(record.get(record.size() - 1))
                        && !StringUtils.hasText(record.get(record.size() - 2))) {

                    diffFromPrevious = 0;
                    lastRecord = Integer.parseInt(record.get(record.size() - 1));

                } else {

                    diffFromPrevious = 0;

                }

                String stateName = (record.get("Province/State").equals("") || 
                record.get("Province/State") == null) ? "<Country-wide-data>" : record.get("Province/State");

                String countryName = record.get("Country/Region");
                String latitude = record.get("Lat");
                String longitude = record.get("Long");

                covidDeathTrackerObj.setState(stateName);
                covidDeathTrackerObj.setCountry(countryName);
                covidDeathTrackerObj.setLatitude(latitude);
                covidDeathTrackerObj.setLongitude(longitude);
                covidDeathTrackerObj.setLatestTotalCases(lastRecord);
                covidDeathTrackerObj.setDiffFromPrevious(diffFromPrevious);

                fetchDeathCasesDataMap.put(countryName.concat(stateName), covidDeathTrackerObj);

            }

            csvReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fetchDeathCasesDataMap;

    }


}