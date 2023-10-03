package alquilafacil.model;


import alquilafacil.exception.*;
import javafx.scene.control.Alert;
import lombok.Getter;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Getter
public class AlquilaFacil {

    private static final Logger LOGGER = Logger.getLogger(AlquilaFacil.class.getName());

    public static ArrayList<String> facturas=new ArrayList<String>();

    public static ArrayList<Cliente> clientesRegistrados = new ArrayList<Cliente>();

    public static ArrayList<Vehiculo> vehiculosRegistrados = new ArrayList<Vehiculo>();

    public static ArrayList<Alquiler> vehiculosAlquilados=new ArrayList<Alquiler>();
    
    private static AlquilaFacil alquilafacil;

    private static final String[] marcas={"chevrolet","nissan","ferrari"};

    private static final String[] modelos={"2000","2001","2002","2003","2004","2005"};

    //Metodos clientes

    public void buscarCliente(String cedula)  {
        for (int i = 0; i < clientesRegistrados.size(); i++) {
            if (cedula.equals(clientesRegistrados.get(i).getCedula()))
            {
                throw new ClienteRegistrado("Este usuario ya se encuentra registrado");
            }
        }
    }

    public void validarCedula(String cedula)  {

        boolean validacionCaracteres = validarCaracteres(cedula);

        if (cedula.isEmpty())
        {
            throw new CedulaNovalida("la cedula no puede estar vacia");
        }
        else if (cedula == null)
        {
            throw new CedulaNovalida("la cedula no puede ser null");
        }
        else if (cedula.length() != 8)
        {
            throw new CedulaNovalida("la cedula debe tener 8 caracteres exclusivamente");
        }
        else if (validacionCaracteres == true)
        {
            throw new CedulaNovalida("la cedula solo puede tener numeros");
        }
    }

    public boolean validarCaracteres(String cualquiera) {
        boolean tieneCaracteres = false;
        for (int i = 0; i < cualquiera.length(); i++) {
            if (cualquiera.charAt(i) < 48 || cualquiera.charAt(i) > 57)
            {
                tieneCaracteres = true;
            }
        }
        return tieneCaracteres;
    }

    public void validarTelefono(String telefono) {
        if (telefono.length() != 10)
        {
            throw new TelefonoNovalido("su telefono debe contener 10 caracteres exclusivamente");
        }
    }

    public void validarVacio(String cualquiera, String msg)  {
        if (cualquiera.isEmpty() || cualquiera == null)
        {
            throw new Vacio(msg);
        }

    }
    public void registrarCliente(String nombre, String cedula, String correo, String direccion, String ciudad, String telefono)  {
        validarCedula(cedula);
        validarVacio(telefono, "debe ingresar algun telefono");
        validarTelefono(telefono);
        validarVacio(nombre, "debe ingresar algun nombre");
        validarVacio(correo, "debe ingresar algun email");
        validarVacio(direccion, "debe ingresar alguna direccion");
        validarVacio(ciudad, "debe ingresar algun lugar de residencia");
        buscarCliente(cedula);
        Cliente cliente = new Cliente.ClienteBuilder().cedula(cedula).nombre(nombre).correo(correo).direccion(direccion).ciudad(ciudad).telefono(telefono).build();
        clientesRegistrados.add(cliente);

    }



    private AlquilaFacil(){

        try {
            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter( new SimpleFormatter());
            LOGGER.addHandler(fh);
        }catch (IOException e){
            LOGGER.log( Level.SEVERE, e.getMessage() );
        }

        LOGGER.log( Level.INFO, "Se crea una nueva instancia de AlquilaFacil" );
    }

    /**
     * Método que se usará en las otras clases que requieran una instancia de esta clase
     * @return Instancia del objeto AlquilaFacil
     */
    public static AlquilaFacil getInstance(){
        if(alquilafacil == null){
            alquilafacil = new AlquilaFacil();
        }

        return alquilafacil;
    }

    //Metodos vehiculos

    public void buscarVehiculo(Vehiculo vehiculo)  {
        for(int i=0;i<vehiculosRegistrados.size();i++)
        {

            if(vehiculo.equals(vehiculosRegistrados.get(i)))
            {
                    throw new VehiculoException("este vehiculo ya se encuentra registrado");
            }
        }
    }

    public void comprobarCaracteresPlaca(String primeraMitad,String segundaMitad) {
        for(int i=0;i<3;i++)
        {
            if(primeraMitad.charAt(i)<'a'||primeraMitad.charAt(i)>'z')
            {
                throw new PlacaNovalida("los primeros 3 digitos deben contener exclusivamente letras");
            }
            if(segundaMitad.charAt(i)<'0'||segundaMitad.charAt(i)>'9')
            {
                throw new PlacaNovalida("los 3 ultimos digitos deben tener unicamente numeros ");
            }
        }
    }
    public void validarPlaca(String placa) {
        if(placa.length()!=6)
        {
            throw new PlacaNovalida("su placa debe tener 6 digitos");
        }


        String primeraMitad=placa.substring(0,3).toLowerCase();
        String segundaMitad=placa.substring(3,6);

        System.out.println(primeraMitad+"  "+segundaMitad);
        comprobarCaracteresPlaca(primeraMitad,segundaMitad);

    }

    public void validarMarca(String marca)  {
        boolean apoyo=false;
        for(int i=0;i<marcas.length;i++)
        {
            if(marca.equals(marcas[i]))
            {
                apoyo=true;
                i=marcas.length;
            }
        }

        if(apoyo==false)
        {
            throw new MarcaYmodeloNovalida("ingrese una marca que manejemos en nuestra empresa. Manejamos, nissan, chvrolet y ferrari");
        }
    }

