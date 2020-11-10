package com.dtvc.api.serviceimpl;

import com.dtvc.api.service.FirebaseService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Service
public class FirebaseServiceImp implements FirebaseService {

    private StorageOptions storageOptions;

    @Override
    public String uploadObject(String projectId, String bucketName, String firebaseName, String filePath) throws IOException {
        FileInputStream serviceAccount = new FileInputStream("C:\\serviceAccountKey.json");
        this.storageOptions = StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
        Storage storage = storageOptions.getService();
        BlobId blobId = BlobId.of(bucketName, firebaseName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("application/pdf").build();
        Blob blob = storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));
        URL url = storage.signUrl(BlobInfo.newBuilder(blob.getBlobId()).build(), 100001, TimeUnit.DAYS);
        File file = new File(filePath);
        file.delete();
        return url.toString();
    }

}
