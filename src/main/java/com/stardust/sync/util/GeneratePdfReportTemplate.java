package com.stardust.sync.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.DeviceRgb;
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
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.stardust.sync.model.Meter;
import com.stardust.sync.service.MeterService;

@Service
public class GeneratePdfReportTemplate {

	@Autowired
	MeterService meterService;

	private static final Logger logger = LoggerFactory.getLogger(GeneratePdfReportTemplate.class);

	private static final String[] office = { "3rd", "4th", "5th", "6th" };

	private static DecimalFormat df2 = new DecimalFormat("#.###");

	public ByteArrayInputStream electricalDR(String efr, String eto) {
		df2.setRoundingMode(RoundingMode.UP);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			PdfDocument pdfDoc = new PdfDocument(new PdfWriter(out));
			Document doc = new Document(pdfDoc, new PageSize(595, 842));
			doc.setMargins(70, 20, 25, 20);
			
			PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
			
			// Creating an ImageData object
			String imageFile = "C:/sync/Capture.jpg";
			ImageData data = ImageDataFactory.create(imageFile);
			// Creating an Image object
			Image img = new Image(data);

			Header headerHandler = new Header(img, "Facility electrical daily reading log");
			Footer footerHandler = new Footer();

			pdfDoc.addEventHandler(PdfDocumentEvent.START_PAGE, headerHandler);
			pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, footerHandler);
			
			
			DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date fromDate = dFormat.parse(efr);
			Date toDate = dFormat.parse(eto);

			int limit = (int) (((toDate.getTime()-fromDate.getTime())/86400000L) < 7 ? 
					(toDate.getTime()-fromDate.getTime())/86400000L : 7);
			
			
			for (int i = 0; i < limit+1 ; i++) {

				
				List<Meter> dailyReadingByExtBetweenStartAndEnd = meterService.dailyReadingByExtBetweenStartAndEnd("kWh", 
						fromDate , new Date(fromDate.getTime()+86399000L));

				Table table = new Table(new float[4]).useAllAvailableWidth();
				table.setMarginTop(5);
				table.setMarginBottom(5);
				table.setTextAlignment(TextAlignment.CENTER);

				// first row
				Cell cell = new Cell(1, 4).add(new Paragraph("Table:" + (i+1) + " for the date:" + dFormat.format(fromDate)));
				cell.setTextAlignment(TextAlignment.CENTER);
				cell.setPadding(5);
				cell.setBackgroundColor(new DeviceRgb(28, 200, 138));
				table.addCell(cell);

				String[] header = { "Floor", "Office", "Timestamp", "Meter reading"};

				for (String head : header) {
					Cell cellx = new Cell().add(new Paragraph(head));
					//cellx.setBold();
					cellx.setFont(bold);
					cellx.setTextAlignment(TextAlignment.CENTER);
					table.addCell(cellx);
				}

				for (Meter meter : dailyReadingByExtBetweenStartAndEnd) {
					table.addCell(meter.getId()+" Floor");
					table.addCell(meter.getId().equals("Ground") ? "Com area" : "Office "+meter.getUnit());
					table.addCell(meter.getTimeStamp().toLocaleString());
					table.addCell(df2.format(meter.getValue())+" "+meter.getExt());
				}

				doc.add(table);
				fromDate = new Date(fromDate.getTime()+86400000L);
			}

	

