package java.uk.ac.sheffield.com114.assignment3_2526.gui;

import java.uk.ac.sheffield.com114.assignment3_2526.codeprovided.gui.AbstractMoviesDashboardPanel;

import javax.swing.JFrame;

/**
 * The main application window for the Movies Dashboard.
 * This JFrame serves as the container for the dashboard panel and manages
 * the window properties such as title, size, and close operation.
 */
public class MoviesDashboard extends JFrame {

    /**
     * Creates a new MoviesDashboard window containing the specified panel.
     * The window is maximized to fill the entire screen.
     *
     * @param panel The dashboard panel to display in the window
     */
    public MoviesDashboard(AbstractMoviesDashboardPanel panel) {
        setTitle("Movies Dashboard");
        add(panel);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
