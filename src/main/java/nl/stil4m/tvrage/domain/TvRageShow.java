package nl.stil4m.tvrage.domain;

public class TvRageShow {

    private final String title;
    private final String href;
    private final String id;

    public TvRageShow(String id, String title, String href) {
        this.id = id;
        this.title = title;
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public String getHref() {
        return href;
    }

    public String getId() {
        return id;
    }
}
