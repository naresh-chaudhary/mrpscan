package flipkart.retail.temp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.protobuf.ByteString;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by mohammad.talha on 20/12/18.
 */
public class MRPScan {

  public static void main(String[] args) throws IOException {
//    System.getenv().put("GOOGLE_APPLICATION_CREDENTIALS", "/Users/mohammad.talha/Downloads/key.json");
    try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

      // The path to the image file to annotate
      List<AnnotateImageRequest> requests = new ArrayList<>();
      String baseFile = "/Users/mohammad.talha/Downloads/fileText_3";
      for (int i = 11; i <= 14; i++) {
        String fileName = "/Users/mohammad.talha/Downloads/next_3/a" + i + ".jpeg";
//      export GOOGLE_APPLICATION_CREDENTIALS="/Users/mohammad.talha/Downloads/key.json";

        Path path = Paths.get(fileName);
        byte[] data = Files.readAllBytes(path);
        ByteString imgBytes = ByteString.copyFrom(data);

        // Builds the image annotation request

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
            .addFeatures(feat)
            .setImage(img)
            .build();
        requests.add(request);
      }

      // Performs label detection on the image file

//      long st = System.currentTimeMillis();
      BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
      List<AnnotateImageResponse> responses = response.getResponsesList();
      int c = 11;
      for (AnnotateImageResponse res : responses) {
        if (res.hasError()) {
          System.out.printf("Error: %s\n", res.getError().getMessage());
          return;
        }
        TextAnnotation textAnnotation = res.getFullTextAnnotation();
        String text = textAnnotation.getText();
        text = text.replace("\n", " ");
        System.out.println(text);
        File fout = new File(baseFile + "/a" + c++ + ".csv");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write(text);
        bw.newLine();
        bw.close();
      }
    }
  }
}

