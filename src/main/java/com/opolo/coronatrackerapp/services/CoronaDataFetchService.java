package com.opolo.coronatrackerapp.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.opolo.coronatrackerapp.model.CovidDeathTrackerModel;
import com.opolo.coronatrackerapp.model.CovidRecovTrackerModel;
import com.opolo.coronatrackerapp.model.CovidTrackerModel;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CoronaDataFetchService {

    private static final String datatURL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

    private List<CovidTrackerModel> geoCovidDataList = new ArrayList<CovidTrackerModel>();

    @Autowired
    private CoronaDeathCaseService coronaDeathServ;

    @Autowired
    private CoronaRecovCasesDataFetchService coronaRecovServ;

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchData() throws IOException, InterruptedException {

        int diffFromPrevious = 0, lastRecord = 0, secondLastRecord = 0;
        CovidTrackerModel covidTrackerObj;
        final List<CovidTrackerModel> localGeoCovidDataList = new ArrayList<CovidTrackerModel>();

        Map<String, CovidDeathTrackerModel> countryWiseDeathMap = 
        new HashMap<String, CovidDeathTrackerModel>();

        Map<String, CovidRecovTrackerModel> countryWiseRecovMap = 
        new HashMap<String, CovidRecovTrackerModel>();

        try {

            // fetch external timeseries covid death cases data...
            countryWiseDeathMap = coronaDeathServ.fetchDeathCasesData();

            // fetch external timeseries covid confirmed recovered cases data...
            countryWiseRecovMap = coronaRecovServ.fetchRecovCasesData();

            // fetch external timeseries covid confirmed cases data...
            final HttpClient client = HttpClient.newHttpClient();
            final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(datatURL)).build();
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            final StringReader csvReader = new StringReader(response.body());

            final Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvReader);

            for (final CSVRecord record : records) {

                covidTrackerObj = new CovidTrackerModel();

                if (StringUtils.hasText(record.get(record.size() - 1))
                        && StringUtils.hasText(record.get(record.size() - 2))) {

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

                //accumulate death cases timeseries data from map...
                String searchDeathKey = countryName.concat(stateName);
                CovidDeathTrackerModel covidDeathTrackerObj = countryWiseDeathMap.get(searchDeathKey);
                int totalDeathPerCountry = covidDeathTrackerObj.getLatestTotalCases();
                int diffInDeathPerCountry = covidDeathTrackerObj.getDiffFromPrevious();


                //accumulate recovered cases timeseries data from map...
                String searchRecovKey = countryName.concat(stateName);
                CovidRecovTrackerModel covidRecovTrackerObj = countryWiseRecovMap.get(searchRecovKey);
                int totalRecovPerCountry = covidRecovTrackerObj.getLatestTotalCases();
                int diffInRecovPerCountry = covidRecovTrackerObj.getDiffFromPrevious();


                covidTrackerObj.setState(stateName);
                covidTrackerObj.setCountry(countryName);
                covidTrackerObj.setLatitude(latitude);
                covidTrackerObj.setLongitude(longitude);
                covidTrackerObj.setLatestTotalCases(lastRecord);
                covidTrackerObj.setDiffFromPrevious(diffFromPrevious);
                covidTrackerObj.setTotalDeathCases(totalDeathPerCountry);
                covidTrackerObj.setDiffInDeathFrPrev(diffInDeathPerCountry);
                covidTrackerObj.setTotalRecovCases(totalRecovPerCountry);
                covidTrackerObj.setDiffInRecovCases(diffInRecovPerCountry);
                //System.out.println(diffInRecovPerCountry);

                localGeoCovidDataList.add(covidTrackerObj);

            }

            this.geoCovidDataList = localGeoCovidDataList;

            csvReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<CovidTrackerModel> getGeoCovidDataList() {
        return geoCovidDataList;
    }

    public void setGeoCovidDataList(List<CovidTrackerModel> geoCovidDataList) {
        this.geoCovidDataList = geoCovidDataList;
    }

}