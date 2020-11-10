package com.dtvc.api.service;

import java.io.IOException;

public interface FirebaseService {

    String uploadObject(String projectId, String bucketName, String firebaseName, String filePath) throws IOException;
}
