package java.uk.ac.sheffield.com114.assignment3_2526.gui;

import java.uk.ac.sheffield.com114.assignment3_2526.codeprovided.gui.AbstractPieChart;
import java.uk.ac.sheffield.com114.assignment3_2526.codeprovided.gui.AbstractPieChartPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Map;

/**
 * A panel that renders a pie chart showing the distribution of entries by age certification.
 * Uses Java2D for drawing the pie slices and legend.
 */
public class PieChartPanel extends AbstractPieChartPanel {

    private static final int LEGEND_SQUARE_SIZE = 15;
    private static final int LEGEND_PADDING = 5;
    private static final int LEGEND_LINE_HEIGHT = 20;
    private static final int PIE_PADDING = 50;

    /**
     * Creates a new PieChartPanel with the specified pie chart data.
     *
     * @param pieChart The pie chart data to render
     */
    public PieChartPanel(AbstractPieChart pieChart) {
        super(pieChart);
        setBackground(Color.WHITE);
    }

    /**
     * Paints the pie chart including slices, legend, and total count label.
     *
     * @param g The graphics context
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Enable anti-aliasing for smoother graphics
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        PieChart pie = (PieChart) getPieChart();

        int width = getWidth();
        int height = getHeight();

        // Calculate pie chart dimensions
        int legendWidth = 180;
        int availableWidth = width - legendWidth - PIE_PADDING * 2;
        int availableHeight = height - PIE_PADDING * 2;
        int diameter = Math.min(availableWidth, availableHeight);

        // Center the pie chart in the available space
        int pieX = PIE_PADDING + (availableWidth - diameter) / 2;
        int pieY = PIE_PADDING + (availableHeight - diameter) / 2;

        // Draw pie slices
        drawPieSlices(g2, pie, pieX, pieY, diameter);

        // Draw total count in the center
        drawTotalCount(g2, pie, pieX, pieY, diameter);

        // Draw legend on the right side
        drawLegend(g2, pie, width - legendWidth - 10, PIE_PADDING);
    }

    /**
     * Draws the pie slices based on certification counts.
     *
     * @param g2       The graphics context
     * @param pie      The pie chart data
     * @param x        The x coordinate of the pie
     * @param y        The y coordinate of the pie
     * @param diameter The diameter of the pie
     */
    private void drawPieSlices(Graphics2D g2, PieChart pie, int x, int y, int diameter) {
        Map<String, Integer> counts = pie.getCertificationCounts();
        Map<String, Color> colors = pie.getCertificationColors();

        if (pie.getTotalCount() == 0) {
            // Draw empty circle if no data
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawOval(x, y, diameter, diameter);
            g2.setFont(new Font("Arial", Font.PLAIN, 14));
            g2.setColor(Color.GRAY);
            g2.drawString("No data", x + diameter / 2 - 25, y + diameter / 2);
            return;
        }

        int startAngle = 0;

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            String certification = entry.getKey();
            int count = entry.getValue();

            if (count > 0) {
                int arcAngle = (int) Math.round(pie.getAngleForCount(count));

                // Ensure we don't exceed 360 degrees due to rounding
                if (startAngle + arcAngle > 360) {
                    arcAngle = 360 - startAngle;
                }

                Color color = colors.get(certification);
                if (color != null) {
                    g2.setColor(color);
                    g2.fillArc(x, y, diameter, diameter, startAngle, arcAngle);

                    // Draw border
                    g2.setColor(Color.WHITE);
                    g2.drawArc(x, y, diameter, diameter, startAngle, arcAngle);
                }

                startAngle += arcAngle;
            }
        }

        // Draw outer border
        g2.setColor(Color.DARK_GRAY);
        g2.drawOval(x, y, diameter, diameter);
    }

    /**
     * Draws the total count label in the center of the pie chart.
     *
     * @param g2       The graphics context
     * @param pie      The pie chart data
     * @param x        The x coordinate of the pie
     * @param y        The y coordinate of the pie
     * @param diameter The diameter of the pie
     */
    private void drawTotalCount(Graphics2D g2, PieChart pie, int x, int y, int diameter) {
        int centerX = x + diameter / 2;
        int centerY = y + diameter / 2;

        // Draw semi-transparent background for text
        g2.setColor(new Color(255, 255, 255, 200));
        g2.fillOval(centerX - 45, centerY - 20, 90, 40);

        // Draw total count text
        g2.setColor(Color.DARK_GRAY);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        String totalText = "Total: " + pie.getTotalCount();
        int textWidth = g2.getFontMetrics().stringWidth(totalText);
        g2.drawString(totalText, centerX - textWidth / 2, centerY + 5);
    }

    /**
     * Draws the legend showing certification colors and counts.
     *
     * @param g2       The graphics context
     * @param pie      The pie chart data
     * @param x        The x coordinate for the legend
     * @param y        The y coordinate for the legend
     */
    private void drawLegend(Graphics2D g2, PieChart pie, int x, int y) {
        Map<String, Integer> counts = pie.getCertificationCounts();
        Map<String, Color> colors = pie.getCertificationColors();

        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        int currentY = y;

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            String certification = entry.getKey();
            int count = entry.getValue();

            if (count > 0) {
                Color color = colors.get(certification);
                if (color != null) {
                    // Draw color square
                    g2.setColor(color);
                    g2.fillRect(x, currentY, LEGEND_SQUARE_SIZE, LEGEND_SQUARE_SIZE);
                    g2.setColor(Color.DARK_GRAY);
                    g2.drawRect(x, currentY, LEGEND_SQUARE_SIZE, LEGEND_SQUARE_SIZE);

                    // Draw label with count
                    g2.setColor(Color.BLACK);
                    String label = certification + " (" + count + ")";
                    g2.drawString(label, x + LEGEND_SQUARE_SIZE + LEGEND_PADDING, currentY + LEGEND_SQUARE_SIZE - 2);

                    currentY += LEGEND_LINE_HEIGHT;
                }
            }
        }
    }
}
