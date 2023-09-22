/*package dao.implement;

import dto.CategoriaGastoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoriaGastoDaoImplTest {
    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;
    private CategoriaGastoDaoImpl categoriaGastoDao;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);
        categoriaGastoDao = new CategoriaGastoDaoImpl();
    }

    @Test
    void testInsertCategoriaGastoNotValida() throws SQLException {
        // GIVEN
        CategoriaGastoDto categoriaGastoDto = new CategoriaGastoDto();
        categoriaGastoDto.setNombreCategoria("Alimentos");

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

        // WHEN
        int categoriaId = categoriaGastoDao.insert(categoriaGastoDto);

        // THEN
        assertEquals(1, categoriaId); // Verificamos que se obtenga el ID correcto
        verify(mockPreparedStatement).setString(1, categoriaGastoDto.getNombreCategoria());
        verify(mockPreparedStatement).executeUpdate();
        verify(mockPreparedStatement).getGeneratedKeys();
    }
}*/