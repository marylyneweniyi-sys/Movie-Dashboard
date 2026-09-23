package java.uk.ac.sheffield.com114.assignment3_2526.codeprovided;

import uk.ac.sheffield.com114.assignment3_2526.Entry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

// This abstract class provides basic reading functionalities of the provided dataset with entries.
// You will need to implement all the abstract methods in this class by overriding them.
// You can not change the implementation of this class as it is part of a codeprovided package

public abstract class AbstractEntryCatalog {

    protected List<AbstractEntry> entriesList;

    // reads data from the textFileName and populates entryList, the one containing all data entries
    public AbstractEntryCatalog(String textFileName) throws IllegalArgumentException, IOException {
        this.entriesList = new ArrayList<>();
        List<AbstractEntry> entriesFromFile = readEntriesFromTextDataFile(textFileName);
        entriesList.addAll(entriesFromFile);
    }

    // reads the text file provided and creates a list of Entry objects in the data file
    // catches exception errors should they occur and delegates handling of other exceptions
    private List<AbstractEntry> readEntriesFromTextDataFile(String textFileName)
            throws IllegalArgumentException, IOException {
        List<AbstractEntry> entriesList = new ArrayList<>();
        int count = 1;

        textFileName = textFileName.replaceAll(" ", "");

        BufferedReader br = new BufferedReader(new FileReader(textFileName));
        String line = br.readLine();
        if (line == null) {
            throw new IllegalArgumentException("File is empty." +
                    " Please run the programme again and provide a valid dataset.");
        }
        while ((line = br.readLine()) != null) {
            try {
                // The entry ID is created by this reader; it is not provided in the original files
                // The ID should _not_ be modified later
                int id = count;
                // You will need to work on the implementation of your Entry class
                AbstractEntry entry = new Entry(id, parseEntryLine(line));
                entriesList.add(entry);
                count++;

            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("File format is incorrect; only double values are allowed. "
                        + "See line: " + (count + 1));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Malformed entry line: " + line + "\nSee line: " + (count + 1));
            }
        }
        return entriesList;
    }

    // returns the list with the entries
    public List<AbstractEntry> getEntriesList() {
        return entriesList;
    }

    // parses the properties from a given line in the entry catalog file
    // you SHOULD expect that values appear in the CORRECT order
    // IF anything in that line is malformed (undefined properties, less or more values than expected, then
    // an IllegalArgumentException should be thrown with an appropriate message
    public abstract List<String> parseEntryLine(String line) throws IllegalArgumentException;


    public abstract int getNumberOfEntries();

    public abstract int getEarliestReleaseYear();

    public abstract double getAverageImdbScoreTitlesReleasedAfterYear(int year);

    public abstract double getAverageImdbScoreTitlesReleasedBeforeYear(int year);

    public abstract double getAverageImdbScoreTitlesBetweenYears(int startYear, int endYear);

    public abstract double getMinRuntimeRatedAgeCertification(Certification certification);

    public abstract double getMaxRuntimeRatedAgeCertification(Certification certification);

    public abstract double getAverageRuntimeRatedAgeCertification(Certification certification);

    public abstract Map<String, Set<String>> getTitlesBasedOnTrueStory();

    public abstract int getNoTitlesImdbScore(double score, Operator operator);

    public abstract double getAverageImdbScoreTitle(Type type);// -> MOVIE or SHOW

    public abstract double getMinimumImdbScoreTitle(Type type);

    public abstract double getMaximumImdbScoreTitle(Type type);

    public abstract List<String> getDocumentaries();

    public abstract List<String> getLastFiveYearsMovies();

    /**
     * Type of operators
     * @see #EQUAL
     * @see #NOT_EQUAL
     * @see #GREATER_THAN
     * @see #GREATER_THAN_EQUAL_TO
     * @see #LESS_THAN
     * @see #LESS_THAN_EQUAL_TO
     */
    public enum Operator {
        EQUAL("=="), NOT_EQUAL("!="), GREATER_THAN(">"), GREATER_THAN_EQUAL_TO(">="), LESS_THAN("<"),
        LESS_THAN_EQUAL_TO("<=");
        private final String operator;

        Operator(String operator) {
            this.operator = operator;

        }

        public String getOperator() {
            return operator;
        }

    }

    /**
     * Type of titles
     * @see #SHOW
     * @see #MOVIE
     */
    public enum Type {
        MOVIE("Movie"), SHOW("Tv Show or show");
        private final String type;

        Type(String type) {
            this.type = type;
        }

        public String getOperator() {
            return type;
        }

    }

    /**
     * Type of certification
     * @see #G
     * @see #PG
     * @see #PG_13
     * @see #R
     * @see #NC_17
     * @see #TV_MA
     * @see #TV_14
     * @see #TV_G
     * @see #TV_PG
     * @see #TV_Y
     * @see #TV_Y7
     */
    public enum Certification {
        //Movies
        G("General Audience"), PG("Parental Guidance"), PG_13("Parental guidance for children under 13"),
        R("Restricted"), NC_17("Adults only - 17 and older"),

        //Tv Shows
        TV_MA("Mature Audience"), TV_14("Unsuitable for children under 14"), TV_G("General audience"),
        TV_PG("Parental guidance suggested"), TV_Y("Suitable for all children"),
        TV_Y7("Suitable for children age 7 and above");

        private final String certification;

        Certification(String certification) {
            this.certification = certification;
        }

    }

}
