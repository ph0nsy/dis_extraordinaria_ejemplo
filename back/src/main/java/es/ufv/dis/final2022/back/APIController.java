package es.ufv.dis.final2022.back;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity; // Generar respuestas HTTP
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador de la API que se encarga de manejar los datos de la base de datos (almacenada en archivos JSON).
 * <br><br>
 * Las llamadas a la API pueden ser GET, PUT y DELETE
 * @since v0.4
 */
@RestController
public class APIController {
    LectorJSON lectorJSON = new LectorJSON();
    PDFManager pdf = new PDFManager();
    List<Producto> listaProductos = lectorJSON.leerJsonProductos();

    /**
     * Obtener lista de todos los productos (GET)
     * @return Lista de Objetos (Producto)
     * @since v0.4
     */
    @GetMapping("/ObtenerProductos")
    public List<Producto> obtenerTodosProductos(){
        return lectorJSON.leerJsonProductos();
    }

    /**
     * Encontrar un Producto en particular de template.json (GET)
     * @param producto Nombre del producto que tomar
     * @return Objeto (Producto)
     * @since v0.4
     */
    @GetMapping("/EncontrarProducto")
    public Producto obtenerProducto(@RequestBody String producto){
        for(Producto producto_actual:listaProductos){
            if(producto_actual.getNombre().equals(producto)){
                return producto_actual;
            }
        }
        return null;
    }

    /**
     * Añadir un nuevo producto a template.json (POST)
     * @param producto Producto que añadir
     * @return Respuesta HTTP
     * @since v0.4
     */
    @PostMapping(path="/AñadirProducto",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Producto>> añadirProducto(@RequestBody Producto producto){
        listaProductos.add(producto);
        lectorJSON.actualizarJson(listaProductos);
        pdf.generarPDF(listaProductos);
        return new ResponseEntity<>(listaProductos, HttpStatus.CREATED);
    }
}
