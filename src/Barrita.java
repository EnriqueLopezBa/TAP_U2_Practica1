

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Random;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarPainter;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.GradientPaintTransformer;
import org.jfree.ui.StandardGradientPaintTransformer;

/**
 *
 * @author Enrique
 */
public class Barrita {

    public Dimension tamV = new Dimension(500, 270);
    public ValuesBar val = new ValuesBar();

    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    public Barrita() {
    }

    public Barrita(Dimension tam) {
        this.tamV = tam;
    }

    public void insertarValoresX() {

        if (val.getValoresX().length > val.getValoresY().length) {
            for (int i = 0; i < val.getValoresY().length; i++) {
                if (val.isColoresAleatorios()) {
                    dataset.setValue(Integer.parseInt(val.getValoresY()[i]), "Grafica", val.getValoresX()[i]);
                } else {
                    dataset.setValue(Integer.parseInt(val.getValoresY()[i]), val.getValoresX()[i], "Grafica");
                }
            }
        } else if (val.getValoresX().length < val.getValoresY().length) {
            for (int i = 0; i < val.getValoresX().length; i++) {
                if (val.isColoresAleatorios()) {
                  dataset.setValue(Integer.parseInt(val.getValoresY()[i]), "Grafica", val.getValoresX()[i]);
                } else {
                    dataset.setValue(Integer.parseInt(val.getValoresY()[i]), val.getValoresX()[i], "Grafica");
                }
            }
        } else {
            for (int i = 0; i < val.getValoresX().length; i++) {
                if (val.isColoresAleatorios()) {
                dataset.setValue(Integer.parseInt(val.getValoresY()[i]), "Grafica", val.getValoresX()[i]);
                } else {
                    dataset.setValue(Integer.parseInt(val.getValoresY()[i]), val.getValoresX()[i], "Grafica");
                }
            }
        }
    }

    private Color randomColor() {
        Random random = new Random();
        int R = random.nextInt(256);
        int G = random.nextInt(256);
        int B = random.nextInt(256);
        return new Color(R, B, G);

    }

    private Paint[] createPaint() {
        Paint[] arrayOfPaint = new Paint[val.getValoresX().length];
        for (int i = 0; i < val.getValoresX().length; i++) {
            arrayOfPaint[i] = new GradientPaint(0.0F, 0.0F, randomColor(), 0.0F, 0.0F, Color.white);
        }
        return arrayOfPaint;
    }

    static class CustomBarRenderer extends BarRenderer {

        private Paint[] colors;

        public CustomBarRenderer(Paint[] param1ArrayOfPaint) {
            this.colors = param1ArrayOfPaint;
        }

        @Override
        public Paint getItemPaint(int param1Int1, int param1Int2) {
            return this.colors[param1Int2 % this.colors.length];
        }
    }

    public ChartPanel crearGrafico() {
        JFreeChart chart;
        if (val.getBarraVertical()) {    
            if(val.isColoresAleatorios())
                chart = ChartFactory.createBarChart(val.getTitulo(), val.getTituloAbajo(), val.getTituloCostado(), dataset, PlotOrientation.VERTICAL, false, true, false); 
            else
                chart = ChartFactory.createBarChart(val.getTitulo(), val.getTituloAbajo(), val.getTituloCostado(), dataset, PlotOrientation.VERTICAL, true, true, false);   
        } else {
            if(val.isColoresAleatorios())
                chart = ChartFactory.createBarChart(val.getTitulo(), val.getTituloAbajo(), val.getTituloCostado(), dataset, PlotOrientation.VERTICAL, false, true, false); 
            else
                chart = ChartFactory.createBarChart(val.getTitulo(), val.getTituloAbajo(), val.getTituloCostado(), dataset, PlotOrientation.HORIZONTAL, true, true, false);
        }
        CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();
        categoryPlot.setNoDataMessage("NO DATA!");
        categoryPlot.setBackgroundPaint(val.getColorFondo());
        categoryPlot.setOutlinePaint(val.getColorContorno());
        categoryPlot.setRangeGridlinePaint(val.getColorCuadricula());
        categoryPlot.setRangeGridlineStroke(new BasicStroke(val.getTrazadoCuadricula()));
//        ((BarRenderer) categoryPlot.getRenderer()).setBarPainter(new StandardBarPainter());
        if (val.getListaColores().length >= val.getValoresX().length && !val.isColoresAleatorios()) {
            BarRenderer r = (BarRenderer) chart.getCategoryPlot().getRenderer();
            for (int i = 0; i < val.getValoresX().length; i++) {
                Color color = null;
                try {
                    if (val.getListaColores()[i].matches("\\((\\d{0,3}):(\\d{0,3}):(\\d{0,3})\\)")) {
                        String arr[] = val.getListaColores()[i].replaceAll(":", ",").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(" ", "").split(",");
                        int red = Integer.parseInt(arr[0]);
                        int green = Integer.parseInt(arr[1]);
                        int blue = Integer.parseInt(arr[2]);
                        if ((red >= 0 && red <= 255) && (green >= 0 && green <= 255) && (blue >= 0 && blue <= 255)) {
                            color = new Color(red, green, blue);
                        }
                    } else {
                        Field field = Class.forName("java.awt.Color").getField(val.getListaColores()[i]);
                        color = (Color) field.get(null);
                    }
                } catch (Exception e) {
                    color = null;
                }
                r.setSeriesPaint(i, color);
            }
        }
        if (val.isColoresAleatorios()) {
            Paint[] arrayOfPaint = createPaint();
            CustomBarRenderer customBarRenderer = new CustomBarRenderer(arrayOfPaint);
            customBarRenderer.setBarPainter((BarPainter) new StandardBarPainter());
            customBarRenderer.setDrawBarOutline(true);
            customBarRenderer.setGradientPaintTransformer((GradientPaintTransformer) new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_HORIZONTAL));
            categoryPlot.setRenderer((CategoryItemRenderer) customBarRenderer);
        }
        NumberAxis numberAxis = (NumberAxis) categoryPlot.getRangeAxis();
        if (val.getMaximoY() != 0) {
            numberAxis.setRange(0, val.getMaximoY());
        }
        numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(tamV);
        panel.setBounds(0, 0, tamV.width, tamV.height);

        return panel;
    }

}
