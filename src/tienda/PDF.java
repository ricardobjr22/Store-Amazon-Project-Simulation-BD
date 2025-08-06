
package tienda;

import java.util.Date;
import javax.swing.ImageIcon;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PDF {
    private String name;
    private long numberCard;
    private String banco;
    private Date caducidad;
    private int cvv;

    public String toString(){
        String result = "";
        result += "Nombre: " + name + "\n";
        result += "Numero de tarjeta: " + String.valueOf(numberCard) + "\n";
        result += "Banco: " + banco + "\n";
        result += "Caducidad: " + caducidad.toString() + "\n";
        result += "CVV: " + String.valueOf(cvv) + "\n";
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(long numberCard) {
        this.numberCard = numberCard;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public Date getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(Date caducidad) {
        this.caducidad = caducidad;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }
    
    
    public void generar(String name, String image, int width, int height) throws Exception{
        Document documento = new Document();
        try{
            PdfWriter.getInstance(documento, new FileOutputStream(name + ".pdf"));
            Image img = Image.getInstance(image);
            img.scaleToFit(width, height);
            img.setAlignment(Chunk.ALIGN_CENTER);
            documento.open();
            PdfPTable table = new PdfPTable(5);
            table.addCell("Nombre: " + name);
            table.addCell("Numero de tarjeta: " + String.valueOf(numberCard));
            table.addCell("Banco: " + banco);
            table.addCell("Fecha de caducidad: " + caducidad.toString());
            table.addCell("CVV: " + cvv);
            
            documento.add(table);
            documento.add(img);
            documento.close();
        }catch(DocumentException | FileNotFoundException ex){
            throw ex;
        }
    }
}
