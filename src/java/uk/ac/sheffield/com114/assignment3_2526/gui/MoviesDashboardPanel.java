package java.uk.ac.sheffield.com114.assignment3_2526.gui;

import java.uk.ac.sheffield.com114.assignment3_2526.Entry;
import java.uk.ac.sheffield.com114.assignment3_2526.EntryCatalog;
import java.uk.ac.sheffield.com114.assignment3_2526.codeprovided.AbstractEntry;
import java.uk.ac.sheffield.com114.assignment3_2526.codeprovided.gui.AbstractMoviesDashboardPanel;
import java.uk.ac.sheffield.com114.assignment3_2526.codeprovided.gui.RangeSlider;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The main dashboard panel containing all GUI components for the Movies Dashboard.
 * Includes filter controls (sliders and combo boxes), a pie chart visualization,
 * and a statistics table. All components update dynamically based on filter selections.
 */
public class MoviesDashboardPanel extends AbstractMoviesDashboardPanel {

    private RangeSlider imdbScoreSlider;
    private RangeSlider runtimeSlider;
    private RangeSlider releaseYearSlider;

    private JComboBox<String> comboBoxMovieTitles;
    private JComboBox<String> comboBoxType;
    private JComboBox<String> comboBoxAgeCertification;

    private JTable statsTable;
    private DefaultTableModel statsTableModel;

    private PieChart pieChart;
    private PieChartPanel pieChartPanel;

    /**
     * Creates a new MoviesDashboardPanel with the specified entry catalog.
     *
     * @param entryCatalog The catalog containing all movie/TV show entries
     */
    public MoviesDashboardPanel(EntryCatalog entryCatalog) {
        super(entryCatalog);
        this.setLayout(new BorderLayout());

        setupRangeSliders();
        setupComboBoxes();
        setupControlsPanel();
        setupPieChartPanel();
        setupStatsPanel();
        setupListeners();

        // Initial update
        updateFilteredData();
    }

    /**
     * Sets up the range sliders for IMDB score, runtime, and release year filtering.
     */
    @Override
    protected void setupRangeSliders() {
        imdbScoreSlider = new RangeSlider(0.0, 10.0, 0.0, 10.0);
        imdbScoreSlider.setPreferredSize(new Dimension(150, 50));

        runtimeSlider = new RangeSlider(0, 300, 0, 300);
        runtimeSlider.setPreferredSize(new Dimension(150, 50));

        releaseYearSlider = new RangeSlider(1950, 2025, 1950, 2025);
        releaseYearSlider.setPreferredSize(new Dimension(150, 50));
    }

    /**
     * Sets up the combo boxes for title, type, and age certification filtering.
     * Populates them with actual data from the catalog.
     */
    @Override
    protected void setupComboBoxes() {
        // Title combo box
        comboBoxMovieTitles = new JComboBox<>();
        comboBoxMovieTitles.addItem("");
        Set<String> titles = new HashSet<>();
        for (AbstractEntry entry : entryCatalog.getEntriesList()) {
            titles.add(((Entry) entry).getTitle());
        }
        for (String title : titles) {
            comboBoxMovieTitles.addItem(title);
        }
        comboBoxMovieTitles.setPreferredSize(new Dimension(200, 25));

        // Type combo box
        comboBoxType = new JComboBox<>();
        comboBoxType.addItem("");
        comboBoxType.addItem("MOVIE");
        comboBoxType.addItem("SHOW");
        comboBoxType.setPreferredSize(new Dimension(100, 25));

        // Age certification combo box
        comboBoxAgeCertification = new JComboBox<>();
        comboBoxAgeCertification.addItem("");
        String[] certifications = {"G", "PG", "PG-13", "R", "NC-17",
                "TV-G", "TV-PG", "TV-14", "TV-MA", "TV-Y", "TV-Y7"};
        for (String cert : certifications) {
            comboBoxAgeCertification.addItem(cert);
        }
        comboBoxAgeCertification.setPreferredSize(new Dimension(100, 25));
    }

