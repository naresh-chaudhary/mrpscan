package flipkart.retail.temp;

import com.google.api.client.util.Lists;
import com.google.api.client.util.Maps;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.protobuf.ByteString;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;

/**
 * Created by mohammad.talha on 20/12/18.
 */
public class MRPScan {

  private Parser parser = new Parser();


  public String fetchMRPfromImage(InputStream image) throws IOException {
    String  mrp = null;

    try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

      List<AnnotateImageRequest> requests = new ArrayList<>();


        ByteString imgBytes = ByteString.copyFrom(IOUtils.toByteArray(image));

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
            .addFeatures(feat)
            .setImage(img)
            .build();
        requests.add(request);


      BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
      List<AnnotateImageResponse> responses = response.getResponsesList();
      //int c = 11;

      for (AnnotateImageResponse res : responses) {
        if (res.hasError()) {
          System.out.printf("Error: %s\n", res.getError().getMessage());
          return null;
        }
        TextAnnotation textAnnotation = res.getFullTextAnnotation();
        String text = textAnnotation.getText();
        text = text.replace("\n", " ");
        System.out.println("TEXT: " + text);
         mrp = parser.findMrp(text);
        if (mrp.length() != 0 && (mrp.charAt(0) == '2' || mrp.charAt(0) == '3')) {
          Double d = Double.parseDouble(mrp);
          if (d > 1999.0) {
            mrp = mrp.substring(1);
          }
        }
        System.out.println("MRP: " + mrp);

      }
    }
    return mrp;
  }
}

