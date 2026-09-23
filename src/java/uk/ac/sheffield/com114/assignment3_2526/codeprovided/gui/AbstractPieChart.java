package java.uk.ac.sheffield.com114.assignment3_2526.codeprovided.gui;

// This abstract class is a placeholder class that you should use to represent your
// pie chart.
// You need to extend this class to add the pie chart functionality.
// You can not change the implementation of this class as it is part of the codeprovided package
// You will be able to change the class PieChart that extends it.

import java.uk.ac.sheffield.com114.assignment3_2526.codeprovided.AbstractEntry;
import java.uk.ac.sheffield.com114.assignment3_2526.codeprovided.AbstractEntryCatalog;

import java.util.List;

public abstract class AbstractPieChart {

    protected final AbstractEntryCatalog catalog;
    protected List<AbstractEntry> filteredEntriesList;

    // this constructor receives the catalog with the movie/tv entries
    // and a list with the entries that need to be represented by
    // the pie chart (after applying the GUI controls selections)
    public AbstractPieChart(AbstractEntryCatalog catalog, List<AbstractEntry> filteredEntriesList) {
        this.catalog = catalog;
        this.filteredEntriesList = filteredEntriesList;
    }

}