    /**
     * Sets up the controls panel at the top of the dashboard containing
     * all sliders and combo boxes for filtering.
     */
    @Override
    protected void setupControlsPanel() {
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        controlsPanel.add(createLabeledSlider("IMDB Score", imdbScoreSlider));
        controlsPanel.add(createLabeledSlider("Runtime (min)", runtimeSlider));
        controlsPanel.add(createLabeledSlider("Release Year", releaseYearSlider));

        controlsPanel.add(new JLabel("Title:"));
        controlsPanel.add(comboBoxMovieTitles);

        controlsPanel.add(new JLabel("Type:"));
        controlsPanel.add(comboBoxType);

        controlsPanel.add(new JLabel("Age Cert:"));
        controlsPanel.add(comboBoxAgeCertification);

        this.add(controlsPanel, BorderLayout.NORTH);
    }

    /**
     * Sets up the pie chart panel in the center of the dashboard.
     */
    @Override
    protected void setupPieChartPanel() {
        filteredEntriesList = entryCatalog.getEntriesList();
        pieChart = new PieChart(entryCatalog, filteredEntriesList);
        pieChartPanel = new PieChartPanel(pieChart);
        pieChartPanel.setPreferredSize(new Dimension(600, 500));
        this.add(pieChartPanel, BorderLayout.CENTER);
    }

    /**
     * Sets up the statistics panel on the right side of the dashboard
     * showing min, max, and mean values for numeric attributes.
     */
    @Override
    protected void setupStatsPanel() {
        String[] columnNames = {"Statistic", "Release Year", "IMDB Score", "Runtime (min)"};
        Object[][] data = {
                {"Total Entries", "", "", ""},
                {"Minimum", "", "", ""},
                {"Maximum", "", "", ""},
                {"Mean", "", "", ""}
        };

        statsTableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        statsTable = new JTable(statsTableModel);
        statsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        statsTable.setRowHeight(25);
        statsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane statisticsScrollPane = new JScrollPane(statsTable);
        statisticsScrollPane.setPreferredSize(new Dimension(450, 150));

        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
        statsPanel.add(statisticsScrollPane, BorderLayout.CENTER);

        this.add(statsPanel, BorderLayout.EAST);
    }

    /**
     * Sets up event listeners for all filter controls.
     * When any filter changes, the filtered data is updated and the display is refreshed.
     */
    @Override
    protected void setupListeners() {
        // Slider change listener
        ChangeListener sliderListener = e -> updateFilteredData();

        imdbScoreSlider.addChangeListener(sliderListener);
        runtimeSlider.addChangeListener(sliderListener);
        releaseYearSlider.addChangeListener(sliderListener);

        // Combo box action listener
        ActionListener comboListener = e -> updateFilteredData();

        comboBoxMovieTitles.addActionListener(comboListener);
        comboBoxType.addActionListener(comboListener);
        comboBoxAgeCertification.addActionListener(comboListener);
    }

    /**
     * Updates the filtered entries list based on current filter selections,
     * then updates the pie chart and statistics table.
     */
    private void updateFilteredData() {
        List<AbstractEntry> newFilteredList = new ArrayList<>();

        double minImdb = imdbScoreSlider.getLowerValue();
        double maxImdb = imdbScoreSlider.getUpperValue();
        double minRuntime = runtimeSlider.getLowerValue();
        double maxRuntime = runtimeSlider.getUpperValue();
        double minYear = releaseYearSlider.getLowerValue();
        double maxYear = releaseYearSlider.getUpperValue();

        String selectedTitle = (String) comboBoxMovieTitles.getSelectedItem();
        String selectedType = (String) comboBoxType.getSelectedItem();
        String selectedCert = (String) comboBoxAgeCertification.getSelectedItem();

        for (AbstractEntry abstractEntry : entryCatalog.getEntriesList()) {
            Entry entry = (Entry) abstractEntry;

            // Apply filters
            if (entry.getImdbScore() < minImdb || entry.getImdbScore() > maxImdb) {
                continue;
            }
            if (entry.getRuntime() < minRuntime || entry.getRuntime() > maxRuntime) {
                continue;
            }
            if (entry.getReleaseYear() < minYear || entry.getReleaseYear() > maxYear) {
                continue;
            }
            if (selectedTitle != null && !selectedTitle.isEmpty()
                    && !entry.getTitle().equals(selectedTitle)) {
                continue;
            }
            if (selectedType != null && !selectedType.isEmpty()
                    && !entry.getType().equals(selectedType)) {
                continue;
            }
            if (selectedCert != null && !selectedCert.isEmpty()
                    && !entry.getAgeCertification().equals(selectedCert)) {
                continue;
            }

            newFilteredList.add(entry);
        }

        filteredEntriesList = newFilteredList;

        // Update pie chart
        pieChart.updateFilteredEntries(filteredEntriesList);
        pieChartPanel.repaint();

        // Update statistics
        updateStatistics();
    }

