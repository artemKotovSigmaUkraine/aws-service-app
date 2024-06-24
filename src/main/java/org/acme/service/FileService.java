package org.acme.service;

import static java.util.Objects.requireNonNull;
import static software.amazon.awssdk.core.sync.RequestBody.fromFile;

import com.amazonaws.util.IOUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import software.amazon.awssdk.services.s3.S3Client;

@ApplicationScoped
public class FileService {

    @Inject
    AwsHelper awsHelper;

    public void saveFileOnS3(String fileName, InputStream inputStream) {
        S3Client s3Client = awsHelper.buildS3Client();
        s3Client.putObject(awsHelper.buildPutObjectRequest(fileName), fromFile(requireNonNull(inputStreamIntoFile(fileName, inputStream))));
        s3Client.close();
    }

    public byte[] downloadFileFromS3(String fileName) {
        S3Client s3Client = awsHelper.buildS3Client();

        try (s3Client) {
            return s3Client.getObject(awsHelper.buildGetObjectRequest(fileName)).readAllBytes();
        } catch (IOException e) {
            return null;
        }
    }

    private File inputStreamIntoFile(String fileName, InputStream inputStream) {
        File file = new File(fileName);
        try (OutputStream outputStream = new FileOutputStream(file)) {
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            return null;
        }
        return file;
    }

}
