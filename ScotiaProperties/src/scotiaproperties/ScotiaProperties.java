/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scotiaproperties;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Scanner;

/**
 *
 * @author delpl
 */
public class ScotiaProperties {
    
    public static void crearArchivo(){
        Metodos metodos = new Metodos();
        Scanner sc = new Scanner(System.in);
        String ruta = metodos.rutaArchivo();
        System.out.println("Ingrese los sig. datos para crear el archivo de configuración");
        String mensaje = "";
        System.out.println("Ingrese la IP de la BD");
        String sIP = sc.nextLine();
        System.out.println("Ingrese el Puerto de la BD");
        String sPuerto = sc.nextLine();
        System.out.println("Ingrese el SSID de la BD");
        String sSSID = sc.nextLine();
        System.out.println("Ingrese el Usuario de la BD");
        String sUsuario = sc.nextLine();
        System.out.println("Ingrese el Password de la BD");
        String sPassword = sc.nextLine();

        if (sIP.equals("") == true || sIP == "" || sIP == null)
          {
            mensaje += "La dirección IP de la base de datos se encuentra vacía \n";
          }
        if (sPuerto.equals("") == true || sPuerto == "" || sPuerto == null)
          {
            mensaje += "El puerto de la base de datos se encuentra vacío \n";
          }
        if (sSSID.equals("") == true || sSSID == "" || sSSID == null)
          {
            mensaje += "El SSID de la base de datos se encuentra vacío \n";
          }
        if (sUsuario.equals("") == true || sUsuario == "" || sUsuario == null)
          {
            mensaje += "El usuario de la base de datos se encuentra vacío \n";
          }
        if (sPassword.equals("") == true || sPassword == "" || sPassword == null)
          {
            mensaje += "La contraseña de la base de datos se encuentra vacía \n";
          }

        if (mensaje.equals("") == true)
          {
            boolean respuesta;
            respuesta = metodos.crearArchivoProperties(sIP, sUsuario, sPassword, sPuerto, sSSID);
            if (respuesta == true)
              {
                System.out.println("Archivo de propiedades guardado correctamente en: " + metodos.rutaArchivo());
              }
          }
        else
          {
            System.out.println("Se producieron los siguientes errores: \n" + mensaje);
          }
    }
    
    public static void backupArchivo(){
        Metodos metodos = new Metodos();
        String ruta = metodos.rutaArchivo();
        String bkRuta = metodos.buckupRutaArchivo();
        Properties propiedades = new Properties();
        Properties config = new Properties();
        OutputStream configOutput = null;
        
        try {
            propiedades.load(new FileReader(ruta));
            configOutput = new FileOutputStream(bkRuta);
            config.setProperty("db.url", propiedades.getProperty("db.url").replace("\\", ""));
            config.setProperty("db.usr", propiedades.getProperty("db.usr").replace("\\", ""));
            config.setProperty("db.pwd", propiedades.getProperty("db.pwd").replace("\\", ""));
            config.setProperty("db.ip", propiedades.getProperty("db.ip").replace("\\", ""));
            config.setProperty("db.ssid", propiedades.getProperty("db.ssid").replace("\\", ""));
            config.setProperty("db.puerto", propiedades.getProperty("db.puerto").replace("\\", ""));
            config.store(configOutput, "Archivo de conexion a la BD");
            
            System.out.println("Se ha creado una copia de seguridad del archivo de configuración con en la siguiente ruta: " + bkRuta);
        } catch (Exception e) {
            System.out.println("Error sl crear la copia de seguridad del archivo de configuración: " + e);
        }
    }
    
    public static void main(String[] args){
        System.out.println("Configuración del archivo para conexión de base de datos Scotia");
        Metodos metodos = new Metodos();
        AESProperties Encrypt = new AESProperties();
        Scanner sc = new Scanner(System.in);
        String ruta = metodos.rutaArchivo();
        
        File archivo = new File(ruta);
        
        if (archivo.exists())
          {
            System.out.println("Por favor ingrese la clave de desencriptación del archivo");
            String claveDesencriptacion = sc.nextLine();
            
            if (claveDesencriptacion.equals(Encrypt.llaveDesencriptada()))
              {
                try{
                    beanDatosConfig datosConexion = new beanDatosConfig();
                    datosConexion = metodos.desencriptarDatosArchivo();

                    /**Imprimimos los valores*/
                    System.out.println("Detalles del archivo \n");
                    System.out.println("IP: " + datosConexion.getIp()+ 
                            "\n" +"Usuario: " + datosConexion.getUsuario() +
                            "\n" +"Password: " + datosConexion.getPassword()+
                            "\n" +"Puerto: " + datosConexion.getPuerto()+
                            "\n" +"SSID: " + datosConexion.getSsid()
                    );
                    System.out.println("¿Desea editar el archivo? (Si, s, No, n)");
                    String respuesta = sc.nextLine();
                    if (respuesta.equals("Si") || respuesta.equals("s"))
                    {
                        backupArchivo();
                        crearArchivo();
                    }
                } catch (FileNotFoundException ex){
                    System.out.println("Error: " + ex.getMessage());
                } catch (IOException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
              }
            else
              {
                System.out.println("Lo sentimos la clave de desencriptación es incorrecta");
              }
          }
        else
          {
            crearArchivo();
          }

    }
}
