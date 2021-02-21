package Projects.Example;

import com.itextpdf.io.IOException;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.TextAlignment;

import java.io.BufferedReader;
import java.io.FileReader;

public class PdfExample {
    public static void main(String args[]) throws Exception {
        String img_dest = "src/main/resources/static/images/background.jpg";
        String dest = "E://Example.pdf";
        String fullText = "Полное описание характера по лицу Полное описание характера по лицу Полное описание характера по лицу Полное описание характера по лицу Полное описание характера по лицу Полное описание характера по лицу Полное описание характера по лицу Полное описание характера по лицу Полное описание характера по лицу Полное описание характера по лицу Полное описание характера по лицу Полное описание характера по лицу";
        String author = "Allah";
        String title = "ПОЛНОЕ ОПИСАНИЕ ХАРАКТЕРА ПО ЛИЦУ";
        createPdf(dest, img_dest, title, fullText, author);

    }

    public static void createPdf(String dest, String img_dest, String title, String fullText, String author) throws IOException, java.io.IOException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        PageSize pagesize = new PageSize( 4682, 2658 );
        FontProgramFactory.registerFont("src/main/resources/static/fonts/Commissioner.ttf", "Commissioner");
        PdfFont font = PdfFontFactory.createRegisteredFont("Commissioner","Identity-H", true);
        pdf.setDefaultPageSize(pagesize);
        Image image = new Image(ImageDataFactory.create(img_dest));
        pdf.addEventHandler(PdfDocumentEvent.START_PAGE, new Header(image));
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, new Footer(author, font));
        Document document = new Document(pdf)
                .setTextAlignment(TextAlignment.JUSTIFIED);
        document.add(new Paragraph(title)
                .setFont(font)
                .setMarginBottom(400)
                .setMarginTop(1000)
                .setMarginLeft(510)
                .setMarginRight(1800)
                .setFontSize(100));
        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        document.add(new Paragraph(fullText)
                    .setFont(font)
                    .setMarginBottom(400)
                    .setMarginTop(1000)
                    .setMarginLeft(510)
                    .setMarginRight(1800)
                    .setFontSize(50));
        document.close();
    }

    protected static class Footer implements IEventHandler {

        String author;
        PdfFont font;

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdf = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(
                    page.getLastContentStream(), page.getResources(), pdf);
            Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
            float x = 850;
            float y = pageSize.getBottom() + 170;
            canvas
                    .setFont(font)
                    .setFontSize(100)
                    .showTextAligned("Автор: @" + author, x, y, TextAlignment.CENTER);
        }

        public Footer(String text, PdfFont font){
            this.author = text;
            this.font = font;
        }
    }

    protected static class Header implements IEventHandler {

        Image image;

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdf = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            Rectangle pageSize = page.getPageSize();
            PdfCanvas pdfCanvas = new PdfCanvas(
                    page.getLastContentStream(), page.getResources(), pdf);
            Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
            canvas
                    .add(image);
        }

        public Header(Image img){
            this.image = img;
        }
    }
}
