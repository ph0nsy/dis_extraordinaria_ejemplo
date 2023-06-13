package es.ufv.dis.final2022.front;

/**
 * Conforma el objeto que utilizaremos para la mayoría de las funcionaldiades del sistema.
 * @since v0.1
 */
public class Producto {

    public String nombre;
    public String categoria;
    public int precio;
    public String ean13;

    /**
     * @param nombre Nombre del producto (String)
     * @param categoria Tipo de producto (String)
     * @param precio Precio del producto (Float)
     * @param ean13 Códigos obtenidos de <a href="https://generate.plus/en/number/ean">esta dirección</a> (Int)
     * @return Objeto de tipo Producto
     * @since v0.1
     */
    public Producto(String nombre, String categoria, int precio, String ean13) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.ean13 = ean13;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getEan13() {
        return ean13;
    }

    public void setEan13(String ean13) {
        this.ean13 = ean13;
    }
}
