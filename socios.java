package Beans;

import static Controlador.LoginControl.conexion;
import static Security.ValidarCedula.validacionCedula;
import static Security.ValidarRucEP.validaRucEP;
import static Security.ValidarRucSociedades.validacionRUC;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

@ManagedBean
@SessionScoped
public class socios implements Serializable {

    private String username;
    private String password;
    private String identificacion;
    private String tipoDocumento;
    private String Nombres;
    private String Apellidos;
    private String direccion;
    private String telefono;
    private String codigo;
    private String descripcion;
    private String selectedCoop;
    private Cooperativas coop;
    private String nombrecoop;
    private socios selectedsocio;
    private List<socios> selectedsocios;
    private List<socios> listarsocios;
    private List<socios> listarsociosfiltrados;
    private Date fechaexpiracion;
    private String fechaactual;
    private String codSocio;
    private boolean guardado = false;
    private int progreso = 0;
    private int totalsocios = 1;
    private int contsocio = 0;
    private String busqcedula;
    private String busqcodigo;
    private String totSocios;
    private int ing_soc = 0;
    private String mensaje;

    public int getContsocio() {
        return contsocio;
    }

    public void setContsocio(int contsocio) {
        this.contsocio = contsocio;
    }

    public Integer getIng_soc() {
        return ing_soc;
    }

    public void setIng_soc(Integer ing_soc) {
        this.ing_soc = ing_soc;
    }

    private LazyDataModel<socios> LazySociosModel;

//    public socios(String identificacion, String Nombres, String Apellidos, String coop) {
//        this.identificacion = identificacion;
//        this.Nombres = Nombres;
//        this.Apellidos = Apellidos;
//        this.nombrecoop = coop;
//    }
    public socios() {

    }

    @PostConstruct
    public void init() {
        this.listarSocios();
    }
///////////////constructores ///////////////

    private void criarMensagem(String texto) {

        FacesMessage msg = new FacesMessage(texto);
        FacesContext.getCurrentInstance().addMessage("", msg);
    }

    public socios(List<socios> listarsociosfiltrados) {
        this.listarsociosfiltrados = listarsociosfiltrados;
    }

    public socios(String codigo, String identificacion, String Nombres, String Apellidos, String codSocio) {
        this.identificacion = identificacion;
        this.Nombres = Nombres;
        this.Apellidos = Apellidos;
        this.codigo = codigo;
        this.codSocio = codSocio;
    }

    public socios(String identificacion, String Nombres, String Apellidos, String codSocio) {
        this.identificacion = identificacion;
        this.Nombres = Nombres;
        this.Apellidos = Apellidos;
        this.codSocio = codSocio;
    }

    public socios(String identificacion, String Nombres, String Apellidos, String direccion, String codigo, String descripcion, String nombrecoop) {
        this.identificacion = identificacion;
        this.Nombres = Nombres;
        this.Apellidos = Apellidos;
        this.direccion = direccion;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.nombrecoop = nombrecoop;
    }

    public socios(String codigo, String identificacion, String Nombres, String Apellidos, String direccion, String nombrecoop, String descripcion, String username, String password) {
        this.username = username;
        this.password = password;
        this.identificacion = identificacion;
        this.Nombres = Nombres;
        this.Apellidos = Apellidos;
        this.direccion = direccion;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.nombrecoop = nombrecoop;
    }

    public socios(String codigo, String identificacion, String Nombres, String Apellidos, String direccion, String nombrecoop, String descripcion, String username, String password, String codSocio) {
        this.username = username;
        this.password = password;
        this.identificacion = identificacion;
        this.Nombres = Nombres;
        this.Apellidos = Apellidos;
        this.direccion = direccion;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.nombrecoop = nombrecoop;
        this.codSocio = codSocio;
    }

    public void reset() {
        limpiar();
        RequestContext.getCurrentInstance().reset(":FormUser");
    }
/////////////propiedades///////////////

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTotSocios() {
        return totSocios;
    }

