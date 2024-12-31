package server;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class FileUploader implements Runnable {

  private String directory;
  public FileUploader(String path) {
    this.directory = path;
  }

  @Override
  public void run() {
     System.out.println("Uploading files.");
    String bucket_name = "gladshire-accelerometer";

    System.out.format("Objects in S3 bucket %s:\n", bucket_name);
    final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
    ListObjectsV2Result result = s3.listObjectsV2(bucket_name);
    List<S3ObjectSummary> objects = result.getObjectSummaries();
    HashSet<String> s3FileSet = new HashSet<>();
    for (S3ObjectSummary os : objects) {
      System.out.println("* " + os.getKey());
      s3FileSet.add(os.getKey());
    }
    File filesOnDragonBoard = new File(directory);
    File[] filelist = filesOnDragonBoard.listFiles();
    List<String> needToUploadList = new ArrayList<>();
    for(int i=0; i < filelist.length;i++) {
       File oneFile = filelist[i];
       String name = oneFile.getName();
       if(!s3FileSet.contains(name)) {
         System.out.println("Need to upload: "+ name);
         needToUploadList.add(name);
       }
    }
    for(String keyname: needToUploadList) {
      String filepath = directory + keyname;
      try {
        s3.putObject(bucket_name, keyname, new File(filepath));
      } catch (AmazonServiceException e) {
        System.err.println(e.getErrorMessage());
        System.exit(1);
      }
    }

  }
}
