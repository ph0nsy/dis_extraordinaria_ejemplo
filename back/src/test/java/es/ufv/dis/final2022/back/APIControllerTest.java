package es.ufv.dis.final2022.back;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.http.HttpRequest;

import static org.junit.jupiter.api.Assertions.*;

class APIControllerTest {

    APIController apiController = new APIController();

    @Test
    void addProducto() {
        // Elemento ya existente
        assertEquals(new ResponseEntity<>(null, HttpStatus.CREATED), apiController.addProducto(new Producto("nombre", "categoria", 3, "0322500532677")));
        // Nuevo elemento
        Producto producto_test = new Producto("nuevo", "nuevo", 23, "nuevo");
        //assertEquals(new ResponseEntity<>(producto_test, HttpStatus.CREATED), apiController.addProducto(producto_test));
    }
}