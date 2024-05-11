package com.example.myapplicationrashi;

import android.content.Context;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;

public class PrintFile {


	public PrintFile(Context context, String filePath)
	{

	        //create object of print manager in your device
	        PrintManager printManager = (PrintManager) context.getSystemService(Context.PRINT_SERVICE);

	        //create object of print adapter
	        PrintDocumentAdapter printAdapter = new PdfDocumentAdapter(context, filePath);

	        //provide name to your newly generated pdf file
	        String jobName = context.getString(R.string.app_name) + " Print File";

	        //open print dialog
	        printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
			//openPdfPrint();


	}
}
