/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scotiaproperties;

/**
 *
 * @author user
 */
public class beanDatosConfig {
    private String ip;
    private String ssid;
    private String puerto;
    private String ipcompleta;
    private String usuario;
    private String password;

    public beanDatosConfig() 
      {
          
      }    

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getIpcompleta() {
        return ipcompleta;
    }

    public void setIpcompleta(String ipcompleta) {
        this.ipcompleta = ipcompleta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
