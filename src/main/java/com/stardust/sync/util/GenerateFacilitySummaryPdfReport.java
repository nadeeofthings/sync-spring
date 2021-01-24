package com.stardust.sync.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
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
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.stardust.sync.core.Constants;
import com.stardust.sync.model.Meter;
import com.stardust.sync.model.MeterExtended;
import com.stardust.sync.service.MeterService;

@Service
public class GenerateFacilitySummaryPdfReport {

	@Autowired
	MeterService meterService;
	
	@Autowired
	ChartGenerator chartGenerator;

	private static final Logger logger = LoggerFactory.getLogger(GenerateFacilitySummaryPdfReport.class);

	private static final String[] office = { "3rd", "4th", "5th", "6th" };

	private static DecimalFormat df2 = new DecimalFormat("#.###");

	public ByteArrayInputStream summaryReport(String efr, String eto) {
		df2.setRoundingMode(RoundingMode.UP);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			PdfWriter pdfWriter = new PdfWriter(out);
			PdfDocument pdfDoc = new PdfDocument(pdfWriter);
			Document doc = new Document(pdfDoc, new PageSize(595, 842));
			doc.setMargins(70, 20, 25, 20);
			
			FontProgram fontProgram = FontProgramFactory.createFont(Constants.CALIBRI_REGULAR);
            PdfFont calibriRegular = PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI, true);
            PdfFont calibriBold = PdfFontFactory.createFont(Constants.CALIBRI_BOLD, true);
            PdfFont calibriItalic = PdfFontFactory.createFont(Constants.CALIBRI_ITALIC, true);
			
			// Creating an ImageData object
			String imageFile = "src/main/webapp/resources/images/customer_logo.png";
			ImageData data = ImageDataFactory.create(imageFile);
			// Creating an Image object
			Image img = new Image(data);

			Header headerHandler = new Header(img, "Facility Summary Report");
			Footer footerHandler = new Footer();

			pdfDoc.addEventHandler(PdfDocumentEvent.START_PAGE, headerHandler);
			pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, footerHandler);
			
			
			//Fetching and populating data
			DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat dFormat2 = new SimpleDateFormat("dd/MM");
			Date fromDate = dFormat.parse(efr);
			Date toDate = dFormat.parse(eto);

			int limit = (int) (((toDate.getTime()-fromDate.getTime())/86400000L) < 93 ? 
					(toDate.getTime()-fromDate.getTime())/86400000L : 93);
			
			List<Meter> listBuildingDailyUsagekWh = meterService.listBuildingDailyUsage("kWh", toDate, limit);
			List<Meter> listBuildingDailyUsageBTU = meterService.listBuildingDailyUsage("BTU", toDate, limit);
			
			Table table = new Table(new float[3]).useAllAvailableWidth();
			table.setMarginTop(5);
			table.setMarginBottom(5);
			table.setTextAlignment(TextAlignment.CENTER);
			
			DefaultCategoryDataset dataset4 = new DefaultCategoryDataset( );

			// first row
			Cell cell = new Cell(1, 3).add(new Paragraph("Table:" + " from: " + dFormat.format(fromDate) + " to: "+ dFormat.format(toDate)));
			cell.setTextAlignment(TextAlignment.CENTER);
			cell.setPadding(5);
			cell.setFont(calibriRegular);
			cell.setBackgroundColor(new DeviceRgb(246, 194, 62));
			table.addCell(cell);

			String[] header = { "Timestamp", "Electricity Usage", "Aircon Usage"};

			for (String head : header) {
				Cell cellx = new Cell().add(new Paragraph(head));
				//cellx.setBold();
				cellx.setFont(calibriRegular);
				cellx.setTextAlignment(TextAlignment.CENTER);
				table.addCell(cellx);
			}
			
			for (int counter = 0 ; counter < listBuildingDailyUsagekWh.size(); counter++) {
				Cell cellx = new Cell().add(new Paragraph(dFormat.format(listBuildingDailyUsagekWh.get(counter).getTimeStamp())));
				cellx.setFont(calibriRegular);
				cellx.setPadding(0f);
				cellx.setTextAlignment(TextAlignment.CENTER);
				table.addCell(cellx);
				
				cellx = new Cell().add(new Paragraph(df2.format(listBuildingDailyUsagekWh.get(counter).getValue())+" "+listBuildingDailyUsagekWh.get(counter).getExt()));
				cellx.setFont(calibriRegular);
				cellx.setPadding(0f);
				cellx.setTextAlignment(TextAlignment.CENTER);
				table.addCell(cellx);
				
				cellx = new Cell().add(new Paragraph(df2.format(listBuildingDailyUsageBTU.get(counter).getValue())+" "+listBuildingDailyUsageBTU.get(counter).getExt()));
				cellx.setFont(calibriRegular);
				cellx.setPadding(0f);
				cellx.setTextAlignment(TextAlignment.CENTER);
				table.addCell(cellx);
				
				dataset4.addValue( listBuildingDailyUsagekWh.get(counter).getValue() ,"Electricity" , dFormat2.format(listBuildingDailyUsagekWh.get(counter).getTimeStamp()) );
				dataset4.addValue( listBuildingDailyUsageBTU.get(counter).getValue()/1000 ,"Air Conditioning" , dFormat2.format(listBuildingDailyUsageBTU.get(counter).getTimeStamp()) );
			}
        
	        
	        JFreeChart chart4 = ChartFactory.createLineChart(
	                "",
	                "Time","kWh/BTU x1000",
	                dataset4,
	                PlotOrientation.VERTICAL,
	                true,true,false);
	        
	     // trick to change the default font of the chart
	        chart4.setTitle(new TextTitle("Facility Energy Usage", new java.awt.Font("Serif", Font.PLAIN, 14)));
	        chart4.setBackgroundPaint(Color.white);
	        
	        CategoryPlot plot2 = (CategoryPlot) chart4.getPlot();
	        //plot2.setForegroundAlpha(0.3f);
	        plot2.getRenderer().setSeriesStroke(0, new BasicStroke(2.0f));
	        plot2.getDomainAxis().setLabelFont(new Font("Serif", java.awt.Font.PLAIN, 12));
	        plot2.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
	        plot2.getDomainAxis().setTickLabelFont(new Font("Serif", java.awt.Font.PLAIN, 9));
	        plot2.getRangeAxis().setLabelFont(new Font("Serif", java.awt.Font.PLAIN, 12));
	        
			PdfReader reader = new PdfReader(new ByteArrayInputStream(chartGenerator.generateChartPDF(chart4)));
			PdfDocument chartDoc = new PdfDocument(reader);
			PdfFormXObject chartDocObj = chartDoc.getFirstPage().copyAsFormXObject(pdfDoc);
			
			Image chartImage = new Image(chartDocObj);
			chartImage.setBorder(new SolidBorder(1.0f));
			chartImage.setHorizontalAlignment(HorizontalAlignment.CENTER);
			
			//Canvas canvas = new Canvas();
			doc.add(chartImage);
			
			doc.add(table);

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
			canvas.showTextAligned(header, (pageSize.getWidth() / 2)+50f, pageSize.getTop() - 50,
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