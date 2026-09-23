package java.uk.ac.sheffield.com114.assignment3_2526;

import uk.ac.sheffield.com114.assignment3_2526.codeprovided.AbstractEntry;
import uk.ac.sheffield.com114.assignment3_2526.codeprovided.AbstractEntryCatalog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a catalog containing all movie and TV show entries from the dataset.
 * Provides methods to query and analyze the entries including statistics on
 * IMDB scores, runtimes, and filtering by various criteria.
 */
public class EntryCatalog extends AbstractEntryCatalog {

    private static final int EXPECTED_COLUMNS = 8;

    /**
     * Creates a new EntryCatalog by loading entries from the specified CSV file.
     *
     * @param entryFile Path to the CSV file containing movie/TV show data
     * @throws IllegalArgumentException If the file format is invalid
     * @throws IOException              If there's an error reading the file
     */
    public EntryCatalog(String entryFile) throws IllegalArgumentException, IOException {
        super(entryFile);
    }

    /**
     * Parses a single line from the CSV file into a list of field values.
     * Handles quoted fields that may contain commas.
     *
     * @param line The CSV line to parse
     * @return List of parsed field values
     * @throws IllegalArgumentException If the line has incorrect number of columns
     */
    @Override
    public List<String> parseEntryLine(String line) throws IllegalArgumentException {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(currentField.toString().trim());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }
        fields.add(currentField.toString().trim());

        if (fields.size() != EXPECTED_COLUMNS) {
            throw new IllegalArgumentException("Invalid number of columns. Expected "
                    + EXPECTED_COLUMNS + " but got " + fields.size());
        }

