package com.sevensenders.challenge.stripsfetcher;


import com.google.gson.Gson;
import com.sevensenders.challenge.stripsfetcher.controller.StripsController;
import com.sevensenders.challenge.stripsfetcher.fetchers.Webcomic;
import com.sevensenders.challenge.stripsfetcher.models.Strips;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StripsControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(StripsControllerTest.class);

    @Autowired
    @InjectMocks
    StripsController stripsController; // For injecting mocks into controller

    @MockBean
    Webcomic webcomicmock; // Mocking Webcomic component

    @PostConstruct
    public void setupTest() throws IOException, JSONException, ParseException {

        // Creation of mock response for internal calls

        JSONArray jsonarray=new JSONArray();
        int count=11;
        for(int i=0;i<10;i++){
            JSONObject json = new JSONObject();
            json.put("month",count);
            json.put("year",2021);
            json.put("day",count);
            json.put("title","Mocking_xkcd");
            json.put("num",i);
            json.put("img","http://someurl");
            jsonarray.put(json);
            count--;
        }

        when(webcomicmock.fetchbody(any())).thenReturn(jsonarray);

        List<Strips> htmlfetcher= new ArrayList<>();
        Date date=new Date();
        for(int i=0;i<10;i++){
            htmlfetcher.add(new Strips("http://someurl","Mocking_RSSFEED","http://weburl",date));
            date=new Date(date.getTime() - Duration.ofDays(1).toMillis());
        }

        when(webcomicmock.fetchhtml(any())).thenReturn(htmlfetcher);
    }

    @Test
    public void test() throws JSONException, ParseException, IOException {
        String jsonResponse=new Gson().toJson(stripsController.strips());
        System.out.println(jsonResponse); // Printing final mock response, the strips function works as expected
        JSONArray jsonArray=new JSONArray(jsonResponse);
        assertEquals(20,jsonArray.length()); // asserting the size of jsonresponse
    }

}
