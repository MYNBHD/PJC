package Beans;

import static Controlador.LoginControl.conexion;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author CodeSystems
 */
@ManagedBean(name = "peps")
@SessionScoped
public class PEPS implements Serializable {

    private static final long serialVersionUID = -2152389656664659476L;
    private String id;
    private String estado;
    private String identidad;
    private String nombres;
    private String puesto;
    private String entidad;
    private String codigoSocio;
    private String cedulaBuscar;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private PEPS selectedPEPS;
    private List<PEPS> listarPEPS = null;
    private LazyDataModel<PEPS> LazyPEPS;
    private List<PEPS> listarPEPSProcesadas = null;
    private LazyDataModel<PEPS> LazyPEPSProcesadas;
    private List<PEPS> listarfiltradas;
    private boolean leido = false;
    private String sc_id;
    private String fecha_carga;

    @PostConstruct
    public void init() {

    }

//Constructores de la clase
    public PEPS(String id, String identidad, String nombres, String puesto, String entidad, String estado) {

        this.id = id;
        this.identidad = identidad;
        this.nombres = nombres;
        this.puesto = puesto;
        this.entidad = entidad;
        this.estado = estado;

    }

    public PEPS(String id, String identidad, String nombres, String puesto, String entidad, String estado, String codigoSocio) {

        this.id = id;
        this.identidad = identidad;
        this.nombres = nombres;
        this.puesto = puesto;
        this.entidad = entidad;
        this.estado = estado;
        this.codigoSocio = codigoSocio;

    }

