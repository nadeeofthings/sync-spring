package com.stardust.sync.util;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.stardust.sync.model.Meter;
import com.stardust.sync.service.MeterService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class GeneratePdfReport {
	
	@Autowired
	MeterService meterService;

    private static final Logger logger = LoggerFactory.getLogger(GeneratePdfReport.class);
    private static final String esrc = "c:/sync/elec.pdf";
    private static final String asrc = "c:/sync/aircon.pdf";
    
    private static final String[] office = {"3rd","4th","5th","6th"}; 
    
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    

    public ByteArrayInputStream electricalR(String efl, String efr, String ero, String epp, String edi, String eof, String eto, String erp, String epe) {
    	df2.setRoundingMode(RoundingMode.UP);
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	try {
            
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(esrc), new PdfWriter(out));
            
            PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
            
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date   fromDate       = format.parse (efr);   
            Date   toDate       = format.parse (eto);   
            
            Calendar cal = Calendar.getInstance();
    		cal.setTime(new Date());
    		
    		cal.add(Calendar.MONTH, 2);
    		
            
            Meter totalUseageOfAUnit = meterService.totalUseageOfAUnit(efl, eof, "kWh", 1, fromDate, toDate);
            
            Meter peakUseageOfAUnit = meterService.peakUseageOfAUnit(efl, eof, "kWh", 1, fromDate, toDate);
            
            double offpeak_useage = totalUseageOfAUnit.getValue()-peakUseageOfAUnit.getValue();
            double offpeak_current_bill = offpeak_useage*Double.parseDouble(ero);
            double peak_current_bill = peakUseageOfAUnit.getValue()*Double.parseDouble(erp);
            double total_current_bill = offpeak_current_bill+peak_current_bill;
    		//customer
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(106, 701)
    						.showText("Office "+eof) 
    						.endText();
          //block
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(106, 671)
    						.showText(eof) 
    						.endText();
            
          //Floor
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(106, 656)
    						.showText(efl+" Floor") 
    						.endText();
          //Invo number
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(365, 701)
    						.showText("0000001") 
    						.endText();
            
          //Invo Date
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(365, 686)
    						.showText(new Date().toLocaleString()) 
    						.endText();
            
          //Invo From
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(365, 656)
    						.showText(efr) 
    						.endText();
          //Invo To
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(365, 641)
    						.showText(eto) 
    						.endText();
            
          //Previous balance
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(70, 598)
    						.showText("0.00 LKR") 
    						.endText();
          //Adjustments
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(160, 598)
    						.showText(epp+ " LKR") 
    						.endText();
          //Balance fwd
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(250, 598)
    						.showText("0.00 LKR") 
    						.endText();
          //Current bill
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(335, 598)
    						.showText(df2.format(total_current_bill)+" LKR") 
    						.endText();
            
          //Current balance
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(435, 598)
    						.showText(df2.format(total_current_bill)+" LKR") 
    						.endText();
            //Peak
            //Prev reading
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(62, 514)
    						.showText("0.00 kWh") 
    						.endText();
            
          //Current reading
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(200, 514)
    						.showText("0.00 kWh") 
    						.endText();
            
            //Usage
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(350, 514)
    						.showText(df2.format(peakUseageOfAUnit.getValue())+" kWh") 
    						.endText();
            //Offpeak
          //Prev reading
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(62, 486)
    						.showText("0.00 kWh") 
    						.endText();
            
          //Current reading
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(200, 486)
    						.showText("0.00 kWh") 
    						.endText();
            
            //Usage
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(350, 486)
    						.showText(df2.format(offpeak_useage)+" kWh") 
    						.endText();
          //Prev Balance
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(400, 431)
    						.showText("0.00 LKR") 
    						.endText();
          //Payment
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(400, 416)
    						.showText(df2.format(Float.parseFloat(epp))+" LKR") 
    						.endText();
            
          //peak Usage
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(135, 387)
    						.showText(df2.format(peakUseageOfAUnit.getValue())) 
    						.endText();
            
          //off peak Usage
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(135, 372)
    						.showText(df2.format(offpeak_useage)) 
    						.endText();
            
          //peak rate
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(222, 387)
    						.showText(erp) 
    						.endText();
            
          //off peak rate
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(222, 372)
    						.showText(ero) 
    						.endText();
            
            //peak Usage rupees
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
      						.moveText(330, 387)
      						.showText(df2.format(peak_current_bill)) 
      						.endText();
              
            //off peak Rupees
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
      						.moveText(330, 372)
      						.showText(df2.format(offpeak_current_bill)) 
      						.endText();
              
              //service charge
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
      						.moveText(330, 357)
      						.showText("0.00") 
      						.endText();
              
              //This month bill
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD), 9) 
      						.moveText(405, 344)
      						.showText(df2.format(total_current_bill)+" LKR") 
      						.endText();
            
            //Penalty
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
      						.moveText(405, 331)
      						.showText("0.00 LKR") 
      						.endText();
              
              //Discounts
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
      						.moveText(405, 317)
      						.showText("0.00 LKR") 
      						.endText();
              
              //Total payable
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD), 9) 
      						.moveText(405, 303)
      						.showText(df2.format(total_current_bill)+" LKR") 
      						.endText();
              
            //Due on
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD), 12) 
      						.moveText(130, 207)
      						.showText(cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR)) 
      						.endText();
              
              //Amount
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD), 12) 
      						.moveText(360, 207)
      						.showText(df2.format(total_current_bill)+" LKR") 
      						.endText();
              
              
    		pdfDoc.close();

    		} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        return new ByteArrayInputStream(out.toByteArray());
    }
    

    public ByteArrayInputStream airconR(String afl, String afr, String aro, String app, String adi, String aof, String ato, String arp, String ape) {
    	df2.setRoundingMode(RoundingMode.UP);
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	
    	try {
            
    		PdfDocument pdfDoc = new PdfDocument(new PdfReader(asrc), new PdfWriter(out));
            
            PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
            
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date   fromDate       = format.parse (afr);   
            Date   toDate       = format.parse (ato);   
            
            Calendar cal = Calendar.getInstance();
    		cal.setTime(new Date());
    		
    		cal.add(Calendar.MONTH, 2);
    		
            
            Meter totalUseageOfAUnit = meterService.totalUseageOfAUnit(afl, aof, "BTU", 2, fromDate, toDate);
            
            Meter peakUseageOfAUnit = meterService.peakUseageOfAUnit(afl, aof, "BTU", 2, fromDate, toDate);
            
            double offpeak_useage = totalUseageOfAUnit.getValue()-peakUseageOfAUnit.getValue();
            double offpeak_current_bill = offpeak_useage*Double.parseDouble(aro);
            double peak_current_bill = peakUseageOfAUnit.getValue()*Double.parseDouble(arp);
            double total_current_bill = offpeak_current_bill+peak_current_bill;
    		//customer
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(106, 701)
    						.showText("Office "+aof) 
    						.endText();
          //block
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(106, 671)
    						.showText(aof) 
    						.endText();
            
          //Floor
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(106, 656)
    						.showText(afl+" Floor") 
    						.endText();
          //Invo number
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(365, 701)
    						.showText("0000001") 
    						.endText();
            
          //Invo Date
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(365, 686)
    						.showText(new Date().toLocaleString()) 
    						.endText();
            
          //Invo From
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(365, 656)
    						.showText(afr) 
    						.endText();
          //Invo To
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(365, 641)
    						.showText(ato) 
    						.endText();
            
          //Previous balance
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(70, 598)
    						.showText("0.00 LKR") 
    						.endText();
          //Adjustments
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(160, 598)
    						.showText(app+ " LKR") 
    						.endText();
          //Balance fwd
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(250, 598)
    						.showText("0.00 LKR") 
    						.endText();
          //Current bill
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(335, 598)
    						.showText(df2.format(total_current_bill)+" LKR") 
    						.endText();
            
          //Current balance
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(435, 598)
    						.showText(df2.format(total_current_bill)+" LKR") 
    						.endText();
            //Peak
            //Prev reading
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(62, 514)
    						.showText("0.00 BTU") 
    						.endText();
            
          //Current reading
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(200, 514)
    						.showText("0.00 BTU") 
    						.endText();
            
            //Usage
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(350, 514)
    						.showText(df2.format(peakUseageOfAUnit.getValue())+" BTU") 
    						.endText();
            //Offpeak
          //Prev reading
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(62, 486)
    						.showText("0.00 BTU") 
    						.endText();
            
          //Current reading
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(200, 486)
    						.showText("0.00 BTU") 
    						.endText();
            
            //Usage
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(350, 486)
    						.showText(df2.format(offpeak_useage)+" BTU") 
    						.endText();
          //Prev Balance
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(400, 431)
    						.showText("0.00 LKR") 
    						.endText();
          //Payment
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(400, 416)
    						.showText(df2.format(Float.parseFloat(app))+" LKR") 
    						.endText();
            
          //peak Usage
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(135, 387)
    						.showText(df2.format(peakUseageOfAUnit.getValue())) 
    						.endText();
            
          //off peak Usage
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(135, 372)
    						.showText(df2.format(offpeak_useage)) 
    						.endText();
            
          //peak rate
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(222, 387)
    						.showText(arp) 
    						.endText();
            
          //off peak rate
            canvas.beginText().setFontAndSize(
    				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
    						.moveText(222, 372)
    						.showText(aro) 
    						.endText();
            
            //peak Usage rupees
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
      						.moveText(330, 387)
      						.showText(df2.format(peak_current_bill)) 
      						.endText();
              
            //off peak Rupees
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
      						.moveText(330, 372)
      						.showText(df2.format(offpeak_current_bill)) 
      						.endText();
              
              //service charge
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
      						.moveText(330, 357)
      						.showText("0.00") 
      						.endText();
              
              //This month bill
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD), 9) 
      						.moveText(405, 344)
      						.showText(df2.format(total_current_bill)+" LKR") 
      						.endText();
            
            //Penalty
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
      						.moveText(405, 331)
      						.showText("0.00 LKR") 
      						.endText();
              
              //Discounts
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.HELVETICA), 9) 
      						.moveText(405, 317)
      						.showText("0.00 LKR") 
      						.endText();
              
              //Total payable
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD), 9) 
      						.moveText(405, 303)
      						.showText(df2.format(total_current_bill)+" LKR") 
      						.endText();
              
            //Due on
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD), 12) 
      						.moveText(130, 207)
      						.showText(cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR)) 
      						.endText();
              
              //Amount
              canvas.beginText().setFontAndSize(
      				PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD), 12) 
      						.moveText(360, 207)
      						.showText(df2.format(total_current_bill)+" LKR") 
      						.endText();
              
              
    		pdfDoc.close();

    		} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        return new ByteArrayInputStream(out.toByteArray());
    }
}