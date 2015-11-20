package com.fapse.mampf.model;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;

public class Printer {
	
	final static DocFlavor FLAVOR = DocFlavor.STRING.TEXT_PLAIN;
	
	public static void printText(String text) {
		DocAttributeSet attributeSet = new HashDocAttributeSet();
		attributeSet.add(MediaSizeName.ISO_A4);
		Doc document = new SimpleDoc(text, FLAVOR, attributeSet);
		
		PrintService service = PrintServiceLookup.lookupDefaultPrintService();
		
		if (!service.isDocFlavorSupported(FLAVOR)) {
			System.out.println(service.getName() + " unterstützt " +
					 FLAVOR + " nicht");
			System.exit(1);
		}
		
		DocPrintJob job = service.createPrintJob();
		
		PrintRequestAttributeSet params = new HashPrintRequestAttributeSet();
		params.add(new Copies(1));

		try {
			job.print(document, params);
		} catch (PrintException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
