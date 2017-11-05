package com.test.screens;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;

/**
 * Created by ssekar on 11/5/17.
 */
public class Sample {
    public static void main(String Args[]) throws ParseException {
        RestAssured.baseURI = "https://api.flickr.com/services/feeds/photos_public.gne";
        Response response = given().config( RestAssured.config().sslConfig(
                new SSLConfig().relaxedHTTPSValidation() ) )
                .contentType( "application/json" )
                .accept( "application/json" )
                .param("format","json")
                .param("nojsoncallback","1")
                .param("tags","Euston")
                .request().get();
        String responseOut = response.body().asString();
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(responseOut);
        JSONObject jsonObject = (JSONObject) obj;
        List<JSONObject> jsonObjects = (List<JSONObject>) jsonObject.get("items");
        List<String> finalarray = new ArrayList<>();
        jsonObjects.forEach(jsonObject1 -> finalarray.add(jsonObject1.get("title").toString()));
        from( responseOut ).get( "items.title" );
    }
}