    public void setTotSocios(String totSocios) {
        this.totSocios = totSocios;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String Nombres) {

        this.Nombres = Nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Cooperativas getCoop() {
        return coop;
    }

    public void setCoop(Cooperativas coop) {
        this.coop = coop;
    }

    public String getSelectedCoop() {
        return selectedCoop;
    }

    public void setSelectedCoop(String selectedCoop) {
        this.selectedCoop = selectedCoop;
    }

    public List<socios> getListarsocios() {

        return listarsocios;
    }

    public void setListarsocios(List<socios> listarsocios) {
        this.listarsocios = listarsocios;
    }

    public List<socios> getListarsociosfiltrados() {

        return listarsociosfiltrados;
    }

    public void setListarsociosfiltrados(List<socios> listarsociosfiltrados) {
        this.listarsociosfiltrados = listarsociosfiltrados;
    }

    public String getNombrecoop() {
        return nombrecoop;
    }

    public void setNombrecoop(String nombrecoop) {
        this.nombrecoop = nombrecoop;
    }

    public socios getSelectedsocio() {
        //System.out.println("GET "+selectedsocio.codSocio);
        return selectedsocio;
    }

    public void setSelectedsocio(socios selectedsocio) {
        this.selectedsocio = selectedsocio;
    }

    public List<socios> getSelectedsocios() {
        return selectedsocios;
    }

    public void setSelectedsocios(List<socios> selectedsocios) {
        this.selectedsocios = selectedsocios;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaexpiracion() {
        return fechaexpiracion;
    }

    public void setFechaexpiracion(Date fechaexpiracion) {
        this.fechaexpiracion = fechaexpiracion;
    }

    public String getFechaactual() {
        SimpleDateFormat format = new SimpleDateFormat("d/M/yy");
        java.util.Date fecha = new java.util.Date();

        return format.format(fecha).trim();

    }

    public void setFechaactual(String fechaactual) {
        this.fechaactual = fechaactual;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public String getCodSocio() {
        return codSocio;
    }

    public void setCodSocio(String codSocio) {
        this.codSocio = codSocio;
    }
    ///////////METODOS/////////////////

    public void limpiar() {
        this.identificacion = "";
        this.codigo = "";
        this.codSocio = "";
        this.Nombres = "";
        this.Apellidos = "";
        //this.tipoDocumento = "";

    }
//INGRESAR UN SOCIO

    public boolean insertSocios() {

        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        FacesMessage message = null;
        boolean save = false;

        String insertTableSQL = "INSERT INTO SOCIOS"
                + "(SOC_ID,SOC_IDENT,SOC_TIPO,SOC_NOM,SOC_APE) VALUES"
                + "(SEC_ID_SOC.NextVal,?,?,?,?)";

        try {
            dbConnection = conexion();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setString(1, this.identificacion);
            preparedStatement.setString(2, this.tipoDocumento);
            preparedStatement.setString(3, this.Nombres.toUpperCase());
            preparedStatement.setString(4, this.Apellidos.toUpperCase());

            preparedStatement.executeUpdate();
            save = true;
            //System.out.println("Registro Insertado en Socios!");
            guardado = true;

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", this.identificacion + " " + this.Nombres + " " + this.Apellidos);

        } catch (SQLException e) {
            save = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No se Guardo el Registro");
            System.out.println(e.getMessage());

        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(socios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(socios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return save;

    }

    //iNGRESAR SOCIO CON PROCEDIMIENTO
    public boolean savesocio() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.getMaxInactiveInterval();
        String idcoop = (String) session.getAttribute("coopid");

        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        FacesMessage message = null;

        boolean save = false;

        try {
            dbConnection = conexion();
            preparedStatement = dbConnection.prepareStatement("{call INSERTARSOCIO(?,?,?,?,?,?)}");

            preparedStatement.setString(1, this.identificacion);
            preparedStatement.setString(2, this.tipoDocumento);
            preparedStatement.setString(3, idcoop);
            preparedStatement.setString(4, this.codSocio);
            preparedStatement.setString(5, this.Nombres.toUpperCase());
            preparedStatement.setString(6, this.Apellidos.toUpperCase());

            preparedStatement.executeUpdate();

            System.out.println("Registro Insertado en Socios! " + this.codSocio);

            save = true;
        } catch (SQLException e) {
            save = false;
            System.out.println(e.getMessage());

        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(socios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(socios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return save;

    }

    public void guardarsocio(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String idcoop = (String) session.getAttribute("coopid");
        FacesMessage message = null;
        //if(){}
        if ((this.tipoDocumento.equals("1") && this.identificacion.length() == 10) || (this.tipoDocumento.equals("2") && this.identificacion.length() == 13)) {
            if (validacionCedula(this.identificacion) || validaRucEP(this.identificacion) || validacionRUC(this.identificacion)) {
                if (!ExisteCodigoSocio()) {
                    if (ExisteEnCoop()) {
                        message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "El Socio ya esta Registrado");
                        guardado = true;
                    } else if (savesocio()) {
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", this.identificacion + " " + this.Nombres + " " + this.Apellidos);
                        guardado = true;
                        this.listarSocios();
                        limpiar();
                    } else {
                        message = new FacesMessage(FacesMessage.SEVERITY_WARN, "No se Guardo", this.identificacion + " " + this.Nombres + " " + this.Apellidos);
                        guardado = false;
                    }
                } else {
                    message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Codigo Usuario en Uso");
                    guardado = false;
                    limpiar();
                }
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Cedula/Ruc no Valida " + this.identificacion);
                guardado = false;

            }
        } else if (!ExisteCodigoSocio()) {
            if (ExisteEnCoop()) {
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "El Socio ya esta Registrado");
                guardado = true;
            } else if (savesocio()) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Agregado", this.identificacion + " " + this.Nombres + " " + this.Apellidos);
                guardado = true;
                this.listarSocios();
                limpiar();
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "No se Guardo", this.identificacion + " " + this.Nombres + " " + this.Apellidos);
                guardado = false;
            }
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Codigo Usuario en Uso");
            guardado = false;
            limpiar();
        }
        requestContext.addCallbackParam("estaGuardado", guardado);
        requestContext.update("FormTabla:tabsocios");
        requestContext.update("FormTabla:growl");
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void edit(ActionEvent event) {
        boolean save = false;
        System.out.println(this.selectedsocio.codigo);

        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "UPDATE SOCIOS SET SOC_NOM = ?, SOC_APE = ? WHERE SOC_ID = ?";

        try {
            dbConnection = conexion();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setString(1, this.selectedsocio.Nombres.toUpperCase());
            preparedStatement.setString(2, this.selectedsocio.Apellidos.toUpperCase());
            preparedStatement.setInt(3, Integer.parseInt(this.selectedsocio.codigo));

            System.out.println("Edit " + preparedStatement.executeUpdate());

            System.out.println("Registro Modificado en Socios!" + this.selectedsocio.codigo + " " + this.selectedsocio.Nombres.toUpperCase() + " " + this.selectedsocio.Apellidos.toUpperCase());

            save = true;

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificado Correctamente", this.selectedsocio.identificacion + " " + this.selectedsocio.Nombres.toUpperCase() + " " + this.selectedsocio.Apellidos.toUpperCase());

        } catch (SQLException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No se Guardo el Registro");
            save = false;
            System.out.println(e.getMessage());
            e.printStackTrace();

        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(socios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(socios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        requestContext.addCallbackParam("editado", save);
        requestContext.update("FormTabla:tabsocios");
        requestContext.update("FormTabla:growl");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void eliminar(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String idcoop = (String) session.getAttribute("coopid");
        guardado = false;
        //System.out.println("Socios Eliminado "+this.selectedsocio.codigo.trim());

        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "UPDATE SOC_COOP SET SOC_ESTADO = ? WHERE SOC_ID = ? AND COOP_ID = ?";

        try {
            dbConnection = conexion();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, Integer.parseInt(this.selectedsocio.codigo.trim()));
            preparedStatement.setInt(3, Integer.parseInt(idcoop));

            System.out.println("Socio codigo: " + this.selectedsocio.codigo.trim() + " Modificado");

            preparedStatement.executeUpdate();
            System.out.println("Registro Eliminado en Socios!");
            limpiar();
            guardado = true;

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado Correctamente", this.selectedsocio.identificacion + " " + this.selectedsocio.Nombres.toUpperCase() + " " + this.selectedsocio.Apellidos.toUpperCase());

        } catch (SQLException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No se Elimino el Registro");
            guardado = false;
            System.out.println(e.getMessage());

        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(socios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(socios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        this.listarSocios();
        requestContext.addCallbackParam("estaEliminado", guardado);
        requestContext.update("FormTabla:tabsocios");
        requestContext.update("FormTabla:growl");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public boolean existesocio() {

//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
//        String idcoop = (String) session.getAttribute("coopid");
        boolean existe = false;
        Connection connection = conexion();

        try {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select SOCIOS.SOC_ID,SOCIOS.SOC_IDENT,SOCIOS.SOC_NOM,SOCIOS.SOC_APE, SOCIOS.SOC_TIPO from SOCIOS WHERE SOCIOS.SOC_IDENT = '" + this.identificacion + "'");

            while (rs.next()) {
                if (rs.getString("SOC_IDENT").equals(this.identificacion)) {
                    existe = true;
                    this.codigo = rs.getString("SOC_ID");
                    this.Nombres = rs.getString("SOC_NOM");
                    this.Apellidos = rs.getString("SOC_APE");
                    this.identificacion = rs.getString("SOC_IDENT");
                    this.tipoDocumento = rs.getString("SOC_TIPO");
                    this.codSocio = "";

                    break;
                } else {
                    existe = false;
                    break;
                }
            }
            System.out.println("Metodo existesocio: " + this.identificacion);
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(socios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;

    }

////VERIFICA SI EXISTE UN SOCIO CON CEDULA O RUC XXXXXXXXXXXX YA EXISTE REGISTRADO EN LA COOPERATIVA
    public boolean ExisteEnCoop() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String idcoop = (String) session.getAttribute("coopid");
        boolean existe = false;
        Connection connection = conexion();

        try {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select SOCIOS.SOC_ID,SOCIOS.SOC_IDENT,SOCIOS.SOC_NOM,SOCIOS.SOC_APE, SOCIOS.SOC_TIPO,SOC_COOP.SOC_CODIGO,COOPERATIVA.COOP_ID from SOCIOS INNER JOIN SOC_COOP ON SOCIOS.SOC_ID = SOC_COOP.SOC_ID INNER JOIN COOPERATIVA ON COOPERATIVA.COOP_ID = SOC_COOP.COOP_ID WHERE SOC_COOP.SOC_ESTADO = 1 AND SOC_COOP.SOC_ID = " + this.codigo + " AND SOC_COOP.COOP_ID = " + idcoop);

            while (rs.next()) {
                if (rs.getInt("SOC_ID") == Integer.parseInt(this.codigo) && rs.getInt("COOP_ID") == Integer.parseInt(idcoop)) {
                    existe = true;
                    break;
                } else {
                    existe = false;
                    break;
                }
            }
            System.out.println("Metodo existecodsociocoop: " + this.codigo);
            connection.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            //Logger.getLogger(socios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;

    }

//// VERIFICA SI EXISTE EL CODIGO DEL SOCIO QUE SE ESTA GUARDANDO PARA NO REDUNDAR DATOS
    public boolean ExisteCodigoSocio() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        //Map params = facesContext.getExternalContext().getRequestParameterMap();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String idcoop = (String) session.getAttribute("coopid");
        boolean existe = false;
        Connection connection = conexion();

        try {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select SOCIOS.SOC_ID,SOCIOS.SOC_IDENT,SOCIOS.SOC_NOM,SOCIOS.SOC_APE, SOCIOS.SOC_TIPO,SOC_COOP.SOC_CODIGO,COOPERATIVA.COOP_ID from SOCIOS INNER JOIN SOC_COOP ON SOCIOS.SOC_ID = SOC_COOP.SOC_ID INNER JOIN COOPERATIVA ON COOPERATIVA.COOP_ID = SOC_COOP.COOP_ID WHERE SOC_COOP.SOC_ESTADO = 1 AND SOC_COOP.SOC_CODIGO = " + this.codSocio + " AND SOC_COOP.COOP_ID = " + idcoop);

            while (rs.next()) {
                if (rs.getInt("SOC_CODIGO") == Integer.parseInt(this.codSocio) && rs.getInt("COOP_ID") == Integer.parseInt(idcoop)) {
                    existe = true;
                    break;
                } else {
                    existe = false;
                    break;
                }
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(socios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;

    }

    public void listarSocios() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //Map params = facesContext.getExternalContext().getRequestParameterMap();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String idcoop = (String) session.getAttribute("coopid");
        this.selectedCoop = idcoop;//(String)params.get("coop_id");
        //System.out.println("Cooperativa : " + idcoop);
        listarsocios = new ArrayList<socios>();
        Connection connection = conexion();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select SOCIOS.SOC_ID,SOCIOS.SOC_IDENT,SOCIOS.SOC_NOM,SOCIOS.SOC_APE, SOCIOS.SOC_TIPO,SOC_COOP.SOC_CODIGO from SOCIOS INNER JOIN SOC_COOP ON SOCIOS.SOC_ID = SOC_COOP.SOC_ID INNER JOIN COOPERATIVA ON COOPERATIVA.COOP_ID = SOC_COOP.COOP_ID WHERE SOC_COOP.SOC_ESTADO = 1 AND SOC_COOP.COOP_ID = " + this.selectedCoop);
            while (rs.next()) {
                listarsocios.add(new socios(String.valueOf(rs.getInt("SOC_ID")), rs.getString("SOC_IDENT"), rs.getString("SOC_NOM"), rs.getString("SOC_APE"), rs.getString("SOC_CODIGO")));

            }
            LazySociosModel = new LazySociosTableModel(listarsocios);

            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(socios.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void BuscarSocios() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //Map params = facesContext.getExternalContext().getRequestParameterMap();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String idcoop = (String) session.getAttribute("coopid");
        this.selectedCoop = idcoop;//(String)params.get("coop_id");
        //System.out.println("Cooperativa : " + idcoop);
        listarsocios = new ArrayList<socios>();
        Connection connection = conexion();
        try {
            Statement stmt = connection.createStatement();
            if (busqcedula.equals("")) {
                ResultSet rs = stmt.executeQuery("select SOCIOS.SOC_ID,SOCIOS.SOC_IDENT,SOCIOS.SOC_NOM,SOCIOS.SOC_APE, SOCIOS.SOC_TIPO,SOC_COOP.SOC_CODIGO from SOCIOS INNER JOIN SOC_COOP ON SOCIOS.SOC_ID = SOC_COOP.SOC_ID INNER JOIN COOPERATIVA ON COOPERATIVA.COOP_ID = SOC_COOP.COOP_ID WHERE SOC_COOP.SOC_ESTADO = 1 AND SOC_COOP.COOP_ID = " + this.selectedCoop);
                while (rs.next()) {
                    listarsocios.add(new socios(String.valueOf(rs.getInt("SOC_ID")), rs.getString("SOC_IDENT"), rs.getString("SOC_NOM"), rs.getString("SOC_APE"), rs.getString("SOC_CODIGO")));

                }
            } else {
                ResultSet rs = stmt.executeQuery("select SOCIOS.SOC_ID,SOCIOS.SOC_IDENT,SOCIOS.SOC_NOM,SOCIOS.SOC_APE, SOCIOS.SOC_TIPO,SOC_COOP.SOC_CODIGO from SOCIOS INNER JOIN SOC_COOP ON SOCIOS.SOC_ID = SOC_COOP.SOC_ID INNER JOIN COOPERATIVA ON COOPERATIVA.COOP_ID = SOC_COOP.COOP_ID WHERE SOC_COOP.SOC_ESTADO = 1 AND SOC_COOP.COOP_ID = " + this.selectedCoop + " AND SOCIOS.SOC_IDENT = '" + busqcedula + "'");
                while (rs.next()) {
                    listarsocios.add(new socios(String.valueOf(rs.getInt("SOC_ID")), rs.getString("SOC_IDENT"), rs.getString("SOC_NOM"), rs.getString("SOC_APE"), rs.getString("SOC_CODIGO")));

                }
            }

            LazySociosModel = new LazySociosTableModel(listarsocios);
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(socios.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void BuscarSociosporCodigo() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //Map params = facesContext.getExternalContext().getRequestParameterMap();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String idcoop = (String) session.getAttribute("coopid");
        this.selectedCoop = idcoop;//(String)params.get("coop_id");
        //System.out.println("Cooperativa : " + idcoop);
        listarsocios = new ArrayList<socios>();
        Connection connection = conexion();
        try {
            Statement stmt = connection.createStatement();
            if (busqcodigo.equals("")) {
                ResultSet rs = stmt.executeQuery("select SOCIOS.SOC_ID,SOCIOS.SOC_IDENT,SOCIOS.SOC_NOM,SOCIOS.SOC_APE, SOCIOS.SOC_TIPO,SOC_COOP.SOC_CODIGO from SOCIOS INNER JOIN SOC_COOP ON SOCIOS.SOC_ID = SOC_COOP.SOC_ID INNER JOIN COOPERATIVA ON COOPERATIVA.COOP_ID = SOC_COOP.COOP_ID WHERE SOC_COOP.SOC_ESTADO = 1 AND SOC_COOP.COOP_ID = " + this.selectedCoop);
                while (rs.next()) {
                    listarsocios.add(new socios(String.valueOf(rs.getInt("SOC_ID")), rs.getString("SOC_IDENT"), rs.getString("SOC_NOM"), rs.getString("SOC_APE"), rs.getString("SOC_CODIGO")));

                }
            } else {
                ResultSet rs = stmt.executeQuery("select SOCIOS.SOC_ID,SOCIOS.SOC_IDENT,SOCIOS.SOC_NOM,SOCIOS.SOC_APE, SOCIOS.SOC_TIPO,SOC_COOP.SOC_CODIGO from SOCIOS INNER JOIN SOC_COOP ON SOCIOS.SOC_ID = SOC_COOP.SOC_ID INNER JOIN COOPERATIVA ON COOPERATIVA.COOP_ID = SOC_COOP.COOP_ID WHERE SOC_COOP.SOC_ESTADO = 1 AND SOC_COOP.COOP_ID = " + this.selectedCoop + " AND SOC_COOP.SOC_CODIGO = '" + busqcodigo + "'");
                while (rs.next()) {
                    listarsocios.add(new socios(String.valueOf(rs.getInt("SOC_ID")), rs.getString("SOC_IDENT"), rs.getString("SOC_NOM"), rs.getString("SOC_APE"), rs.getString("SOC_CODIGO")));

                }
            }

            LazySociosModel = new LazySociosTableModel(listarsocios);
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(socios.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String ObtieneTotalSocios() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //Map params = facesContext.getExternalContext().getRequestParameterMap();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String idcoop = (String) session.getAttribute("coopid");
        this.selectedCoop = idcoop;//(String)params.get("coop_id");
        //System.out.println("Cooperativa : " + idcoop);
        listarsocios = new ArrayList<socios>();
        Connection connection = conexion();
        try {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("Select count (*) from soc_coop where coop_id=" + this.selectedCoop + " and soc_estado=1");
            while (rs.next()) {
                totSocios = rs.getString(1);

            }

        } catch (SQLException ex) {
            Logger.getLogger(socios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totSocios;
    }

    public FacesMessage cargarListaSocios(List<socios> listaSocios) {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String idcoop = (String) session.getAttribute("coopid");
        FacesMessage msg = null;
        FacesMessage msg1 = null;
        int sociossubidos = 0;

        totalsocios = listaSocios.size();
        resetarProgresso();

        for (int i = 0; i < listaSocios.size(); i++) {
            contsocio = i + 1;
            ing_soc = i;
            System.out.println("PROGRESO: " + progreso + " " + mensaje);

            //System.out.println("Progreso: " + progreso + "%");
            Connection dbConnection = null;
            Statement statement = null;
            PreparedStatement preparedStatement = null;
            progreso = (ing_soc * 100) / totalsocios;
            System.out.println("PROGRESO: " + progreso + " " + mensaje);
            mensaje = "procesando " + (i + 1) + " de " + totalsocios;

            try {

                dbConnection = conexion();
                preparedStatement = dbConnection.prepareStatement("{call INSERTARSOCIO(?,?,?,?,?,?)}");

                preparedStatement.setString(1, listaSocios.get(i).identificacion);
                switch (listaSocios.get(i).identificacion.length()) {
                    case 10:
                        preparedStatement.setString(2, "1");
                        break;
                    case 13:
                        preparedStatement.setString(2, "2");
                        break;
                    default:
                        preparedStatement.setString(2, "3");
                        break;
                }
                preparedStatement.setString(3, idcoop);
                preparedStatement.setString(4, listaSocios.get(i).codSocio);
                preparedStatement.setString(5, listaSocios.get(i).Nombres.toUpperCase());
                preparedStatement.setString(6, listaSocios.get(i).Apellidos.toUpperCase());

                preparedStatement.executeUpdate();
                sociossubidos++;

                ///    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Subido: " + ((i + 1) * 100 / listaSocios.size()) + "% "));
                System.out.println("Socios Insertado: " + listaSocios.get(i).codSocio + " Coopid: " + idcoop);
                //actualizarProgresso(ing_soc, totalsocios);

                // System.out.println(progress);
                // msg1=new FacesMessage(FacesMessage.SEVERITY_INFO, "Estado! ", "Subido: " + ((i + 1) * 100 / listaSocios.size()) + "% " + (i + 1) + " de " + listaSocios.size() + " - Socios: " + listaSocios.get(i).codSocio.trim() + sociossubidos + " Socios");
                // System.out.println("Subido : " + ((i + 1) * 100 / listaSocios.size()) + "% " + (i + 1) + " de " + listaSocios.size() + " - Socios: " + listaSocios.get(i).codSocio.trim());
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Estado! ", "Subido Correctamente: " + sociossubidos + " Socios");

            } catch (SQLException e) {

                System.out.println(e.getMessage());
                //e.printStackTrace();

            } finally {

                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException ex) {
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Socios! ", ex.getMessage());

                        System.out.println(ex.getMessage());
                    }
                }

                if (dbConnection != null) {
                    try {
                        dbConnection.close();
                    } catch (SQLException ex) {
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Socios! ", ex.getMessage());
                        System.out.println(ex.getMessage());
                    }
                }
            }
            actualizarProgreso();

        }
        resetarProgresso();
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return msg;

    }

    public void datoslista(List<socios> listaSocios) {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        ing_soc = listaSocios.size();

    }
////variable de progreso 

    public String getMensaje() {
        if (mensaje == null) {
            mensaje = "SIN CARGA";
        }
//        else {
//            int i=0;
//            i++;
//            mensaje= "Cargando..." +i;}
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    private void resetarProgresso() {

        progreso = 0;
        mensaje = "";

    }
    ////////////// FIN DE BARRA DE PROGRESO

    public void onComplete() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Progress Completed", "Progress Completed"));
    }

    private void actualizarProgreso() {

        //progreso = ;
        //return progreso;
    }

    public int getProgreso() {

        if (progreso < 0 || progreso > 100) {
            progreso = 0;
        }

        //progreso=progreso + 1;
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }

    public int getTotalsocios() {
        return totalsocios;
    }

    public void setTotalsocios(int totalsocios) {
        this.totalsocios = totalsocios;
    }

    public LazyDataModel<socios> getLazySociosModel() {
        //this.listarSocios();
        return LazySociosModel;
    }

    public void setLazySociosModel(LazyDataModel<socios> LazySociosModel) {
        this.LazySociosModel = LazySociosModel;
    }

    public String getBusqcedula() {
        return busqcedula;
    }

    public void setBusqcedula(String busqcedula) {
        this.busqcedula = busqcedula;
    }

    public String getBusqcodigo() {
        return busqcodigo;
    }

    public void setBusqcodigo(String busqcodigo) {
        this.busqcodigo = busqcodigo;
    }

}
