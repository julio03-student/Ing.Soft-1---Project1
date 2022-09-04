package panaderia.datos;

import java.util.ArrayList;
import java.util.List;

public class DatosPruebaProductos implements IFuenteDatos{

    @Override
	public List<String[]> obtenerDatosBase() {
		String[] tienda1 = {"10-10","La esquina"};
		String[] tienda2 = {"10-20","El encuentro"};
		String[] tienda3 = {"10-20","Tinteadero"};
		List<String[]> tiendas = new ArrayList<>();
		tiendas.add(tienda1);
		tiendas.add(tienda2);
		tiendas.add(tienda3);
		return tiendas;
	}
}
