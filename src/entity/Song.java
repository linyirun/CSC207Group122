package entity;

import java.util.Map;

public class Song {

    private final String id;

    private final String name;

    private final Map<String, Long> artists;    // name : popularity

    public Song(String id, String name, Map<String, Long> artists) {
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

    public Map<String, Long> getArtists() {
        return artists;
    }
}
