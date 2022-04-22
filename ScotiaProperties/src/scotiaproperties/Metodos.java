/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scotiaproperties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import javax.crypto.*;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class Metodos {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    String fechaHoy = dtf.format(LocalDateTime.now());
    
    //Ambiente de prueba
    //public String ruta = "C:/app/config.properties";
    //public String backupRuta = "C:/app/config_" + fechaHoy + ".properties";
    
    //Producción
    public String ruta = "ScotiaFid/cfg/config.properties";
    public String backupRuta = "ScotiaFid/cfg/config_" + fechaHoy + ".properties";
    
    public static beanDatosConfig encriptarDatosArchivo(String IP, String Usuario, String Password, String Puerto, String SSID)
    {
        AESProperties encriptador = new AESProperties();
        beanDatosConfig datosConfig = new beanDatosConfig();
        try
          {
            datosConfig.setIp(encriptador.encriptar(IP));
            datosConfig.setUsuario(encriptador.encriptar(Usuario));
            datosConfig.setPassword(encriptador.encriptar(Password));
            datosConfig.setPuerto(encriptador.encriptar(Puerto));
            datosConfig.setSsid(encriptador.encriptar(SSID));
            datosConfig.setIpcompleta(encriptador.encriptar("jdbc:db2://" + IP + ":" + Puerto + "/" + SSID));
          } 
        catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex)
          {
             System.out.println("Error al encriptar los datos: " + ex.getMessage());
          }
        return datosConfig;
    }
    
    public beanDatosConfig desencriptarDatosArchivo() throws FileNotFoundException, IOException
    {
        InputStream inputStream = new FileInputStream(ruta);
        Properties config = new Properties();
        AESProperties encriptador = new AESProperties();
        beanDatosConfig datosConfig = new beanDatosConfig();
        
        try
          {
            config.load(inputStream);
            datosConfig.setIpcompleta(encriptador.desencriptar(config.getProperty("db.url").replace("\\", "")));
            datosConfig.setUsuario(encriptador.desencriptar(config.getProperty("db.usr").replace("\\", "")));
            datosConfig.setPassword(encriptador.desencriptar(config.getProperty("db.pwd").replace("\\", "")));
            datosConfig.setIp(encriptador.desencriptar(config.getProperty("db.ip").replace("\\", "")));
            datosConfig.setSsid(encriptador.desencriptar(config.getProperty("db.ssid").replace("\\", "")));
            datosConfig.setPuerto(encriptador.desencriptar(config.getProperty("db.puerto").replace("\\", "")));
            
          } 
        catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex)
          {
             System.out.println("Error al desencriptar los datos: " + ex.getMessage());
          }
        return datosConfig;
    }
    
    public boolean crearArchivoProperties(String IP, String Usuario, String Password, String Puerto, String SSID)
    {
        Properties config = new Properties();
        OutputStream configOutput;
        
        beanDatosConfig datosConfig = new beanDatosConfig();
        datosConfig = encriptarDatosArchivo(IP, Usuario, Password, Puerto, SSID);
        boolean respuesta;
        respuesta = false;
        try
        {
            configOutput = new FileOutputStream(ruta);
            config.setProperty("db.url", datosConfig.getIpcompleta());
            config.setProperty("db.usr", datosConfig.getUsuario());
            config.setProperty("db.pwd", datosConfig.getPassword());
            config.setProperty("db.ip", datosConfig.getIp());
            config.setProperty("db.puerto", datosConfig.getPuerto());
            config.setProperty("db.ssid", datosConfig.getSsid());
            config.store(configOutput, "Archivo de conexion a la BD");
            respuesta = true;

        }
        catch(Exception ex)
        {
            System.out.println("Error guardando configuración: " + ex.getMessage());
        }
        return respuesta;
    }
    
    public String rutaArchivo(){
        return ruta;
    }
    
    public String buckupRutaArchivo(){
        return backupRuta;
    }
}
