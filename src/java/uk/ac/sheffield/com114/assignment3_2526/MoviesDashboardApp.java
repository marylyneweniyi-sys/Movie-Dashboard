package java.uk.ac.sheffield.com114.assignment3_2526;

import uk.ac.sheffield.com114.assignment3_2526.codeprovided.gui.AbstractMoviesDashboardPanel;
import uk.ac.sheffield.com114.assignment3_2526.gui.MoviesDashboard;
import uk.ac.sheffield.com114.assignment3_2526.gui.MoviesDashboardPanel;

import java.io.IOException;

/**
 * The main application class for the Movies Dashboard.
 * This class is responsible for loading the dataset and launching the GUI.
 * It serves as the entry point for the application.
 */
public class MoviesDashboardApp {

    /** The catalog containing all movie and TV show entries. */
    private final EntryCatalog entryCatalog;

    /**
     * Creates a new MoviesDashboardApp by loading entries from the specified CSV file.
     * If the file cannot be loaded, the application will exit with an error.
     *
     * @param entriesFileName The path to the CSV file containing movie/TV show data
     */
    public MoviesDashboardApp(String entriesFileName) {
        EntryCatalog abstractEntryCatalog = null;
        try {
            abstractEntryCatalog = new EntryCatalog(entriesFileName);
        } catch (IllegalArgumentException | IOException e) {
            System.err.println(e);
            System.exit(-1);
        }
        this.entryCatalog = abstractEntryCatalog;
    }

    /**
     * The main entry point for the application.
     * If no command line argument is provided, defaults to "./resources/titles.csv".
     *
     * @param args Command line arguments; first argument is the path to the CSV file
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            args = new String[]{
                    "./resources/titles.csv"
            };
        }
        MoviesDashboardApp moviesDashboardApp = new MoviesDashboardApp(args[0]);
        moviesDashboardApp.startGUI();
    }

    /**
     * Starts the graphical user interface.
     * Creates the dashboard panel and main window, then makes the window visible.
     */
    public void startGUI() {
        AbstractMoviesDashboardPanel moviesDashboardPanel = new MoviesDashboardPanel(entryCatalog);
        MoviesDashboard eDashboard = new MoviesDashboard(moviesDashboardPanel);
        eDashboard.setVisible(true);
    }
}
