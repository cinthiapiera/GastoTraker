package dto;

public class CategoriaGastoDto {
    private int id;
    private String nombreCategoria;
    public CategoriaGastoDto() { }
    public CategoriaGastoDto(int id, String nombreCategoria) {
        this.id = id;
        this.nombreCategoria = nombreCategoria;
    }
    public CategoriaGastoDto(String nombreCategoria) {
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
}
