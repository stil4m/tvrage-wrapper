package nl.stil4m.tvrage.domain;

public class SimpleEpisode {

    private final Integer episodeNumber;
    private final Integer seasonEpisodeNumber;
    private final String episodeName;

    public SimpleEpisode(Integer episodeNumber, Integer seasonEpisodeNumber, String episodeName) {
        this.episodeNumber = episodeNumber;
        this.seasonEpisodeNumber = seasonEpisodeNumber;
        this.episodeName = episodeName;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public Integer getSeasonEpisodeNumber() {
        return seasonEpisodeNumber;
    }

    public String getEpisodeName() {
        return episodeName;
    }
}
