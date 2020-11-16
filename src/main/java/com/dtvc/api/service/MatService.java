package com.dtvc.api.service;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;


public class MatService {

    public static byte[] matToStream(Mat mat) {
        try {
            MatOfByte matOfByte = new MatOfByte();
            Imgcodecs.imencode(".jpg", mat, matOfByte);
            return matOfByte.toArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
