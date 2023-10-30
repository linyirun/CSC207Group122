package entity;

import java.util.List;

public class Song {

    private final String id;

    private final String name;

    private List<String> artists;

    public Song(String id, String name, List<String> artists) {
        this.id = id;
        this.name = name;
        this.artists = artists;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getArtists() {
        return artists;
    }
}
