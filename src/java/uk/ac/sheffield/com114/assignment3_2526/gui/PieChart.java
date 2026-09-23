package java.uk.ac.sheffield.com114.assignment3_2526.gui;

import java.uk.ac.sheffield.com114.assignment3_2526.Entry;
import java.uk.ac.sheffield.com114.assignment3_2526.codeprovided.AbstractEntry;
import java.uk.ac.sheffield.com114.assignment3_2526.codeprovided.AbstractEntryCatalog;
import java.uk.ac.sheffield.com114.assignment3_2526.codeprovided.gui.AbstractPieChart;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a pie chart that displays the distribution of entries by age certification.
 * Calculates slice data based on the filtered entries list and provides methods
 * to access the data for rendering.
 */
public class PieChart extends AbstractPieChart {

    private final Map<String, Integer> certificationCounts;
    private final Map<String, Color> certificationColors;
    private int totalCount;

    /** Array of colors for the pie chart slices. */
    private static final Color[] SLICE_COLORS = {
            new Color(70, 130, 180),   // Steel Blue
            new Color(60, 179, 113),   // Medium Sea Green
            new Color(255, 99, 71),    // Tomato
            new Color(255, 215, 0),    // Gold
            new Color(147, 112, 219),  // Medium Purple
            new Color(255, 182, 193),  // Light Pink
            new Color(0, 206, 209),    // Dark Turquoise
            new Color(255, 140, 0),    // Dark Orange
            new Color(123, 104, 238),  // Medium Slate Blue
            new Color(144, 238, 144),  // Light Green
            new Color(250, 128, 114),  // Salmon
            new Color(176, 196, 222)   // Light Steel Blue
    };

    /** Age certification labels in display order. */
    private static final String[] CERTIFICATION_ORDER = {
            "Not-Specified", "G", "PG", "PG-13", "R", "NC-17",
            "TV-G", "TV-PG", "TV-14", "TV-MA", "TV-Y", "TV-Y7"
    };

    /**
     * Creates a new PieChart with the specified catalog and filtered entries.
     *
     * @param catalog         The entry catalog containing all entries
     * @param filteredEntries The list of entries to display in the pie chart
     */
    public PieChart(AbstractEntryCatalog catalog, List<AbstractEntry> filteredEntries) {
        super(catalog, filteredEntries);
        this.certificationCounts = new LinkedHashMap<>();
        this.certificationColors = new LinkedHashMap<>();
        calculateData();
    }

    /**
     * Calculates the count of entries for each age certification category.
     */
    private void calculateData() {
        certificationCounts.clear();
        certificationColors.clear();
        totalCount = 0;

        // Initialize all certifications with zero count
        for (String cert : CERTIFICATION_ORDER) {
            certificationCounts.put(cert, 0);
        }

        // Count entries by certification
        for (AbstractEntry abstractEntry : filteredEntriesList) {
            Entry entry = (Entry) abstractEntry;
            String cert = entry.getAgeCertification();

            // Handle empty/missing certification
            if (cert == null || cert.isEmpty()) {
                cert = "Not-Specified";
            }

            certificationCounts.merge(cert, 1, Integer::sum);
            totalCount++;
        }

        // Assign colors to certifications that have entries
        int colorIndex = 0;
        for (String cert : CERTIFICATION_ORDER) {
            if (certificationCounts.getOrDefault(cert, 0) > 0) {
                certificationColors.put(cert, SLICE_COLORS[colorIndex % SLICE_COLORS.length]);
                colorIndex++;
            }
        }
    }

    /**
     * Updates the filtered entries list and recalculates the pie chart data.
     *
     * @param newFilteredEntries The new list of filtered entries
     */
    public void updateFilteredEntries(List<AbstractEntry> newFilteredEntries) {
        this.filteredEntriesList = newFilteredEntries;
        calculateData();
    }

    /**
     * Gets the count of entries for each certification category.
     *
     * @return Map of certification names to their counts
     */
    public Map<String, Integer> getCertificationCounts() {
        return certificationCounts;
    }

    /**
     * Gets the color assigned to each certification category.
     *
     * @return Map of certification names to their colors
     */
    public Map<String, Color> getCertificationColors() {
        return certificationColors;
    }

    /**
     * Gets the total number of entries in the filtered list.
     *
     * @return The total count
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * Calculates the angle in degrees for a pie slice based on the count.
     *
     * @param count The count for this slice
     * @return The angle in degrees
     */
    public double getAngleForCount(int count) {
        if (totalCount == 0) {
            return 0;
        }
        return (count * 360.0) / totalCount;
    }

    /**
     * Gets the filtered entries list.
     *
     * @return The list of filtered entries
     */
    public List<AbstractEntry> getFilteredEntriesList() {
        return filteredEntriesList;
    }
}
