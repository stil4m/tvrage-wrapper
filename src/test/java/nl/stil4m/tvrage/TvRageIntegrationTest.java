package nl.stil4m.tvrage;

import nl.stil4m.tvrage.domain.SearchResult;
import nl.stil4m.tvrage.domain.Season;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class TvRageIntegrationTest {

    private TvRage tvRage;

    @Before
    public void before() {
        tvRage = new TvRage();
    }

    @Test
    public void testFetchingLost() throws IOException {
        List<SearchResult> result = tvRage.search("lost");

        assertThat(result.get(0).getScore(), is(100.0));
        assertThat(result.get(0).getTvRageShow().getTitle(), is("Lost"));
        assertThat(result.get(0).getTvRageShow().getId(), is(nullValue()));
        assertThat(result.get(0).getTvRageShow().getHref(), is("/Lost"));

        List<Season> seasons = tvRage.getSeasons(result.get(0).getTvRageShow());
        assertThat(seasons.size(), is(6));

        assertThat(seasons.get(0).getSeasonNumber(), is(1));
        assertThat(seasons.get(0).getEpisodes().size(), is(25));
        assertThat(seasons.get(0).getEpisodes().get(0).getEpisodeName(), is("Pilot (1)"));
        assertThat(seasons.get(0).getEpisodes().get(0).getEpisodeNumber(), is(1));
        assertThat(seasons.get(0).getEpisodes().get(0).getSeasonEpisodeNumber(), is(1));

    }
}
