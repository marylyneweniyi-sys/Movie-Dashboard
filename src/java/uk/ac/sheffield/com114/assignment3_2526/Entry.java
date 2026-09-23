package java.uk.ac.sheffield.com114.assignment3_2526;

import java.uk.ac.sheffield.com114.assignment3_2526.codeprovided.AbstractEntry;

import java.util.List;

/**
 * Represents a single movie or TV show entry from the dataset.
 * Stores all properties parsed from a CSV line including title, type,
 * description, release year, age certification, runtime, and IMDB score.
 */
public class Entry extends AbstractEntry {

    private final String entryId;
    private final String title;
    private final String type;
    private final String description;
    private final int releaseYear;
    private final String ageCertification;
    private final int runtime;
    private final double imdbScore;

    /**
     * Creates a new Entry from parsed CSV data.
     *
     * @param id        The unique identifier assigned by the catalog
     * @param entryLine List of strings containing the parsed CSV fields
     */
    public Entry(int id, List<String> entryLine) {
        super(id, entryLine);
        this.entryId = entryLine.get(0);
        this.title = entryLine.get(1);
        this.type = entryLine.get(2);
        this.description = entryLine.get(3);
        this.releaseYear = Integer.parseInt(entryLine.get(4));
        this.ageCertification = entryLine.get(5);
        this.runtime = Integer.parseInt(entryLine.get(6));
        this.imdbScore = Double.parseDouble(entryLine.get(7));
    }

    /**
     * Gets the original ID from the dataset.
     *
     * @return The entry ID (e.g., "tm84618")
     */
    public String getEntryId() {
        return entryId;
    }

    /**
     * Gets the title of the movie or TV show.
     *
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the type of the entry.
     *
     * @return The type - either "MOVIE" or "SHOW"
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the description/plot summary.
     *
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the release year.
     *
     * @return The release year
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * Gets the age certification.
     *
     * @return The age certification (e.g., "R", "PG-13", "TV-MA") or empty string if not specified
     */
    public String getAgeCertification() {
        return ageCertification;
    }

    /**
     * Gets the runtime in minutes.
     *
     * @return The runtime in minutes
     */
    public int getRuntime() {
        return runtime;
    }

    /**
     * Gets the IMDB score.
     *
     * @return The IMDB score (0-10 scale)
     */
    public double getImdbScore() {
        return imdbScore;
    }
}
