

import java.awt.BasicStroke;
import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.awt.Graphics;

/**
 *
 * @author Enrique
 */
public class ValuesBar implements Serializable {

    private final PropertyChangeSupport supp = new PropertyChangeSupport(this);

    private String titulo = "Nombre Grafica";
    private String[] valoresX = {"Uno", "Dos"};
    private String[] valoresY = {"3", "5"};
    private String tituloAbajo = "Titulo", tituloCostado = "Titulo";
    private boolean vertical = true;
    private Color colorFondo = new Color(255, 255, 255);
    private Color colorContorno = new Color(0, 0, 0);
    private float trazadoCuadricula = 0F;
    private Color colorCuadricula = new Color(0, 0, 0);

    private int MaximoY = 0;
    private boolean coloresAleatorios = false;
    private String[] listaColores = {"red", "blue"};
    public ValuesBar() {

    }

   

    public String[] getListaColores() {
        return listaColores;
    }

    public void setListaColores(String[] listaColores) {
        this.listaColores = listaColores;
    }

    public boolean isColoresAleatorios() {
        return coloresAleatorios;
    }

    public void setColoresAleatorios(boolean coloresAleatorios) {
        this.coloresAleatorios = coloresAleatorios;
    }

    public int getMaximoY() {
        return MaximoY;
    }

    public void setMaximoY(int MaximoY) {
        this.MaximoY = MaximoY;
    }



    public float getTrazadoCuadricula() {
        return trazadoCuadricula;
    }

    public void setTrazadoCuadricula(float trazadoCuadricula) {
        this.trazadoCuadricula = trazadoCuadricula;
    }

    public Color getColorCuadricula() {
        return colorCuadricula;
    }

    public void setColorCuadricula(Color colorCuadricula) {
        this.colorCuadricula = colorCuadricula;
    }

    public Color getColorContorno() {
        return colorContorno;
    }

    public void setColorContorno(Color colorContorno) {
        this.colorContorno = colorContorno;
    }

    public Color getColorFondo() {
        return colorFondo;
    }

    public void setColorFondo(Color colorFondo) {
        this.colorFondo = colorFondo;
    }

    public void setBarraVertical(boolean t) {
        boolean old = this.vertical;
        this.vertical = t;
        if (old != t) {
            supp.firePropertyChange("barraVertical", old, t);
        }
    }

    public boolean getBarraVertical() {
        return this.vertical;
    }

    public void setTituloAbajo(String titulo) {
        String old = this.tituloAbajo;
        this.tituloAbajo = titulo;
        if (old != titulo) {
            supp.firePropertyChange("tituloAbajo", old, titulo);
        }
    }

    public String getTituloAbajo() {
        return this.tituloAbajo;
    }

    public void setTituloCostado(String titulo) {
        String old = this.tituloCostado;
        this.tituloCostado = titulo;
        if (old != titulo) {
            supp.firePropertyChange("tituloCostado", old, titulo);
        }
    }

    public String getTituloCostado() {
        return this.tituloCostado;
    }

    public void setValoresY(String valoresY[]) {
        for(String var : valoresY)
            if(!var.matches("[\\d]*"))
                throw new IllegalArgumentException("Solo se permiten numeros");
        String[] old = this.valoresY;
        this.valoresY = valoresY;

        supp.firePropertyChange("valoresY", old, valoresY);
    }

    public String[] getValoresY() {
        return this.valoresY;
    }

    public void setValoresX(String[] valor) {
        String[] old = this.valoresX;
        this.valoresX = valor;

        supp.firePropertyChange("valoresX", old, valor);

    }

    public String[] getValoresX() {
        return this.valoresX;
    }

    public void setTitulo(String titulo) {
        String old = this.titulo;
        this.titulo = titulo;

        if (titulo != old) {
            supp.firePropertyChange("titulo", old, titulo);
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void removePropertyChangeListener(PropertyChangeListener pl) {
        supp.removePropertyChangeListener(pl);
    }

    public void addPropertyChangeListener(PropertyChangeListener pl) {
        supp.addPropertyChangeListener(pl);
    }

}
