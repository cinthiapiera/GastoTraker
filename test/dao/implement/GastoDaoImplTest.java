
/*import static org.mockito.Mockito.*;

import dao.GastoDao;
import dao.MockGastoDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GastoDaoImplTest {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;

    private GastoDao gastoDao;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        gastoDao = new MockGastoDao();
    }

    @Test
    void deleteGastoExitoso() throws SQLException {
        int gastoId= 2;
        when(mockPreparedStatement.executeUpdate()).thenReturn(2);
        gastoDao.deleteGasto(gastoId);
        verify(mockPreparedStatement).setInt(2,gastoId);
        verify(mockPreparedStatement).executeUpdate();
    }

}*/