    public void validarModelo(String modelo)  {
        boolean apoyo=false;
        for(int i=0;i<modelos.length;i++)
        {
            if(modelo.equals(modelos[i]))
            {
                apoyo=true;
                i=modelos.length;
            }
        }

        if(apoyo==false)
        {
            throw new MarcaYmodeloNovalida("ingrese un modelo valido. Solo admitimos modelos de 2000 a 2005");
        }
    }

    public void verificarNumerosNegativos(double precioDia,int kilometraje,int numPuertas,String msg)  {
        if(precioDia<0.0||kilometraje<0||numPuertas<0)
        {
            throw new NoNumerosNegativos(msg);
        }
    }


    public void registrarVehiculo(String placa,String referencia,String marca,String modelo,String foto,String kilometraje,String precioDia,boolean esAutomatico,String numPuertas) {
        validarVacio(placa,"su vehiculo debe tener placa");
        validarVacio(referencia,"su vehiculo debe tener un nombre");
        validarVacio(marca,"su vehiculo debe tener una marca asociada");
        validarVacio(modelo,"su vehiculo debe tener un modelo ");
        validarVacio(foto,"debe ingresar fotos del vehiculo");
        validarVacio(kilometraje,"su vehiculo debe tener kilometraje");
        validarVacio(precioDia,"su vehiculo debe tener precio por dia");
        validarVacio(numPuertas,"debe suministrar la cantidad de puertas que tiene su vehiculo");
        validarPlaca(placa);
        validarMarca(marca);
        validarModelo(modelo);
        verificarNumerosNegativos(Double.parseDouble(precioDia),Integer.parseInt(kilometraje),Integer.parseInt(numPuertas),"profavor el precio, el kilometraje, y la cantidad de puertas son valores que no pueden llegar a ser negativos");
        Vehiculo vehiculo=new Vehiculo.VehiculoBuilder().placa(placa).referencia(referencia).marca(marca).modelo(modelo).foto(foto).kilometraje(Integer.parseInt(kilometraje)).precioDia(Double.parseDouble(precioDia)).esAutomatico(esAutomatico).numPuertas(Integer.parseInt(numPuertas)).ocupado(false).build();
        buscarVehiculo(vehiculo);
        vehiculosRegistrados.add(vehiculo);

    }

    //Metodos alquiler

    public void alquilarVheiculo(LocalDate fechaFin,LocalDate fechaInicio,Vehiculo vehiculo,String cedula)  {
        Cliente cliente=buscarCedulaAlqui(cedula);
        String factura="";
        if(cliente!=null) {
            boolean fechaMala = validarFechas(fechaFin, fechaInicio);
            boolean vehiculoOcupado = vehiculo.isOcupado();
            if (fechaMala == false && vehiculoOcupado == false)
            {
                Alquiler alquilado= new Alquiler.AlquilerBuilder().
                        cliente(cliente).
                        vehiculo(vehiculo).
                        fechaRegistro(LocalDateTime.now()).
                        fechaInicio(fechaInicio).fechaFin(fechaFin).
                        valorTotal(fechaInicio.until(fechaFin, ChronoUnit.DAYS) * vehiculo.getPrecioDia()).
                        build();
                vehiculo.setOcupado(true);
                vehiculosAlquilados.add(alquilado);
                factura+="Nombre del cliente "+cliente.getNombre()+
                        "Cedula del cliente "+
                        cliente.getCedula()+
                        "Nombre del vehiculo "+
                        vehiculo.getReferencia()+
                        "Modelo del vehiculo "+
                        vehiculo.getModelo()+
                        "Marca del vehiculo "+
                        vehiculo.getMarca()+
                        "Fecha de inicio del alquiler "
                        +fechaInicio+
                        "Fecha de entrega del vehciulo "
                        +fechaFin
                        +"Fecha del registro del alquiler "
                        +fechaInicio.until(fechaFin, ChronoUnit.DAYS);
                facturas.add(factura);

                if (fechaMala == true)
                {
                    throw new MalasFechas("debe ingresar una fecha valida");
                }

                if (vehiculoOcupado == true)
                {
                    throw new VehiculoException("este vehiculo ya se encuentra alquilado ");
                }
            }
            else
            {
                throw new ClienteRegistrado("usted no se encuentra registrado");
            }
        }
    }

    public boolean validarFechas(LocalDate fechaFin,LocalDate fechaInicio) {
        boolean fechaMala=false;
        if(fechaInicio.isAfter(fechaFin))
        {
            fechaMala=true;
        }
        return fechaMala;
    }

    public Cliente buscarCedulaAlqui(String cedula) {
        Cliente cliente=null;
        for(int i=0;i<clientesRegistrados.size();i++)
        {
            if(cedula.equals(clientesRegistrados.get(i).getCedula()))
            {
                cliente=clientesRegistrados.get(i);
            }
        }
        return cliente;
    }

    public String determinarVehimasAlquilado() {
        String vehiculoMas="";
        String apoyo="";
        int centinela=0;
        int vehiculoAlquiladoTotal=0;
        for(int i=0;i<marcas.length;i++)
        {
            for(int j=0;j<vehiculosRegistrados.size();j++)
            {

            }
        }
        return vehiculoMas;
    }

    public Cliente obtenerCliente(String cedula){

        for(Cliente c : clientesRegistrados){
            if(c.getCedula().equals(cedula)){
                return c;
            }
        }

        return null;
    }

    public static void mensajeAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


}