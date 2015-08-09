package com.giljulio.tuttifrutti.net;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by Gil on 09/08/15.
 */
public class DynamicJsonConverter implements Converter {

    @Override public Object fromBody(TypedInput typedInput, Type type) throws ConversionException {
        try {
            InputStream in = typedInput.in(); // convert the typedInput to String
            String string = fromStream(in);
            in.close(); // we are responsible to close the InputStream after use


            // Wikimedia generates keys based on article id, therefore conversion has be done manually
            JsonElement rootElement = new JsonParser().parse(string);

            if(rootElement.isJsonObject() && rootElement.getAsJsonObject().has("query")){
                JsonObject queryObject = rootElement.getAsJsonObject().getAsJsonObject("query");
                JsonObject pagesObject = queryObject.getAsJsonObject("pages");

                // Picks the first entry in the json object
                Iterator<Map.Entry<String, JsonElement>> keys = pagesObject.entrySet().iterator();
                if(keys.hasNext()){
                    Map.Entry<String, JsonElement> element = keys.next();
                    return new Gson().fromJson(element.getValue(), type);
                }
            }
            return new Gson().fromJson(string, type); // convert to the supplied type, typically Object, JsonObject or Map<String, Object>
        } catch (Exception e) { // a lot may happen here, whatever happens
            throw new ConversionException(e); // wrap it into ConversionException so retrofit can process it
        }
    }

    @Override public TypedOutput toBody(Object object) { // not required
        return null;
    }

    private static String fromStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            out.append("\r\n");
        }
        return out.toString();
    }
}