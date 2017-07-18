package Beans;

import static Controlador.LoginControl.conexion;
import static Security.ValidarCedula.validacionCedula;
import static Security.ValidarRucEP.validaRucEP;
import static Security.ValidarRucSociedades.validacionRUC;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.event.FlowEvent;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author CodeSystems
 */
@ManagedBean(name = "coactivas")
@SessionScoped
public class Coactivas implements Serializable {

    private static final long serialVersionUID = -2152389656664659476L;
    private String cedula;
    private String nombre;
    private String numeroCoactiva;
    private String apellido;
    private String causa;
    private String asunto;
    private String valor;
    private String valorBloq;
    private String saldo;
    private String numerTramite;
    private String numeroOficio;
    private String coactivaId;
    private String numeroAnexo;
    private String fecha;
    private String numeroJuicio;
    private String Ciudad;
    private String Remitente;
    private String cargoRemitente;
    private String cedulaBuscar;
    private String anexoBuscar;
    private String tramiteBuscar;
    private String CodigoSocio;
    private Integer progress = 0;
    private String usuarioCarga;
    private Date formatoFecha;
    private Date fechaInicial;
    private String numeroBuscar;

    private Date fechaFinal;

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private Integer estado;
    private String fechaCarga;
    private String observaciones;
    private String usuarioBuscar;

    private Coactivas selectedCoactiva;
    private List<Coactivas> listarCoactivas = null;
    private List<Coactivas> listarCoactivasBloq = null;
    private List<Coactivas> listarCoactivasDesb = null;
    private List<Coactivas> listarCoactivasLeidas = null;
    private List<Coactivas> listarCoactivasNoLeidas = null;
    private List<Coactivas> listarCoactivasInso = null;
    private List<Coactivas> listarCoactivasInfo = null;
    private List<Coactivas> listarCoactivasTodas = null;
    private List<Coactivas> listarfiltradas;
    private List<Coactivas> listarSel;
    private List<Coactivas> listarCoactivasPendientes = null;
    private List<Coactivas> listarCoactivasHistorial = null;
    private List<Coactivas> listarCoactivasUsuario = null;
    private List<Coactivas> listarCoactivasFechas = null;
    private List<Coactivas> listarCoactivasAnexo = null;
    private List<Coactivas> listarCoactivasNumero = null;
    private String numeroCircularSeps;
    private String numeroOficioInterno;
    private boolean leido = false;
    private String rev_id;
    private boolean subiendo = true; // Controla la subida a la base de datos
    private LazyDataModel<Coactivas> LazyCoactivas;
    private LazyDataModel<Coactivas> LazyCoactivasBloq;
    private LazyDataModel<Coactivas> LazyCoactivasDesb;
    private LazyDataModel<Coactivas> LazyCoactivasRev;
    private LazyDataModel<Coactivas> LazyCoactivasInso;
    private LazyDataModel<Coactivas> LazyCoactivasInfo;
    private LazyDataModel<Coactivas> LazyCoactivasTodas;
    private LazyDataModel<Coactivas> LazyCoactivasLeidas;
    private LazyDataModel<Coactivas> LazyCoactivasPendientes;
    private LazyDataModel<Coactivas> LazyCoactivasHistorial;
    private LazyDataModel<Coactivas> LazyCoactivasUsuarios;
    private LazyDataModel<Coactivas> LazyCoactivasFechas;
    private LazyDataModel<Coactivas> LazyCoactivasAnexo;
    private LazyDataModel<Coactivas> LazyCoactivasNumero;

    @PostConstruct
    public void init() {
        //this.listarCoactivas();
    }

//Constructores de la clase
    public Coactivas(String numeroCoactiva, String nombre, String apellido, String cedula, String causa, String asunto, String valor, String numerTramite, String numeroOficio, String numeroAnexo, String fecha, String numeroJuicio, String Ciudad, String Remitente, String cargoRemitente, String numeroCircularSeps) {

        this.cedula = cedula;
        this.nombre = nombre;
        this.numeroCoactiva = numeroCoactiva;
        this.apellido = apellido;
        this.causa = causa;
        this.asunto = asunto;
        this.valor = valor;
        this.numerTramite = numerTramite;
        this.numeroOficio = numeroOficio;
        this.numeroAnexo = numeroAnexo;
        this.fecha = fecha;
        this.numeroJuicio = numeroJuicio;
        this.Ciudad = Ciudad;
        this.Remitente = Remitente;
        this.cargoRemitente = cargoRemitente;
        this.numeroCircularSeps = numeroCircularSeps;

    }

    public Coactivas(String numeroCoactiva, String nombre, String apellido, String cedula, String asunto, String causa, String valor, String numerTramite, String numeroOficio, String numeroAnexo, String fecha, String numeroJuicio, String Ciudad, String Remitente, String cargoRemitente, String numeroCircularSeps, String observaciones, String usuarioCarga, Integer estadoCarga, String fechaCarga) {

        this.numeroCoactiva = numeroCoactiva;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.causa = causa;
        this.asunto = asunto;
        this.valor = valor;
        this.numerTramite = numerTramite;
        this.numeroOficio = numeroOficio;
        this.numeroAnexo = numeroAnexo;
        this.fecha = fecha;
        this.numeroJuicio = numeroJuicio;
        this.Ciudad = Ciudad;
        this.Remitente = Remitente;
        this.cargoRemitente = cargoRemitente;
        this.numeroCircularSeps = numeroCircularSeps;
        this.observaciones = observaciones;
        this.usuarioCarga = usuarioCarga;
        this.estado = estadoCarga;
        this.fechaCarga = fechaCarga;
    }

    public Coactivas(String id, String numeroCoactiva, String cedula, String nombre, String apellido, String codSocio, String causa, String asunto, String valor, String numeroAnexo, String fecha, String numerTramite, String rev, String numeroOficio, String numeroJuicio, String Ciudad, String Remitente, String cargoRemitente, String numeroCircularSeps) {
        this.coactivaId = id;
        this.numeroCoactiva = numeroCoactiva;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.CodigoSocio = codSocio;
        this.causa = causa;
        this.asunto = asunto;
        this.valor = valor;
        this.numeroAnexo = numeroAnexo;
        this.fecha = fecha;
        this.numerTramite = numerTramite;
        this.rev_id = rev;
        this.numeroOficio = numeroOficio;
        this.numeroJuicio = numeroJuicio;
        this.Ciudad = Ciudad;
        this.Remitente = Remitente;
        this.cargoRemitente = cargoRemitente;
        this.numeroCircularSeps = numeroCircularSeps;
    }

    public Coactivas(String id, String numeroCoactiva, String cedula, String nombre, String apellido, String CodigoSocio, String causa, String asunto, String valor, String numeroAnexo, String fecha, String numerTramite, String rev, String numeroOficio, String numeroJuicio, String Ciudad, String Remitente, String cargoRemitente, String numeroCircularSeps, String valorBloq, String oficioInterno) {
        this.coactivaId = id;
        this.numeroCoactiva = numeroCoactiva;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.CodigoSocio = CodigoSocio;
        this.causa = causa;
        this.asunto = asunto;
        this.valor = valor;
        this.numeroAnexo = numeroAnexo;
        this.fecha = fecha;
        this.rev_id = rev;
        this.valorBloq = valorBloq;
        this.numeroCircularSeps = numeroCircularSeps;
        this.numeroOficio = numeroOficio;
        this.numerTramite = numerTramite;
        this.numeroJuicio = numeroJuicio;
        this.Ciudad = Ciudad;
        this.Remitente = Remitente;
        this.cargoRemitente = cargoRemitente;
        this.numeroOficioInterno = oficioInterno;
    }

