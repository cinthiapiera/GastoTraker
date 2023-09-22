package dao;

import dto.GastoDto;
import java.util.List;

public interface GastoDao {

    //CREATE
    void insert(GastoDto gastoDto);

    //READ
    List<GastoDto> getAll();

    //UPDATE
    void updateGasto(GastoDto gastoDto);

    GastoDto buscarGastoPorId(int id);

    // DELETE
    void deleteGasto(int gastoId);
}
