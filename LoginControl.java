package Controlador;

import Beans.usuarios;
import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginControl implements Serializable {

    public static Connection connection = null;

    public static Connection conexion() {

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, e);

        }

        try {

        connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1526:xe", "PJC", "PJC");
        
        } catch (SQLException e) {

            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, e);

        }

        if (connection != null) {
            //System.out.println("Conexion Exitosa!");
        } else {
            System.out.println("Fallo Conexion!");
        }

        return connection;
    }

    public static void cerrarConexion() {

        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static usuarios validarUsuario(String usuario, String clave) {
        conexion();
        boolean existe = false;
        usuarios us = null;
        String nom = "";
        try {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("select * from USUARIOS INNER JOIN COOPERATIVA on USUARIOS.USU_ORG = COOPERATIVA.COOP_ID WHERE USU_ESTADO = 1 AND USU_USER= '" + usuario + "' AND USU_PASS = '" + clave + "'");

            while (rs.next()) {
                if (rs.getString("USU_USER").equals(usuario) && rs.getString("USU_PASS").equals(clave)) {
                    us = new usuarios();
                    us.setNombres(rs.getString("USU_NOM"));
                    us.setApellidos(rs.getString("USU_APE"));
                    us.setUsername(usuario);
                    us.setUsuario_rol(rs.getString("USU_ROL"));
                    us.setSelectedCoop(rs.getString("COOP_ID"));
                    us.setUsuarioExpira(rs.getString("USU_EXP"));
                    us.setCodigo(rs.getString("USU_ID"));
                    us.setNombrecoop(rs.getString("COOP_NOM"));

                    break;
                    //return us;
                } else {
                    us = null;
                    break;

                }

                //System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            }
            System.out.println(nom);

            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return us;
    }

    public static usuarios validarUsuarioRena(String usuario, String clave) {
        conexion();
        boolean existe = false;
        usuarios us = null;
        try {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("select * from USUARIOS_RENAFIPSE INNER JOIN COOPERATIVA on USUARIOS_RENAFIPSE.USU_ORG = COOPERATIVA.COOP_ID WHERE USU_ESTADO = 1 AND USU_USER= '" + usuario + "' AND USU_PASS = '" + clave + "'");

            while (rs.next()) {
                if (rs.getString("USU_USER").equals(usuario) && rs.getString("USU_PASS").equals(clave)) {
                    us = new usuarios();
                    us.setNombres(rs.getString("USU_NOM"));
                    us.setApellidos(rs.getString("USU_APE"));
                    us.setUsername(usuario);
                    us.setUsuario_rol(rs.getString("USU_ROL"));
                    us.setSelectedCoop(rs.getString("COOP_ID"));
                    us.setUsuarioExpira(rs.getString("USU_EXP"));
                    us.setCodigo(rs.getString("USU_ID"));
                    break;
                    //return us;
                } else {
                    us = null;
                    break;

                }
                //System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return us;
    }

}
