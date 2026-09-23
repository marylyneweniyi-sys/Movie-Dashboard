package java.uk.ac.sheffield.com114.assignment3_2526.codeprovided.gui;

import java.uk.ac.sheffield.com114.assignment3_2526.codeprovided.AbstractEntry;
import java.uk.ac.sheffield.com114.assignment3_2526.codeprovided.AbstractEntryCatalog;

import javax.swing.*;
import java.util.List;

// This abstract class provides a basic implementation of the main elements in the GUI as discussed in the handout.
// You need to implement the abstract methods in this class by overriding them.
// You can not change the implementation of this class as it is part of a codeprovided package

public abstract class AbstractMoviesDashboardPanel extends JPanel {

    protected final AbstractEntryCatalog entryCatalog;
    protected List<AbstractEntry> filteredEntriesList;

    public AbstractMoviesDashboardPanel(AbstractEntryCatalog entryCatalog) {
        this.entryCatalog = entryCatalog;
        this.filteredEntriesList = entryCatalog.getEntriesList();
    }

    protected abstract void setupControlsPanel();
    protected abstract void setupRangeSliders();
    protected abstract void setupComboBoxes();
    protected abstract void setupPieChartPanel();
    protected abstract void setupStatsPanel();
    protected abstract void setupListeners();

}
