package server;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.List;
public class FileUploader implements Runnable{

  @Override
  public void run() {
     System.out.println("Uploading files.");
    String bucket_name = "gladshire-accelerometer";

    System.out.format("Objects in S3 bucket %s:\n", bucket_name);
    final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
    ListObjectsV2Result result = s3.listObjectsV2(bucket_name);
    List<S3ObjectSummary> objects = result.getObjectSummaries();
    for (S3ObjectSummary os : objects) {
      System.out.println("* " + os.getKey());
    }
  }
}
