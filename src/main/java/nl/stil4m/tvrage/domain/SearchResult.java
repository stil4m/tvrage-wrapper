package nl.stil4m.tvrage.domain;

public class SearchResult {

    private TvRageShow tvRageShow;

    private Double score;

    public SearchResult(TvRageShow tvRageShow, Double score) {
        this.tvRageShow = tvRageShow;
        this.score = score;
    }

    public TvRageShow getTvRageShow() {
        return tvRageShow;
    }

    public Double getScore() {
        return score;
    }
}