        return fields;
    }

    /**
     * Gets the total number of entries in the catalog.
     *
     * @return The number of entries
     */
    @Override
    public int getNumberOfEntries() {
        return entriesList.size();
    }

    /**
     * Gets the earliest release year in the dataset.
     *
     * @return The earliest release year
     */
    @Override
    public int getEarliestReleaseYear() {
        int earliest = Integer.MAX_VALUE;
        for (AbstractEntry abstractEntry : entriesList) {
            Entry entry = (Entry) abstractEntry;
            if (entry.getReleaseYear() < earliest) {
                earliest = entry.getReleaseYear();
            }
        }
        return earliest;
    }

    /**
     * Calculates the average IMDB score for titles released after the specified year.
     * The specified year is NOT included in the calculation.
     *
     * @param year The year threshold (exclusive)
     * @return The average IMDB score, or 0 if no matching entries
     */
    @Override
    public double getAverageImdbScoreTitlesReleasedAfterYear(int year) {
        double sum = 0;
        int count = 0;
        for (AbstractEntry abstractEntry : entriesList) {
            Entry entry = (Entry) abstractEntry;
            if (entry.getReleaseYear() > year) {
                sum += entry.getImdbScore();
                count++;
            }
        }
        return count > 0 ? sum / count : 0;
    }

    /**
     * Calculates the average IMDB score for titles released before the specified year.
     * The specified year is NOT included in the calculation.
     *
     * @param year The year threshold (exclusive)
     * @return The average IMDB score, or 0 if no matching entries
     */
    @Override
    public double getAverageImdbScoreTitlesReleasedBeforeYear(int year) {
        double sum = 0;
        int count = 0;
        for (AbstractEntry abstractEntry : entriesList) {
            Entry entry = (Entry) abstractEntry;
            if (entry.getReleaseYear() < year) {
                sum += entry.getImdbScore();
                count++;
            }
        }
        return count > 0 ? sum / count : 0;
    }

    /**
     * Calculates the average IMDB score for titles released between the specified years.
     * Both start and end years ARE included in the calculation.
     *
     * @param startYear The start year (inclusive)
     * @param endYear   The end year (inclusive)
     * @return The average IMDB score, or 0 if no matching entries
     */
    @Override
    public double getAverageImdbScoreTitlesBetweenYears(int startYear, int endYear) {
        double sum = 0;
        int count = 0;
        for (AbstractEntry abstractEntry : entriesList) {
            Entry entry = (Entry) abstractEntry;
            int releaseYear = entry.getReleaseYear();
            if (releaseYear >= startYear && releaseYear <= endYear) {
                sum += entry.getImdbScore();
                count++;
            }
        }
        return count > 0 ? sum / count : 0;
    }

    /**
     * Gets the minimum runtime for entries with the specified age certification.
     *
     * @param certification The age certification to filter by
     * @return The minimum runtime in minutes, or 0 if no matching entries
     */
    @Override
    public double getMinRuntimeRatedAgeCertification(Certification certification) {
        double minRuntime = Double.MAX_VALUE;
        boolean found = false;
        String certString = convertCertificationToString(certification);

        for (AbstractEntry abstractEntry : entriesList) {
            Entry entry = (Entry) abstractEntry;
            if (entry.getAgeCertification().equals(certString)) {
                if (entry.getRuntime() < minRuntime) {
                    minRuntime = entry.getRuntime();
                }
                found = true;
            }
        }
        return found ? minRuntime : 0;
    }

    /**
     * Gets the maximum runtime for entries with the specified age certification.
     *
     * @param certification The age certification to filter by
     * @return The maximum runtime in minutes, or 0 if no matching entries
     */
    @Override
    public double getMaxRuntimeRatedAgeCertification(Certification certification) {
        double maxRuntime = Double.MIN_VALUE;
        boolean found = false;
        String certString = convertCertificationToString(certification);

        for (AbstractEntry abstractEntry : entriesList) {
            Entry entry = (Entry) abstractEntry;
            if (entry.getAgeCertification().equals(certString)) {
                if (entry.getRuntime() > maxRuntime) {
                    maxRuntime = entry.getRuntime();
                }
                found = true;
            }
        }
        return found ? maxRuntime : 0;
    }

    /**
     * Calculates the average runtime for entries with the specified age certification.
     *
     * @param certification The age certification to filter by
     * @return The average runtime in minutes, or 0 if no matching entries
     */
    @Override
    public double getAverageRuntimeRatedAgeCertification(Certification certification) {
        double sum = 0;
        int count = 0;
        String certString = convertCertificationToString(certification);

        for (AbstractEntry abstractEntry : entriesList) {
            Entry entry = (Entry) abstractEntry;
            if (entry.getAgeCertification().equals(certString)) {
                sum += entry.getRuntime();
                count++;
            }
        }
        return count > 0 ? sum / count : 0;
    }

    /**
     * Converts a Certification enum value to its string representation as used in the CSV.
     *
     * @param certification The certification enum value
     * @return The string representation (e.g., "PG-13" for PG_13)
     */
    private String convertCertificationToString(Certification certification) {
        switch (certification) {
            case G:
                return "G";
            case PG:
                return "PG";
            case PG_13:
                return "PG-13";
            case R:
                return "R";
            case NC_17:
                return "NC-17";
            case TV_MA:
                return "TV-MA";
            case TV_14:
                return "TV-14";
            case TV_G:
                return "TV-G";
            case TV_PG:
                return "TV-PG";
            case TV_Y:
                return "TV-Y";
            case TV_Y7:
                return "TV-Y7";
            default:
                return "";
        }
    }

    /**
     * Gets all titles based on true stories, organized by type.
     * Searches for keywords: "true story", "true events", "real events" in descriptions.
     *
     * @return Map with keys "MOVIE" and "SHOW", each containing a set of matching titles
     */
    @Override
    public Map<String, Set<String>> getTitlesBasedOnTrueStory() {
        Map<String, Set<String>> result = new HashMap<>();
        result.put("MOVIE", new HashSet<>());
        result.put("SHOW", new HashSet<>());

        for (AbstractEntry abstractEntry : entriesList) {
            Entry entry = (Entry) abstractEntry;
            String descLower = entry.getDescription().toLowerCase();

            if (descLower.contains("true story")
                    || descLower.contains("true events")
                    || descLower.contains("real events")) {
                result.get(entry.getType()).add(entry.getTitle());
            }
        }
        return result;
    }

    /**
     * Counts the number of titles matching the specified IMDB score condition.
     *
     * @param score    The IMDB score threshold
     * @param operator The comparison operator to use
     * @return The count of matching titles
     */
    @Override
    public int getNoTitlesImdbScore(double score, Operator operator) {
        int count = 0;
        for (AbstractEntry abstractEntry : entriesList) {
            Entry entry = (Entry) abstractEntry;
            double entryScore = entry.getImdbScore();

            boolean matches = false;
            switch (operator) {
                case EQUAL:
                    matches = entryScore == score;
                    break;
                case NOT_EQUAL:
                    matches = entryScore != score;
                    break;
                case GREATER_THAN:
                    matches = entryScore > score;
                    break;
                case GREATER_THAN_EQUAL_TO:
                    matches = entryScore >= score;
                    break;
                case LESS_THAN:
                    matches = entryScore < score;
                    break;
                case LESS_THAN_EQUAL_TO:
                    matches = entryScore <= score;
                    break;
            }

            if (matches) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the average IMDB score for the specified type (MOVIE or SHOW).
     *
     * @param type The type to filter by
     * @return The average IMDB score, or 0 if no matching entries
     */
    @Override
    public double getAverageImdbScoreTitle(Type type) {
        double sum = 0;
        int count = 0;
        String typeString = type == Type.MOVIE ? "MOVIE" : "SHOW";

        for (AbstractEntry abstractEntry : entriesList) {
            Entry entry = (Entry) abstractEntry;
            if (entry.getType().equals(typeString)) {
                sum += entry.getImdbScore();
                count++;
            }
        }
        return count > 0 ? sum / count : 0;
    }

    /**
     * Gets the minimum IMDB score for the specified type (MOVIE or SHOW).
     *
     * @param type The type to filter by
     * @return The minimum IMDB score, or 0 if no matching entries
     */
    @Override
    public double getMinimumImdbScoreTitle(Type type) {
        double minScore = Double.MAX_VALUE;
        boolean found = false;
        String typeString = type == Type.MOVIE ? "MOVIE" : "SHOW";

        for (AbstractEntry abstractEntry : entriesList) {
            Entry entry = (Entry) abstractEntry;
            if (entry.getType().equals(typeString)) {
                if (entry.getImdbScore() < minScore) {
                    minScore = entry.getImdbScore();
                }
                found = true;
            }
        }
        return found ? minScore : 0;
    }

    /**
     * Gets the maximum IMDB score for the specified type (MOVIE or SHOW).
     *
     * @param type The type to filter by
     * @return The maximum IMDB score, or 0 if no matching entries
     */
    @Override
    public double getMaximumImdbScoreTitle(Type type) {
        double maxScore = Double.MIN_VALUE;
        boolean found = false;
        String typeString = type == Type.MOVIE ? "MOVIE" : "SHOW";

        for (AbstractEntry abstractEntry : entriesList) {
            Entry entry = (Entry) abstractEntry;
            if (entry.getType().equals(typeString)) {
                if (entry.getImdbScore() > maxScore) {
                    maxScore = entry.getImdbScore();
                }
                found = true;
            }
        }
        return found ? maxScore : 0;
    }

    /**
     * Gets all entries identified as documentaries.
     * Searches for the exact keyword "documentary" in the description.
     *
     * @return List of titles that are documentaries
     */
    @Override
    public List<String> getDocumentaries() {
        List<String> documentaries = new ArrayList<>();
        for (AbstractEntry abstractEntry : entriesList) {
            Entry entry = (Entry) abstractEntry;
            if (entry.getDescription().toLowerCase().contains("documentary")) {
                documentaries.add(entry.getTitle());
            }
        }
        return documentaries;
    }

    /**
     * Gets all movies released in the last five years (2021-2025 inclusive).
     * Uses 2025 as the reference year.
     *
     * @return List of movie titles from the last five years
     */
    @Override
    public List<String> getLastFiveYearsMovies() {
        List<String> recentMovies = new ArrayList<>();
        int referenceYear = 2025;
        int startYear = referenceYear - 4;

        for (AbstractEntry abstractEntry : entriesList) {
            Entry entry = (Entry) abstractEntry;
            if (entry.getType().equals("MOVIE")
                    && entry.getReleaseYear() >= startYear
                    && entry.getReleaseYear() <= referenceYear) {
                recentMovies.add(entry.getTitle());
            }
        }
        return recentMovies;
    }
}
