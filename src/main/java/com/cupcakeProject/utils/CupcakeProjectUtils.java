package com.cupcakeProject.utils;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class CupcakeProjectUtils {

    private CupcakeProjectUtils() {

    }

    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus status) {
        return new ResponseEntity<>("{\"message\":\"" + message + "\"}", status);
    }

    public static String getUUID() {
        Date data = new Date();
        long time = data.getTime();
        return "Bill - " + time;
    }

    public static JSONArray getJsonArrayFromString(String data) throws JSONException {
        return new JSONArray(data);
    }

    public static Map<String, Object> getMapFromJson(String data) {
        if (!Strings.isNullOrEmpty(data)) {
            return new Gson().fromJson(data, new TypeToken<Map<String, Object>>(){
            }.getType());
        }
        return new HashMap<>();
    }
    public static Boolean isFileExist(String filePath){
        log.info("inside is fileExist: " + filePath);
        try {
            File file = new File(filePath);
            return (file !=null && file.exists())? Boolean.TRUE : Boolean.FALSE;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
