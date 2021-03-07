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

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Webcomic {

    public JSONArray fetchbody(String url) throws IOException, JSONException {
        JSONObject json = new JSONObject(IOUtils.toString(new URL(url), Charset.forName("UTF-8")));
        JSONArray jsonarray=new JSONArray();
        jsonarray.put(json);
        int num=(int)json.get("num");
        for(int i=num-1;i>(num-10);i--){
            jsonarray.put(new JSONObject(IOUtils.toString(new URL("https://xkcd.com/"+i+"/info.0.json"), Charset.forName("UTF-8"))));
        }
        return jsonarray;
    }

    public List<Strips> fetchhtml(String url) throws IOException, ParseException {
        Document doc = Jsoup.connect(url).get();
        Elements links=doc.getElementsByTag("item");
        List<Strips> htmlfetcher= new ArrayList<>();
        int count=0;
        for(Element link:links){
            count++;
            System.out.println(link.getElementsByTag("pubDate").get(0).text());
            Document doccontent=Jsoup.parse(link.getElementsByTag("content:encoded").get(0).text());
            System.out.println(doccontent.getElementsByTag("figure").get(0).getElementsByTag("img").get(0).attr("src"));
            Date date1=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z").parse(link.getElementsByTag("pubDate").get(0).text());
            htmlfetcher.add(new Strips(doccontent.getElementsByTag("figure").get(0).getElementsByTag("img").get(0).attr("src"),link.getElementsByTag("title").get(0).text(),link.getElementsByTag("link").get(0).text(),date1));
            if(count==10){
                break;
            }
        }
        System.out.println(htmlfetcher.toString());
        return htmlfetcher;

    }

}
