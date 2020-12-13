package com.stardust.sync.util;

import java.awt.Rectangle;

import org.jfree.chart.JFreeChart;
import org.springframework.stereotype.Service;

import com.orsonpdf.PDFDocument;
import com.orsonpdf.PDFGraphics2D;
import com.orsonpdf.Page;

@Service
public class ChartGenerator {

	    public byte[] generateChartPDF(JFreeChart chart) {
	        // here we use OrsonPDF to generate PDF in a byte array
	        PDFDocument doc = new PDFDocument();
	        Rectangle bounds = new Rectangle(595, 335);
	        Page page = doc.createPage(bounds);
	        PDFGraphics2D g2 = page.getGraphics2D();
	        chart.draw(g2, bounds);
	        return doc.getPDFBytes();
	    }
}
