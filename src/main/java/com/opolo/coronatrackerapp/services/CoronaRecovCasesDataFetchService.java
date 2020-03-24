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

import com.opolo.coronatrackerapp.model.CovidRecovTrackerModel;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CoronaRecovCasesDataFetchService{

private static final String DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Recovered.csv";

    // fetch external timeseries covid confirmed recovered cases data...
    @PostConstruct
    public Map<String,CovidRecovTrackerModel> fetchRecovCasesData() throws IOException, InterruptedException {

        int diffFromPrevious = 0, lastRecord = 0, secondLastRecord = 0;
        CovidRecovTrackerModel covidRecovTrackerObj;

        Map<String, CovidRecovTrackerModel> fetchRecovCasesDataMap =
          new HashMap<String, CovidRecovTrackerModel>();

        try {

            final HttpClient client = HttpClient.newHttpClient();
            final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(DATA_URL)).build();
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final StringReader csvReader = new StringReader(response.body());

            final Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvReader);

            for (final CSVRecord record : records) {

                covidRecovTrackerObj = new CovidRecovTrackerModel();

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

                covidRecovTrackerObj.setState(stateName);
                covidRecovTrackerObj.setCountry(countryName);
                covidRecovTrackerObj.setLatitude(latitude);
                covidRecovTrackerObj.setLongitude(longitude);
                covidRecovTrackerObj.setLatestTotalCases(lastRecord);
                covidRecovTrackerObj.setDiffFromPrevious(diffFromPrevious);

                fetchRecovCasesDataMap.put(countryName.concat(stateName), covidRecovTrackerObj);

            }

            csvReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fetchRecovCasesDataMap;

    }
}