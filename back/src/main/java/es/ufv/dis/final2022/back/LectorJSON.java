package es.ufv.dis.final2022.back;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clase encargada de gestionar las funcionalidades de lectura/escritura de JSON.
 * @since v0.2
 */
public class LectorJSON {


    /**
     * Leemos los contenidos del archivo template.json y los almacenamos en una lista de productos
     * @return Lista de Objetos (Producto)
     * @since v0.2
     */
    public List<Producto> leerJsonProductos(){

        try {
            //Lee el fichero que le pasamos y lo carga en un reader
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("template.json"))));
            // Convierte el array JSON a un arraylist de Productos
            List<Producto> productos =
                    new Gson().fromJson(reader, new TypeToken<List<Producto>>()
                    {}.getType());
            reader.close();
            return productos;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ArrayList<>(); //si no ha leido nada, devuelve un array vacio
        }
    }

    /**
     * Sobreescribimos el archivo template.json con la lista actualizada
     * @param listaProductosActualizada Lista de Objetos (Producto) que sustituirá a la antigua
     * @since v0.2
     */
    public void actualizarJson (List<Producto> listaProductosActualizada){

        try {
            //lee el fichero que le pasamos y lo carga en un reader

            BufferedWriter contenido = new BufferedWriter(new FileWriter("./src/main/resources/template.json",false));
            // convierte el array JSON a un arraylist de Zonas basicas de salud
            List<Producto> listaActualizada = new ArrayList<Producto>(listaProductosActualizada);

            new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .toJson(listaActualizada, contenido);

            contenido.close();// close writer

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex); //si no ha escrito nada,  array devuelve una excepcion
        }
    }


}
