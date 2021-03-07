package com.sevensenders.challenge.stripsfetcher.fetchers;

import com.sevensenders.challenge.stripsfetcher.models.Strips;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class Webcomic {


    // fetching json body for xkcd public api
    public JSONArray fetchbody(String url) throws IOException, JSONException {
        JSONObject json = new JSONObject(IOUtils.toString(new URL(url), Charset.forName("UTF-8")));
        JSONArray jsonarray=new JSONArray();
        jsonarray.put(json);
        int num=(int)json.get("num");
        for(int i=num-1;i>(num-10);i--){
            jsonarray.put(new JSONObject(IOUtils.toString(new URL("https://xkcd.com/"+i+"/info.0.json"), Charset.forName("UTF-8")))); // Dynamic creation of URL and fetching json data for xkcd comics
        }
        return jsonarray;
    }

    // fetching entire html content of Poorly Draw lines and providing relevant data in the form of List
    public List<Strips> fetchhtml(String url) throws IOException, ParseException {
        Document doc = Jsoup.connect(url).get(); // Get DOM object of the URL Page

        // Parsing the DOM Object to get relevant data
        Elements links=doc.getElementsByTag("item");
        List<Strips> htmlfetcher= new ArrayList<>();
        int count=0;
        for(Element link:links){
            count++;
            System.out.println(link.getElementsByTag("pubDate").get(0).text());
            Document doccontent=Jsoup.parse(link.getElementsByTag("content:encoded").get(0).text());
            System.out.println(doccontent.getElementsByTag("figure").get(0).getElementsByTag("img").get(0).attr("src"));
            Date date1=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").parse(link.getElementsByTag("pubDate").get(0).text());
            htmlfetcher.add(new Strips(doccontent.getElementsByTag("figure").get(0).getElementsByTag("img").get(0).attr("src"),link.getElementsByTag("title").get(0).text(),link.getElementsByTag("link").get(0).text(),date1)); // Adding Relevant data as Strips object
            if(count==10){
                break;
            }
        }
        System.out.println(htmlfetcher.toString());
        return htmlfetcher;

    }

}
