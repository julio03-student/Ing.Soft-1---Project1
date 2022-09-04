package panaderia.logica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import panaderia.datos.EscritorArchivoOrdenes;
import panaderia.datos.LectorArchivo;
import panaderia.entidades.base.Recorrido;
import panaderia.entidades.base.Tienda;
import panaderia.entidades.pedido.OrdenPedido;

/**
 * Lógica del programa de un recorrido de un vendedor,
 * para crear las órdenes de pedido de las tiendas.
 * 
 * @version 1.0
 */
public class ControlRecorrido {
	private Recorrido recorrido;
	private OrdenPedido ordenEnProceso;

	public ControlRecorrido() {
		this.recorrido = new Recorrido();
		this.ordenEnProceso = null;
	}

	public void cargarDatosIniciales() throws FileNotFoundException, IOException {
		CargadorDatos cargador = new CargadorDatos(recorrido);
		cargador.cargarDatosIniciales();
		
	}

	/** 
	 * Verificación de la existencia de una tienda en los datos
	 * inicialmente cargados recibiendo el codigo
	 * 
	 * @param String codigoTienda
	 * @return verdadero si efectivamente existe
	 * 		 y falso contrariamente
	 */
	public boolean existeTienda(String codigoTienda) {
		if (recorrido.buscarTienda(codigoTienda) != null) {
			return true;
		}
		return false;
	}

	/** 
	 * A partir de un codigo de una tienda, se hace la busqueda de la misma
	 * en la lista inicialmente cargada de tiendas
	 * 
	 * @param String codigoTienda
	 * @return un objeto Tienda si existe,
	 * 		 o si no existe se hace un lanzamiento de una exception
	 * 		 contrariamente
	 * @throws IllegalArgumentException
	 */
	public String obtenerDatosTienda(String codigoTienda) throws IllegalArgumentException {
		Tienda tienda = null;
		if (recorrido.buscarTienda(codigoTienda) != null) {
			tienda = recorrido.buscarTienda(codigoTienda);
		} else {
			throw new IllegalArgumentException("La tienda no existe");
		}
		return tienda.toString();
	}

	/**
	 * @return listaDetallesOrdenados del la orden en proceso 
	 */
	public List<String> obtenerDetallesOrdenados() {
		return ordenEnProceso.getDetallesOrdenados();
	}

	/** 
	 * Se hace la creación de una orden de pedido, con la busqueda de la tienda con el codigo ingresado,
	 * se hace la lectura del archivo de la orden, y luego se hace la cración de los detalles del
	 * pedido en proceso
	 * 
	 * @param String nombreArchivoProductos
	 * @param String codigoTienda
	 */
	public void crearOrden(String nombreArchivoProductos, String codigoTienda) throws FileNotFoundException, IOException {

			ordenEnProceso = new OrdenPedido(recorrido.buscarTienda(codigoTienda));
			LectorArchivo lectorArchivo = new LectorArchivo(nombreArchivoProductos);
			List<String[]> detallesPedido = lectorArchivo.obtenerDatosBase();
			crearDetalle(ordenEnProceso, detallesPedido);
			System.out.println(ordenEnProceso.toString());
		 
	}

	/** 
	 * Se asignan los detalles de la orden en proceso a la misma agregandolos con el metodo correspodiente
	 * 
	 * @param OrdenPedido orden
	 * @param List<String[]> datosBaseDetalle
	 */
	private void crearDetalle(OrdenPedido orden, List<String[]> datosBaseDetalle) {
		for (String[] detalle : datosBaseDetalle) {
			if (recorrido.buscarProducto(detalle[0]) != null) {
				orden.addDetalle(recorrido.buscarProducto(detalle[0]), Integer.parseInt(detalle[1]));
			}
		}
	}

	/**
	 * Envía la orden para que su información
	 * se guarde en un archivo, y luego
	 * deja el atributo en null para que se
	 * pueda procesar una nueva orden.
	 */
	public void guardarOrden() {
		EscritorArchivoOrdenes escritor = new EscritorArchivoOrdenes();
		escritor.escribirOrden(ordenEnProceso);
		ordenEnProceso = null;
	}
}
