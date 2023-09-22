package dao;

import dto.CategoriaGastoDto;
public interface CategoriaGastoDao {

    // CREATE //
    int insert(CategoriaGastoDto categoriaGastoDto);

    CategoriaGastoDto findByNombre(String nombre);

    CategoriaGastoDto findById(int id);

}
