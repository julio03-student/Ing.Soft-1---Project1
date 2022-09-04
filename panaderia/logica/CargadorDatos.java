package panaderia.logica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import panaderia.datos.IFuenteDatos;
import panaderia.datos.LectorArchivo;
import panaderia.entidades.base.Producto;
import panaderia.entidades.base.Recorrido;
import panaderia.entidades.base.Tienda;

/**
 * Se encarga de crear los objetos a partir
 * de arreglos de cadenas de texto con la información
 * (esas cadenas pueden provenir de diferentes
 *  fuentes, por eso usa una interfaz, 
 *  para que se puedan cambiar fácilmente).
 * 
 * @version  1.0
 */
public class CargadorDatos {
	private Recorrido recorrido;
	private final String rutaProductos = "panaderia\\recursos\\productos.txt";
	private final String rutaTiendas = "panaderia\\recursos\\tiendas.txt";

	public CargadorDatos(Recorrido recorrido) {
		this.recorrido = recorrido;
	}
	
	/**
	 * Carga los datos iniciales que necesita el programa:
	 * tiendas y productos.
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void cargarDatosIniciales() throws FileNotFoundException, IOException {

		IFuenteDatos leerTiendas = new LectorArchivo(rutaTiendas);
		List<String[]> datosBaseTiendas = leerTiendas.obtenerDatosBase();
		this.cargarDatosTiendas(datosBaseTiendas);
		
		IFuenteDatos leerProductos = new LectorArchivo(rutaProductos);
		List<String[]> datosBaseProductos = leerProductos.obtenerDatosBase();
		this.cargarDatosProductos(datosBaseProductos);
	}
	
	/**
	 * A partir de los datos base (cadenas de texto),
	 * obtiene los datos de las tiendas,
	 * crea los objetos y los guarda en el objeto recorrido.
	 */
	private void cargarDatosTiendas(List<String[]> datosBase) {
		for (String[] datoBaseTienda: datosBase) {
			String codigo = datoBaseTienda[0];
			String nombre = datoBaseTienda[1];
			Tienda tienda = new Tienda(codigo, nombre);
			this.recorrido.addTienda(tienda);
		}
	}
	
	/**
	 * A partir de los datos base (cadenas de texto),
	 * obtiene los datos de los productos,
	 * crea los objetos y los guarda en el objeto recorrido.
	 */
	private void cargarDatosProductos(List<String[]> datosBase) {
		for (String[] datoBaseProducto: datosBase) {
			String codigo = datoBaseProducto[0];
			String nombre = datoBaseProducto[1];
			double valorUnitario = Double.parseDouble(datoBaseProducto[2]);
			Producto producto = new Producto(codigo, nombre, valorUnitario); 
			this.recorrido.addProducto(producto);
		}
	}
}