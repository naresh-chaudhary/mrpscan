package flipkart.retail.temp;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import flipkart.retail.temp.config.AppConfiguration;
import flipkart.retail.temp.resources.MRPScanResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 * Created by nareshkumar.v on 21/12/18.
 */
public class MRPScanApp  extends Application<AppConfiguration> {
  public static void main(String[] args) throws Exception {
    MRPScanApp mrpScan = new MRPScanApp();
    mrpScan.run(args);
  }



  @Override
  public void initialize(Bootstrap<AppConfiguration> bootstrap) {
    // nothing to do yet
  }

  @Override
  public void run(AppConfiguration configuration,
      Environment environment) {
    // nothing to do yet
    final MRPScanResource resource = new MRPScanResource(

    );
    environment.jersey().register(resource);
    environment.jersey().register(MultiPartFeature.class);




  }

}
