package com.stardust.sync.util;

import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.text.pdf.ColumnText;
import com.stardust.sync.core.Constants;
import com.stardust.sync.model.Billing;
import com.stardust.sync.model.BillingProperties;
import com.stardust.sync.model.Meter;
import com.stardust.sync.model.MeterConfiguration;
import com.stardust.sync.service.BillingPropertiesService;
import com.stardust.sync.service.BillingService;
import com.stardust.sync.service.MeterConfigurationService;
import com.stardust.sync.service.MeterService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class GeneratePdfBill {
	
	

    private static final Logger logger = LoggerFactory.getLogger(GeneratePdfBill.class);
    private static final String esrc = "src/main/webapp/resources/pdf/elec.pdf";
    private static final String asrc = "src/main/webapp/resources/pdf/aircon.pdf";
    
    private static final String[] office = {"3rd","4th","5th","6th"}; 
    
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    
    DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    
/**
 * 
 * The util class only genetares the pdf. the billing service should pass all the objects to that class and util class can use the data without and db calls.
 * @param ext 
 * @param billLatest 
 * 
 * 
 * 
 */
    public ByteArrayInputStream generate(String ext, MeterConfiguration config, HashMap<String, String> billProps, Billing billLatest) {
    	df2.setRoundingMode(RoundingMode.UP);
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	try {
            
            PdfDocument pdfDoc;
            if(ext.equalsIgnoreCase("kWh"))
            	pdfDoc = new PdfDocument(new PdfReader(esrc), new PdfWriter(out));
            else
            	pdfDoc = new PdfDocument(new PdfReader(asrc), new PdfWriter(out));
            
            pdfDoc.getDefaultPageSize().getHeight();
            PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
            
            FontProgram fontProgram = FontProgramFactory.createFont(Constants.CALIBRI_REGULAR);
            PdfFont calibriRegular = PdfFontFactory.createFont(fontProgram, PdfEncodings.WINANSI, true);
            PdfFont calibriBold = PdfFontFactory.createFont(Constants.CALIBRI_BOLD, true);
            PdfFont calibriItalic = PdfFontFactory.createFont(Constants.CALIBRI_ITALIC, true);
            
            Color myColor = new DeviceRgb(0, 0, 0);
            
            //Start generating
            
            BigDecimal peakUseage = billLatest.getPeakUseage();
            BigDecimal offPeakUseage = billLatest.getOffPeakUseage();
            BigDecimal peakRate = billLatest.getPeakrate();
            BigDecimal offPeakRate = billLatest.getOffPeakRate();
            BigDecimal peakBill = peakRate.multiply(peakUseage);
            BigDecimal offPeakBill = offPeakRate.multiply(offPeakUseage);
            
            if(ext.equalsIgnoreCase("BTU")) {
            	peakBill = peakBill.divide(new BigDecimal("10000"));
            	offPeakBill = offPeakBill.divide(new BigDecimal("10000"));
	        }
            
            BigDecimal totalUseage = peakBill.add(offPeakBill);
            BigDecimal totalBill = totalUseage.add(billLatest.getServiceCharge());
            
            BigDecimal discount = (totalBill.multiply(billLatest.getDiscount())).divide(new BigDecimal("100.00"));
            BigDecimal adjustmentsDiscounts = discount.add(billLatest.getAdjustments());
            
            BigDecimal penalties = (totalBill.multiply(billLatest.getPenalty())).divide(new BigDecimal("100.00"));
            
            BigDecimal currentBill = totalBill.subtract(adjustmentsDiscounts).add(penalties);
            BigDecimal nbTax = (currentBill.multiply(billLatest.getNbTax())).divide(new BigDecimal("100.00"));
            BigDecimal vaTax = (currentBill.multiply(billLatest.getVaTax())).divide(new BigDecimal("100.00"));		
            BigDecimal totalPayableForThisBillingPeriod = currentBill.add(nbTax).add(vaTax);
            
            BigDecimal previousBalance = billLatest.getBalance().add(billLatest.getPayment()).subtract(totalPayableForThisBillingPeriod);
            
            if (previousBalance.compareTo(BigDecimal.ONE)< 0)
            	previousBalance = new BigDecimal("0.00");
            
            //T&C
            Rectangle rect = new Rectangle(53, 175.5f, 493.5f, 72);
            canvas.rectangle(rect);
            //canvas.stroke();
            Canvas childCanvas = new Canvas(canvas, pdfDoc, rect);
            Text title = new Text(billProps.get(Constants.CONFIG_KEY_T_AND_C)).setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            Paragraph p = new Paragraph().add(title).setTextAlignment(TextAlignment.JUSTIFIED);
            childCanvas.add(p);
            childCanvas.close();
            
            //Customer address
            rect = new Rectangle(127, 674.5f, 160, 37);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(config.getCustomer().getAddress()).setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.LEFT).setFixedLeading(10.3f);
            childCanvas.add(p);
            childCanvas.close();
            
            //Current bill
            rect = new Rectangle(309.5f, 573f, 111, 28.5f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", totalPayableForThisBillingPeriod)).setFont(calibriRegular).setFontColor(myColor).setFontSize(12);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            childCanvas.add(p);
            childCanvas.close();
            
            //Total payable
            rect = new Rectangle(435.5f, 573f, 111, 28.5f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", billLatest.getBalance())).setFont(calibriRegular).setFontColor(myColor).setFontSize(12);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            childCanvas.add(p);
            childCanvas.close();
            
            //Payment received
            rect = new Rectangle(180f, 573f, 111, 28.5f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", billLatest.getPayment())).setFont(calibriRegular).setFontColor(myColor).setFontSize(12);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            childCanvas.add(p);
            childCanvas.close();
            
            //Previous balance
            rect = new Rectangle(54, 573f, 111, 28.5f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", previousBalance)).setFont(calibriRegular).setFontColor(myColor).setFontSize(12);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE);
            childCanvas.add(p);
            childCanvas.close();
            
            //Service charge column
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(217.2f, 524.6f+6.9f, 75.4f, 5.8f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(billLatest.getServiceCharge().toString()).setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(217.2f, 502.8f+6.9f, 75.4f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", totalUseage)).setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(217.2f, 481.2f+6.9f, 75.4f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", totalBill)).setFont(calibriBold).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(217.2f, 445.2f+6.9f, 75.4f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", adjustmentsDiscounts)).setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(217.2f, 423.6f+6.9f, 75.4f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", penalties)).setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(217.2f, 402.0f+6.9f, 75.4f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", currentBill)).setFont(calibriBold).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(217.2f, 380.4f+6.9f, 75.4f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", vaTax)).setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(217.2f, 358.8f+6.9f, 75.4f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", nbTax )).setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(217.2f, 330.0f+6.9f, 75.4f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", totalPayableForThisBillingPeriod)).setFont(calibriBold).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            //End service charge column
            
            //peak useage column
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(482.4f, 513.8f+6.7f, 64.4f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", peakBill)).setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(482.4f, 484.8f+6.7f, 64.4f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", offPeakBill)).setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(482.4f, 463.2f+6.7f, 64.4f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", totalUseage)).setFont(calibriBold).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(482.4f, 405.7f+6.7f, 64.4f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", billLatest.getAdjustments())).setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(482.4f, 384.0f+6.7f, 64.4f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", discount)).setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(482.4f, 362.5f+6.7f, 64.4f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", adjustmentsDiscounts)).setFont(calibriBold).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(482.4f, 304.9f+6.7f, 64.4f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", penalties)).setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(482.4f, 283.2f+6.7f, 64.4f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", penalties)).setFont(calibriBold).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            //end peak useage column
            
            //Middle column
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(432.4f, 513.6f+6.7f, 39.6f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", billLatest.getPeakrate())).setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(432.4f, 484.9f+6.7f, 39.6f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", billLatest.getOffPeakRate())).setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(312.3f, 513.6f+6.7f, 85.8f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", billLatest.getPeakUseage())).setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(312.3f, 484.9f+6.7f, 85.8f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", billLatest.getOffPeakUseage())).setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(442.8f, 383.9f+6.7f, 31.1f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.1f", billLatest.getDiscount())+"%").setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(380.7f, 383.9f+6.7f, 60.1f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", totalBill)).setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(442.8f, 304.8f+6.7f, 31.1f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.1f", billLatest.getPenalty())+"%").setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            // rect = new Rectangle(230.5f, 330f, 58.5f, 209f);
            rect = new Rectangle(380.7f, 304.8f+6.7f, 60.1f, 6.0f);
            canvas.rectangle(rect);
            //canvas.stroke();
            childCanvas = new Canvas(canvas, pdfDoc, rect);
            title = new Text(String.format("%.2f", totalBill)).setFont(calibriRegular).setFontColor(myColor).setFontSize(9);
            p = new Paragraph().add(title).setTextAlignment(TextAlignment.RIGHT);
            childCanvas.add(p);
            childCanvas.close();
            
            //customer
            canvas.beginText().setFontAndSize(calibriRegular, 9) 
    						.moveText(127, 709.20f)
    						.setColor(myColor, true)
    						.showText(config.getCustomer().getName()) 
    						.endText();
            
            if(billLatest.getId().equals("Ground")) {
            	//block
                canvas.beginText().setFontAndSize(
        				calibriRegular, 9) 
        						.moveText(106, 669.5f)
        						.showText("Meter "+config.getMeter()) 
        						.endText();
            }else {
            	//block
                canvas.beginText().setFontAndSize(
        				calibriRegular, 9) 
        						.moveText(106, 669.5f)
        						.showText("Office "+config.getUnit()) 
        						.endText();
            }

          //Floor
            canvas.beginText().setFontAndSize(
    				calibriRegular, 9) 
    						.moveText(106, 655.5)
    						.showText(billLatest.getId()+" Floor") 
    						.endText();
            
          //Invo number
            canvas.beginText().setFontAndSize(
    				calibriRegular, 9) 
    						.moveText(367, 709.2f)
    						.showText(String.format("%012d", billLatest.getBillId()))
    						.endText();
            
          //Invo Date
            canvas.beginText().setFontAndSize(
    				calibriRegular, 9) 
    						.moveText(372, 691.2f)
    						.showText(dateTimeFormat.format(LocalDateTime.now())) 
    						.endText();
            
          //Invo From To
            canvas.beginText().setFontAndSize(
    				calibriRegular, 9) 
    						.moveText(378.5, 676.8f)
    						.showText(format.format(billLatest.getFromDate())+" - "+format.format(billLatest.getToDate())) 
    						.endText();
            
          //Invo due date
            Calendar cal = Calendar.getInstance();
    		cal.setTime(billLatest.getTimestamp());
    		cal.add(Calendar.DATE, billLatest.getDueDaysPeriod().intValue());
    		
            canvas.beginText().setFontAndSize(
    				calibriRegular, 9) 
    						.moveText(357, 655.2f)
    						.showText(format.format(cal.getTime())) 
    						.endText();
         
          //Emergency contact
            canvas.beginText().setFontAndSize(
    				calibriRegular, 9) 
    						.moveText(361.0f, 754.7f)
    						.showText(billProps.get(Constants.CONFIG_KEY_EMERGENCY_CONTACT))
    						.endText();
             
          //Billing inquiries
            canvas.beginText().setFontAndSize(
    				calibriRegular, 9) 
    						.moveText(315.8f, 740.4f)
    						.showText(billProps.get(Constants.CONFIG_KEY_BILLING_INQUIRIES)) 
    						.endText();
            /*
          //Balance fwd
            canvas.beginText().setFontAndSize(
    				calibriBold, 9) 
    						.moveText(250, 598)
    						.showText(df2.format(total_current_bill)+" LKR") 
    						.endText();
          //Current bill
            canvas.beginText().setFontAndSize(
    				calibriBold, 9) 
    						.moveText(335, 598)
    						.showText(df2.format(total_current_bill)+" LKR") 
    						.endText();
            
          //Current balance
            canvas.beginText().setFontAndSize(
    				calibriBold, 9) 
    						.moveText(435, 598)
    						.showText(df2.format(current_balance)+" LKR") 
    						.endText();
            //Peak
            //Prev reading
            canvas.beginText().setFontAndSize(
    				calibriBold, 9) 
    						.moveText(62, 514)
    						.showText("0.00 kWh") 
    						.endText();
            
          //Current reading
            canvas.beginText().setFontAndSize(
    				calibriBold, 9) 
    						.moveText(200, 514)
    						.showText("0.00 kWh") 
    						.endText();
            
            //Usage
            canvas.beginText().setFontAndSize(
    				calibriBold, 9) 
    						.moveText(350, 514)
    						.showText(df2.format(peakUseageOfAUnit.getValue())+" kWh") 
    						.endText();
            //Offpeak
          //Prev reading
            canvas.beginText().setFontAndSize(
    				calibriBold, 9) 
    						.moveText(62, 486)
    						.showText("0.00 kWh") 
    						.endText();
            
          //Current reading
            canvas.beginText().setFontAndSize(
    				calibriBold, 9) 
    						.moveText(200, 486)
    						.showText("0.00 kWh") 
    						.endText();
            
            //Usage
            canvas.beginText().setFontAndSize(
    				calibriBold, 9) 
    						.moveText(350, 486)
    						.showText(df2.format(offpeak_useage)+" kWh") 
    						.endText();
          //Prev Balance
            canvas.beginText().setFontAndSize(
    				calibriBold, 9) 
    						.moveText(400, 431)
    						.showText("0.00 LKR") 
    						.endText();
          //Payment
            canvas.beginText().setFontAndSize(
    				calibriBold, 9) 
    						.moveText(400, 416)
    						.showText(df2.format(Float.parseFloat(epp))+" LKR") 
    						.endText();
            
          //peak Usage
            canvas.beginText().setFontAndSize(
    				calibriBold, 9) 
    						.moveText(135, 387)
    						.showText(df2.format(peakUseageOfAUnit.getValue())) 
    						.endText();
            
          //off peak Usage
            canvas.beginText().setFontAndSize(
    				calibriBold, 9) 
    						.moveText(135, 372)
    						.showText(df2.format(offpeak_useage)) 
    						.endText();
            
          //peak rate
            canvas.beginText().setFontAndSize(
    				calibriBold, 9) 
    						.moveText(222, 387)
    						.showText(erp) 
    						.endText();
            
          //off peak rate
            canvas.beginText().setFontAndSize(
    				calibriBold, 9) 
    						.moveText(222, 372)
    						.showText(ero) 
    						.endText();
            
            //peak Usage rupees
              canvas.beginText().setFontAndSize(
      				calibriBold, 9) 
      						.moveText(330, 387)
      						.showText(df2.format(peak_current_bill)) 
      						.endText();
              
            //off peak Rupees
              canvas.beginText().setFontAndSize(
      				calibriBold, 9) 
      						.moveText(330, 372)
      						.showText(df2.format(offpeak_current_bill)) 
      						.endText();
              
              //service charge
              canvas.beginText().setFontAndSize(
      				calibriBold, 9) 
      						.moveText(330, 357)
      						.showText("0.00") 
      						.endText();
              
              //This month bill
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.COURIER_BOLD), 9) 
      						.moveText(405, 344)
      						.showText(df2.format(current_balance)+" LKR") 
      						.endText();
            
            //Penalty
              canvas.beginText().setFontAndSize(
      				calibriBold, 9) 
      						.moveText(405, 331)
      						.showText("0.00 LKR") 
      						.endText();
              
              //Discounts
              canvas.beginText().setFontAndSize(
      				calibriBold, 9) 
      						.moveText(405, 317)
      						.showText(df2.format(discount)+" LKR") 
      						.endText();
              
              //Total payable
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.COURIER_BOLD), 9) 
      						.moveText(405, 303)
      						.showText(df2.format(current_balance-discount)+" LKR") 
      						.endText();
              
            //Due on
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.COURIER_BOLD), 12) 
      						.moveText(130, 207)
      						.showText(cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR)) 
      						.endText();
              
              //Amount
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.COURIER_BOLD), 12) 
      						.moveText(360, 207)
      						.showText(df2.format(current_balance-discount)+" LKR") 
      						.endText();
              */
              
    		pdfDoc.close();

    		} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
			}

        return new ByteArrayInputStream(out.toByteArray());
    }
    
}