			// Write the total number of pages to the placeholder
			footerHandler.writeTotal(pdfDoc);
			doc.close();
			//pdfDoc.close();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ByteArrayInputStream(out.toByteArray());
	}
	
	public ByteArrayInputStream airconDR(String efr, String eto) {
		df2.setRoundingMode(RoundingMode.UP);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			PdfDocument pdfDoc = new PdfDocument(new PdfWriter(out));
			Document doc = new Document(pdfDoc, new PageSize(595, 842));
			doc.setMargins(70, 20, 25, 20);
			
			PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
			
			// Creating an ImageData object
			String imageFile = "C:/sync/Capture.jpg";
			ImageData data = ImageDataFactory.create(imageFile);
			// Creating an Image object
			Image img = new Image(data);

			Header headerHandler = new Header(img, "Facility aircon daily reading log");
			Footer footerHandler = new Footer();

			pdfDoc.addEventHandler(PdfDocumentEvent.START_PAGE, headerHandler);
			pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, footerHandler);
			
			
			DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date fromDate = dFormat.parse(efr);
			Date toDate = dFormat.parse(eto);

			int limit = (int) (((toDate.getTime()-fromDate.getTime())/86400000L) < 7 ? 
					(toDate.getTime()-fromDate.getTime())/86400000L : 7);
			
			
			for (int i = 0; i < limit+1 ; i++) {

				
				List<Meter> dailyReadingByExtBetweenStartAndEnd = meterService.dailyReadingByExtBetweenStartAndEnd("BTU", 
						fromDate , new Date(fromDate.getTime()+86399000L));

				Table table = new Table(new float[4]).useAllAvailableWidth();
				table.setMarginTop(5);
				table.setMarginBottom(5);
				table.setTextAlignment(TextAlignment.CENTER);

				// first row
				Cell cell = new Cell(1, 4).add(new Paragraph("Table:" + (i+1) + " for the date:" + dFormat.format(fromDate)));
				cell.setTextAlignment(TextAlignment.CENTER);
				cell.setPadding(5);
				cell.setBackgroundColor(new DeviceRgb(78, 115, 223));
				table.addCell(cell);

				String[] header = { "Floor", "Office", "Timestamp", "Meter reading"};

				for (String head : header) {
					Cell cellx = new Cell().add(new Paragraph(head));
					//cellx.setBold();
					cellx.setFont(bold);
					cellx.setTextAlignment(TextAlignment.CENTER);
					table.addCell(cellx);
				}

				for (Meter meter : dailyReadingByExtBetweenStartAndEnd) {
					table.addCell(meter.getId()+" Floor");
					table.addCell(meter.getId().equals("Ground") ? "Com area "+(meter.getMeter()-1) : "Office "+meter.getUnit());
					table.addCell(meter.getTimeStamp().toLocaleString());
					table.addCell(df2.format(meter.getValue())+" "+meter.getExt());
				}

				doc.add(table);
				fromDate = new Date(fromDate.getTime()+86400000L);
			}

	

			// Write the total number of pages to the placeholder
			footerHandler.writeTotal(pdfDoc);
			doc.close();
			//pdfDoc.close();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

	// Header event handler
	protected class Header implements IEventHandler {
		private String header;
		private Image img;

		public Header(Image img, String header) {
			this.header = header;
			this.img = img;
		}

		@Override
		public void handleEvent(Event event) {
			PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
			PdfDocument pdf = docEvent.getDocument();

			PdfPage page = docEvent.getPage();
			Rectangle pageSize = page.getPageSize();

			Canvas canvas = new Canvas(new PdfCanvas(page), pdf, pageSize);
			canvas.setFontSize(22);
			img.scaleToFit(100, 100);
			img.setMargins(10, 10, 10, 20);
			canvas.add(img);
			// Write text at position
			canvas.showTextAligned(header, pageSize.getWidth() / 2, pageSize.getTop() - 50,
					TextAlignment.CENTER);
			canvas.close();
		}
	}

	// Footer event handler
	protected class Footer implements IEventHandler {
		protected PdfFormXObject placeholder;
		protected float side = 20;
		protected float x = 300;
		protected float y = 10;
		protected float space = 4.5f;
		protected float descent = 3;

		public Footer() {
			placeholder = new PdfFormXObject(new Rectangle(0, 0, side, side));
		}

		@Override
		public void handleEvent(Event event) {
			PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
			PdfDocument pdf = docEvent.getDocument();
			PdfPage page = docEvent.getPage();
			int pageNumber = pdf.getPageNumber(page);
			Rectangle pageSize = page.getPageSize();

			// Creates drawing canvas
			PdfCanvas pdfCanvas = new PdfCanvas(page);
			Canvas canvas = new Canvas(pdfCanvas, pdf, pageSize);
			canvas.setFontSize(10);
			Paragraph p = new Paragraph().add("Page ").add(String.valueOf(pageNumber)).add(" of");

			canvas.showTextAligned(p, x, y, TextAlignment.RIGHT);
			canvas.close();

			// Create placeholder object to write number of pages
			pdfCanvas.addXObject(placeholder, x + space, y - descent);
			pdfCanvas.release();
		}

		public void writeTotal(PdfDocument pdf) {
			Canvas canvas = new Canvas(placeholder, pdf);
			canvas.setFontSize(10);
			canvas.showTextAligned(String.valueOf(pdf.getNumberOfPages()), 0, descent, TextAlignment.LEFT);
			canvas.close();
		}
	}

}