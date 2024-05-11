package com.example.myapplicationrashi;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;

import android.print.PrintManager;
import com.github.barteksc.pdfviewer.PDFView; // For getDocumentUrl
public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int PDF_SELECTION_CODE = 200;
    Uri selectedFileUri;
    public Context context;
    private PDFView pdfView;
    private String rashi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        pdfView = findViewById(R.id.pdfView);
        context = MainActivity.this;
        // Check if a PDF is being opened from an external source
        Uri uri = getIntent().getData();
        Log.d("team1", String.valueOf(uri));
        if (uri != null && uri.toString().contains(".pdf")) {
            displayPDF(uri);
        } else {
            setDefaultPdfHandler();
            // Handle case where no PDF is opened directly
            // (You can show a message or prompt the user to select a PDF)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
//                selectPDFFile();
            } else {
                requestPermission();
            }
        } else {
//            selectPDFFile();
        }
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            selectPDFFile();
        } else {
            Toast.makeText(this, "Permission Denied! Please allow the permission to read PDF files.", Toast.LENGTH_SHORT).show();
        }
    }


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PDF_SELECTION_CODE && resultCode == RESULT_OK && data != null) {
            selectedFileUri = data.getData();
            displayPDF(selectedFileUri);
        }
    }

    private void displayPDF(Uri fileUri) {
        pdfView.fromUri(fileUri)
                .defaultPage(0)
                .enableSwipe(true)
                .enableDoubletap(true)
                .swipeHorizontal(false)
                .onLoad(nbPages -> {
                })
                .onPageChange((page, pageCount) -> {
                })
                .scrollHandle(new DefaultScrollHandle(this))
                .enableAnnotationRendering(true)
                .password(null)
                .pageFitPolicy(FitPolicy.WIDTH)
                .load();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {  //on option menu selection function
        switch (item.getItemId()) {
            case R.id.item1:  //call the update password menu button
//                applicationExit();
                onBackPressed();

//                Intent intent=new Intent(ManagerMain.this,SystemSettings.class);
//                startActivity(intent);
                break;
//            case R.id.item2:  //call the update password menu button
////                Print();
//
//                Uri uri = getIntent().getData();
//                openPdfPrint1(uri + "");
//                break;
        }

        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setDefaultPdfHandler() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set as Default PDF Viewer?")
                .setMessage("Do you want to set this application as the default app for opening PDF files?")
                .setPositiveButton("Yes", (dialog, id) -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("application/pdf"));
                    intent.setType("application/pdf"); // Set MIME type explicitly
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent); // No need for ACTION_ASSIGN_INTENT
                })
                .setNegativeButton("No", (dialog, id) -> {
                    // User declined
                })
                .create()
                .show();
    }

    public void openPdfPrint1(String openFile) {
        new PrintFile(context,openFile);
        Log.d("team4", openFile);

    }

//***********************Rashi***********************
//    public void Print() {
//        // Check if printing is supported
//        if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, PERMISSION_REQUEST_CODE);
//            return;
//        }
//        // Get the current PDF uri                      .getDocumentUrl();
//
//        Uri uri = getIntent().getData();
////        if (uri != null && uri.toString().contains(".pdf"))
////            displayPDF(uri);
//        Uri pdfUri = uri;
//
//        // Use an Intent to print the PDF
//        Context context;
//        if (pdfUri != null) {
//            Intent printIntent = new Intent(Intent.ACTION_VIEW);
//            printIntent.setDataAndType(pdfUri, "application/pdf");
//            printIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//            startActivity(printIntent);
//        } else {
//            Toast.makeText(this, "No PDF loaded to print!", Toast.LENGTH_SHORT).show();
//        }
//    }
//    private boolean hasPermission(String permission) {
//        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private void requestPermission(String permission, int requestCode) {
//        ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
//    }
//***********************Rashi***********************



}