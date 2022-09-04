package panaderia.datos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectorArchivo implements IFuenteDatos {

    private String nombreArchivo;
    private List<String[]> archivo = new ArrayList<>();

    public LectorArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * Este metodo permite a partir del nombre del archivo y su ruta trasnformar su
     * contenido en una Lista de arreglos de String
     * 
     * @throws IOException
     * @throws FileNotFoundException
     */
    @Override
    public List<String[]> obtenerDatosBase() throws FileNotFoundException, IOException {
        
            File file = new File(nombreArchivo);
            if(!file.exists()){
                throw new FileNotFoundException("No se encontró el archivo");
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String s;

            if(reader.readLine() == null){
                reader.close();        
                throw new IOException("El archivo de texto se encuentra vacío");
            }
            while ((s = reader.readLine()) != null) {
                archivo.add(s.split("-"));
            }

            reader.close();             

        return archivo;

    }

}