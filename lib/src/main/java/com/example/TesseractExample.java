package com.example;
/**
 * Created by bryan on 2/9/15.
 */

import net.sourceforge.tess4j.*;


import java.io.File;

public class TesseractExample {

    TesseractExample(){}

    public static void example(String[] args) {
        File imageFile = new File("eurotext.tif");
        Tesseract instance = Tesseract.getInstance();  // JNA Interface Mapping
        // Tesseract1 instance = new Tesseract1(); // JNA Direct Mapping

        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}