    /**
     * Updates the statistics table with calculated values from the filtered entries.
     */
    private void updateStatistics() {
        if (filteredEntriesList.isEmpty()) {
            statsTableModel.setValueAt(0, 0, 1);
            statsTableModel.setValueAt("-", 1, 1);
            statsTableModel.setValueAt("-", 2, 1);
            statsTableModel.setValueAt("-", 3, 1);
            statsTableModel.setValueAt("-", 1, 2);
            statsTableModel.setValueAt("-", 2, 2);
            statsTableModel.setValueAt("-", 3, 2);
            statsTableModel.setValueAt("-", 1, 3);
            statsTableModel.setValueAt("-", 2, 3);
            statsTableModel.setValueAt("-", 3, 3);
            return;
        }

        int minYear = Integer.MAX_VALUE;
        int maxYear = Integer.MIN_VALUE;
        double sumYear = 0;

        double minImdb = Double.MAX_VALUE;
        double maxImdb = Double.MIN_VALUE;
        double sumImdb = 0;

        int minRuntime = Integer.MAX_VALUE;
        int maxRuntime = Integer.MIN_VALUE;
        double sumRuntime = 0;

        for (AbstractEntry abstractEntry : filteredEntriesList) {
            Entry entry = (Entry) abstractEntry;

            // Year stats
            int year = entry.getReleaseYear();
            if (year < minYear) minYear = year;
            if (year > maxYear) maxYear = year;
            sumYear += year;

            // IMDB stats
            double imdb = entry.getImdbScore();
            if (imdb < minImdb) minImdb = imdb;
            if (imdb > maxImdb) maxImdb = imdb;
            sumImdb += imdb;

            // Runtime stats
            int runtime = entry.getRuntime();
            if (runtime < minRuntime) minRuntime = runtime;
            if (runtime > maxRuntime) maxRuntime = runtime;
            sumRuntime += runtime;
        }

        int count = filteredEntriesList.size();

        // Update table
        statsTableModel.setValueAt(count, 0, 1);
        statsTableModel.setValueAt(minYear, 1, 1);
        statsTableModel.setValueAt(maxYear, 2, 1);
        statsTableModel.setValueAt(String.format("%.2f", sumYear / count), 3, 1);

        statsTableModel.setValueAt(String.format("%.2f", minImdb), 1, 2);
        statsTableModel.setValueAt(String.format("%.2f", maxImdb), 2, 2);
        statsTableModel.setValueAt(String.format("%.2f", sumImdb / count), 3, 2);

        statsTableModel.setValueAt(minRuntime, 1, 3);
        statsTableModel.setValueAt(maxRuntime, 2, 3);
        statsTableModel.setValueAt(String.format("%.2f", sumRuntime / count), 3, 3);
    }

    /**
     * Creates a panel containing a labeled slider.
     *
     * @param label  The label text to display above the slider
     * @param slider The range slider to include in the panel
     * @return A panel containing the label and slider
     */
    private JPanel createLabeledSlider(String label, RangeSlider slider) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(label), BorderLayout.NORTH);
        panel.add(slider, BorderLayout.CENTER);
        return panel;
    }
}
