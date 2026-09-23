package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.sheffield.com114.assignment3_2526.codeprovided.AbstractEntryCatalog;

import java.io.IOException;

public class TestEntryCatalog {
    private static final double DELTA = 0.0001;

    @Test
    public void testUpdateCatalog() {
        EntryCatalog catalog = null;
        try {
            catalog = new EntryCatalog("./resources/titles.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(5283, catalog.getEntriesList().size());

    }
    @Test
    public void testGetAverageImdbScoreTitleForShow() {
        EntryCatalog catalog = null;
        try {
            catalog = new EntryCatalog("./resources/titles.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        double avgScore = catalog.getAverageImdbScoreTitle(AbstractEntryCatalog.Type.SHOW);
        Assertions.assertEquals(7.017377398720683, avgScore, DELTA);
    }

}
