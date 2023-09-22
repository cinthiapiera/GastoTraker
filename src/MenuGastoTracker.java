import dao.CategoriaGastoDao;
import dao.GastoDao;
import dao.implement.CategoriaGastoDaoImpl;
import dao.implement.GastoDaoImpl;
import dto.CategoriaGastoDto;
import dto.GastoDto;
import exceptions.MontoNegativoException;
import interfaces.TotalGastos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MenuGastoTracker implements TotalGastos {
    private Scanner scanner = new Scanner(System.in);
    private int opcion;
    public GastoDao gastoDao;
    public MenuGastoTracker(GastoDao gastoDao) {
        this.gastoDao = gastoDao;
    }

    /*---Método principal para iniciar y mostrar el menú de opciones---*/
    public void iniciar() {
        System.out.println("Bienvenido a GastoTracker");

        while (true) {
            mostrarMenu(); // Mostramos el menú de opciones
            switch (opcion) {
                case 1 -> {
                    agregarGasto();
                    System.out.println();
                }
                case 2 -> {
                    mostrarListaDeGastos();
                    System.out.println();
                }
                case 3 -> {
                    actualizarGasto();
                    System.out.println();
                }
                case 4 -> {
                    eliminarGasto();
                    System.out.println();
                }
                case 5 -> {
                    mostrarTotalDeGastos();
                    System.out.println();
                }
                case 0 -> salir();
                default -> System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
            }
        }
    }
    public void mostrarMenu() {
        System.out.println("Menu de servicios: ");
        System.out.println("1. Agregar gasto");
        System.out.println("2. Lista de gastos");
        System.out.println("3. Actualizar gasto");
        System.out.println("4. Eliminar gasto");
        System.out.println("5. Total de gastos");
        System.out.println("0. Salir");
        System.out.print("Ingrese la opción deseada: ");
        try{
            opcion = scanner.nextInt();
        }catch(InputMismatchException e){
            System.out.println("Error: Ingrese una opción válida (número entero).");
            opcion = -1; // Otra opción inválida para repetir el bucle
        }
        scanner.nextLine(); // Limpiar el búfer del escáner
    }
    /*-------------------------------*/


    /*---INSERT Categoria y Gastos ---*/
    public void agregarGasto() {
        boolean agregarMasGastos = true;

        while (agregarMasGastos) {
            try {
                // Solicitar la categoria, monto y la fecha al usuario
                CategoriaGastoDto categoriaGastoDto = solicitarCategoria();
                double monto = solicitarMonto();
                Date fecha = solicitarFecha();

                // Crear el objeto Gasto con el ID de la categoría obtenido
                GastoDto gastoDto = crearGasto(monto, fecha, categoriaGastoDto.getId());
                guardarGastoEnBaseDeDatos(gastoDto);

                System.out.println("Gasto agregado");
            } catch (MontoNegativoException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Por favor, ingrese un monto válido.");
                continue; // Permite que el usuario ingrese nuevamente el monto
            }
            agregarMasGastos = preguntarSiAgregarMasGastos(); // Preguntar si se desea agregar otro gasto
        }
    }
    private GastoDto crearGasto(double monto, Date fecha, int categoriaId) {
        return new GastoDto(monto, fecha, categoriaId);
    }
    private void guardarGastoEnBaseDeDatos(GastoDto gastoDto) {
        gastoDao.insert(gastoDto);
    }
    private CategoriaGastoDto solicitarCategoria() {
        CategoriaGastoDto categoria = null;
        String nombreCategoria;
        while (categoria == null) {
            System.out.print("Ingrese el nombre de la categoría del gasto: ");
            nombreCategoria = scanner.nextLine().trim();

            if (nombreCategoria.isEmpty()) {
                System.out.println("El nombre de la categoría no puede estar vacío. Por favor, ingrese un nombre válido.");
            } else {
                CategoriaGastoDao categoriaGastoDao = new CategoriaGastoDaoImpl();
                CategoriaGastoDto categoriaExistente = categoriaGastoDao.findByNombre(nombreCategoria);

                if (categoriaExistente != null) {
                    categoria = categoriaExistente;
                } else {
                    // La categoría no existe, insértela en la base de datos
                    CategoriaGastoDto nuevaCategoria = new CategoriaGastoDto(nombreCategoria);
                    int categoriaId = categoriaGastoDao.insert(nuevaCategoria);
                    nuevaCategoria.setId(categoriaId);
                    categoria = nuevaCategoria;
                }
            }
        }

        return categoria;
    }
    private double solicitarMonto() throws MontoNegativoException {
        double montoGasto;
        do {
            System.out.print("Ingrese el monto del gasto: ");
            montoGasto = scanner.nextDouble();
            scanner.nextLine();

            if (montoGasto < 0) {
                throw new MontoNegativoException("El monto del gasto no puede ser negativo.");
            }

        } while (montoGasto <= 0);
        return montoGasto;
    }
    private Date solicitarFecha() {
        System.out.print("Ingrese la fecha del gasto (dd/MM/yyyy): ");
        String stringFechaGasto = scanner.nextLine();
        try {
            return parsearFecha(stringFechaGasto);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    private Date parsearFecha(String fecha) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return sdf.parse(fecha); //parsear una fecha en formato "dd/MM/yyyy"
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    private boolean preguntarSiAgregarMasGastos() {
        while (true) {
            System.out.print("¿Desea agregar otro gasto? (Si/No): ");
            String respuesta = scanner.nextLine();
            if (respuesta.equalsIgnoreCase("Si")) {
                return true;
            } else if (respuesta.equalsIgnoreCase("No")) {
                return false;
            } else {
                System.out.println("Respuesta inválida. Por favor, ingrese 'Si' o 'No'.");
            }
        }
    }
    /*-------------------------------*/


    /*---------READ Gastos----------*/
    public void mostrarListaDeGastos() {
        List<GastoDto> gastos = gastoDao.getAll();

        if (gastos.isEmpty()) {
            System.out.println("No hay gastos registrados.");
            return;
        }

        System.out.println("Lista de Gastos:");
        System.out.println("---------------------------------------------");
        System.out.printf("%-5s%-15s%-15s%-15s%n", "ID", "Monto", "Fecha", "Categoría");
        System.out.println("---------------------------------------------");

        Map<Integer, String> nombresCategorias = obtenerNombresCategorias(gastos);

        for (GastoDto gasto : gastos) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fechaFormateada = sdf.format(gasto.getFecha());
            String nombreCategoria = nombresCategorias.get(gasto.getCategoriaId());
            System.out.printf("%-5d%-15.2f%-15s%-15s%n", gasto.getId(), gasto.getMonto(), fechaFormateada, nombreCategoria);
        }

        System.out.println("---------------------------------------------");
    }
    private Map<Integer, String> obtenerNombresCategorias(List<GastoDto> gastos) {
        Map<Integer, String> nombresCategorias = new HashMap<>();
        CategoriaGastoDao categoriaGastoDao = new CategoriaGastoDaoImpl();

        for (GastoDto gasto : gastos) {
            int categoriaId = gasto.getCategoriaId();

            if (!nombresCategorias.containsKey(categoriaId)) {
                CategoriaGastoDto categoriaDto = categoriaGastoDao.findById(categoriaId);
                nombresCategorias.put(categoriaId, categoriaDto.getNombreCategoria());
            }
        }

        return nombresCategorias;
    }
    /*-------------------------------*/


    /*---------UPDATE Gastos----------*/
    public void actualizarGasto() {
        GastoDao gastoDao = new GastoDaoImpl();
        System.out.print("Ingrese el ID del gasto que desea actualizar: ");
        int gastoId = scanner.nextInt();
        scanner.nextLine(); // Limpiar el búfer del escáner

        // Verificar si el gasto existe
        GastoDto gastoExistente = gastoDao.buscarGastoPorId(gastoId);

        if (gastoExistente == null) {
            System.out.println("El gasto con el ID proporcionado no existe.");
            return;
        }

        try {
            // Solicitar al usuario el nuevo monto
            System.out.print("Ingrese el nuevo monto del gasto: ");
            double nuevoMonto = scanner.nextDouble();
            scanner.nextLine(); // Limpiar el búfer del escáner

            // Solicitar al usuario la nueva fecha
            System.out.print("Ingrese la nueva fecha del gasto (dd/MM/yyyy): ");
            String stringNuevaFecha = scanner.nextLine();
            Date nuevaFecha = parsearFecha(stringNuevaFecha);

            // Crear un nuevo GastoDto con los nuevos valores
            GastoDto nuevoGasto = new GastoDto();
            nuevoGasto.setId(gastoId);
            nuevoGasto.setMonto(nuevoMonto);
            nuevoGasto.setFecha(nuevaFecha);

            // Actualizar el gasto en la base de datos
            gastoDao.updateGasto(nuevoGasto);

            System.out.println("Gasto actualizado con éxito.");
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un valor válido para el monto.");
        } catch (ParseException e) {
            System.out.println("Error: Formato de fecha inválido. Use el formato dd/MM/yyyy.");
        }
    }
    /*-------------------------------*/


    /*---------DELETE Gastos----------*/
    public void eliminarGasto() {
        GastoDao gastoDao = new GastoDaoImpl();
        System.out.print("Ingrese el ID del gasto que desea eliminar: ");
        int gastoId = scanner.nextInt();
        scanner.nextLine(); // Limpiar el búfer del escáner

        // Verificar si el gasto existe antes de eliminarlo
        GastoDto gastoExistente = gastoDao.buscarGastoPorId(gastoId);

        if (gastoExistente == null) {
            System.out.println("El gasto con el ID proporcionado no existe.");
        } else {
            // Eliminar el gasto
            gastoDao.deleteGasto(gastoId);
            System.out.println("Gasto eliminado con éxito.");
        }
    }
    /*-------------------------------*/


    /*---------TOTAL Gastos----------*/
    private void mostrarTotalDeGastos() {
        double totalGastos = calcularTotalGastos(gastoDao); // Usar el GastoDao proporcionado
        System.out.println("-----------------------------");
        System.out.println("Total de gastos: S/." + totalGastos);
        System.out.println("-----------------------------");
    }
    @Override
    public double calcularTotalGastos(GastoDao gastoDao) {
        List<GastoDto> gastos = gastoDao.getAll(); // Obtén la lista de gastos del GastoDao
        return gastos.stream()
                .mapToDouble(GastoDto::getMonto)
                .sum();
    }
    /*-------------------------------*/


    /*---------SALIR----------*/
    public void salir() {
        scanner.close();
        System.exit(0); // salir de la aplicación
    }
    /*-------------------------------*/
}