    public PEPS(String id, String identidad, String nombres, String puesto, String entidad, String estado, String codigoSocio, String sc_id, String fecha_carga) {

        this.id = id;
        this.identidad = identidad;
        this.nombres = nombres;
        this.puesto = puesto;
        this.entidad = entidad;
        this.estado = estado;
        this.codigoSocio = codigoSocio;
        this.sc_id = sc_id;
        this.fecha_carga = fecha_carga;

    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public PEPS() {
    }

    public String getFecha_carga() {
        return fecha_carga;
    }

    public void setFecha_carga(String fecha_carga) {
        this.fecha_carga = fecha_carga;
    }

    public String getSc_id() {
        return sc_id;
    }

    public void setSc_id(String sc_id) {
        this.sc_id = sc_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentidad() {
        return identidad;
    }

    public void setIdentidad(String identidad) {
        this.identidad = identidad;
    }

    public List<PEPS> getListarPEPSProcesadas() {
        return listarPEPSProcesadas;
    }

    public void setListarPEPSProcesadas(List<PEPS> listarPEPSProcesadas) {
        this.listarPEPSProcesadas = listarPEPSProcesadas;
    }

    public LazyDataModel<PEPS> getLazyPEPSProcesadas() {
        return LazyPEPSProcesadas;
    }

    public void setLazyPEPSProcesadas(LazyDataModel<PEPS> LazyPEPSProcesadas) {
        this.LazyPEPSProcesadas = LazyPEPSProcesadas;
    }

    public PEPS getSelectedPEPS() {
        return selectedPEPS;
    }

    public void setSelectedPEPS(PEPS selectedPEPS) {
        this.selectedPEPS = selectedPEPS;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoSocio() {
        return codigoSocio;
    }

    public void setCodigoSocio(String codigoSocio) {
        this.codigoSocio = codigoSocio;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getCedulaBuscar() {
        return cedulaBuscar;
    }

    public void setCedulaBuscar(String cedulaBuscar) {
        this.cedulaBuscar = cedulaBuscar;
    }

    public SimpleDateFormat getFormat() {
        return format;
    }

    public void setFormat(SimpleDateFormat format) {
        this.format = format;
    }

    public List<PEPS> getListarPEPS() {
        return listarPEPS;
    }

    public void setListarPEPS(List<PEPS> listarPEPS) {
        this.listarPEPS = listarPEPS;
    }

    public LazyDataModel<PEPS> getLazyPEPS() {
        return LazyPEPS;
    }

    public void setLazyPEPS(LazyDataModel<PEPS> LazyPEPS) {
        this.LazyPEPS = LazyPEPS;
    }

    public List<PEPS> getListarfiltradas() {
        return listarfiltradas;
    }

    public void setListarfiltradas(List<PEPS> listarfiltradas) {
        this.listarfiltradas = listarfiltradas;
    }

    public PEPS(List<PEPS> listarfiltradas) {
        this.listarfiltradas = listarfiltradas;
    }

    public String timestamp() {
        java.util.Date date = new java.util.Date();
        //System.out.println(new Timestamp(date.getTime()));
        return new Timestamp(date.getTime()) + "";
    }

//MÉTODO PARA LISTAR PEPS ENCONTRADOS SEGÚN EL CÓDIGO DE COOPERATIVA 
    public void listarPEPS() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();

        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String idcoop = (String) session.getAttribute("coopid");
        listarPEPS = new ArrayList<PEPS>();
        listarPEPSProcesadas = new ArrayList<PEPS>();

        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        
        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT *"
                    + "FROM SC_PEPS SCP \n"
                    + "INNER JOIN SOC_COOP SC ON SCP.SOC_COOP_ID=SC.SOC_COOP_ID\n"
                    + "INNER JOIN PEPS  P ON P.PEP_ID=SCP.PEP_ID\n"
                    + "INNER JOIN COOPERATIVA COOP ON COOP.COOP_ID= SC.COOP_ID WHERE SC.SOC_ESTADO=1 AND COOP.COOP_ID=" + idcoop + " ORDER BY P.PEP_NOMBRES");

         
            while (rs.next()) {

                if (rs.getString("SCP_ESTADO").equals("1")) {
                    listarPEPS.add(new PEPS(rs.getString("PEP_ID"), rs.getString("PEP_IDENTIDAD"), rs.getString("PEP_NOMBRES"), rs.getString("PEP_PUESTO"), rs.getString("PEP_ENTIDAD"), rs.getString("PEP_ESTADO"), rs.getString("SOC_CODIGO"), rs.getString("SCP_ID"), rs.getString("SCP_FECHA")));
                    fecha_carga=format.format(rs.getDate("PEP_FECHA_REG"));
                } else {

                    listarPEPSProcesadas.add(new PEPS(rs.getString("PEP_ID"), rs.getString("PEP_IDENTIDAD"), rs.getString("PEP_NOMBRES"), rs.getString("PEP_PUESTO"), rs.getString("PEP_ENTIDAD"), rs.getString("PEP_ESTADO"), rs.getString("SOC_CODIGO"), rs.getString("SCP_ID"), rs.getString("SCP_FECHA")));
                }

            }
            LazyPEPS = new LazyPEPSTableModel(listarPEPS);
            LazyPEPSProcesadas = new LazyPEPSTableModel(listarPEPSProcesadas);

        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarPEPS: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarPEPS: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarPEPS: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarPEPS: " + ex.getMessage());
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //MÉTODO PARA  BUSCAR PEPS POR NÚMERO DE CEDULA EN SOCIOS DE CADA COOP
    public void BusquedaPEPSCedula() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        listarPEPS = new ArrayList<PEPS>();
        listarPEPSProcesadas = new ArrayList<PEPS>();

        String idcoop = (String) session.getAttribute("coopid");

        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();

            if (cedulaBuscar.equals("")) {
                ResultSet rs = statement.executeQuery("SELECT *"
                        + "FROM SC_PEPS SCP \n"
                        + "INNER JOIN SOC_COOP SC ON SCP. SOC_COOP_ID=SC.SOC_COOP_ID\n"
                        + "INNER JOIN PEPS  P ON P.PEP_ID=SCP.PEP_ID\n"
                        + "INNER JOIN COOPERATIVA COOP ON COOP.COOP_ID= SC.COOP_ID \n"
                        + "WHERE SC.SOC_ESTADO=1 AND COOP.COOP_ID=" + idcoop + " ORDER BY P.PEP_NOMBRES");

                while (rs.next()) {

                    if (rs.getString("SCP_ESTADO").equals("1")) {
                        listarPEPS.add(new PEPS(rs.getString("PEP_ID"), rs.getString("PEP_IDENTIDAD"), rs.getString("PEP_NOMBRES"), rs.getString("PEP_PUESTO"), rs.getString("PEP_ENTIDAD"), rs.getString("PEP_ESTADO"), rs.getString("SOC_CODIGO"), rs.getString("SCP_ID"), rs.getString("SCP_FECHA")));
                    } else {

                        listarPEPSProcesadas.add(new PEPS(rs.getString("PEP_ID"), rs.getString("PEP_IDENTIDAD"), rs.getString("PEP_NOMBRES"), rs.getString("PEP_PUESTO"), rs.getString("PEP_ENTIDAD"), rs.getString("PEP_ESTADO"), rs.getString("SOC_CODIGO"), rs.getString("SCP_ID"), rs.getString("SCP_FECHA")));
                    }

                }

            } else {

                ResultSet rs = statement.executeQuery("SELECT *"
                        + "FROM SC_PEPS SCP \n"
                        + "INNER JOIN SOC_COOP SC ON SCP. SOC_COOP_ID=SC.SOC_COOP_ID\n"
                        + "INNER JOIN PEPS  P ON P.PEP_ID=SCP.PEP_ID\n"
                        + "INNER JOIN COOPERATIVA COOP ON COOP.COOP_ID= SC.COOP_ID \n"
                        + "WHERE P.PEP_IDENTIDAD='" + cedulaBuscar + "' AND SC.SOC_ESTADO=1 AND COOP.COOP_ID=" + idcoop + " ORDER BY P.PEP_NOMBRES");

                while (rs.next()) {
                    if (rs.getString("SCP_ESTADO").equals("1")) {
                        listarPEPS.add(new PEPS(rs.getString("PEP_ID"), rs.getString("PEP_IDENTIDAD"), rs.getString("PEP_NOMBRES"), rs.getString("PEP_PUESTO"), rs.getString("PEP_ENTIDAD"), rs.getString("PEP_ESTADO"), rs.getString("SOC_CODIGO"), rs.getString("SCP_ID"), rs.getString("SCP_FECHA")));
                    } else {

                        listarPEPSProcesadas.add(new PEPS(rs.getString("PEP_ID"), rs.getString("PEP_IDENTIDAD"), rs.getString("PEP_NOMBRES"), rs.getString("PEP_PUESTO"), rs.getString("PEP_ENTIDAD"), rs.getString("PEP_ESTADO"), rs.getString("SOC_CODIGO"), rs.getString("SCP_ID"), rs.getString("SCP_FECHA")));
                    }

                }
            }

            LazyPEPS = new LazyPEPSTableModel(listarPEPS);
            LazyPEPSProcesadas = new LazyPEPSTableModel(listarPEPSProcesadas);

            //connection.close();
        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarPEPS: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarPEPS: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarPEPS: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarPEPS: " + ex.getMessage());
                    Logger.getLogger(PEPS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //MÉTODO PARA  BUSCAR PEPS POR NÚMERO DE CEDULA EN TODA LA BBDD
    public void BusquedaPEPS() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        listarPEPS = new ArrayList<PEPS>();

        String idcoop = (String) session.getAttribute("coopid");

        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT P.PEP_ID, P.PEP_IDENTIDAD, P.PEP_NOMBRES, P.PEP_PUESTO, P.PEP_ENTIDAD, P.PEP_ESTADO, SC.SOC_CODIGO FROM PEPS P INNER JOIN SOCIOS S ON TRIM(S.SOC_IDENT)=TRIM(P.PEP_IDENTIDAD) OR TRIM(S.SOC_APE ||' '||S.SOC_NOM) = (P.PEP_NOMBRES)  INNER JOIN SOC_COOP SC ON SC.SOC_ID=S.SOC_ID INNER JOIN COOPERATIVA COOP ON COOP.COOP_ID= SC.COOP_ID WHERE P.PEP_IDENTIDAD='" + cedulaBuscar + "' ORDER BY P.PEP_NOMBRES ");

            while (rs.next()) {

                listarPEPS.add(new PEPS(rs.getString("PEP_ID"), rs.getString("PEP_IDENTIDAD"), rs.getString("PEP_NOMBRES"), rs.getString("PEP_PUESTO"), rs.getString("PEP_ENTIDAD"), rs.getString("PEP_ESTADO"), rs.getString("SOC_CODIGO")));

            }

            LazyPEPS = new LazyPEPSTableModel(listarPEPS);

            //connection.close();
        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarPEPS: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarPEPS: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarPEPS: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarPEPS: " + ex.getMessage());
                    Logger.getLogger(PEPS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void leerpeps() {
        leido = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "UPDATE SC_PEPS SET SCP_ESTADO = ? WHERE SCP_ID = ?";

        try {
            System.out.println("Revisado: " + this.selectedPEPS.sc_id);
            dbConnection = conexion();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setInt(1, 2);
            preparedStatement.setString(2, this.selectedPEPS.sc_id);

            preparedStatement.executeUpdate();
            this.LazyPEPS = null;
            this.LazyPEPSProcesadas = null;

            if (LazyPEPS == null) {

                this.listarPEPS();
            }
            //System.out.println("Coactiva " + this.selectedCoactiva.rev_id + " Leida");
            leido = true;

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Realizar proceso de debida diligencia con el socio", this.selectedPEPS.identidad + " " + this.selectedPEPS.nombres);

        } catch (SQLException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No se pudo Revisar");
            leido = false;
            System.out.println(e.getMessage());
            e.printStackTrace();

        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    System.out.println("Metodo LeerPEPS: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException ex) {
                    System.out.println("Metodo LeerPEPS: " + ex.getMessage());
                }
            }
        }

        requestContext.addCallbackParam("estaGuardado", leido);
        requestContext.update("up:growl");
        requestContext.update("up:tab1:tablapeps");
        requestContext.update("up:tab1:tablapepsprocesadas");

        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    //METODO PARA CAMBIAR DE ESTADO UNA COACTIVA A NO REVISADA
    public void reversarPeps() {
        leido = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "UPDATE SC_PEPS SET SCP_ESTADO= ? WHERE SCP_ID = ?";

        try {
            System.out.println("Reversado: " + this.selectedPEPS.sc_id);
            dbConnection = conexion();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, this.selectedPEPS.sc_id);

            preparedStatement.executeUpdate();

            //System.out.println("Coactiva " + this.selectedCoactiva.rev_id + " Leida");
            leido = true;

            this.LazyPEPS = null;
            this.LazyPEPSProcesadas = null;

            if (LazyPEPS == null) {

                this.listarPEPS();
            }

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Reversado", this.selectedPEPS.identidad + " " + this.selectedPEPS.nombres);

        } catch (SQLException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No se pudo leer");
            leido = false;
            System.out.println("Metodo reversarPEPS: " + e.getMessage());
            //e.printStackTrace();

        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    System.out.println("Metodo reversarPEPS: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException ex) {
                    System.out.println("Metodo reversarPEPS: " + ex.getMessage());
                }
            }
        }

        requestContext.addCallbackParam("estaGuardado", leido);
        requestContext.update("up:growl");
        requestContext.update("up:tab1:tablapeps");
        requestContext.update("up:tab1:tablapepsprocesadas");

        FacesContext.getCurrentInstance().addMessage(null, message);

    }

}
