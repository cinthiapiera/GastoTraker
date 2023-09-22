package interfaces;

import dao.GastoDao;

public interface TotalGastos {
    double calcularTotalGastos(GastoDao gastoDao);
}