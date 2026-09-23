package java.uk.ac.sheffield.com114.assignment3_2526.codeprovided.gui;

import javax.swing.*;

// This abstract class should be used to display a pie chart.
// You need to extend this class to add the pie chart drawing functionality.
// You can not change the implementation of this class as it is part of the codeprovided package
// You will be able to change the class PieChartPanel that extends it.

public abstract class AbstractPieChartPanel extends JPanel {
    private final AbstractPieChart pieChart;

    public AbstractPieChartPanel(AbstractPieChart pieChart) {
        super();
        this.pieChart = pieChart;
    }

    public AbstractPieChart getPieChart() {
        return pieChart;

    }
}

