package net.project.journalApp.api.response;

import lombok.Data;

@Data
public class WeatherResponse{
    private Request request;
    private Current current;


    @Data
    public class Current{

        private int temperature;


        private int humidity;

        private int feelslike;

        private int visibility;

    }


    @Data
    public class Request{
        private String type;
        private String query;
        private String language;
        private String unit;
    }

}







