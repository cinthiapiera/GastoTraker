import dao.GastoDao;
import dao.implement.GastoDaoImpl;

public class IniciarApp {
    public static void main(String[] args) {
        GastoDao gastoDao = new GastoDaoImpl();
        MenuGastoTracker menu = new MenuGastoTracker(gastoDao);
        menu.gastoDao = new GastoDaoImpl();
        menu.iniciar();
    }
}