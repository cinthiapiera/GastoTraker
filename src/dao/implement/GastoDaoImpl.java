package dao.implement;

import config.JdbcConfig;
import dao.GastoDao;
import dto.GastoDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GastoDaoImpl implements GastoDao {

    @Override
    public void insert(GastoDto gastoDto) {
        try (Connection conexion = JdbcConfig.getDBConnection()) {
            String insertQuery = "INSERT INTO Gasto (monto, fecha, categoria_id) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertQuery)) {
                preparedStatement.setDouble(1, gastoDto.getMonto());
                preparedStatement.setDate(2, new java.sql.Date(gastoDto.getFecha().getTime()));
                preparedStatement.setInt(3, gastoDto.getCategoriaId()); // Cambio aquí

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<GastoDto> getAll() {
        List<GastoDto> gastos = new ArrayList<>();

        try (Connection conexion = JdbcConfig.getDBConnection()) {
            String selectQuery = "SELECT * FROM Gasto";

            try (PreparedStatement preparedStatement = conexion.prepareStatement(selectQuery)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("ID"); // Obtener el valor de ID
                        double monto = resultSet.getDouble("monto");
                        Date fecha = resultSet.getDate("fecha");
                        int categoriaId = resultSet.getInt("categoria_id");

                        GastoDto gastoDto = new GastoDto(id, monto, fecha, categoriaId);
                        gastos.add(gastoDto);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gastos;
    }

    @Override
    public void updateGasto(GastoDto gastoDto) {
        try (Connection conexion = JdbcConfig.getDBConnection()) {
            String updateQuery = "UPDATE Gasto SET monto = ?, fecha = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(updateQuery)) {
                preparedStatement.setDouble(1, gastoDto.getMonto());
                preparedStatement.setDate(2, new java.sql.Date(gastoDto.getFecha().getTime()));
                preparedStatement.setInt(3, gastoDto.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GastoDto buscarGastoPorId(int id) {
        GastoDto gastoDto = null;

        try (Connection conexion = JdbcConfig.getDBConnection()) {
            String selectQuery = "SELECT * FROM Gasto WHERE id = ?";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        double monto = resultSet.getDouble("monto");
                        Date fecha = resultSet.getDate("fecha");
                        int categoriaId = resultSet.getInt("categoria_id");

                        gastoDto = new GastoDto(monto, fecha, categoriaId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gastoDto;
    }

    @Override
    public void deleteGasto(int gastoId) {
        try (Connection conexion = JdbcConfig.getDBConnection()) {
            String deleteQuery = "DELETE FROM Gasto WHERE ID = ?";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, gastoId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
