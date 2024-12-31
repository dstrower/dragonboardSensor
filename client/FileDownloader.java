package client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

public class FileDownloader implements Runnable {


  private ClientPanel clientPanel;
  public FileDownloader(ClientPanel cp) {
    this.clientPanel = cp;
  }

  @Override
  public void run() {
    int count = 0;
    String downloadDirectory = clientPanel.getDownloadDirectory();
    String bucketName = "gladshire-accelerometer";
    System.out.format("Objects in S3 bucket %s:\n", bucketName);
    final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
    ListObjectsV2Result result = s3.listObjectsV2(bucketName);
    List<S3ObjectSummary> objects = result.getObjectSummaries();
    List<String> s3FileList = new ArrayList<>();
    for (S3ObjectSummary os : objects) {
      System.out.println("* " + os.getKey());
      s3FileList.add(os.getKey());
    }
    File filesOnLaptop = new File(downloadDirectory);
    File[] filelist = filesOnLaptop.listFiles();
    HashSet<String> filesOnDirectorySet = new HashSet<>();
    for(int i=0; i < filelist.length;i++) {
       File oneFile = filelist[i];
       String name = oneFile.getName();
       filesOnDirectorySet.add(name);
    }
    List<String> needToDownloadList = new ArrayList<>();
    for(String keyname: s3FileList) {
       if(!filesOnDirectorySet.contains(keyname)) {
         needToDownloadList.add(keyname);
         System.out.println("Need to download: "+  keyname);
       }
    }

    for(String keyname: needToDownloadList) {
      GetObjectRequest objectRequest = GetObjectRequest.builder()
          .key(keyname)
          .bucket(bucketName)
          .build();
      S3Client s3Client = createS3Client();
      ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(objectRequest);
      byte[] data = objectBytes.asByteArray();
      String path = downloadDirectory + keyname;
      try (FileOutputStream stream = new FileOutputStream(path)) {
        stream.write(data);
        count++;
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    clientPanel.getTextArea().append("Downloaded: " + count + " files." + '\n');
  }

  public static S3Client createS3Client() {

    return S3Client.builder().region(Region.US_WEST_2).build();
  }
}
