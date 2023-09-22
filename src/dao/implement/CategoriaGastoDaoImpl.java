package dao.implement;

import config.JdbcConfig;
import dao.CategoriaGastoDao;
import dto.CategoriaGastoDto;

import java.sql.*;

public class CategoriaGastoDaoImpl implements CategoriaGastoDao {

    @Override
    public int insert(CategoriaGastoDto categoriaGastoDto) {
        int categoriaId = -1; // Variable para almacenar el ID de la nueva categoría

        try (Connection conexion = JdbcConfig.getDBConnection()) {
            String insertQuery = "INSERT INTO CategoriaGasto (nombreCategoria) VALUES (?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, categoriaGastoDto.getNombreCategoria());

                int filasAfectadas = preparedStatement.executeUpdate();
                if (filasAfectadas > 0) {
                    // Obtenemos el ID generado automáticamente
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            categoriaId = generatedKeys.getInt(1);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoriaId;
    }

    @Override
    public CategoriaGastoDto findByNombre(String nombre) {
        CategoriaGastoDto categoriaGastoDto = null;

        try (Connection conexion = JdbcConfig.getDBConnection()) {
            String selectQuery = "SELECT * FROM CategoriaGasto WHERE nombreCategoria = ?";

            try (PreparedStatement preparedStatement = conexion.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, nombre);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String nombreCategoria = resultSet.getString("nombreCategoria");

                        categoriaGastoDto = new CategoriaGastoDto(id, nombreCategoria);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoriaGastoDto;
    }

    @Override
    public CategoriaGastoDto findById(int id) {
        CategoriaGastoDto categoriaGastoDto = null;

        try (Connection conexion = JdbcConfig.getDBConnection()) {
            String selectQuery = "SELECT * FROM CategoriaGasto WHERE id = ?";

            try (PreparedStatement preparedStatement = conexion.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String nombreCategoria = resultSet.getString("nombreCategoria");

                        categoriaGastoDto = new CategoriaGastoDto(id, nombreCategoria);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoriaGastoDto;
    }

}
