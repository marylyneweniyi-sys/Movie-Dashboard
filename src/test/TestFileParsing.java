package test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class TestFileParsing {
    private static double DELTA = 0.0001;

    @Test
    public void testParseFileLine(){
        EntryCatalog entryCatalog = null;
        try {
            entryCatalog = new EntryCatalog("./resources/titles.csv");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        String sampleLine = "tm84618,Taxi Driver,MOVIE,\"A mentally unstable Vietnam War veteran " +
                "works as a night-time taxi driver in New York City where the perceived decadence " +
                "and sleaze feed his urge for violent action, attempting to save a preadolescent " +
                "prostitute in the process.\",1976,R,113,8.3";

        assert entryCatalog !=null;
        List<String> fields = entryCatalog.parseEntryLine(sampleLine);
        Assertions.assertEquals("tm84618", fields.get(0));
        Assertions.assertEquals("Taxi Driver", fields.get(1));

    }
    @Test
    public void testParseFileLineTooManyColumns() {
        EntryCatalog catalog = null;
        try {
            catalog = new EntryCatalog("./resources/titles.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String sampleLine = "0,tm84618,Taxi Driver,MOVIE," +
                "\"A mentally unstable Vietnam War veteran works as a night-time taxi driver in " +
                "New York City where the perceived decadence and sleaze feed his urge for violent " +
                "action, attempting to save a preadolescent prostitute in the process.\"" +
                ",1976,R,113,tt0075314,8.3,795222.0,test";

        EntryCatalog entryCatalog = catalog;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assert entryCatalog != null;
            entryCatalog.parseEntryLine(sampleLine);
        });
    }
    @Test
    public void testParseFileLineTooFewColumns() {
        EntryCatalog catalog = null;
        try {
            catalog = new EntryCatalog("./resources/titles.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String sampleLine = "0,tm84618,Taxi Driver,MOVIE," +
                "\"A mentally unstable Vietnam War veteran works as a night-time taxi driver in " +
                "New York City where the perceived decadence and sleaze feed his urge for violent " +
                "action, attempting to save a preadolescent prostitute in the process.\"," +
                "1976";

        EntryCatalog entryCatalog = catalog;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            assert entryCatalog != null;
            entryCatalog.parseEntryLine(sampleLine);
        });
    }
}