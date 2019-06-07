package com.example.musicmanagment.model;



//import javax.persistence.Entity;
//import javax.persistence.Table;

public class Song {
    private int id;
    private String name;
    private String singer;
    private boolean favor;
    private boolean listened;

    public boolean isFavor() {
        return favor;
    }

    public void setFavor(boolean favor) {
        this.favor = favor;
    }

    public boolean isListened() {
        return listened;
    }

    public void setListened(boolean listened) {
        this.listened = listened;
    }



    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
               ", singer='" + singer + '\'' +
                ", favor='" + favor + '\'' +
                ", listened='" + listened + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}
