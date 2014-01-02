package nl.stil4m.tvrage;

import nl.stil4m.tvrage.domain.SearchResult;
import nl.stil4m.tvrage.domain.Season;
import nl.stil4m.tvrage.domain.SimpleEpisode;
import nl.stil4m.tvrage.domain.TvRageShow;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TvRage {

    private String BASE_URL = "http://www.tvrage.com";

    public List<SearchResult> search(String query) throws IOException {
        Connection connection = Jsoup.connect(BASE_URL + "/search.php?search=" + query.replace(" ", "+") + "&sonly=1");
        Document document = connection.get();

        Elements elements = document.select("div#show_search");
        List<SearchResult> result = new ArrayList<>();
        for (Element element : elements) {
            final String title = element.select("h2").text().trim();
            final String href = element.select("h2").select("a").attr("href");
            final String id;
            if (href.lastIndexOf("-") == -1) {
                id = null;
            } else {
                id = href.substring(href.lastIndexOf("-") + 1);
            }

            double score = calculateScore(query, title);
            result.add(new SearchResult(new TvRageShow(id, title, href), score));
        }
        return result;
    }

    public List<Season> getSeasons(TvRageShow show) throws IOException {
        Connection connection = Jsoup.connect(BASE_URL + show.getHref() + "/episode_list/all");
        Document document = connection.get();

        Map<Integer, Season> seasonMap = new HashMap<>();

        for (Element element : document.select("tr#brow")) {
            Elements tds = element.select("td");
            if (!tds.get(0).text().matches("[0-9]*")) {
                continue;
            }
            Integer episodeNumber = Integer.parseInt(tds.get(0).text());
            String episodeDescriptor = tds.get(1).text();
            String[] episodeDescriptorParts = episodeDescriptor.split("x");
            Integer seasonNumber = Integer.parseInt(episodeDescriptorParts[0]);
            Integer seasonEpisodeNumber = Integer.parseInt(episodeDescriptorParts[1]);
            String episodeName = tds.get(3).text().trim();

            SimpleEpisode simpleEpisode = new SimpleEpisode(episodeNumber, seasonEpisodeNumber, episodeName);
            Season season = seasonMap.get(seasonNumber);
            if (season == null) {
                season = new Season(seasonNumber);
                seasonMap.put(seasonNumber, season);
            }
            season.addEpisode(simpleEpisode);
        }

        List<Season> seasons = new ArrayList<>();
        seasons.addAll(seasonMap.values());
        Collections.sort(seasons, new Comparator<Season>() {
            @Override
            public int compare(Season o1, Season o2) {
                return o1.getSeasonNumber().compareTo(o2.getSeasonNumber());
            }
        });
        return seasons;
    }

    private double calculateScore(String query, String title) {
        Set<String> querySet = new HashSet<>();
        Set<String> titleSet = new HashSet<>();
        querySet.addAll(Arrays.asList(query.toLowerCase().split(" ")));
        titleSet.addAll(Arrays.asList(title.toLowerCase().split(" ")));

        int size = titleSet.size() > querySet.size() ? titleSet.size() : querySet.size();
        titleSet.retainAll(querySet);
        return 100.0 / size * titleSet.size();
    }
}
