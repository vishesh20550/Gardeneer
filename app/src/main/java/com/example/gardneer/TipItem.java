package com.example.gardneer;

public class TipItem {

    private String heading;
    private String details;

    public TipItem(String heading, String details) {
        this.heading = heading;
        this.details = details;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
