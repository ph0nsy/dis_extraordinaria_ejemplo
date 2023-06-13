package es.ufv.dis.final2022.back;

import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.List;

/**
 * Clase encargada de gestionar las funcionalidades de escritura de PDF.
 * @since v0.3
 */
public class PDFManager {

    /**
     * Escribir/sobreescribir archivo PDF que contiene el backup
     * @param productoList Lista de Objetos (Producto) que guardar
     * @since v0.3
     */
    public void generarPDF(List<Producto> productoList){
        try {
            Document doc = new Document(PageSize.A4, 50, 50, 100, 72);
            PdfWriter writer = PdfWriter.getInstance(doc, new
                    FileOutputStream("../../../../productos/backup.pdf"));
            doc.open();
            // Convertimos la lista en un String con formato JSON
            String text = new Gson().toJson(productoList);
            Paragraph p = new Paragraph(text);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            doc.add(p);
            doc.close();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}
