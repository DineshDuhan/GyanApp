package com.example1.android.gyanmantra;

public class data {
    private String googlelink,youtubelink;


     public data(){

     }
    public data(String googlelink, String youtubelink) {
        this.googlelink = googlelink;
        this.youtubelink = youtubelink;
    }

    public String getGooglelink() {
        return googlelink;
    }

    public void setGooglelink(String googlelink) {
        this.googlelink = googlelink;
    }

    public String getYoutubelink() {
        return youtubelink;
    }

    public void setYoutubelink(String youtubelink) {
        this.youtubelink = youtubelink;
    }
}
