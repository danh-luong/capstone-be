package com.dtvc.api.controller;

import com.dtvc.api.service.MatService;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
@RestController
public class StreamingController {

    private static final String urlCamera = "rtsp://admin:DNJKLM@192.168.43.23:554";
    private static final String urlCamera1 = "rtsp://admin:DERRUH@192.168.43.110:554";

    static {
        String path = null;
        try {
            //I have copied dlls from opencv folder to my project folder
            path = "C:\\Users\\ASUS\\Desktop\\dtvc-be";
            System.load(path+"\\opencv_java430.dll");
            System.load(path+"\\opencv_videoio_ffmpeg430_64.dll");
        } catch (UnsatisfiedLinkError e) {
            System.out.println("Error loading libs");
        }
    }

//    @PostMapping("/camera")
//    public Map<String, String> getFrameOfCamera() {
//        VideoCapture camera = new VideoCapture();
//        camera.open(urlCamera);
//        Mat mat = new Mat();
//        camera.read(mat);
//        if (camera.isOpened()) {
//            byte[] imgBytes = MatService.matToStream(mat);
//            String imgBase64 = Base64.getEncoder().encodeToString(imgBytes);
//            Map<String, String> listStringMap = new HashMap<>();
//            listStringMap.put("frame", imgBase64);
//            return listStringMap;
//        }
//        return null;
//    }

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/left/camera/frame")
    @SendTo("/dtvc/left/camera")
    public Map<String, String> getFrameOfLeftCamera() {
        SimpMessagingTemplate temp = this.template;
        VideoCapture camera = new VideoCapture();
        camera.set(Videoio.CAP_PROP_FRAME_WIDTH,  640);
        camera.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);
        camera.open(urlCamera1);
        Mat mat = new Mat();
        new Runnable() {
            @Override
            public void run() {
                while (true) {
                    camera.read(mat);
                    if (camera.isOpened()) {
                        byte[] imgBytes = MatService.matToLeftStream(mat);
                        String imgBase64 = Base64.getEncoder().encodeToString(imgBytes);
                        Map<String, String> listStringMap = new HashMap<>();
                        listStringMap.put("frame", imgBase64);
                        temp.convertAndSend("/dtvc/left/camera", listStringMap);
                    }
                }
            }
        }.run();
        return null;
    }

    @MessageMapping("/right/camera/frame")
    @SendTo("/dtvc/right/camera")
    public Map<String, String> getFrameOfRightCamera() {
        SimpMessagingTemplate temp = this.template;
        VideoCapture camera = new VideoCapture();
        camera.set(Videoio.CAP_PROP_FRAME_WIDTH,  640);
        camera.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);
        camera.open(urlCamera);
        Mat mat = new Mat();
        new Runnable() {
            @Override
            public void run() {
                while (true) {
                    camera.read(mat);
                    if (camera.isOpened()) {
                        byte[] imgBytes = MatService.matToRightStream(mat);
                        String imgBase64 = Base64.getEncoder().encodeToString(imgBytes);
                        Map<String, String> listStringMap = new HashMap<>();
                        listStringMap.put("frame", imgBase64);
                        temp.convertAndSend("/dtvc/right/camera", listStringMap);
                    }
                }
            }
        }.run();
        return null;
    }
}
