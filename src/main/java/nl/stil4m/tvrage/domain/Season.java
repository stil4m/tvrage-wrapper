package nl.stil4m.tvrage.domain;

import java.util.ArrayList;
import java.util.List;

public class Season {

    private final List<SimpleEpisode> episodes;
    private final Integer seasonNumber;

    public Season(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
        this.episodes = new ArrayList<>();
    }

    public List<SimpleEpisode> getEpisodes() {
        return episodes;
    }

    public void addEpisode(SimpleEpisode simpleEpisode) {
        episodes.add(simpleEpisode);
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }
}
