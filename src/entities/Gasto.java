package entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Gasto{

    private int id;
    private double monto;
    private Date fecha;
    private int categoriaId;
    public Gasto() {

    }
    public Gasto(int id, double monto, Date fecha, int categoriaId) {
        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
        this.categoriaId = categoriaId;
    }

    public Gasto(double monto, Date fecha, int categoriaId) {
        this.monto = monto;
        this.fecha = fecha;
        this.categoriaId = categoriaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormateada = sdf.format(fecha);
        return String.format("%-15.2f%-15s%-15s%n", monto, fechaFormateada, categoriaId);
    }
}