    public Coactivas(String coacId, String numeroCoactiva, String cedula, String nombre, String apellido, String causa, String asunto, String valor, String numeroAnexo, String fecha, String rev_id) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.numeroCoactiva = numeroCoactiva;
        this.apellido = apellido;
        this.causa = causa;
        this.asunto = asunto;
        this.valor = valor;
        this.numeroAnexo = numeroAnexo;
        this.fecha = fecha;
        this.coactivaId = coacId;
        this.rev_id = rev_id;
    }

    public Coactivas(String coacId, String numeroCoactiva, String cedula, String nombre, String apellido, String causa, String asunto, String valor, String numeroAnexo, String fecha, String numeroTramite, String numeroOficio, String numeroJuicio, String ciudad, String remitente, String cargoRemitente, String circularSeps) {
        this.coactivaId = coacId;
        this.numeroCoactiva = numeroCoactiva;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.causa = causa;
        this.asunto = asunto;
        this.valor = valor;
        this.numeroAnexo = numeroAnexo;
        this.fecha = fecha;
        this.numerTramite = numeroTramite;
        this.numeroOficio = numeroOficio;
        this.numeroJuicio = numeroJuicio;
        this.Ciudad = ciudad;
        this.Remitente = remitente;
        this.cargoRemitente = cargoRemitente;
        this.numeroCircularSeps = circularSeps;

    }

    public Coactivas(String coacId, String causa, String numeroAnexo, String fecha, String numeroTramite, String numeroOficio, String ciudad, String remitente, String cargoRemitente, String circularSeps) {
        this.coactivaId = coacId;
        this.causa = causa;
        this.numeroAnexo = numeroAnexo;
        this.fecha = fecha;
        this.numerTramite = numeroTramite;
        this.numeroOficio = numeroOficio;
        this.Ciudad = ciudad;
        this.Remitente = remitente;
        this.cargoRemitente = cargoRemitente;
        this.numeroCircularSeps = circularSeps;
    }

    public Coactivas(String coacId, String numeroCoactiva, String cedula, String nombre, String apellido, String causa, String asunto, String valor, String numeroAnexo, String fecha, String rev_id, String valorBloq, String numCircular, String numOficioInt) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.numeroCoactiva = numeroCoactiva;
        this.apellido = apellido;
        this.causa = causa;
        this.asunto = asunto;
        this.valor = valor;
        this.numeroAnexo = numeroAnexo;
        this.fecha = fecha;
        this.coactivaId = coacId;
        this.rev_id = rev_id;
        this.valorBloq = valorBloq;
        this.numeroCircularSeps = numCircular;
        this.numeroOficioInterno = numOficioInt;
    }

    public Coactivas(String nombre, String cedula) {
        this.nombre = nombre;
        this.cedula = cedula;
    }

    public Coactivas(List<Coactivas> listarfiltradas) {
        this.listarfiltradas = listarfiltradas;
    }

    public Coactivas() {
    }

    public List<Coactivas> getListarSel() {
        //this.listarCoactivas();
        return listarSel;
    }

    public void setListarSel(List<Coactivas> listarSel) {
        this.listarSel = listarSel;
    }

    public List<Coactivas> getListarfiltradas() {
        //this.listarCoactivas();
        return listarfiltradas;
    }

    public void setListarCoactivasTodas(List<Coactivas> listarCoactivasTodas) {
        this.listarCoactivasTodas = listarCoactivasTodas;
    }

    public void setListarfiltradas(List<Coactivas> listarfiltradas) {
        this.listarfiltradas = listarfiltradas;
    }

    public List<Coactivas> getListarCoactivasTodas() {
        //this.listarCoactivas();
        return listarCoactivasTodas;
    }

    public void setListarfiltradasTodas(List<Coactivas> listarCoactivasTodas) {
        this.listarCoactivasTodas = listarCoactivasTodas;
    }

    public SimpleDateFormat getFormat() {
        return format;
    }

    public void setFormat(SimpleDateFormat format) {
        this.format = format;
    }

    public String getNumeroBuscar() {
        return numeroBuscar;
    }

    public void setNumeroBuscar(String numeroBuscar) {
        this.numeroBuscar = numeroBuscar;
    }

    public List<Coactivas> getListarCoactivasNumero() {
        return listarCoactivasNumero;
    }

    public void setListarCoactivasNumero(List<Coactivas> listarCoactivasNumero) {
        this.listarCoactivasNumero = listarCoactivasNumero;
    }

    public LazyDataModel<Coactivas> getLazyCoactivasNumero() {
        return LazyCoactivasNumero;
    }

    public void setLazyCoactivasNumero(LazyDataModel<Coactivas> LazyCoactivasNumero) {
        this.LazyCoactivasNumero = LazyCoactivasNumero;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public List<Coactivas> getListarCoactivasFechas() {
        return listarCoactivasFechas;
    }

    public void setListarCoactivasFechas(List<Coactivas> listarCoactivasFechas) {
        this.listarCoactivasFechas = listarCoactivasFechas;
    }

    public List<Coactivas> getListarCoactivasAnexo() {
        return listarCoactivasAnexo;
    }

    public void setListarCoactivasAnexo(List<Coactivas> listarCoactivasAnexo) {
        this.listarCoactivasAnexo = listarCoactivasAnexo;
    }

    public LazyDataModel<Coactivas> getLazyCoactivasUsuarios() {
        return LazyCoactivasUsuarios;
    }

    public void setLazyCoactivasUsuarios(LazyDataModel<Coactivas> LazyCoactivasUsuarios) {
        this.LazyCoactivasUsuarios = LazyCoactivasUsuarios;
    }

    public LazyDataModel<Coactivas> getLazyCoactivasFechas() {
        return LazyCoactivasFechas;
    }

    public void setLazyCoactivasFechas(LazyDataModel<Coactivas> LazyCoactivasFechas) {
        this.LazyCoactivasFechas = LazyCoactivasFechas;
    }

    public LazyDataModel<Coactivas> getLazyCoactivasAnexo() {
        return LazyCoactivasAnexo;
    }

    public void setLazyCoactivasAnexo(LazyDataModel<Coactivas> LazyCoactivasAnexo) {
        this.LazyCoactivasAnexo = LazyCoactivasAnexo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getUsuarioCarga() {
        return usuarioCarga;
    }

    public void setUsuarioCarga(String usuarioCarga) {
        this.usuarioCarga = usuarioCarga;
    }

    public String getCedulaBuscar() {
        return cedulaBuscar;
    }

    public void setCedulaBuscar(String cedulaBuscar) {
        this.cedulaBuscar = cedulaBuscar;
    }

    public String getUsuarioBuscar() {
        return usuarioBuscar;
    }

    public void setUsuarioBuscar(String usuarioBuscar) {
        this.usuarioBuscar = usuarioBuscar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroCoactiva() {
        return numeroCoactiva;
    }

    public void setNumeroCoactiva(String numeroCoactiva) {
        this.numeroCoactiva = numeroCoactiva;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public String getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(String fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getNumerTramite() {
        return numerTramite;
    }

    public void setNumerTramite(String numerTramite) {
        this.numerTramite = numerTramite;
    }

    public String getNumeroOficio() {
        return numeroOficio;
    }

    public void setNumeroOficio(String numeroOficio) {
        this.numeroOficio = numeroOficio;
    }

    public String getNumeroAnexo() {
        return numeroAnexo;
    }

    public void setNumeroAnexo(String numeroAnexo) {
        this.numeroAnexo = numeroAnexo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Date getFormatoFecha() {
        return formatoFecha;
    }

    public void setFormatoFecha(Date formatoFecha) {
        this.formatoFecha = formatoFecha;
    }

    public String getNumeroJuicio() {
        return numeroJuicio;
    }

    public void setNumeroJuicio(String numeroJuicio) {
        this.numeroJuicio = numeroJuicio;
    }

    public String getCiudad() {
        return Ciudad;
    }

    public void setCiudad(String Ciudad) {
        this.Ciudad = Ciudad;
    }

    public String getRemitente() {
        return Remitente;
    }

    public void setRemitente(String Remitente) {
        this.Remitente = Remitente;
    }

    public String getCargoRemitente() {
        return cargoRemitente;
    }

    public void setCargoRemitente(String cargoRemitente) {
        this.cargoRemitente = cargoRemitente;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getProgress() {

        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;

    }

    public List<Coactivas> getListarCoactivas() {
        //this.listarCoactivas();
        return listarCoactivas;
    }

    public void setListarCoactivas(List<Coactivas> listarCoactivas) {
        this.listarCoactivas = listarCoactivas;
    }

    public String getCoactivaId() {
        return coactivaId;
    }

    public void setCoactivaId(String coactivaId) {
        this.coactivaId = coactivaId;
    }

    public List<Coactivas> getListarCoactivasBloq() {
        //this.listarCoactivas();
        //System.out.println("Listando Coactivas Bloqueadas");
        return listarCoactivasBloq;
    }

    public void setListarCoactivasBloq(List<Coactivas> listarCoactivasBloq) {
        this.listarCoactivasBloq = listarCoactivasBloq;
    }

    public List<Coactivas> getListarCoactivasDesb() {
        this.listarCoactivas();
        return listarCoactivasDesb;
    }

    public void setListarCoactivasDesb(List<Coactivas> listarCoactivasDesb) {
        this.listarCoactivasDesb = listarCoactivasDesb;
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Coactiva Seleccionada", ((Coactivas) event.getObject()).getNumeroCoactiva());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Coactivas getSelectedCoactiva() {
        return selectedCoactiva;
    }

    public void setSelectedCoactiva(Coactivas selectedCoactiva) {

        this.selectedCoactiva = selectedCoactiva;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public String getRev_id() {
        return rev_id;
    }

    public void setRev_id(String rev_id) {
        this.rev_id = rev_id;
    }

    public String getValorBloq() {
        return valorBloq;
    }

    public void setValorBloq(String valorBloq) {
        this.valorBloq = valorBloq;
    }

    public void onCellEdit(CellEditEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        try {
            Coactivas coac = (Coactivas) ((DataTable) event.getComponent()).getRowData();
            ActualizarValor(coac.getCoactivaId(), coac.getValorBloq(), coac.getNumeroCircularSeps(), coac.getNumeroOficioInterno());

        } catch (Exception ex) {
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        }
        requestContext.update("up:growl");
        requestContext.update("up:tab1:tablacoacBloq");
    }

    public String getCodigoSocio() {

        return CodigoSocio;
    }

    public void setCodigoSocio(String CodigoSocio) {
        this.CodigoSocio = CodigoSocio;
    }

    public String getSaldo() {

        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getNumeroCircularSeps() {
        return numeroCircularSeps;
    }

    public void setNumeroCircularSeps(String numeroCircularSeps) {
        this.numeroCircularSeps = numeroCircularSeps;
    }

    public String getNumeroOficioInterno() {
        return numeroOficioInterno;
    }

    public void setNumeroOficioInterno(String numeroOficioInterno) {
        this.numeroOficioInterno = numeroOficioInterno;
    }

    public String getAnexoBuscar() {
        return anexoBuscar;
    }

    public void setAnexoBuscar(String anexoBuscar) {
        this.anexoBuscar = anexoBuscar;
    }

    public String getTramiteBuscar() {
        return tramiteBuscar;
    }

    public void setTramiteBuscar(String tramiteBuscar) {
        this.tramiteBuscar = tramiteBuscar;
    }

    public String timestamp() {
        java.util.Date date = new java.util.Date();
        //System.out.println(new Timestamp(date.getTime()));
        return new Timestamp(date.getTime()) + "";
    }

    public LazyDataModel<Coactivas> getLazyCoactivas() {
        return LazyCoactivas;
    }

    public void setLazyCoactivas(LazyDataModel<Coactivas> LazyCoactivas) {
        this.LazyCoactivas = LazyCoactivas;
    }

    public LazyDataModel<Coactivas> getLazyCoactivasUsuario() {
        return LazyCoactivasUsuarios;
    }

    public void setLazyCoactivasUsuario(LazyDataModel<Coactivas> LazyCoactivasUsuario) {
        this.LazyCoactivasUsuarios = LazyCoactivasUsuario;
    }

    public LazyDataModel<Coactivas> getLazyCoactivasTodas() {
        return LazyCoactivasTodas;
    }

    public void setLazyCoactivasTodas(LazyDataModel<Coactivas> LazyCoactivasTodas) {
        this.LazyCoactivasTodas = LazyCoactivasTodas;
    }

    public LazyDataModel<Coactivas> getLazyCoactivasBloq() {
        return LazyCoactivasBloq;
    }

    public void setLazyCoactivasBloq(LazyDataModel<Coactivas> LazyCoactivasBloq) {
        this.LazyCoactivasBloq = LazyCoactivasBloq;
    }

    public LazyDataModel<Coactivas> getLazyCoactivasDesb() {
        return LazyCoactivasDesb;
    }

    public void setLazyCoactivasDesb(LazyDataModel<Coactivas> LazyCoactivasDesb) {
        this.LazyCoactivasDesb = LazyCoactivasDesb;
    }

    public LazyDataModel<Coactivas> getLazyCoactivasRev() {
        return LazyCoactivasRev;
    }

    public void setLazyCoactivasRev(LazyDataModel<Coactivas> LazyCoactivasRev) {
        this.LazyCoactivasRev = LazyCoactivasRev;
    }

    public LazyDataModel<Coactivas> getLazyCoactivasInso() {
        return LazyCoactivasInso;
    }

    public void setLazyCoactivasInso(LazyDataModel<Coactivas> LazyCoactivasInso) {
        this.LazyCoactivasInso = LazyCoactivasInso;
    }

    public LazyDataModel<Coactivas> getLazyCoactivasInfo() {
        return LazyCoactivasInfo;
    }

    public void setLazyCoactivasInfo(LazyDataModel<Coactivas> LazyCoactivasInfo) {
        this.LazyCoactivasInfo = LazyCoactivasInfo;
    }

    public LazyDataModel<Coactivas> getLazyCoactivasLeidas() {
        return LazyCoactivasLeidas;
    }

    public void setLazyCoactivasLeidas(LazyDataModel<Coactivas> LazyCoactivasLeidas) {
        this.LazyCoactivasLeidas = LazyCoactivasLeidas;
    }

    public LazyDataModel<Coactivas> getLazyCoactivasPendientes() {
        return LazyCoactivasPendientes;
    }

    public void setLazyCoactivasPendientes(LazyDataModel<Coactivas> LazyCoactivasPendientes) {
        this.LazyCoactivasPendientes = LazyCoactivasPendientes;
    }

    public LazyDataModel<Coactivas> getLazyCoactivasHistorial() {
        return LazyCoactivasHistorial;
    }

    public void setLazyCoactivasHistorial(LazyDataModel<Coactivas> LazyCoactivasHistorial) {
        this.LazyCoactivasHistorial = LazyCoactivasHistorial;
    }

    public void closeDialog() {
        //pass back to level 2
        RequestContext.getCurrentInstance().closeDialog(numeroAnexo);
    }

    public List<Coactivas> getListarCoactivasLeidas() {
        //this.listarCoactivas();
        return listarCoactivasLeidas;
    }

    public void setListarCoactivasLeidas(List<Coactivas> listarCoactivasLeidas) {
        this.listarCoactivasLeidas = listarCoactivasLeidas;
    }

    public List<Coactivas> getListarCoactivasNoLeidas() {
        //this.listarCoactivas();
        return listarCoactivasNoLeidas;
    }

    public void setListarCoactivasNoLeidas(List<Coactivas> listarCoactivasNoLeidas) {
        this.listarCoactivasNoLeidas = listarCoactivasNoLeidas;
    }

    public List<Coactivas> getListarCoactivasHistorial() {
        //this.listarCoactivas();
        return listarCoactivasHistorial;
    }

    public void setListarCoactivasHistorial(List<Coactivas> listarCoactivasHistorial) {
        this.listarCoactivasHistorial = listarCoactivasHistorial;
    }

    public List<Coactivas> getListarCoactivasInso() {
        //this.listarCoactivas();
        return listarCoactivasInso;
    }

    public void setListarCoactivasInso(List<Coactivas> listarCoactivasInso) {
        this.listarCoactivasInso = listarCoactivasInso;
    }

    public List<Coactivas> getListarCoactivasInfo() {
        //this.listarCoactivas();
        return listarCoactivasInfo;
    }

    public void setListarCoactivasInfo(List<Coactivas> listarCoactivasInfo) {
        this.listarCoactivasInfo = listarCoactivasInfo;
    }

    public List<Coactivas> getListarCoactivasUsuario() {
        //this.listarCoactivas();
        return listarCoactivasUsuario;
    }

    public void setListarCoactivasUsuario(List<Coactivas> listarCoactivasUsuario) {
        this.listarCoactivasUsuario = listarCoactivasUsuario;
    }

    public List<Coactivas> getListarCoactivasPendientes() {
        //this.listarCoactivas();
        return listarCoactivasPendientes;
    }

    public void setListarCoactivasPendientes(List<Coactivas> listarCoactivasPendientes) {
        this.listarCoactivasPendientes = listarCoactivasPendientes;
    }

    public boolean isSubiendo() {
        return subiendo;

    }

    public void setSubiendo(boolean subiendo) {
        this.subiendo = subiendo;
    }

    public void submit() {
        //pass back to level 2

    }

    public void listarCoactivasTodas() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        listarCoactivasTodas = new ArrayList<Coactivas>();

        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();

            ResultSet rs = statement.executeQuery("Select * from coactivas inner join usuarios on coac_usuario_carga=usu_id order by coac_num desc");
            while (rs.next()) {
                listarCoactivasTodas.add(new Coactivas(rs.getString("COAC_NUM"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CEDULA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_CAUSA"), rs.getString("COAC_VALOR"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS"), rs.getString("COAC_OBSERVACIONES"), rs.getString("USU_USER"), rs.getInt("COAC_ESTADO_CARGA"), (format.format(rs.getDate("COAC_FECHA_CARGA")))));

            }

            LazyCoactivasTodas = new LazyCoactivasTableModel(listarCoactivasTodas);

            //connection.close();
        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //METODO ENCARGADO DE LISTAR LAS COACTIVAS DE LOS SOCIOS REGISTRADOS DE CADA COOPERATIVA
    public void listarCoactivas() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String idcoop = (String) session.getAttribute("coopid");
        listarCoactivas = new ArrayList<Coactivas>();
        listarCoactivasBloq = new ArrayList<Coactivas>();
        listarCoactivasDesb = new ArrayList<Coactivas>();
        listarCoactivasLeidas = new ArrayList<Coactivas>();
        listarCoactivasInso = new ArrayList<Coactivas>();
        listarCoactivasInfo = new ArrayList<Coactivas>();

        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM REV_COACTIVAS INNER JOIN SOC_COOP ON REV_COACTIVAS.SOC_ID = SOC_COOP.SOC_ID INNER JOIN COACTIVAS ON REV_COACTIVAS.COAC_ID = COACTIVAS.COAC_ID WHERE SOC_COOP.SOC_ESTADO = '1' and SOC_COOP.COOP_ID =" + idcoop + " and REV_COACTIVAS.COOP_ID =" + idcoop + " AND COACTIVAS.COAC_ESTADO_CARGA=1 and COACTIVAS.EST_ID=1  ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");

            while (rs.next()) {
                //String asunto = Fila_hssf.getCell(5) + "";
                if (!rs.getString("COAC_ASUNTO").equals("") || !rs.getString("COAC_ASUNTO").isEmpty() || !rs.getString("COAC_NOMBRES").equals("") || !rs.getString("COAC_NOMBRES").isEmpty() || !rs.getString("COAC_NUM").equals("") || !rs.getString("COAC_NUM").isEmpty() || !rs.getString("COAC_APELLIDOS").equals("") || !rs.getString("COAC_APELLIDOS").isEmpty() || !rs.getString("COAC_CAUSA").equals("") || !rs.getString("COAC_CAUSA").isEmpty() || !rs.getString("COAC_VALOR").equals("") || !rs.getString("COAC_VALOR").isEmpty() || !rs.getString("COAC_ANEXO").equals("") || !rs.getString("COAC_ANEXO").isEmpty() || !rs.getString("COAC_FECHA").equals("") || !rs.getString("COAC_FECHA").isEmpty()) {
                    listarCoactivas.add(new Coactivas(String.valueOf(rs.getInt("COAC_ID")), rs.getString("COAC_NUM"), rs.getString("COAC_CEDULA"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("SOC_ID"), rs.getString("COAC_CAUSA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_VALOR"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_TRAMITE"), rs.getString("REV_ID"), rs.getString("COAC_OFICIO"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS")));
                    if ((rs.getString("COAC_ASUNTO").equals("BLOQUEO")) && (rs.getString("REV_ESTADO").equals("0"))) {

                        listarCoactivasBloq.add(new Coactivas(String.valueOf(rs.getInt("COAC_ID")), rs.getString("COAC_NUM"), rs.getString("COAC_CEDULA"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("SOC_ID"), rs.getString("COAC_CAUSA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_VALOR"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_TRAMITE"), rs.getString("REV_ID"), rs.getString("COAC_OFICIO"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS"), rs.getString("SOC_SALDO"), rs.getString("COAC_OFI_INT")));
                    }
                    if ((rs.getString("COAC_ASUNTO").equals("DESBLOQUEO")) && (rs.getString("REV_ESTADO").equals("0"))) {
                        listarCoactivasDesb.add(new Coactivas(String.valueOf(rs.getInt("COAC_ID")), rs.getString("COAC_NUM"), rs.getString("COAC_CEDULA"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("SOC_ID"), rs.getString("COAC_CAUSA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_VALOR"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_TRAMITE"), rs.getString("REV_ID"), rs.getString("COAC_OFICIO"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS"), rs.getString("SOC_SALDO"), rs.getString("COAC_OFI_INT")));
                    }
                    if ((rs.getString("COAC_ASUNTO").equals("INSOLVENCIA")) && (rs.getString("REV_ESTADO").equals("0"))) {

                        listarCoactivasInso.add(new Coactivas(String.valueOf(rs.getInt("COAC_ID")), rs.getString("COAC_NUM"), rs.getString("COAC_CEDULA"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("SOC_ID"), rs.getString("COAC_CAUSA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_VALOR"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_TRAMITE"), rs.getString("REV_ID"), rs.getString("COAC_OFICIO"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS"), rs.getString("SOC_SALDO"), rs.getString("COAC_OFI_INT")));
                    }
                    if ((rs.getString("COAC_ASUNTO").equals("INFORMACION")) && (rs.getString("REV_ESTADO").equals("0"))) {

                        listarCoactivasInfo.add(new Coactivas(String.valueOf(rs.getInt("COAC_ID")), rs.getString("COAC_NUM"), rs.getString("COAC_CEDULA"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("SOC_ID"), rs.getString("COAC_CAUSA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_VALOR"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_TRAMITE"), rs.getString("REV_ID"), rs.getString("COAC_OFICIO"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS"), rs.getString("SOC_SALDO"), rs.getString("COAC_OFI_INT")));
                    }

                    if (rs.getString("REV_ESTADO").equals("1")) {
                        listarCoactivasLeidas.add(new Coactivas(String.valueOf(rs.getInt("COAC_ID")), rs.getString("COAC_NUM"), rs.getString("COAC_CEDULA"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("SOC_ID"), rs.getString("COAC_CAUSA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_VALOR"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_TRAMITE"), rs.getString("REV_ID"), rs.getString("COAC_OFICIO"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS"), rs.getString("SOC_SALDO"), rs.getString("COAC_OFI_INT")));
                    }
                } else {
                    System.out.println("Se encontraron Coactivas con errores");
                }

            }
            LazyCoactivas = new LazyCoactivasTableModel(listarCoactivas);
            LazyCoactivasBloq = new LazyCoactivasTableModel(listarCoactivasBloq);
            LazyCoactivasDesb = new LazyCoactivasTableModel(listarCoactivasDesb);
            LazyCoactivasRev = new LazyCoactivasTableModel(listarCoactivasLeidas);
            LazyCoactivasInso = new LazyCoactivasTableModel(listarCoactivasInso);
            LazyCoactivasInfo = new LazyCoactivasTableModel(listarCoactivasInfo);

        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //MÉTODO PARA  BUSCAR COACTIVA POR NÚMERO DE CEDULA EN LAS COACTIVAS CON ASUNTO DE BLOQUEO
    public void BusquedaCoactivasBloqueo() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        listarCoactivasBloq = new ArrayList<Coactivas>();

        String idcoop = (String) session.getAttribute("coopid");

        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();

            if (cedulaBuscar.equals("")) {

                ResultSet rs = statement.executeQuery("SELECT * FROM REV_COACTIVAS INNER JOIN SOC_COOP ON REV_COACTIVAS.SOC_ID = SOC_COOP.SOC_ID INNER JOIN COACTIVAS ON REV_COACTIVAS.COAC_ID = COACTIVAS.COAC_ID WHERE SOC_COOP.SOC_ESTADO = '1' and SOC_COOP.COOP_ID =" + idcoop + " and REV_COACTIVAS.COOP_ID =" + idcoop + " AND REV_COACTIVAS.REV_ESTADO=0 and COACTIVAS.EST_ID=1 AND COACTIVAS.COAC_ESTADO_CARGA=1  ORDER BY COAC_NUM DESC");
                while (rs.next()) {
                    if ((rs.getString("COAC_ASUNTO").equals("BLOQUEO")) && (rs.getString("REV_ESTADO").equals("0"))) {
                        listarCoactivasBloq.add(new Coactivas(String.valueOf(rs.getInt("COAC_ID")), rs.getString("COAC_NUM"), rs.getString("COAC_CEDULA"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CAUSA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_VALOR"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS")));
                    }
                }
            } else {

                ResultSet rs = statement.executeQuery("SELECT * FROM REV_COACTIVAS INNER JOIN SOC_COOP ON REV_COACTIVAS.SOC_ID = SOC_COOP.SOC_ID INNER JOIN COACTIVAS ON REV_COACTIVAS.COAC_ID = COACTIVAS.COAC_ID WHERE SOC_COOP.SOC_ESTADO = '1' and SOC_COOP.COOP_ID =" + idcoop + " and REV_COACTIVAS.COOP_ID =" + idcoop + " AND REV_COACTIVAS.REV_ESTADO=0 and COACTIVAS.EST_ID=1 AND COACTIVAS.COAC_ESTADO_CARGA=1  and COACTIVAS.COAC_CEDULA='" + cedulaBuscar + "' ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");
                while (rs.next()) {
                    if ((rs.getString("COAC_ASUNTO").equals("BLOQUEO")) && (rs.getString("REV_ESTADO").equals("0"))) {
                        listarCoactivasBloq.add(new Coactivas(String.valueOf(rs.getInt("COAC_ID")), rs.getString("COAC_NUM"), rs.getString("COAC_CEDULA"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CAUSA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_VALOR"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS")));
                    }
                }
            }

            LazyCoactivasBloq = new LazyCoactivasTableModel(listarCoactivasBloq);

            //connection.close();
        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //MÉTODO PARA  BUSCAR COACTIVA POR NÚMERO DE CEDULA EN LAS COACTIVAS CON ASUNTO DE DESBLOQUEO     
    public void BusquedaCoactivasDes() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        listarCoactivasDesb = new ArrayList<Coactivas>();

        String idcoop = (String) session.getAttribute("coopid");

        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();

            if (cedulaBuscar.equals("")) {

                ResultSet rs = statement.executeQuery("SELECT * FROM REV_COACTIVAS INNER JOIN SOC_COOP ON REV_COACTIVAS.SOC_ID = SOC_COOP.SOC_ID INNER JOIN COACTIVAS ON REV_COACTIVAS.COAC_ID = COACTIVAS.COAC_ID WHERE SOC_COOP.SOC_ESTADO = '1' and SOC_COOP.COOP_ID =" + idcoop + " and REV_COACTIVAS.COOP_ID =" + idcoop + " AND REV_COACTIVAS.REV_ESTADO=0 and COACTIVAS.EST_ID=1 AND COACTIVAS.COAC_ESTADO_CARGA=1  ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");
                while (rs.next()) {
                    if ((rs.getString("COAC_ASUNTO").equals("DESBLOQUEO")) && (rs.getString("REV_ESTADO").equals("0"))) {
                        listarCoactivasDesb.add(new Coactivas(String.valueOf(rs.getInt("COAC_ID")), rs.getString("COAC_NUM"), rs.getString("COAC_CEDULA"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CAUSA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_VALOR"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS")));
                    }
                }
            } else {

                ResultSet rs = statement.executeQuery("SELECT * FROM REV_COACTIVAS INNER JOIN SOC_COOP ON REV_COACTIVAS.SOC_ID = SOC_COOP.SOC_ID INNER JOIN COACTIVAS ON REV_COACTIVAS.COAC_ID = COACTIVAS.COAC_ID WHERE SOC_COOP.SOC_ESTADO = '1' and SOC_COOP.COOP_ID =" + idcoop + " and REV_COACTIVAS.COOP_ID =" + idcoop + " AND REV_COACTIVAS.REV_ESTADO=0 and COACTIVAS.EST_ID=1 AND COACTIVAS.COAC_ESTADO_CARGA=1 and COACTIVAS.COAC_CEDULA='" + cedulaBuscar + "' ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");
                while (rs.next()) {
                    if ((rs.getString("COAC_ASUNTO").equals("DESBLOQUEO")) && (rs.getString("REV_ESTADO").equals("0"))) {
                        listarCoactivasDesb.add(new Coactivas(String.valueOf(rs.getInt("COAC_ID")), rs.getString("COAC_NUM"), rs.getString("COAC_CEDULA"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CAUSA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_VALOR"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS")));
                    }
                }
            }

            LazyCoactivasDesb = new LazyCoactivasTableModel(listarCoactivasDesb);

            //connection.close();
        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

//MÉTODO PARA  BUSCAR COACTIVA POR NÚMERO DE CEDULA EN LAS COACTIVAS CON ASUNTO DE INSOLVENCIA
    public void BusquedaCoactivasInso() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        listarCoactivasInso = new ArrayList<Coactivas>();

        String idcoop = (String) session.getAttribute("coopid");

        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();

            if (cedulaBuscar.equals("")) {

                ResultSet rs = statement.executeQuery("SELECT * FROM REV_COACTIVAS INNER JOIN SOC_COOP ON REV_COACTIVAS.SOC_ID = SOC_COOP.SOC_ID INNER JOIN COACTIVAS ON REV_COACTIVAS.COAC_ID = COACTIVAS.COAC_ID WHERE SOC_COOP.SOC_ESTADO = '1' and SOC_COOP.COOP_ID =" + idcoop + " and REV_COACTIVAS.COOP_ID =" + idcoop + " AND REV_COACTIVAS.REV_ESTADO=0 and COACTIVAS.EST_ID=1 AND COACTIVAS.COAC_ESTADO_CARGA=1 ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");
                while (rs.next()) {
                    if ((rs.getString("COAC_ASUNTO").equals("INSOLVENCIA")) && (rs.getString("REV_ESTADO").equals("0"))) {
                        listarCoactivasInso.add(new Coactivas(String.valueOf(rs.getInt("COAC_ID")), rs.getString("COAC_NUM"), rs.getString("COAC_CEDULA"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CAUSA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_VALOR"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS")));
                    }
                }
            } else {

                ResultSet rs = statement.executeQuery("SELECT * FROM REV_COACTIVAS INNER JOIN SOC_COOP ON REV_COACTIVAS.SOC_ID = SOC_COOP.SOC_ID INNER JOIN COACTIVAS ON REV_COACTIVAS.COAC_ID = COACTIVAS.COAC_ID WHERE SOC_COOP.SOC_ESTADO = '1' and SOC_COOP.COOP_ID =" + idcoop + " and REV_COACTIVAS.COOP_ID =" + idcoop + "  AND REV_COACTIVAS.REV_ESTADO=0 and COACTIVAS.EST_ID=1 AND COACTIVAS.COAC_ESTADO_CARGA=1 and COACTIVAS.COAC_CEDULA='" + cedulaBuscar + "' ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");
                while (rs.next()) {
                    if ((rs.getString("COAC_ASUNTO").equals("INSOLVENCIA")) && (rs.getString("REV_ESTADO").equals("0"))) {
                        listarCoactivasInso.add(new Coactivas(String.valueOf(rs.getInt("COAC_ID")), rs.getString("COAC_NUM"), rs.getString("COAC_CEDULA"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CAUSA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_VALOR"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS")));
                    }
                }
            }

            LazyCoactivasInso = new LazyCoactivasTableModel(listarCoactivasInso);

            //connection.close();
        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

//MÉTODO PARA  BUSCAR COACTIVA POR NÚMERO DE CEDULA EN LAS COACTIVAS CON ASUNTO DE INFORMACION
    public void BusquedaCoactivasInfo() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        listarCoactivasInfo = new ArrayList<Coactivas>();

        String idcoop = (String) session.getAttribute("coopid");

        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();

            if (cedulaBuscar.equals("")) {

                ResultSet rs = statement.executeQuery("SELECT * FROM REV_COACTIVAS INNER JOIN SOC_COOP ON REV_COACTIVAS.SOC_ID = SOC_COOP.SOC_ID INNER JOIN COACTIVAS ON REV_COACTIVAS.COAC_ID = COACTIVAS.COAC_ID WHERE SOC_COOP.SOC_ESTADO = '1' and SOC_COOP.COOP_ID =" + idcoop + " and REV_COACTIVAS.COOP_ID =" + idcoop + " AND REV_COACTIVAS.REV_ESTADO=0 and COACTIVAS.EST_ID=1 AND COACTIVAS.COAC_ESTADO_CARGA=1 ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");
                while (rs.next()) {
                    if ((rs.getString("COAC_ASUNTO").equals("INFORMACION")) && (rs.getString("REV_ESTADO").equals("0"))) {
                        listarCoactivasInfo.add(new Coactivas(String.valueOf(rs.getInt("COAC_ID")), rs.getString("COAC_NUM"), rs.getString("COAC_CEDULA"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CAUSA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_VALOR"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS")));
                    }
                }
            } else {

                ResultSet rs = statement.executeQuery("SELECT * FROM REV_COACTIVAS INNER JOIN SOC_COOP ON REV_COACTIVAS.SOC_ID = SOC_COOP.SOC_ID INNER JOIN COACTIVAS ON REV_COACTIVAS.COAC_ID = COACTIVAS.COAC_ID WHERE SOC_COOP.SOC_ESTADO = '1' and SOC_COOP.COOP_ID =" + idcoop + " and REV_COACTIVAS.COOP_ID =" + idcoop + "  AND REV_COACTIVAS.REV_ESTADO=0 and COACTIVAS.EST_ID=1 AND COACTIVAS.COAC_ESTADO_CARGA=1 and COACTIVAS.COAC_CEDULA='" + cedulaBuscar + "' ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");
                while (rs.next()) {
                    if ((rs.getString("COAC_ASUNTO").equals("INFORMACION")) && (rs.getString("REV_ESTADO").equals("0"))) {
                        listarCoactivasInfo.add(new Coactivas(String.valueOf(rs.getInt("COAC_ID")), rs.getString("COAC_NUM"), rs.getString("COAC_CEDULA"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CAUSA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_VALOR"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS")));
                    }
                }
            }

            LazyCoactivasInfo = new LazyCoactivasTableModel(listarCoactivasInfo);

            //connection.close();
        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //MÉTODO PARA  BUSCAR COACTIVA POR NÚMERO DE CEDULA EN LAS COACTIVAS PROCESADAS
    public void BusquedaCoactivasRev() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        listarCoactivasLeidas = new ArrayList<Coactivas>();

        String idcoop = (String) session.getAttribute("coopid");

        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();

            if (cedulaBuscar.equals("")) {

                ResultSet rs = statement.executeQuery("SELECT * FROM REV_COACTIVAS INNER JOIN SOC_COOP ON REV_COACTIVAS.SOC_ID = SOC_COOP.SOC_ID INNER JOIN COACTIVAS ON REV_COACTIVAS.COAC_ID = COACTIVAS.COAC_ID WHERE SOC_COOP.SOC_ESTADO = '1' and SOC_COOP.COOP_ID =" + idcoop + " and REV_COACTIVAS.COOP_ID =" + idcoop + " AND REV_COACTIVAS.REV_ESTADO=1 and COACTIVAS.EST_ID=1 AND COACTIVAS.COAC_ESTADO_CARGA=1 ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");
                while (rs.next()) {
                    if ((rs.getString("REV_ESTADO").equals("1"))) {
                        listarCoactivasLeidas.add(new Coactivas(String.valueOf(rs.getInt("COAC_ID")), rs.getString("COAC_NUM"), rs.getString("COAC_CEDULA"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CAUSA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_VALOR"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS")));
                    }
                }
            } else {

                ResultSet rs = statement.executeQuery("SELECT * FROM REV_COACTIVAS INNER JOIN SOC_COOP ON REV_COACTIVAS.SOC_ID = SOC_COOP.SOC_ID INNER JOIN COACTIVAS ON REV_COACTIVAS.COAC_ID = COACTIVAS.COAC_ID WHERE SOC_COOP.SOC_ESTADO = '1' and SOC_COOP.COOP_ID =" + idcoop + " and REV_COACTIVAS.COOP_ID =" + idcoop + "  AND REV_COACTIVAS.REV_ESTADO=1 and COACTIVAS.EST_ID=1 AND COACTIVAS.COAC_ESTADO_CARGA=1 and COACTIVAS.COAC_CEDULA='" + cedulaBuscar + "' ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");
                while (rs.next()) {
                    if ((rs.getString("REV_ESTADO").equals("1"))) {
                        listarCoactivasLeidas.add(new Coactivas(String.valueOf(rs.getInt("COAC_ID")), rs.getString("COAC_NUM"), rs.getString("COAC_CEDULA"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CAUSA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_VALOR"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS")));
                    }
                }
            }

            LazyCoactivasRev = new LazyCoactivasTableModel(listarCoactivasLeidas);

            //connection.close();
        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //Método para buscar coactivas por nombre de usuario 
    public void BusquedaCoactivasUsuario() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        listarCoactivasUsuario = new ArrayList<Coactivas>();

        String usucod = null;

        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();

            if (usuarioBuscar.equals("")) {

                listarCoactivasUsuario = null;

            } else {

                ResultSet r = statement.executeQuery("SELECT USU_ID FROM USUARIOS WHERE USU_USER='" + usuarioBuscar + "'");
                while (r.next()) {
                    usucod = String.valueOf(r.getInt("USU_ID"));
                    System.out.print(usucod);
                }

                ResultSet rs = statement.executeQuery("SELECT * FROM COACTIVAS INNER JOIN USUARIOS ON COACTIVAS.COAC_USUARIO_CARGA=USUARIOS.USU_ID WHERE COAC_USUARIO_CARGA=" + usucod + " ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");
                while (rs.next()) {

                    listarCoactivasUsuario.add(new Coactivas(rs.getString("COAC_NUM"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CEDULA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_CAUSA"), rs.getString("COAC_VALOR"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS"), rs.getString("COAC_OBSERVACIONES"), rs.getString("USU_USER"), rs.getInt("COAC_ESTADO_CARGA"), (format.format(rs.getDate("COAC_FECHA_CARGA")))));

                }

            }

            LazyCoactivasUsuarios = new LazyCoactivasTableModel(listarCoactivasUsuario);

            //connection.close();
        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //Método para buscar coactivas por número de coactiva y permite editar todos los campos para usuario AdminRenafipse
    public void BusquedaCoactivasNumero() {

        listarCoactivasNumero = new ArrayList<Coactivas>();

        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();

            if (numeroBuscar.equals("")) {

                listarCoactivasNumero = null;

            } else {

                ResultSet rs = statement.executeQuery("SELECT * FROM COACTIVAS INNER JOIN USUARIOS ON COACTIVAS.COAC_USUARIO_CARGA=USUARIOS.USU_ID WHERE COAC_NUM='" + numeroBuscar + "' ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");
                while (rs.next()) {

                    listarCoactivasNumero.add(new Coactivas(rs.getString("COAC_NUM"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CEDULA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_CAUSA"), rs.getString("COAC_VALOR"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS"), rs.getString("COAC_OBSERVACIONES"), rs.getString("USU_USER"), rs.getInt("COAC_ESTADO_CARGA"), (format.format(rs.getDate("COAC_FECHA_CARGA")))));

                }

            }

            LazyCoactivasNumero = new LazyCoactivasTableModel(listarCoactivasNumero);

            //connection.close();
        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //MÉTODO PARA BUSCAR COACTIVAS POR RANGO DE FECHA DE CARGA
    public void BusquedaCoactivasFechas() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        listarCoactivasFechas = new ArrayList<Coactivas>();

        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        String fechini, fechfin;
        fechini = format.format(fechaInicial);
        fechfin = format.format(fechaFinal);

        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();

            if ((fechaInicial == null) && (fechaInicial == null)) {

                listarCoactivasFechas = null;

            } else {

                ResultSet rs = statement.executeQuery("SELECT * FROM COACTIVAS INNER JOIN USUARIOS ON COACTIVAS.COAC_USUARIO_CARGA=USUARIOS.USU_ID WHERE COAC_FECHA_CARGA BETWEEN TO_DATE('" + fechini + "', 'DD/MM/YYYY') AND to_date('" + fechfin + "', 'DD/MM/YYYY') ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC ");

                System.out.print("SELECT * FROM COACTIVAS WHERE COAC_FECHA_CARGA BETWEEN TO_DATE('" + fechini + "', 'DD-MM-YYYY') AND to_date('" + fechfin + "', 'DD-MM-YYYY')");

                while (rs.next()) {

                    listarCoactivasFechas.add(new Coactivas(rs.getString("COAC_NUM"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CEDULA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_CAUSA"), rs.getString("COAC_VALOR"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS"), rs.getString("COAC_OBSERVACIONES"), rs.getString("USU_USER"), rs.getInt("COAC_ESTADO_CARGA"), (format.format(rs.getDate("COAC_FECHA_CARGA")))));

                }

            }

            LazyCoactivasFechas = new LazyCoactivasTableModel(listarCoactivasFechas);

            //connection.close();
        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //MÉTODO PARA BUSCAR COACTIVAS POR ANEXO Y TRÁMITE
    public void BusquedaCoactivasAnexo() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        listarCoactivasAnexo = new ArrayList<Coactivas>();

        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();

            if (tramiteBuscar.equals("")) {
                System.out.print("SELECT * FROM COACTIVAS INNER JOIN USUARIOS ON COACTIVAS.COAC_USUARIO_CARGA=USUARIOS.USU_ID WHERE COAC_ANEXO ='" + anexoBuscar + "' ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");

                ResultSet rs = statement.executeQuery("SELECT * FROM COACTIVAS INNER JOIN USUARIOS ON COACTIVAS.COAC_USUARIO_CARGA=USUARIOS.USU_ID WHERE COAC_ANEXO ='" + anexoBuscar + "' ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");
                while (rs.next()) {
                    listarCoactivasAnexo.add(new Coactivas(rs.getString("COAC_NUM"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CEDULA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_CAUSA"), rs.getString("COAC_VALOR"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS"), rs.getString("COAC_OBSERVACIONES"), rs.getString("USU_USER"), rs.getInt("COAC_ESTADO_CARGA"), (format.format(rs.getDate("COAC_FECHA_CARGA")))));
                }

            } else {
                ResultSet rs = statement.executeQuery("SELECT * FROM COACTIVAS INNER JOIN USUARIOS ON COACTIVAS.COAC_USUARIO_CARGA=USUARIOS.USU_ID WHERE COAC_ANEXO ='" + anexoBuscar + "' AND COAC_TRAMITE='" + tramiteBuscar + "' ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");
                while (rs.next()) {
                    listarCoactivasAnexo.add(new Coactivas(rs.getString("COAC_NUM"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CEDULA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_CAUSA"), rs.getString("COAC_VALOR"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS"), rs.getString("COAC_OBSERVACIONES"), rs.getString("USU_USER"), rs.getInt("COAC_ESTADO_CARGA"), (format.format(rs.getDate("COAC_FECHA_CARGA")))));
                }
            }

            LazyCoactivasAnexo = new LazyCoactivasTableModel(listarCoactivasAnexo);

            if (anexoBuscar.equals("")) {

                listarCoactivasAnexo = null;

            }

            LazyCoactivasAnexo = new LazyCoactivasTableModel(listarCoactivasAnexo);

            //connection.close();
        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //MÉTODO PARA  BUSCAR COACTIVAS CON ESTADO DE PENDIENTES CUYA CEDULA/RUC NO HA SIDO VALIDADA
    public void BusquedaCoactivasPendientes() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        listarCoactivasPendientes = new ArrayList<Coactivas>();

        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();

            if (cedulaBuscar.equals("")) {

                ResultSet rs = statement.executeQuery("SELECT * FROM COACTIVAS INNER JOIN USUARIOS ON COAC_USUARIO_CARGA=USU_ID WHERE COAC_ESTADO_CARGA=0 ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");
                while (rs.next()) {
                    listarCoactivasPendientes.add(new Coactivas(rs.getString("COAC_NUM"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CEDULA"), rs.getString("COAC_CAUSA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_VALOR"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS"), rs.getString("COAC_OBSERVACIONES"), rs.getString("USU_USER"), rs.getInt("COAC_ESTADO_CARGA"), (format.format(rs.getDate("COAC_FECHA_CARGA")))));

                }
            } else {

                ResultSet rs = statement.executeQuery("SELECT * FROM COACTIVAS INNER JOIN USUARIOS ON COAC_USUARIO_CARGA=USU_ID WHERE COAC_ESTADO_CARGA=0 AND COAC_CEDULA='" + cedulaBuscar + "' ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC ");
                while (rs.next()) {
                    listarCoactivasPendientes.add(new Coactivas(rs.getString("COAC_NUM"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CEDULA"), rs.getString("COAC_CAUSA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_VALOR"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS"), rs.getString("COAC_OBSERVACIONES"), rs.getString("USU_USER"), rs.getInt("COAC_ESTADO_CARGA"), (format.format(rs.getDate("COAC_FECHA_CARGA")))));

                }
            }

            LazyCoactivasPendientes = new LazyCoactivasTableModel(listarCoactivasPendientes);

            //connection.close();
        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

//MÉTODO PARA  BUSCAR COACTIVA POR NÚMERO DE CEDULA EN LAS COACTIVAS CON ASUNTO DE BLOQUEO
    public void buscaCoactivas() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        listarCoactivas = new ArrayList<Coactivas>();

        String idcoop = (String) session.getAttribute("coopid");

        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();

            if (cedulaBuscar.equals("")) {

                ResultSet rs = statement.executeQuery("SELECT * FROM REV_COACTIVAS INNER JOIN SOC_COOP ON REV_COACTIVAS.SOC_ID = SOC_COOP.SOC_ID INNER JOIN COACTIVAS ON REV_COACTIVAS.COAC_ID = COACTIVAS.COAC_ID WHERE SOC_COOP.SOC_ESTADO = '1' and SOC_COOP.COOP_ID =" + idcoop + " and REV_COACTIVAS.COOP_ID =" + idcoop + " AND COACTIVAS.COAC_ESTADO_CARGA=1 AND COACTIVAS.EST_ID=1 ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");
                System.out.print(rs);
                while (rs.next()) {
                    listarCoactivas.add(new Coactivas(String.valueOf(rs.getInt("COAC_ID")), rs.getString("COAC_NUM"), rs.getString("COAC_CEDULA"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CAUSA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_VALOR"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS")));
                }
            } else {

                ResultSet rs = statement.executeQuery("SELECT * FROM REV_COACTIVAS INNER JOIN SOC_COOP ON REV_COACTIVAS.SOC_ID = SOC_COOP.SOC_ID INNER JOIN COACTIVAS ON REV_COACTIVAS.COAC_ID = COACTIVAS.COAC_ID WHERE SOC_COOP.SOC_ESTADO = '1' and SOC_COOP.COOP_ID =" + idcoop + " and REV_COACTIVAS.COOP_ID =" + idcoop + " AND COACTIVAS.COAC_ESTADO_CARGA=1 AND COACTIVAS.EST_ID=1 and COACTIVAS.COAC_CEDULA='" + cedulaBuscar + "' ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");
                System.out.print(rs);
                //ResultSet rs = statement.executeQuery("Select * from COACTIVAS where coac_asunto='BLOQUEO'");
                while (rs.next()) {
                    listarCoactivas.add(new Coactivas(String.valueOf(rs.getInt("COAC_ID")), rs.getString("COAC_NUM"), rs.getString("COAC_CEDULA"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CAUSA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_VALOR"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS")));

                }

            }

            LazyCoactivas = new LazyCoactivasTableModel(listarCoactivas);

            //connection.close();
        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //MÉTODO PARA  OBTENER EL CODIGO DE USUARIO QUE INICIA SESION
    public String ObtenerCodigoUsuario() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        String usu = (String) session.getAttribute("username");
        String usucod = null;

        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT USU_ID FROM USUARIOS WHERE USU_USER='" + usu + "'");
            while (rs.next()) {
                usucod = String.valueOf(rs.getInt("USU_ID"));
            }
            //connection.close();

        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return usucod;
    }

    //MÉTODO PARA BUSCAR EN LA BBDD DE COACTIVAS POR NÚMERO DE CEDULA EN LAS COACTIVAS CON ASUNTO DE BLOQUEO E INSOLVENCIA
    public void consultaCoactivas() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        UIViewRoot uiViewRoot = facesContext.getViewRoot();
        HtmlCommandButton btnDetalle = null;

        btnDetalle = (HtmlCommandButton) uiViewRoot.findComponent("up:tab1:tablacoactodas:b1");

        listarCoactivasTodas = new ArrayList<Coactivas>();

        String tipo = null;

        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();

//            ResultSet rs = statement.executeQuery("SELECT * FROM COACTIVAS WHERE (COAC_CEDULA='" + cedulaBuscar + "' AND COAC_ASUNTO='BLOQUEO') OR (COAC_CEDULA='" + cedulaBuscar + "'AND COAC_ASUNTO='INSOLVENCIA') ");
            ResultSet rs = statement.executeQuery("SELECT * FROM COACTIVAS WHERE COAC_CEDULA='" + cedulaBuscar + "' AND COACTIVAS.COAC_ESTADO_CARGA=1  AND EST_ID=1");

            while (rs.next()) {

                listarCoactivasTodas.add(new Coactivas(rs.getString("COAC_NUM"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CEDULA"),rs.getString("COAC_TRAMITE") , rs.getString("COAC_CAUSA"), rs.getString("COAC_VALOR"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_OFICIO"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS"), rs.getString("COAC_OBSERVACIONES"), "", rs.getInt("COAC_ESTADO_CARGA"), ""));

            }

            ResultSet rsPEPS = statement.executeQuery("SELECT P.PEP_ID, P.PEP_IDENTIDAD, P.PEP_NOMBRES, P.PEP_PUESTO, P.PEP_ENTIDAD, P.PEP_ESTADO FROM PEPS P WHERE P.PEP_IDENTIDAD='" + cedulaBuscar + "' ORDER BY P.PEP_NOMBRES ");
            while (rsPEPS.next()) {

                listarCoactivasTodas.add(new Coactivas("", rsPEPS.getString("PEP_NOMBRES"), "", rsPEPS.getString("PEP_IDENTIDAD"), rsPEPS.getString("PEP_PUESTO"), rsPEPS.getString("PEP_ENTIDAD"), rsPEPS.getString("PEP_ESTADO"), "PEPS", "", "", "", "", "", "", "", ""));

            }

            ResultSet rsSentenciados = statement.executeQuery("SELECT SE.SETE_IDENTIFICACION, SE.SETE_APELLIDOS, SE.SETE_NOMBRES FROM SETENCIADOS SE  WHERE SE.SETE_IDENTIFICACION='" + cedulaBuscar + "'  ORDER BY SE.SETE_APELLIDOS DESC");
            while (rsSentenciados.next()) {
                listarCoactivasTodas.add(new Coactivas("", rsSentenciados.getString("SETE_NOMBRES"), rsSentenciados.getString("SETE_APELLIDOS"), rsSentenciados.getString("SETE_IDENTIFICACION"), "", "", "", "SENTENCIADOS", "", "", "", "", "", "", "", ""));

            }

            ResultSet rsHomonimos = statement.executeQuery("SELECT H.HOMO_IDENTIFICACION, H.HOMO_APELLIDOS, H.HOMO_NOMBRES FROM HOMONIMOS H WHERE H.HOMO_IDENTIFICACION='" + cedulaBuscar + "'  ORDER BY H.HOMO_NOMBRES");
            while (rsHomonimos.next()) {

                listarCoactivasTodas.add(new Coactivas("", rsHomonimos.getString("HOMO_NOMBRES"), rsHomonimos.getString("HOMO_APELLIDOS"), rsHomonimos.getString("HOMO_IDENTIFICACION"), "", "", "", "HOMONIMOS", "", "", "", "", "", "", "", ""));

            }

            LazyCoactivasTodas = new LazyCoactivasTableModel(listarCoactivasTodas);

            for (int i = 0; i < listarCoactivasTodas.size(); i++) {
                tipo = listarCoactivasTodas.get(i).numerTramite;
                System.out.println(tipo);
                if (tipo.equals("PEPS") || (tipo.equals("SENTENCIADOS")) || (tipo.equals("HOMONIMOS"))) {
                    btnDetalle.setDisabled(true);

                }
            }

            //connection.close();
        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public boolean isDouble(String val) {
        boolean num = false;
        try {
            double valorcoac = Double.parseDouble(val);
            num = true;
        } catch (NumberFormatException ex) {
            num = false;
        }
        return num;
    }

    //INSERTA EN LA TABLA COACTIVAS LAS MISMAS QUE SON SUBIDAS DESDE UN ARCHIVO EN EXCEL
    public FacesMessage insertarCoac(List<Coactivas> listaCoac) {
        int coacsubidas = 0;
        FacesMessage msg = null;
        for (int i = 0; i < listaCoac.size(); i++) {
            subiendo = true;
            if (!existeCoactiva(listaCoac.get(i).numeroCoactiva.trim())) {
                if (!listaCoac.get(i).asunto.trim().equals("") && listaCoac.get(i).asunto.trim().length() != 0 && !listaCoac.get(i).asunto.trim().equals("VACIO")) {
                    //if (!existeRevCoactiva(listaCoac.get(i).numeroCoactiva.trim())) {
                    Connection dbConnection = null;
                    Statement statement = null;
                    PreparedStatement preparedStatement = null;

                    String insertTableSQL = "INSERT INTO COACTIVAS"
                            + "(COAC_ID,COAC_NUM,COAC_NOMBRES,COAC_APELLIDOS,COAC_CEDULA,COAC_CAUSA,COAC_ASUNTO,COAC_VALOR,COAC_TRAMITE,COAC_OFICIO,COAC_ANEXO,COAC_FECHA,COAC_JUICIO,COAC_CIUDAD,COAC_REMITENTE,COAC_CARGOREMITENTE,EST_ID,COAC_CIRCULAR_SEPS) VALUES"
                            + "(SEC_ID_COAC.NextVal,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    try {

                        dbConnection = conexion();
                        preparedStatement = dbConnection.prepareStatement(insertTableSQL);

                        preparedStatement.setString(1, listaCoac.get(i).numeroCoactiva.trim());

                        preparedStatement.setString(2, listaCoac.get(i).nombre.trim());
                        preparedStatement.setString(3, listaCoac.get(i).apellido.trim());
                        preparedStatement.setString(4, listaCoac.get(i).cedula.trim());
                        preparedStatement.setString(5, listaCoac.get(i).causa.trim());
                        preparedStatement.setString(6, listaCoac.get(i).asunto.trim());
                        preparedStatement.setString(7, listaCoac.get(i).valor.trim());
                        preparedStatement.setString(8, listaCoac.get(i).numerTramite.trim());
                        preparedStatement.setString(9, listaCoac.get(i).numeroOficio.trim());
                        preparedStatement.setString(10, listaCoac.get(i).numeroAnexo.trim());
                        preparedStatement.setString(11, listaCoac.get(i).fecha.trim());
                        preparedStatement.setString(12, listaCoac.get(i).numeroJuicio.trim());
                        preparedStatement.setString(13, listaCoac.get(i).Ciudad.trim());
                        preparedStatement.setString(14, listaCoac.get(i).Remitente.trim());
                        preparedStatement.setString(15, listaCoac.get(i).cargoRemitente.trim());
                        preparedStatement.setInt(16, 0);
                        preparedStatement.setString(17, listaCoac.get(i).numeroCircularSeps.trim());

                        preparedStatement.executeUpdate();
                        coacsubidas++;
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Estado! ", "Subido Correctamente: " + (i + 1) + " de " + listaCoac.size());
                        System.out.println("Subido : " + ((i + 1) * 100 / listaCoac.size()) + "% " + (i + 1) + " de " + listaCoac.size() + " - Coactivas: " + listaCoac.get(i).numeroCoactiva.trim());

                        subiendo = true;

                        //System.out.println("Registro Insertado en Coactivas!");
                    } catch (SQLException e) {
                        subiendo = false;
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", e.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                        System.out.println(timestamp() + " - Metodo InsertarCoac: " + e.getMessage());
                        Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, e);

                    } finally {

                        if (preparedStatement != null) {
                            try {
                                preparedStatement.close();
                            } catch (SQLException ex) {
                                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                                System.out.println(timestamp() + " - Metodo InsertarCoac: " + ex.getMessage());
                            }
                        }

                        if (dbConnection != null) {
                            try {
                                dbConnection.close();
                            } catch (SQLException ex) {
                                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                                System.out.println(timestamp() + " - Metodo InsertarCoac: " + ex.getMessage());
                            }
                        }
                    }
                } else {
                    System.out.println(timestamp() + " - Coactivas sin Asunto: " + listaCoac.get(i).numeroCoactiva.trim());
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Estado! ", "Registros sin Asunto: Subidos: " + coacsubidas + " de " + listaCoac.size());

                }
            } else {
                System.out.println(timestamp() + " - Coactivas en BD: " + listaCoac.get(i).numeroCoactiva.trim());
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Estado! ", "Registros ya en BD: Subidos: " + coacsubidas + " de " + listaCoac.size());

            }
            consultarCoac(listaCoac.get(i).cedula.trim());
        }

        return msg;

    }

    //METODO CON PROCEDIMIENTO ALMACENADO QUE BUSCA SOCIOS CON COACTIVAS
    public void consultarCoac(String ced) {

        Connection connection = conexion();
        PreparedStatement preparedStatement = null;

        //String insertTableSQL = "{call INSERTARCOACTIVAS(?)}";
        String insertTableSQL = "{call INSERTARCOACTIVAS(?)}";

        try {
            //dbConnection = conexion();
            preparedStatement = connection.prepareStatement(insertTableSQL);

            preparedStatement.setString(1, ced);

            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException e) {

            System.out.println(e.getMessage());
            e.printStackTrace();

        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    System.out.println(timestamp() + " - Metodo ConsultarCoac: " + ex.getMessage());
                }
            }

        }

    }

    //METODO PARA CAMBIAR DE ESTADO UNA COACTIVA A REVISADA
    public void leerCoac() {
        leido = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "UPDATE REV_COACTIVAS SET REV_ESTADO = ? WHERE REV_ID = ?";

        try {
            System.out.println("Revisado: " + this.selectedCoactiva.rev_id);
            dbConnection = conexion();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, this.selectedCoactiva.rev_id);

            preparedStatement.executeUpdate();
            this.LazyCoactivas = null;
            this.LazyCoactivasBloq = null;
            this.LazyCoactivasDesb = null;
            this.LazyCoactivasRev = null;
            this.LazyCoactivasInso = null;
            this.LazyCoactivasInfo = null;

            if (LazyCoactivas == null) {

                this.listarCoactivas();
            }
            //System.out.println("Coactiva " + this.selectedCoactiva.rev_id + " Leida");
            leido = true;

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Revisado", this.selectedCoactiva.numeroCoactiva + " " + this.selectedCoactiva.nombre + " " + this.selectedCoactiva.apellido);

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
                    System.out.println("Metodo LeerCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException ex) {
                    System.out.println("Metodo LeerCoac: " + ex.getMessage());
                }
            }
        }

        requestContext.addCallbackParam("estaGuardado", leido);
        requestContext.update("up:growl");
        requestContext.update("up:tab1:tablacoacBloq");
        requestContext.update("up:tab1:tablacoacDes");
        requestContext.update("up:tab1:tablacoacleidas");

        requestContext.update("up:tab1:tablacoacInso");
        requestContext.update("up:tab1:tablacoacInfo");

        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    //METODO PARA CAMBIAR DE ESTADO UNA COACTIVA A NO REVISADA
    public void cambiarEstadoCoac() {
        leido = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "UPDATE REV_COACTIVAS SET REV_ESTADO = ? WHERE REV_ID = ?";

        try {
            System.out.println(this.selectedCoactiva.rev_id);
            dbConnection = conexion();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, this.selectedCoactiva.rev_id);

            preparedStatement.executeUpdate();

            //System.out.println("Coactiva " + this.selectedCoactiva.rev_id + " Leida");
            leido = true;

            this.LazyCoactivas = null;
            this.LazyCoactivasBloq = null;
            this.LazyCoactivasDesb = null;
            this.LazyCoactivasRev = null;
            this.LazyCoactivasInso = null;
            this.LazyCoactivasInfo = null;

            if (LazyCoactivas == null) {

                this.listarCoactivas();
            }

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Revisado", this.selectedCoactiva.numeroCoactiva + " " + this.selectedCoactiva.nombre + " " + this.selectedCoactiva.apellido);

        } catch (SQLException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No se pudo leer");
            leido = false;
            System.out.println("Metodo CambiarEstadoCoac: " + e.getMessage());
            //e.printStackTrace();

        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    System.out.println("Metodo CambiarEstadoCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException ex) {
                    System.out.println("Metodo CambiarEstadoCoac: " + ex.getMessage());
                }
            }
        }

        requestContext.addCallbackParam("estaGuardado", leido);
        requestContext.update("up:growl");
        requestContext.update("up:tab1:tablacoacDes");
        requestContext.update("up:tab1:tablacoacleidas");
        requestContext.update("up:tab1:tablacoacBloq");
        requestContext.update("up:tab1:tablacoacInso");
        requestContext.update("up:tab1:tablacoacInfo");
        FacesContext.getCurrentInstance().addMessage(null, message);

    }
//MÉTODO QUE OBTIENE LA FECHA ACTUAL DEL SISTEMA    

    public String getFechaactual() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fecha = new java.util.Date();
        return format.format(fecha).trim();
    }

    //MÉTODO PARA VALIDACION DE CEDULA/RUC DE COACTIVAS ANTES DE CARGARLA A LA BBDD:
    //ACTIVA BOTÓN GUARDAR O PENDIENTE SEGÚN VALIDACIÓN
    //1 CARGADO 0 PENDIENTE
    public boolean Validacion() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot uiViewRoot = facesContext.getViewRoot();
        HtmlCommandButton btnGuardar = null;
        HtmlCommandButton btnPendientes = null;

        btnGuardar = (HtmlCommandButton) uiViewRoot.findComponent(":FormTabla:b1");
        btnPendientes = (HtmlCommandButton) uiViewRoot.findComponent(":FormTabla:b2");

        if (validacionCedula(this.cedula) || validaRucEP(this.cedula) || validacionRUC(this.cedula)) {
            btnGuardar.setDisabled(false);
            btnPendientes.setDisabled(true);
            this.estado = 1;
            return true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Cédula o RUC Inválidos"));
            btnGuardar.setDisabled(true);
            btnPendientes.setDisabled(false);
            this.estado = 0;
            return false;
        }

    }

    ///MÉTODO QUE LISTA LAS COACTIVAS CON ESTADO DE PENDIENTE
    public void listarCoactivasPendientes() {
        listarCoactivasPendientes = new ArrayList<Coactivas>();
        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM COACTIVAS INNER JOIN USUARIOS ON COAC_USUARIO_CARGA=USU_ID WHERE COAC_ESTADO_CARGA=0 ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");

            while (rs.next()) {
                //String asunto = Fila_hssf.getCell(5) + "";
                listarCoactivasPendientes.add(new Coactivas(rs.getString("COAC_NUM"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CEDULA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_CAUSA"), rs.getString("COAC_VALOR"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS"), rs.getString("COAC_OBSERVACIONES"), rs.getString("USU_USER"), rs.getInt("COAC_ESTADO_CARGA"), (format.format(rs.getDate("COAC_FECHA_CARGA")))));

            }

            LazyCoactivasPendientes = new LazyCoactivasTableModel(listarCoactivasPendientes);

            //connection.close();
        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

//MÉTODO PARA CAMBIAR ESTADO DE PENDIENTE Y ACTUALIZAR LOS DATOS DE LA COACTIVA POR USUARIO ADMIN RENAFIPSE      
    public void ActualizaCoactiva() {

        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "UPDATE COACTIVAS SET COAC_NOMBRES = ?, COAC_APELLIDOS =?, COAC_CEDULA =?, COAC_CAUSA=?, COAC_ASUNTO=?, COAC_VALOR=?, COAC_TRAMITE=?, COAC_OFICIO=?, COAC_ANEXO=?, COAC_FECHA=?, COAC_JUICIO=?, COAC_CIUDAD=?, COAC_REMITENTE=?, COAC_CARGOREMITENTE=?, COAC_OBSERVACIONES=?, COAC_CIRCULAR_SEPS=?, COAC_ESTADO_CARGA=? WHERE COAC_NUM = ?";

        try {
            System.out.println("Actualizado: " + this.selectedCoactiva.nombre);
            dbConnection = conexion();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setString(1, this.selectedCoactiva.nombre);
            preparedStatement.setString(2, this.selectedCoactiva.apellido);
            preparedStatement.setString(3, this.selectedCoactiva.cedula);
            preparedStatement.setString(4, this.selectedCoactiva.causa);
            preparedStatement.setString(5, this.selectedCoactiva.asunto);
            preparedStatement.setString(6, this.selectedCoactiva.valor);
            preparedStatement.setString(7, this.selectedCoactiva.numerTramite);
            preparedStatement.setString(8, this.selectedCoactiva.numeroOficio);
            preparedStatement.setString(9, this.selectedCoactiva.numeroAnexo);
            preparedStatement.setString(10, this.selectedCoactiva.fecha);
            preparedStatement.setString(11, this.selectedCoactiva.numeroJuicio);
            preparedStatement.setString(12, this.selectedCoactiva.Ciudad);
            preparedStatement.setString(13, this.selectedCoactiva.Remitente);
            preparedStatement.setString(14, this.selectedCoactiva.cargoRemitente);
            preparedStatement.setString(15, "ACTUALIZADO");
            preparedStatement.setString(16, this.selectedCoactiva.numeroCircularSeps);
            preparedStatement.setInt(17, 1);
            preparedStatement.setString(18, this.selectedCoactiva.numeroCoactiva);
            preparedStatement.executeUpdate();
            consultarCoac(this.selectedCoactiva.cedula.trim());
            this.LazyCoactivasPendientes = null;

            if (LazyCoactivasPendientes == null) {

                this.listarCoactivasPendientes();
            }
            //System.out.println("Coactiva " + this.selectedCoactiva.rev_id + " Leida");
            leido = true;

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", this.selectedCoactiva.cedula + " " + this.selectedCoactiva.nombre + " " + this.selectedCoactiva.apellido);

        } catch (SQLException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No se pudo Actualizar");
            leido = false;
            System.out.println(e.getMessage());
            e.printStackTrace();

        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    System.out.println("Metodo LeerCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException ex) {
                    System.out.println("Metodo LeerCoac: " + ex.getMessage());
                }
            }
        }

        requestContext.addCallbackParam("estaActualizado", leido);
        requestContext.update("up:growl");
        requestContext.update("up:tablacoactodas");

        FacesContext.getCurrentInstance().addMessage(null, message);

    }
    //MÉTODO PARA ACTUALIZAR DATOS DE COACTIVA POR PARTE DEL USUARIO CARGA O USUARIO ADMINRENAFIPSE

    public void ActualizaCoactivaUsuario() {

        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "UPDATE COACTIVAS SET COAC_NOMBRES = ?, COAC_APELLIDOS =?, COAC_CEDULA =?, COAC_CAUSA=?, COAC_ASUNTO=?, COAC_VALOR=?, COAC_TRAMITE=?, COAC_OFICIO=?, COAC_ANEXO=?, COAC_FECHA=?, COAC_JUICIO=?, COAC_CIUDAD=?, COAC_REMITENTE=?, COAC_CARGOREMITENTE=?, COAC_OBSERVACIONES=?, COAC_CIRCULAR_SEPS=?, COAC_ESTADO_CARGA=? WHERE COAC_NUM = ?";

        try {
            System.out.println("Actualizado: " + this.selectedCoactiva.nombre);
            dbConnection = conexion();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setString(1, this.selectedCoactiva.nombre);
            preparedStatement.setString(2, this.selectedCoactiva.apellido);
            preparedStatement.setString(3, this.selectedCoactiva.cedula);
            preparedStatement.setString(4, this.selectedCoactiva.causa);
            preparedStatement.setString(5, this.selectedCoactiva.asunto);
            preparedStatement.setString(6, this.selectedCoactiva.valor);
            preparedStatement.setString(7, this.selectedCoactiva.numerTramite);
            preparedStatement.setString(8, this.selectedCoactiva.numeroOficio);
            preparedStatement.setString(9, this.selectedCoactiva.numeroAnexo);
            preparedStatement.setString(10, this.selectedCoactiva.fecha);
            preparedStatement.setString(11, this.selectedCoactiva.numeroJuicio);
            preparedStatement.setString(12, this.selectedCoactiva.Ciudad);
            preparedStatement.setString(13, this.selectedCoactiva.Remitente);
            preparedStatement.setString(14, this.selectedCoactiva.cargoRemitente);
            preparedStatement.setString(15, "ACTUALIZADO");
            preparedStatement.setString(16, this.selectedCoactiva.numeroCircularSeps);
            preparedStatement.setInt(17, 1);
            preparedStatement.setString(18, this.selectedCoactiva.numeroCoactiva);
            preparedStatement.executeUpdate();
            
            this.LazyCoactivasPendientes = null;

            if (LazyCoactivasPendientes == null) {

                this.listarCoactivasPendientes();
            }
            //System.out.println("Coactiva " + this.selectedCoactiva.rev_id + " Leida");
            leido = true;

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", this.selectedCoactiva.cedula + " " + this.selectedCoactiva.nombre + " " + this.selectedCoactiva.apellido);

        } catch (SQLException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No se pudo Actualizar");
            leido = false;
            System.out.println(e.getMessage());
            e.printStackTrace();

        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    System.out.println("Metodo LeerCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException ex) {
                    System.out.println("Metodo LeerCoac: " + ex.getMessage());
                }
            }
        }

        requestContext.addCallbackParam("estaActualizado", leido);
        requestContext.update("up:growl");
        requestContext.update("up:tab1");

        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    //MÉTODO PARA GUARDAR COACTIVA MÚLTIPLE (CAMPOS COMUNES) 
    public void GuardarCoactiva() {

        FacesMessage message = null;

        Connection dbConnection = null;

        PreparedStatement preparedStatement = null;

        int usu = Integer.parseInt(ObtenerCodigoUsuario());
        this.fechaCarga = getFechaactual();

        String insertTableSQL = "INSERT INTO COACTIVAS"
                + "(COAC_ID, COAC_NUM, COAC_NOMBRES, COAC_APELLIDOS, COAC_CEDULA, COAC_CAUSA, COAC_ASUNTO, COAC_VALOR, COAC_TRAMITE, \n"
                + "COAC_OFICIO, COAC_ANEXO, COAC_FECHA, COAC_JUICIO, COAC_CIUDAD, COAC_REMITENTE, COAC_CARGOREMITENTE,\n"
                + "COAC_CIRCULAR_SEPS,COAC_USUARIO_CARGA, COAC_ESTADO_CARGA, COAC_FECHA_CARGA,COAC_OBSERVACIONES) VALUES"
                + "(SEC_ID_COAC.NextVal,SEC_NUM_COAC.NextVal,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            dbConnection = conexion();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setString(1, this.nombre.toUpperCase());
            preparedStatement.setString(2, this.apellido.toUpperCase());
            preparedStatement.setString(3, this.cedula);
            preparedStatement.setString(4, this.causa.toUpperCase());
            preparedStatement.setString(5, this.asunto.toUpperCase());
            preparedStatement.setString(6, this.valor);
            preparedStatement.setString(7, this.numerTramite.toUpperCase());
            preparedStatement.setString(8, this.numeroOficio.toUpperCase());
            preparedStatement.setString(9, this.numeroAnexo);
            preparedStatement.setString(10, format.format(this.formatoFecha));
            preparedStatement.setString(11, this.numeroJuicio.toUpperCase());
            preparedStatement.setString(12, this.Ciudad.toUpperCase());
            preparedStatement.setString(13, this.Remitente.toUpperCase());
            preparedStatement.setString(14, this.cargoRemitente.toUpperCase());

            preparedStatement.setString(15, "SEPS-SGD-SGE-2017-" + this.numeroAnexo);
            preparedStatement.setInt(16, usu);
            preparedStatement.setInt(17, this.estado);
            preparedStatement.setString(18, this.fechaCarga);
            if (this.observaciones == null) {
                this.observaciones = " ";
            }
            preparedStatement.setString(19, this.observaciones.toUpperCase());

            preparedStatement.executeUpdate();

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Coactiva Agregada", this.cedula.toUpperCase());

            System.out.println("Registro Insertado en Coactivas!");
            System.out.println("Usuario: " + usu);
            consultarCoac(cedula.trim());

            //RequestContext requestContext = RequestContext.getCurrentInstance();
//                guardado = true;
            this.nombre = "";
            this.apellido = "";
            this.cedula = "";
            this.valor = "";
            this.numeroJuicio = "";
            this.observaciones = "";
            this.numeroOficio = "";
            this.asunto = "";

        } catch (SQLException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No se Guardo el Registro");
            System.out.println(e.getMessage());

        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

//MÉTODO PARA GUARDAR COACTIVA INDIVIDUAL (CAMPOS NO COMUNES Y LIMPIA TODOS LOS CAMPOS)   
    public void GuardarCoactivaIndividual() {

        FacesMessage message = null;

        Connection dbConnection = null;

        PreparedStatement preparedStatement = null;

        int usu = Integer.parseInt(ObtenerCodigoUsuario());
        this.fechaCarga = getFechaactual();

        String insertTableSQL = "INSERT INTO COACTIVAS"
                + "(COAC_ID, COAC_NUM, COAC_NOMBRES, COAC_APELLIDOS, COAC_CEDULA, COAC_CAUSA, COAC_ASUNTO, COAC_VALOR, COAC_TRAMITE, \n"
                + "COAC_OFICIO, COAC_ANEXO, COAC_FECHA, COAC_JUICIO, COAC_CIUDAD, COAC_REMITENTE, COAC_CARGOREMITENTE,\n"
                + "COAC_CIRCULAR_SEPS,COAC_USUARIO_CARGA, COAC_ESTADO_CARGA, COAC_FECHA_CARGA,COAC_OBSERVACIONES) VALUES"
                + "(SEC_ID_COAC.NextVal,SEC_NUM_COAC.NextVal,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            dbConnection = conexion();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setString(1, this.nombre.toUpperCase());
            preparedStatement.setString(2, this.apellido.toUpperCase());
            preparedStatement.setString(3, this.cedula);
            preparedStatement.setString(4, this.causa.toUpperCase());
            preparedStatement.setString(5, this.asunto.toUpperCase());
            preparedStatement.setString(6, this.valor);
            preparedStatement.setString(7, this.numerTramite);
            preparedStatement.setString(8, this.numeroOficio.toUpperCase());
            preparedStatement.setString(9, this.numeroAnexo);
            preparedStatement.setString(10, format.format(this.formatoFecha));
            preparedStatement.setString(11, this.numeroJuicio.toUpperCase());
            preparedStatement.setString(12, this.Ciudad.toUpperCase());
            preparedStatement.setString(13, this.Remitente.toUpperCase());
            preparedStatement.setString(14, this.cargoRemitente.toUpperCase());

            preparedStatement.setString(15, "SEPS-SGD-SGE-2017-" + this.numeroAnexo);
            preparedStatement.setInt(16, usu);
            preparedStatement.setInt(17, this.estado);
            preparedStatement.setString(18, this.fechaCarga);
            if (this.observaciones == null) {
                this.observaciones = " ";
            }
            preparedStatement.setString(19, this.observaciones.toUpperCase());
            preparedStatement.executeUpdate();

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Coactiva Agregada", this.cedula.toUpperCase());

            System.out.println("Registro Insertado en Coactiva!");
            System.out.println("Usuario: " + usu);
            consultarCoac(cedula.trim());

            //RequestContext requestContext = RequestContext.getCurrentInstance();
//                guardado = true;
            this.nombre = "";
            this.apellido = "";
            this.cedula = "";
            this.valor = "";
            this.numeroJuicio = "";
            this.numeroCoactiva = "";
            this.numeroAnexo = "";
            this.numerTramite = "";
            this.numeroCircularSeps = "";
            this.numeroJuicio = "";
            this.numeroOficio = "";
            this.Remitente = "";
            this.cargoRemitente = "";
            this.Ciudad = "";
            this.causa = "";
            this.formatoFecha = null;
            this.asunto = "";
            this.valor = "";
            this.observaciones = "";

        } catch (SQLException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "No se Guardo el Registro");
            System.out.println(e.getMessage());

        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

//MÉTODO PARA LISTAR EL HISTORIAL DE CARGA DE COACTIVAS USUARIO CARGA RENAFIPSE
    public void listarHistorialCoactivas() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        int usu = Integer.parseInt(ObtenerCodigoUsuario());

        listarCoactivasHistorial = new ArrayList<Coactivas>();
        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery("Select * from coactivas inner join usuarios on coac_usuario_carga=usu_id where coac_usuario_carga='" + usu + "' ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");

            while (rs.next()) {
                //String asunto = Fila_hssf.getCell(5) + "";
                listarCoactivasHistorial.add(new Coactivas(rs.getString("COAC_NUM"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CEDULA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_CAUSA"), rs.getString("COAC_VALOR"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS"), rs.getString("COAC_OBSERVACIONES"), rs.getString("USU_USER"), rs.getInt("COAC_ESTADO_CARGA"), (format.format(rs.getDate("COAC_FECHA_CARGA")))));

            }

            LazyCoactivasHistorial = new LazyCoactivasTableModel(listarCoactivasHistorial);

            //connection.close();
        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //MÉTODO PARA LISTAR EL HISTORIAL DE CARGA DE COACTIVAS USUARIO CARGA RENAFIPSE
    public void buscarHistorial() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        int usu = Integer.parseInt(ObtenerCodigoUsuario());

        listarCoactivasUsuario = new ArrayList<Coactivas>();
        Connection dbConnection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            dbConnection = conexion();
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM COACTIVAS INNER JOIN USUARIOS ON COAC_USUARIO_CARGA=USU_ID WHERE COAC_USUARIO_CARGA='" + usu + "' AND COAC_CEDULA='" + cedulaBuscar + "' ORDER BY TO_DATE(COACTIVAS.COAC_FECHA, 'DD-MM-YYYY') DESC");

            while (rs.next()) {
                //String asunto = Fila_hssf.getCell(5) + "";
                listarCoactivasUsuario.add(new Coactivas(rs.getString("COAC_NUM"), rs.getString("COAC_NOMBRES"), rs.getString("COAC_APELLIDOS"), rs.getString("COAC_CEDULA"), rs.getString("COAC_ASUNTO"), rs.getString("COAC_CAUSA"), rs.getString("COAC_VALOR"), rs.getString("COAC_TRAMITE"), rs.getString("COAC_OFICIO"), rs.getString("COAC_ANEXO"), rs.getString("COAC_FECHA"), rs.getString("COAC_JUICIO"), rs.getString("COAC_CIUDAD"), rs.getString("COAC_REMITENTE"), rs.getString("COAC_CARGOREMITENTE"), rs.getString("COAC_CIRCULAR_SEPS"), rs.getString("COAC_JUICIO"), rs.getString("USU_USER"), rs.getInt("COAC_ESTADO_CARGA"), (format.format(rs.getDate("COAC_FECHA_CARGA")))));

            }

            LazyCoactivasUsuarios = new LazyCoactivasTableModel(listarCoactivasUsuario);

            //connection.close();
        } catch (SQLException ex) {
            System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getCause());
            Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                }
            }

            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    Thread.sleep(Long.parseLong("1000"));
                } catch (SQLException ex) {
                    //msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error Cargando Lista Coactivas! ", ex.getMessage() + " Registros Guardados: " + coacsubidas + " de " + listaCoac.size());

                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    System.out.println(timestamp() + " - Metodo ListarCoac: " + ex.getMessage());
                    Logger.getLogger(Coactivas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //MÉTODO PARA ACTUALIZAR COACTIVAS      
    public boolean ActualizarValor(String coacid, String valor, String numcirc, String numofi) {
        leido = false;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        String idcoop = (String) session.getAttribute("coopid");
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "UPDATE REV_COACTIVAS SET SOC_SALDO = ?, COAC_CIRCULAR = ?, COAC_OFI_INT = ? WHERE COAC_ID = ? AND COOP_ID = ?";

        try {
            //System.out.println(this.selectedCoactiva.rev_id);
            dbConnection = conexion();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setString(1, valor);
            preparedStatement.setString(2, numcirc);
            preparedStatement.setString(3, numofi);
            preparedStatement.setString(4, coacid);
            preparedStatement.setString(5, idcoop);

            preparedStatement.executeUpdate();

            System.out.println("Guardando " + coacid);
            //System.out.println("Coactiva " + this.selectedCoactiva.rev_id + " Leida");
            leido = true;

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualizado", "Actualizado Saldo: " + valor + " Circular: " + numcirc + " Oficio: " + numofi);

        } catch (SQLException e) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "A ocurrido un error");
            leido = false;
            System.out.println(e.getMessage());
            //e.printStackTrace();

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

        requestContext.update("up:growl");
        requestContext.update("up:tab1:tablacoacBloq");

        FacesContext.getCurrentInstance().addMessage(null, message);
        return leido;
    }

    //METODO QUE BUSCA SI YA EXISTE COACTIVA EN LA BD
    public boolean existeCoactiva(String num_coac) {

        boolean existe = false;
        Connection connection = conexion();

        try {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM COACTIVAS WHERE COAC_NUM =" + num_coac);
            while (rs.next()) {
                if (rs.getString("COAC_NUM").equals(num_coac)) {
                    existe = true;
                    break;
                } else {
                    existe = false;
                    break;
                }
            }
            //System.out.println("Existe " + existe + " Coactiva: " + num_coac);
            connection.close();

        } catch (SQLException ex) {
            //Logger.getLogger(socios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;

    }
//MÉTODO PARA VERIFICAR EXISTENCIA DE COACTIVA REVISADA

    public boolean existeRevCoactiva(String num_coac) {

        boolean existe = false;
        Connection connection = conexion();

        try {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM REV_COACTIVAS INNER JOIN COACTIVAS ON COACTIVAS.COAC_ID = REV_COACTIVAS.COAC_ID WHERE COAC_NUM = " + num_coac);

            while (rs.next()) {
                if (rs.getString("COAC_NUM").equals(num_coac)) {
                    existe = true;
                    break;
                } else {
                    existe = false;
                    break;
                }
            }
            //System.out.println("Existe " + existe + " Coactiva: " + num_coac);
            connection.close();

        } catch (SQLException ex) {
            //Logger.getLogger(socios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;

    }

//MÉTODO DE SECUENCIA DE VENTANAS 
    public String onFlowProcess(FlowEvent event) {

        return event.getNewStep();

    }

  

  public void postProcessXLS(Object document) {
      
      HSSFWorkbook wb = (HSSFWorkbook) document;
      HSSFSheet sheet = wb.getSheetAt(0);
      HSSFRow header = sheet.getRow(0);
     
      HSSFCellStyle cellStyle = wb.createCellStyle();  
      cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
      cellStyle.setFillPattern(HSSFCellStyle.ALIGN_CENTER);
        
         
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
             
            HSSFCell cell = header.getCell(i);
             
            cell.setCellStyle(cellStyle);
        }
    }

    
  
}
