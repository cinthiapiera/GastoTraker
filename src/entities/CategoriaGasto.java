package entities;

public class CategoriaGasto {
    private int id;
    private String nombreCategoria;
    public CategoriaGasto() {
    }

    public CategoriaGasto(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public CategoriaGasto(int id, String nombreCategoria) {
        this.id = id;
        this.nombreCategoria = nombreCategoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
    @Override
    public String toString() {
        return "ExpenseCategory{" +
                "id=" + id +
                ", name='" + nombreCategoria + '\'' +
                '}';
    }
}
