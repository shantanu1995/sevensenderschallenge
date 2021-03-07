package com.sevensenders.challenge.stripsfetcher.controller;


import com.sevensenders.challenge.stripsfetcher.fetchers.Webcomic;
import com.sevensenders.challenge.stripsfetcher.models.Strips;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@ComponentScan(basePackageClasses = Webcomic.class)
public class StripsController {

    private static final Logger logger= LoggerFactory.getLogger(StripsController.class);

    @Autowired
    private Webcomic webcomic;

    public StripsController(){

    }

    @GetMapping(value="/strips", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Strips> strips() throws IOException, JSONException, ParseException {
        JSONArray jsonarray=webcomic.fetchbody("https://xkcd.com/info.0.json"); // fetching json data from xkcd
        List<Strips> xkcd= new ArrayList<>();
        for(int i=0;i<jsonarray.length();i++){
            String weburl="https://xkcd.com/"+jsonarray.getJSONObject(i).getString("num")+"/info.0.json"; // Creating Weburl for xkcd feeds
            String publishingDate=jsonarray.getJSONObject(i).getString("day")+"/"+jsonarray.getJSONObject(i).getString("month")+"/"+jsonarray.getJSONObject(i).getString("year");
            Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(publishingDate);
            xkcd.add(new Strips(jsonarray.getJSONObject(i).getString("img"),jsonarray.getJSONObject(i).getString("title"),weburl,date1));
        }

        xkcd.addAll(webcomic.fetchhtml("http://feeds.feedburner.com/PoorlyDrawnLines")); // fetching list of Strips object with last 10 feed data from PDL

        Collections.sort(xkcd, new Comparator<Strips>() {
            @Override
            public int compare(Strips strips, Strips t1) {
                return strips.getPublishingDate().compareTo(t1.getPublishingDate());
            }
        }.reversed()); // Sorting the List based on Publishing Date
        return xkcd;
    }

}
