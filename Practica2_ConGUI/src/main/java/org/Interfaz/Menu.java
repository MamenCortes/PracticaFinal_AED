package org.Interfaz;

import org.Excepciones.*;
import org.InputOutput.ArchivosCSV;
import org.InputOutput.ArchivosCsvGui;
import org.InputOutput.Consola;
import org.Logica.PoblacionRatones;
import org.Logica.Raton;
import org.Logica.Sexo;

import javax.swing.*;
import javax.swing.plaf.SeparatorUI;
import javax.swing.plaf.SliderUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Clase principal del programa.
 * Genera una interfaz gráfica para la aplicación, controla la lógica del programa y captura excepciones.
 */
public class Menu extends JFrame implements ActionListener{
    //Atributos de la clase
    private JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11;
    private TreeMap<String, PoblacionRatones> poblaciones;

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.setVisible(true);
        menu.setResizable(false);

    }

    /**
     * Constructor de la clase, inicializa un menú de opciones.
     */
    public Menu(){
        super("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        this.poblaciones = new TreeMap<>();

        b1 = new JButton("Abrir un arhivo");
        b2 = new JButton("Crear una nueva población");
        b3 = new JButton("Crear una población virtual");
        b4 = new JButton("Añadir un ratón a una población existente");
        b5 = new JButton("Listar los códigos de referencia de los ratones");
        b6 = new JButton("Eliminar un ratón de una población");
        b7 = new JButton("Modificar los datos de un ratón");
        b8 = new JButton("Ver información de un ratón");
        b9 = new JButton("Simulación de MonteCarlo");
        b10 = new JButton("Guardar");
        b11 = new JButton("Guardar como");

        JButton[] botones = {b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11};
        JPanel buttonsPanel= new JPanel(new GridLayout(0,1));

        for (JButton boton:botones) {
            boton.setPreferredSize(new Dimension(200, 30));
            boton.addActionListener(this);
            //boton.setBackground(Color.LIGHT_GRAY);
            buttonsPanel.add(boton);
        }

        //Añadir los botones al Scroll
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(buttonsPanel);

        //Menu text
        JLabel menuText = new JLabel("MENU");
        menuText.setHorizontalAlignment(JLabel.CENTER);
        menuText.setVerticalAlignment(JLabel.CENTER);
        menuText.setPreferredSize(new Dimension(400, 50));

        JPanel emptyHorizontalPanel = new JPanel();
        emptyHorizontalPanel.setPreferredSize(new Dimension(400, 50));
        JPanel emptyVerticalPanel = new JPanel();
        emptyVerticalPanel.setPreferredSize(new Dimension(50, 400));
        JPanel emptyVerticalPanel2 = new JPanel();
        emptyVerticalPanel2.setPreferredSize(new Dimension(50, 400));


        //Añadir los componentes al JFrame
        //Se usarán paneles vacíos como bordes
        getContentPane().add(menuText, BorderLayout.PAGE_START);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        //Para crear un marco alrededor del menú de opciones
        getContentPane().add(emptyHorizontalPanel, BorderLayout.PAGE_END);
        getContentPane().add(emptyVerticalPanel, BorderLayout.LINE_START);
        getContentPane().add(emptyVerticalPanel2, BorderLayout.LINE_END);
    }


    /**
     * Método que captura los eventos lanzados por los botones del menú principal.
     * Para cada evento se generarán una serie de acciones.
     * Maneja el flujo del programa
     * @param e el evento que se va a procesar
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<File> files = new ArrayList<File>();
        if (e.getSource()==b1) {
            //Abrir archivo
            File file = null;
            try{
                //Preguntar al usuario qué archivo quiere abrir
                file = ArchivosCsvGui.openFile();
            }catch (DoNotExistException notExistException){
                showErrorMessage("No hay archivos creados");
            }catch (ErrorFileAccessException ex){
                showErrorMessage(ex.getError());
            }
            //Almacenar la población del archivo en el array de poblaciones
            if(file != null){
                if(files.contains(file)){
                    showErrorMessage("Este archivo ya se ha abierto");
                }else{
                    files.add(file);
                    try {
                        PoblacionRatones p1 = ArchivosCsvGui.readPoblacion(file);
                        if(poblaciones.containsKey(p1.getNombrePoblacion())){
                            showErrorMessage("Este archivo ya se ha abierto");
                        }else {
                            poblaciones.put(p1.getNombrePoblacion(), p1);
                            showMessage("Población abierta con éxito");
                        }
                    }catch (ErrorFileAccessException errorFileAccessException){
                        showErrorMessage("Ha habido un problema leyendo el archivo, vuelva a intentarlo más tarde");
                    }catch (NullPointerException npex){
                        showErrorMessage("Ha habido un problema leyendo el nombre de la poblacion");
                    }catch (IllegalArgument iAex){
                        showErrorMessage("Error creando la población"+iAex.getMessage());
                    }
                }
            }
        }else if(e.getSource() == b2){
            //Crear nueva Población
            //Crear una nueva población y añadirla al array de poblaciones
            try {
                PoblacionRatones poblacion = crearPoblacion();
                poblaciones.put(poblacion.getNombrePoblacion(), poblacion);
                showMessage("Población creada con éxito. Recuerda que la población no contiene ratones");
            }catch (IllegalArgument iAex){
                showErrorMessage("Error creando la poblacion: "+iAex.getMessage());
            }catch (GUIExceptions guiEx){
                if(guiEx.getGuiError().equals(GUIError.EmptyTextField)){
                    showErrorMessage(guiEx.getMessage());
                }
            }
        }
        else if(e.getSource() == b3){
            //Crear Poblacion Virtual
            try {
                PoblacionRatones poblacion = createPoblacionVirtual();
                poblaciones.put(poblacion.getNombrePoblacion(), poblacion);
                showMessage("Población Virtual creada con éxito");
            }catch (IllegalArgument iAex){
                showErrorMessage("Error creando la poblacion: "+iAex.getMessage());
            }catch (GUIExceptions guiEx){
                if(guiEx.getGuiError().equals(GUIError.EmptyTextField)){
                    showErrorMessage(guiEx.getMessage());
                }
            }
        }
        else if(e.getSource() == b4){
            //Crear Raton
            try{
                if(!poblaciones.isEmpty()) {
                    String nombrePoblacion = quePoblacion(poblaciones);
                    if (poblaciones.containsKey(nombrePoblacion)) {
                        poblaciones.get(nombrePoblacion).addRaton(createRaton());
                        showMessage("Ratón creado con éxito");
                    }
                }else {
                    showErrorMessage("No hay poblaciones creadas");
                }
            }catch (IllegalArgument iAex){
                showErrorMessage(iAex.getMessage());
            }catch (GUIExceptions guiEx){
                if(guiEx.getGuiError().equals(GUIError.EmptyTextField)){
                    showErrorMessage(guiEx.getMessage());
                }
            }
        }
        else if(e.getSource() == b5){
            //Listar los códigos de referencia de todos los ratones de una población
            try{
                if(!poblaciones.isEmpty()) {
                    String nombrePoblacion = quePoblacion(poblaciones);
                    if (poblaciones.containsKey(nombrePoblacion)) {
                        int op = queOrden();
                        switch (op) {
                            case 0 ->
                                    showRatonesOrdenados(poblaciones.get(nombrePoblacion).getRatonesOrdenadosPorCodigo());
                            case 1 ->
                                    showRatonesOrdenados(poblaciones.get(nombrePoblacion).getRatonesOrdenadosPorNacimiento());
                            case 2 ->
                                    showRatonesOrdenados(poblaciones.get(nombrePoblacion).getRatonesOrdenadosPorPesoDescendente());
                        }
                    }else {
                        showErrorMessage("No se ha encontrado la población");
                    }
                }else {
                    showErrorMessage("No hay poblaciones creadas");
                }
            }catch (GUIExceptions guiEx){
                //Operación cancelada
            }
        }
        else if(e.getSource() == b6){
            //Eliminar ratón
            try{
                if(!poblaciones.isEmpty()) {
                    String nombrePoblacion = quePoblacion(poblaciones);
                    if (poblaciones.containsKey(nombrePoblacion)) {
                        poblaciones.get(nombrePoblacion).deleteRaton(getCodeRatonAEliminar(poblaciones.get(nombrePoblacion)));
                        showMessage("Error eliminado con éxito");
                    }else {
                        showErrorMessage("No se ha encontrado la población");
                    }
                }else {
                    showErrorMessage("No hay poblaciones creadas");
                }
            }catch (DoNotExistException doNotExistException){
                if(doNotExistException.getErrorType().equals(ErrorType.RatonNotFound)){
                    System.out.println("No se ha encontrado el ratón identificado con ese numero de referencia");
                }else if(doNotExistException.getErrorType().equals(ErrorType.PoblacionDoesNotContainRatones)){
                    System.out.println("La población no contiene ratones");
                }
            }catch (GUIExceptions ex){
                System.out.println(ex.getMessage());
            }
        }
        else if(e.getSource() == b7){
            //Modificar valores de peso, temperatura o Campo de texto
            try{
                if(!poblaciones.isEmpty()) {
                    String nombrePoblacion = quePoblacion(poblaciones);
                    if (poblaciones.containsKey(nombrePoblacion)) {
                        int code = queRaton(poblaciones.get(nombrePoblacion));
                        Raton raton = modificarRaton(poblaciones.get(nombrePoblacion).getRaton(code));
                        poblaciones.get(nombrePoblacion).modificarRaton(raton);
                        showMessage("Ratón modificado con éxito");
                    }else {
                        showErrorMessage("No se ha encontrado la población especificada");
                    }
                }else{
                    showErrorMessage("No hay poblciones creadas");
                }
            }catch (DoNotExistException doNotExistException){
               if(doNotExistException.getErrorType().equals(ErrorType.RatonNotFound)){
                    System.out.println("El código introducido no corresponde con el de ningún ratón de la población");
                }else if(doNotExistException.getErrorType().equals(ErrorType.PoblacionDoesNotContainRatones)){
                    System.out.println("La población no contiene ratones");
                }
            }catch (IllegalArgument iAex){
                if(iAex.getErrorType().equals(ErrorType.IllegalPeso)){
                    System.out.println("El peso introducido no es válido. Debe tomar valores entre 0 y 50 gramos");
                }else if(iAex.getErrorType().equals(ErrorType.IllegalTemperatura)) {
                    System.out.println("La temperatura introducida no es válida. Debe tomar valores entre 20 y 40º");
                }
            }catch (GUIExceptions ex){
                //Operación cancelada
            }
        }
        else if(e.getSource() == b8){
            //Ver info detallada
            try{
                if(!poblaciones.isEmpty()) {
                    String nombrePoblacion = quePoblacion(poblaciones);
                    if (poblaciones.containsKey(nombrePoblacion)) {
                        verInfoDetallada(poblaciones.get(nombrePoblacion));
                    }else {
                        showErrorMessage("No se ha encontrado esa población");
                    }
                }else {
                    showErrorMessage("No hay ninguna población creada");
                }
            }catch (GUIExceptions guiEx){
                //Operación cancelada
            }
        }
        else if(e.getSource() == b9){
            //Simulación de Montecarlo
            try {
                if(!poblaciones.isEmpty()) {
                    String nombrePoblacion = quePoblacion(poblaciones);
                    if (poblaciones.containsKey(nombrePoblacion)) {
                        showTableSimulacion(poblaciones.get(nombrePoblacion));
                    }else {
                        showErrorMessage("No se ha encontrado esa población");
                    }
                }else {
                    showErrorMessage("No hay ninguna población creada");
                }
            }catch (DoNotExistException doNotExistException){
                if(doNotExistException.getErrorType().equals(ErrorType.PoblacionDoesNotContainRatones)){
                    showErrorMessage("La población no contiene ratones");
                }
            }catch (IllegalArgument iAex){
                showErrorMessage(iAex.getMessage());
            }catch (GUIExceptions guiEx){
                //Operación cancelada
            }
        }
        else if(e.getSource() == b10){
            //Guardar
            if(!poblaciones.isEmpty()){
                //El nombre del archivo será igual al nombre de la población
                String nombrePoblacion= Menu.quePoblacion(poblaciones);
                File file = new File("src/main/resourcesCSV/"+nombrePoblacion+".csv");
                //Si el archivo existe
                if(files.contains(file)){
                    try {
                        //Guarda la población en un archivo
                        ArchivosCsvGui.writePoblacion(poblaciones.get(nombrePoblacion), file);
                        showMessage("La población se ha guardado con éxito");
                    }catch (ErrorFileAccessException errorFileAccessException){
                        showErrorMessage("Ha habido un problema guardando los datos en el archivo, vuelva a intentarlo más tarde");
                    }
                }else {
                    showErrorMessage("El archivo no ha sido creado previamente. Selecciones la opción Guardar Como para guardarlo");
                }
            }else{
                showErrorMessage("No hay ninguna población creada, cree una antes de guardar");
            }
        }
        else if(e.getSource() == b11){
            //Guardar Como
            if(!poblaciones.isEmpty()){
                //El nombre del archivo será igual al nombre de la población
                String nombrePoblacion;
                nombrePoblacion = Menu.quePoblacion(poblaciones);

                //Guardar la población en un archivo
                //No se comprueba si el archivo existe porque se asume que se creará uno nuevo
                if(poblaciones.containsKey(nombrePoblacion)){
                    try {
                        File file = new File("src/main/resourcesCSV/"+nombrePoblacion+".csv");
                        //Guarda la población en un archivo
                        ArchivosCsvGui.writePoblacion(poblaciones.get(nombrePoblacion), file);
                        showMessage("La población se ha guardado correctamente");
                    }catch (ErrorFileAccessException errorFileAccessException){
                        showErrorMessage("Ha habido un problema guardando los datos en el archivo, vuelva a intentarlo más tarde");
                    }
                }showErrorMessage("No se ha encontrado esa población");

            }else{
                showErrorMessage("No hay ninguna población creada. Cree una antes de guardar");
            }
        }
    }

    /**
     * Muestra un cuadro de diálogo con el error especificado por parámetro.
     * @param message el mensaje que se va a mostrar
     */
    public static void showErrorMessage(String message){
        JLabel errorMessage = new JLabel(message);
        JOptionPane.showMessageDialog(null, errorMessage, "Error",JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un cuadro de diálogo con el mensaje especificado por parámetro
     * @param message el mensaje que se va a mostrar
     */
    public static void showMessage(String message){
        JLabel errorMessage = new JLabel(message);
        JOptionPane.showMessageDialog(null, errorMessage, "",JOptionPane.DEFAULT_OPTION);
    }

    /**
     * Genera un JOptionPanel que permite al usuario introducir los
     * valores necesarios para crear una población de ratones.
     * @return la población creada
     * @throws IllegalArgument  Si el número de días en la instalación no está entre 0 y 630, o si no es múltiplo de 5.
     *          También si hay el valor introducido para los días en la instalación no es de tipo entero (IllegalFormat).
     * @throws GUIExceptions si no se introducen valores en alguno de los campos (EmptyTExtField), o si se cancela la operación (CanceledOperation)
     */
    public PoblacionRatones crearPoblacion() throws IllegalArgument, GUIExceptions {
        this.setTitle("Crear Poblacion");
        PoblacionRatones poblacion = null;

        //Se crea el panel que contendrá los elementos
        JPanel gridPanel = new JPanel(new GridLayout(3, 2));

        //Se crean textos y sus respectivos campos de texto
        JLabel nombrePoblacionText = new JLabel("Nombre de la población");
        nombrePoblacionText.setVerticalAlignment(JLabel.CENTER);
        nombrePoblacionText.setHorizontalAlignment(JLabel.CENTER);
        JTextField nombrePoblacionInput = new JTextField();

        JLabel nombreResponsableText = new JLabel("Nombre del responsable");
        nombreResponsableText.setVerticalAlignment(JLabel.CENTER);
        nombreResponsableText.setHorizontalAlignment(JLabel.CENTER);
        JTextField nombreResponsableInput = new JTextField();

        JLabel diasInstalacionText = new JLabel("Días en la instalación");
        diasInstalacionText.setVerticalAlignment(JLabel.CENTER);
        diasInstalacionText.setHorizontalAlignment(JLabel.CENTER);
        JTextField diasInstalacionInput = new JTextField();

        //Se añaden en orden al panel
        gridPanel.add(nombrePoblacionText);
        gridPanel.add(nombrePoblacionInput);
        gridPanel.add(nombreResponsableText);
        gridPanel.add(nombreResponsableInput);
        gridPanel.add(diasInstalacionText);
        gridPanel.add(diasInstalacionInput);

        //Se muestra el panel en un cuadro de diálogo que permite devolver valores
        int opcionResultado = JOptionPane.showConfirmDialog(null, gridPanel, "Crear nueva poblacion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(opcionResultado == JOptionPane.OK_OPTION){
            //Comprobar que se han introducido valores
            try {
                //Se genera y devuelve la población
                String nombrePOblacion = nombrePoblacionInput.getText();
                String nombreResponsable = nombreResponsableInput.getText();
                int diasEnInstalacion = Integer.parseInt(diasInstalacionInput.getText());
                poblacion = new PoblacionRatones(nombrePOblacion, nombreResponsable, diasEnInstalacion);
                return poblacion;
            }catch (NullPointerException npex){
                throw new GUIExceptions(GUIError.EmptyTextField, "Todos los campos deben rellenarse para poder crear una población");
            }catch (NumberFormatException nfex){
                throw new IllegalArgument("Los días en la instalación deben ser de tipo entero",ErrorType.IllegalFormat);
            }
        }else {
            throw new GUIExceptions(GUIError.CanceledOperation,"Operación cancelada");
        }
    }

    /**
     * Genera un JOptionPanel que permite al usuario introducir los
     * valores necesarios para crear una población virtual de ratones.
     * @return la población virtual
     * @throws IllegalArgument si hay un error convirtiendo los datos de String a Integer (IllegalFormat);
     *          si el número de días en la instalación no es válido;
     *          y también si los porcentajes introducidos no son válidos (Si no están entre 0-100).
     * @throws GUIExceptions si se cancela la operación (CanceledOperation) o si no se rellenan todos los campos de texto (EmptyTextField)
     */
    public PoblacionRatones createPoblacionVirtual() throws IllegalArgument, GUIExceptions {
        this.setTitle("Crear Poblacion");
        PoblacionRatones poblacion = null;
        //ArrayList que almacenará los textFields en el orden en el que se generen
        ArrayList<JTextField> textFields = new ArrayList<>();
        //Parámetros que tendrá que introducir el usuario
        String[] parametros = {"Nombre de la poblacion", "Nombre del responsable", "Días en la instalación", "Numero de ratones",
                "Porcentaje de Hembras", "Porcentaje de machos estériles", "Porcentaje de machos poligámicos",
                "Porcentaje Hembras mutadas"};

        //Inicializar el panel donde se mostrarán los labels y TextFields
        JPanel gridPanel = new JPanel(new GridLayout(parametros.length, 2));

        //Para cada parámetro
        for (String param:parametros) {
            //Se crea un texto para cada parámetro y se alinea
            JLabel text = new JLabel(param);
            text.setVerticalAlignment(JLabel.CENTER);
            text.setHorizontalAlignment(JLabel.CENTER);
            //Se crea un textField para introducir información
            JTextField textInput = new JTextField();
            //se añaden al panel
            gridPanel.add(text);
            gridPanel.add(textInput);
            textFields.add(textInput);
        }


        int opcionResultado = JOptionPane.showConfirmDialog(null, gridPanel, "Crear nueva poblacion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(opcionResultado == JOptionPane.OK_OPTION){
            try{
                //Se recupera el texto introducido en cada textField y
                // se crea una población virtual a partir de esos valores
                String nombrePOblacion = textFields.get(0).getText();
                String nombreResponsable = textFields.get(1).getText();
                int diasEnInstalacion = Integer.parseInt(textFields.get(2).getText());
                int numRatones = Integer.parseInt(textFields.get(3).getText());
                int pHembras = Integer.parseInt(textFields.get(4).getText());
                int pMachosEst = Integer.parseInt(textFields.get(5).getText());
                int pMachosPolig = Integer.parseInt(textFields.get(6).getText());
                int pcXHembraMut = Integer.parseInt(textFields.get(7).getText());
                poblacion = new PoblacionRatones(nombrePOblacion, nombreResponsable, diasEnInstalacion
                        , numRatones, pHembras, pMachosEst, pMachosPolig, pcXHembraMut);
                return poblacion;

            }catch (NullPointerException npex){
                throw new GUIExceptions(GUIError.EmptyTextField,"Todos los campos deben rellenarse para poder crear una población");
            }catch (NumberFormatException numFex){
                throw new IllegalArgument("Alguno de los valores introducidos no es de tipo entero", ErrorType.IllegalFormat);
            }

        }else {
            throw new GUIExceptions(GUIError.CanceledOperation, "Operación cancelada");
        }

    }

    /**
     * Genera un JOptionPanel que permite al usuario introducir los
     * valores necesarios para crear un ratón.
     * @return el ratón creado
     * @throws IllegalArgument si el formato de la fecha no es correcto (IllegalDate),
     *          si alguno de los valores de entrada no es del tipo correcto y no se consigue convertir (IllegalFormat),
     *          si el peso o la temperatura no son válidos (IllegalPeso e IllegalTemperatura)
     * @throws GUIExceptions si se cancela la operación (CanceledOperation) o si no se rellenan todos los campos de texto (EmptyTextField)
     */
    public Raton createRaton() throws IllegalArgument, GUIExceptions {
        //Array para almacenar los textFields
        ArrayList<JTextField> textFields = new ArrayList<>();
        String[] parametros = {"Codigo de referencia", "Nacimiento (YYYY-MM-DD)", "Peso", "Temperatura", "Información Adicional"};

        //Se inicializa el panel donde se dispondrán los componentes
        JPanel gridPanel = new JPanel(new GridLayout(9, 2));

        //Crear los label, textfields de los parametros y añadirlos al panel
        for (String param:parametros) {
            JLabel text = new JLabel(param);
            text.setVerticalAlignment(JLabel.CENTER);
            text.setHorizontalAlignment(JLabel.CENTER);
            JTextField textInput = new JTextField();
            gridPanel.add(text);
            gridPanel.add(textInput);
            textFields.add(textInput);
        }


        //Crear ComboBoxes para limitar la selección de algunos atributos
        //Sex ComboBox
        JLabel sexText = new JLabel("Sexo");
        sexText.setVerticalAlignment(JLabel.CENTER);
        sexText.setHorizontalAlignment(JLabel.CENTER);
        JComboBox<String> sex =new JComboBox<String>();
        sex.addItem("HEMBRA");
        sex.addItem("MACHO");
        gridPanel.add(sexText);
        gridPanel.add(sex);

        //Cromosoma 1
        JLabel cr1Text = new JLabel("Cromosoma 1");
        cr1Text.setVerticalAlignment(JLabel.CENTER);
        cr1Text.setHorizontalAlignment(JLabel.CENTER);
        JComboBox<String> cr1 =new JComboBox<String>();
        cr1.addItem("Sin Mutación");
        cr1.addItem("Mutado");
        gridPanel.add(cr1Text);
        gridPanel.add(cr1);

        //Cromosoma 2
        JLabel cr2Text = new JLabel("Cromosoma 2");
        cr2Text.setVerticalAlignment(JLabel.CENTER);
        cr2Text.setHorizontalAlignment(JLabel.CENTER);
        JComboBox<String> cr2 =new JComboBox<String>();
        cr2.addItem("Sin Mutación");
        cr2.addItem("Mutado");
        gridPanel.add(cr2Text);
        gridPanel.add(cr2);

        //Se crea el cuadro de diálogo con el panel
        int opcionResultado = JOptionPane.showConfirmDialog(null, gridPanel, "Crear nuevo ratón", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(opcionResultado == JOptionPane.OK_OPTION){
            try{
                int codigoRef = Integer.parseInt(textFields.get(0).getText());
                LocalDate nacimiento = parseNacimiento(textFields.get(1).getText());
                Sexo sexo = Sexo.valueOf((String) sex.getSelectedItem());
                Boolean c1mut = null;
                Boolean c2mut = null;
                String cr1String = (String) cr1.getSelectedItem();
                if(cr1String.equals("Mutado")){
                    c1mut = true;
                } else if (cr1String.equals("Sin Mutación")) {
                    c1mut = false;
                }
                String cr2String = (String) cr2.getSelectedItem();
                if(cr2String.equals("Mutado")){
                    c2mut = true;
                } else if (cr1String.equals("Sin Mutación")) {
                    c2mut = false;
                }

                float peso = Float.parseFloat(textFields.get(2).getText());
                float temp = Float.parseFloat(textFields.get(3).getText());
                String campoTexto = textFields.get(4).getText();
                return new Raton(codigoRef, nacimiento, peso, temp, sexo, Boolean.TRUE.equals(c1mut), Boolean.TRUE.equals(c2mut), campoTexto);
            }catch (NullPointerException npex){
                throw new GUIExceptions(GUIError.EmptyTextField,"Todos los campos deben rellenarse para poder crear una población");
            }catch (NumberFormatException numFex){
                throw new IllegalArgument("Alguno de los valores introducidos don del tipo correcto", ErrorType.IllegalFormat);
            }

        }else{
            throw new GUIExceptions(GUIError.CanceledOperation, "Operación Cancelada");
        }
    }

    /**
     * Convierte una cadena de texto en formato ISO_DATE (YYYY-MM-DD) en una fecha (LocalDate)
     * @param stringFecha la fecha en formato texto
     * @return la fecha convertida
     * @throws IllegalArgument si el formato de stringFecha no es correcto (IllegalDate)
     */
    private static LocalDate parseNacimiento(String stringFecha)throws IllegalArgument{
        try {
            return LocalDate.parse(stringFecha, DateTimeFormatter.ISO_DATE);
        }// If the String pattern is invalid
        catch (IllegalArgumentException e) {
            throw new IllegalArgument("Formato de la fecha incorrecto", ErrorType.IllegalDate);

        } // If the String was unable to be parsed.
        catch (DateTimeParseException e) {
            throw new IllegalArgument("Ha ocurrido un error convirtiendo la fecha", ErrorType.IllegalDate);
        }
    }

    /**
     * Genera un JOptionPanel que permite seleccionar al usuario con qué población quiere trabajar.
     * @param poblaciones un TreeMap con las poblaciones entre las cuales se podrá elegir. La clave corresponderá con el nombre de la población
     * @return el nombre de la población seleccionada
     * @throws GUIExceptions si se cancela la operación (CanceledOperation)
     */
    public static String quePoblacion(TreeMap<String, PoblacionRatones> poblaciones) throws GUIExceptions{
        //Crear un label y un JComboBox para la elección de poblaciones
        JLabel poblacionText = new JLabel("Poblaciones");
        poblacionText.setVerticalAlignment(JLabel.CENTER);
        poblacionText.setHorizontalAlignment(JLabel.CENTER);
        JComboBox<String> poblacionesComboBox =new JComboBox<String>();

        //El combobox contendrá como opciones el nombre de todas las poblaciones abiertas
        for (PoblacionRatones poblacionRatones : poblaciones.values()) {
            poblacionesComboBox.addItem(poblacionRatones.getNombrePoblacion());
        }
        //Se inicializa el panel contendor y se añaden los componentes
        JPanel selectPoblacion = new JPanel(new GridLayout(0, 2));
        selectPoblacion.add(poblacionText);
        selectPoblacion.add(poblacionesComboBox);


        int optionPoblacion = JOptionPane.showConfirmDialog(null, selectPoblacion, "Seleccionar Población", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(optionPoblacion == JOptionPane.OK_OPTION){
            return  (String) poblacionesComboBox.getSelectedItem();

        }else {
            throw new GUIExceptions(GUIError.CanceledOperation, "Operación Cancelada");
        }
    }

    /**
     * Genera un JOptionPanel que permite al usuario seleccionar con qué población quiere trabajar.
     * @param poblacion la población donde están contenidos los ratones
     * @return el código de referencia del ratón seleccionado
     * @throws DoNotExistException si la población no contiene ratones
     * @throws GUIExceptions si se cancela la operación (CanceledOperation)
     */
    private Integer queRaton(PoblacionRatones poblacion) throws DoNotExistException, GUIExceptions{
        //Crear un label y un JComboBox para la elección de ratones
        JLabel ratonText = new JLabel("Ratones");
        ratonText.setVerticalAlignment(JLabel.CENTER);
        ratonText.setHorizontalAlignment(JLabel.CENTER);
        JComboBox<String> ratonesComboBox =new JComboBox<String>();

        //Recuperar una copia de todos los ratones de la población
        Set<Raton> ratones = poblacion.getRatonesOrdenadosPorCodigo();
        //Mientras existan ratones
        if(!ratones.isEmpty()){
            for (Raton raton : ratones) {
                ratonesComboBox.addItem(String.valueOf(raton.getCodigoReferencia()));
            }
        }else {
            throw new DoNotExistException(ErrorType.PoblacionDoesNotContainRatones);
        }
        //Inicializar el panel donde se dispondrán los componentes
        JPanel selectPoblacion = new JPanel(new GridLayout(0, 2));
        selectPoblacion.add(ratonText);
        selectPoblacion.add(ratonesComboBox);


        int optionPoblacion = JOptionPane.showConfirmDialog(null, selectPoblacion, "Seleccionar Población", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(optionPoblacion == JOptionPane.OK_OPTION){
            return Integer.parseInt((String)ratonesComboBox.getSelectedItem());
        }else {
            throw new GUIExceptions(GUIError.CanceledOperation, "Operación cancelada");
        }
    }

    /**
     * Genera un JOptionPanel que permite al usuario seleccionar un criterio de ordenación.
     * @return 0 si se elige ordenar según el código de referencia, 1 si es según la fecha de nacimiento, 2 si es de mayor a menor peso
     * @throws GUIExceptions si se cancela la operación (CanceledOperation)
     */
    private int queOrden() throws GUIExceptions{
        //Crear un label y un JComboBox para la selección de los criterios de ordenación
        JLabel criteriosText = new JLabel("Criterios de ordenación");
        criteriosText.setVerticalAlignment(JLabel.CENTER);
        criteriosText.setHorizontalAlignment(JLabel.CENTER);
        JComboBox<String> ordenComboBox =new JComboBox<String>();
        ordenComboBox.addItem("Según el código de referencia");
        ordenComboBox.addItem("Según la fecha de nacimiento");
        ordenComboBox.addItem("De mayor a menor peso");

        //Se inicializa el contenedor
        JPanel selectPoblacion = new JPanel(new GridLayout(2, 0));
        selectPoblacion.add(criteriosText);
        selectPoblacion.add(ordenComboBox);

        int optionPoblacion = JOptionPane.showConfirmDialog(null, selectPoblacion, "Seleccionar Población", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(optionPoblacion == JOptionPane.OK_OPTION){
            return ordenComboBox.getSelectedIndex();
        }else {
            throw new GUIExceptions(GUIError.CanceledOperation, "Operación Cancelada");
        }
    }

    /**
     * Genera un JOptionPanel que permite al usuario seleccionar un archivo contenido dentro de una lista.
     * @param listaFiles la lista de ficheros
     * @return la posición del array del elemento seleccionado
     * @throws GUIExceptions si se cancela la operación
     * @throws DoNotExistException si listFiles está vacía
     */
    public static Integer queFichero(File[] listaFiles) throws GUIExceptions, DoNotExistException{

        JLabel label = new JLabel("Ficheros existentes");
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        JComboBox<String> filesComboBox =new JComboBox<String>();
        //Se añaden todos los nombres de los ficheros
        if(listaFiles.length!=0){
            for(File file: listaFiles) {
                filesComboBox.addItem(file.getName());
            }
        }else {
            throw new DoNotExistException(ErrorType.FileDoNotExist);
        }

        JPanel selectFichero = new JPanel(new GridLayout(0, 2));
        selectFichero.add(label);
        selectFichero.add(filesComboBox);


        int optionPoblacion = JOptionPane.showConfirmDialog(null, selectFichero, "Seleccionar Población", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(optionPoblacion == JOptionPane.OK_OPTION){
            return filesComboBox.getSelectedIndex();
        }else {
            throw new GUIExceptions(GUIError.CanceledOperation,"Selección de fichero cancelada");
        }
    }

    /**
     * Genera una ventana emergente donde mostrar la información de la población especificada,
     * y de un ratón en concreto
     * @param poblacion la población
     */
    private void verInfoDetallada(PoblacionRatones poblacion){
        //Obtener el código del ratón
        int code = queRaton(poblacion);
        Raton raton = poblacion.getRaton(code);

        //Inicializar el panel donde se dispondrán los componentes
        JPanel poblacionPanel = new JPanel(new GridLayout(0,1));
        poblacionPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JLabel nombreText = new JLabel("Nombre de la población: "+poblacion.getNombrePoblacion());
        JLabel responsableText = new JLabel("Nombre del responsable: "+poblacion.getNombreResponsable());
        JLabel diasText = new JLabel("Días en la instalación: "+poblacion.getDiasEnInstalacion());

        poblacionPanel.add(nombreText);
        poblacionPanel.add(responsableText);
        poblacionPanel.add(diasText);

        //Generar un panel para contener la información del ratón
        JPanel content = new JPanel(new GridLayout(0,1));
        content.add(poblacionPanel);
        content.add(ratonCell(raton));

        //Mostrar la información en un cuadro de diálogo
        JOptionPane.showConfirmDialog(null, content, "Información Detallada", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Genera una ventana emergente en la que muestra la información de todos los ratones especificados
     * @param ratonesOrdenados los ratones
     */
    private void showRatonesOrdenados(Set<Raton> ratonesOrdenados) {
        Iterator<Raton> iterator = ratonesOrdenados.iterator();

        JPanel gridPanel = new JPanel(new GridLayout(ratonesOrdenados.size(), 0));
        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane1.setViewportView(gridPanel);

        //Genera un panel con la información de cada ratón y los añade a un contendor
        while (iterator.hasNext()) {
            gridPanel.add(ratonCell(iterator.next()));
        }

        //Ajustar el ScrollView para que no se amplíe al añadir muchos elementos
        scrollPane1.setPreferredSize(this.getPreferredSize());

        //Mostrar el panel en una ventana emergente
        JOptionPane.showConfirmDialog(null, scrollPane1, "Ratones ordenados", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Genera un panel que contiene la información ordenada de un ratón
     * Este panel está pensado para usarse como celda en tablas, listas, etc.
     * @param raton el ratón
     * @return el panel
     */
    private JPanel ratonCell(Raton raton){
        //Crear panel contenedor. Asignarle un tamaño y generar un borde para que se distinga de otros componentes
        JPanel gridPanel = new JPanel(new GridLayout(0,4));
        gridPanel.setPreferredSize(new Dimension(300, 70));
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        //Codigo Referencia
        JLabel codeRef = new JLabel("Codigo Referencia");
        codeRef.setVerticalAlignment(JLabel.CENTER);
        codeRef.setHorizontalAlignment(JLabel.CENTER);
        JLabel codeRefInfo = new JLabel(String.valueOf(raton.getCodigoReferencia()));
        gridPanel.add(codeRef);
        gridPanel.add(codeRefInfo);

        //Crear textos y TextFields para todos los atributos a mostrar
        //Añadirlos en orden al contenedor
        //Fecha de nacimiento
        JLabel nacimiento = new JLabel("Fecha de Nacimiento");
        nacimiento.setVerticalAlignment(JLabel.CENTER);
        nacimiento.setHorizontalAlignment(JLabel.CENTER);
        JLabel nacimientoInfo = new JLabel(String.valueOf(raton.getNacimiento()));
        gridPanel.add(nacimiento);
        gridPanel.add(nacimientoInfo);

        //Peso
        JLabel peso = new JLabel("Peso");
        peso.setVerticalAlignment(JLabel.CENTER);
        peso.setHorizontalAlignment(JLabel.CENTER);
        JLabel pesoText = new JLabel(String.valueOf(raton.getPeso()));
        gridPanel.add(peso);
        gridPanel.add(pesoText);

        //Temperatura
        JLabel temp = new JLabel("Temperatura");
        temp.setVerticalAlignment(JLabel.CENTER);
        temp.setHorizontalAlignment(JLabel.CENTER);
        JLabel tempInfo = new JLabel(String.valueOf(raton.getTemperatura()));
        gridPanel.add(temp);
        gridPanel.add(tempInfo);

        //Sexo
        JLabel sexo = new JLabel("Sexo");
        sexo.setVerticalAlignment(JLabel.CENTER);
        sexo.setHorizontalAlignment(JLabel.CENTER);
        JLabel sexoText = new JLabel(String.valueOf(raton.getSexo()));
        gridPanel.add(sexo);
        gridPanel.add(sexoText);

        //Cromosomas
        JLabel c1Text = new JLabel(String.valueOf(raton.getCromosoma1()));
        JLabel c2Text = new JLabel(String.valueOf(raton.getCromosoma2()));
        gridPanel.add(c1Text);
        gridPanel.add(c2Text);

        //Campo Texto
        JLabel campoTexto = new JLabel("Campo Texto");
        campoTexto.setVerticalAlignment(JLabel.CENTER);
        campoTexto.setHorizontalAlignment(JLabel.CENTER);
        JLabel campoTextoText = new JLabel(String.valueOf(raton.getCampoTexto()));
        gridPanel.add(campoTexto);
        gridPanel.add(campoTextoText);

        return gridPanel;
    }

    /**
     * Genera una ventana emergente en la que muestra todos los códigos de referencia de los ratones de la población,
     * y permite al usuario introducir el código del ratón que quiere eliminar
     * @param poblacion la población
     * @return el código de referencia del ratón
     * @throws DoNotExistException si la población no contiene ningún ratón
     * @throws GUIExceptions si se cancela la operación (CanceledOperation)
     */
    private Integer getCodeRatonAEliminar(PoblacionRatones poblacion) throws DoNotExistException, GUIExceptions{
        int numRef;
        //Mientras la población no esté vacía
        if (poblacion.containsRatones()) {
            Set<Integer> codes = poblacion.getCodesRatones();
            Iterator<Integer> iterator = codes.iterator();

            //Generar un panel lateral donde se muestren todos los códigos de los ratones
            JPanel gridPanel = new JPanel(new GridLayout(codes.size(), 0));
            JScrollPane scrollPane1 = new JScrollPane();
            scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane1.setViewportView(gridPanel);
            int i = 1;
            while (iterator.hasNext()) {
                gridPanel.add(ratonCodeCell(iterator.next(), i));
                i++;
            }

            JPanel codesContent = new JPanel(new BorderLayout());
            codesContent.add(scrollPane1, BorderLayout.LINE_START);
            codesContent.setAlignmentX(CENTER_ALIGNMENT);
            scrollPane1.setPreferredSize(this.getPreferredSize());

            //Generar otro panel lateral donde se introducirá el código de referencia
            JPanel inputCodePanel = new JPanel();
            inputCodePanel.setLayout(new BoxLayout(inputCodePanel, BoxLayout.PAGE_AXIS));

            JLabel label = new JLabel("Introduce el código del ratón a eliminar");
            label.setVerticalAlignment(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            inputCodePanel.add(label);

            JTextField inputCode = new JTextField();
            inputCodePanel.add(inputCode);
            codesContent.add(inputCodePanel, BorderLayout.CENTER);

            int optionPoblacion = JOptionPane.showConfirmDialog(null, codesContent, "Seleccionar Población", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(optionPoblacion == JOptionPane.OK_OPTION){
                return Integer.parseInt(inputCode.getText());
            }else {
                throw new GUIExceptions(GUIError.CanceledOperation, "Operación Cancelada");
            }
        }else {
            throw new DoNotExistException(ErrorType.PoblacionDoesNotContainRatones);
        }
    }

    /**
     * Genera un panel que muestra el código de referencia del ratón en el siguiente formato: Raton index: code
     * Se pretende utilizar este panel como celda en una lista, tabla, etc.
     * @param code el código del ratón
     * @param index el número del ratón
     * @return un panel
     */
    private JPanel ratonCodeCell(int code, int index){

        JPanel gridPanel = new JPanel(new GridLayout(0,2));
        gridPanel.setPreferredSize(new Dimension(150, 30));
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        //Codigo Referencia
        JLabel codeRef = new JLabel("Raton: "+index+":");
        codeRef.setVerticalAlignment(JLabel.CENTER);
        codeRef.setHorizontalAlignment(JLabel.CENTER);
        JLabel codeRefInfo = new JLabel(String.valueOf(code));
        gridPanel.add(codeRef);
        gridPanel.add(codeRefInfo);

        return gridPanel;
    }

    /**
     * Genera una ventana emergente que permite modificar los valores del peso, temperatura y campoTexto de un ratón dado
     * @param raton el ratón que se va a modificar
     * @return el ratón modificado
     * @throws IllegalArgument si los valores del peso y la temperatura introducidos no son válidos o no son de tipo real.
     * @throws GUIExceptions si se cancela la operación
     */
    private Raton modificarRaton(Raton raton) throws IllegalArgument, GUIExceptions {
        //Inicializar el panel
        JPanel gridPanel = new JPanel(new GridLayout(3, 2));

        //Crear textos y TextFields para las variables que se podrían editar
        //De manera predeterminada los textFields contendrán el valor actual del ratón
        //Peso
        JLabel pesoText = new JLabel("Peso");
        pesoText.setVerticalAlignment(JLabel.CENTER);
        pesoText.setHorizontalAlignment(JLabel.CENTER);
        JTextField pesoTextField = new JTextField();
        pesoTextField.setText(String.valueOf(raton.getPeso()));

        //Temperatura
        JLabel tempText = new JLabel("Temperatura");
        tempText.setVerticalAlignment(JLabel.CENTER);
        tempText.setHorizontalAlignment(JLabel.CENTER);
        JTextField tempTextField = new JTextField();
        tempTextField.setText(String.valueOf(raton.getTemperatura()));

        //Campo de texto
        JLabel campoTextoText = new JLabel("Información adicional");
        campoTextoText.setVerticalAlignment(JLabel.CENTER);
        campoTextoText.setHorizontalAlignment(JLabel.CENTER);
        JTextField campoTextoTextField = new JTextField();
        campoTextoTextField.setText(String.valueOf(raton.getCampoTexto()));

        //Añadir los componentes al panel de forma ordenada
        gridPanel.add(pesoText);
        gridPanel.add(pesoTextField);
        gridPanel.add(tempText);
        gridPanel.add(tempTextField);
        gridPanel.add(campoTextoText);
        gridPanel.add(campoTextoTextField);

        //Generar una ventana emergente para mostra el panel
        int opcionResultado = JOptionPane.showConfirmDialog(null, gridPanel, "Crear nueva poblacion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(opcionResultado == JOptionPane.OK_OPTION){
            //Comprobar que se han introducido valores
            try{
                float peso = Float.parseFloat(pesoTextField.getText());
                raton.setPeso(peso);

                float temp = Float.parseFloat(tempTextField.getText());
                raton.setTemperatura(temp);

                String campoTexto = campoTextoTextField.getText();
                raton.setCampoTexto(campoTexto);
                return raton;
            }catch (NumberFormatException nfex){
                throw new IllegalArgument("Los valores para el peso y la temperatura deben ser números reales", ErrorType.IllegalFormat);
            }
        }else {
            throw new GUIExceptions(GUIError.CanceledOperation, "Operación Cancelada");
        }
    }

    /**
     * Realiza la simulación de montecarlo sobre una población de ratones y
     * genera una tabla con los porcentajes calculados en cada ciclo
     * @param poblacion la población sobre la que se realizará la población
     * @throws IllegalArgument si en la simulación se intenta introducir un macho como madre en una familia,
     * si la temperatura o el peso de alguno de los ratones hijos no es válido,
     * o si uno de los ratones madre no es Hembra.
     * @throws DoNotExistException si la población no contiene ratones
     */
    private void showTableSimulacion(PoblacionRatones poblacion) throws IllegalArgument, DoNotExistException{
        if(!poblacion.containsRatones()){
            throw new DoNotExistException(ErrorType.PoblacionDoesNotContainRatones);
        }else{
            //Generar la simulación de Montecarlo y calcular sus valores
            int[][] porcentajes = poblacion.simulacionMonteCarlo();

            //Array con el nombre de las columnas
            String[] columnas = {"Ciclo","Nº Ratones","%Hembras", "%Machos", "%HN","%Hf", "%He", "%Mn", "%Me","%Mp"};

            //Array de tipo entero con los datos de las filas
            String [][] filas = new String[porcentajes.length][porcentajes[0].length];
            for(int i = 0; i<porcentajes.length; i++){
                for(int j =0; j<porcentajes[i].length; j++){
                    filas[i][j] = String.valueOf(porcentajes[i][j]);
                }
            }

            //Se crea un modelo predeterminado de tabla con las filas y columnas
            DefaultTableModel model = new DefaultTableModel(filas, columnas);
            JTable table = new JTable(model);

            //Se añade la tabla a un ScrollPane (contenedor)
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(table.getPreferredSize());

            //Se genera un panel con el Header de la tabla y la propia tabla
            JPanel tablePanel = new JPanel();
            tablePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
            tablePanel.add(scrollPane, BorderLayout.CENTER);

            //Se genera una ventana emergente mostrando la tabla
            JOptionPane.showConfirmDialog(null, tablePanel, "Simulación de Montecarlo", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
        }
    }
}
