package sample;

public class BarChart extends Chart{

    public BarChart(String title, String xAxisLabel) {
        super(title, xAxisLabel);

        getxAxis().setLabel(xAxisLabel);
        getyAxis().setLabel("y-axis");
    }

}