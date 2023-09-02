package com.example.currencyconverter;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CurrencyService {
    private final String key = "http://apilayer.net/api/live?access_key=72e701414f5c5c72567a61125b6d248f";
    public JsonObject fetchExchangerates(){
        try {
            HttpClient client = HttpClients.createDefault();
            HttpGet request = new HttpGet(key);
            String JsonRespone = EntityUtils.toString(client.execute(request).getEntity());
            JsonObject jsonObject = JsonParser.parseString(JsonRespone).getAsJsonObject();
            return jsonObject.getAsJsonObject("quotes");
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }
}
