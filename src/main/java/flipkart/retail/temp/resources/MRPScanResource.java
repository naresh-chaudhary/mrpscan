package flipkart.retail.temp.resources;

import com.codahale.metrics.annotation.Timed;
import flipkart.retail.temp.MRPScan;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 * Created by nareshkumar.v on 21/12/18.
 */

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.MULTIPART_FORM_DATA)
public class MRPScanResource {

  @POST
  @Path("upload")
  @Produces(MediaType.APPLICATION_JSON)
  @Timed
  public Response upload(@FormDataParam("datafile") InputStream image) throws Exception {
    MRPScan mrpScan = new MRPScan();
    String mrp = mrpScan.fetchMRPfromImage(image);
    return Response.ok().entity(mrp).build();
  }

}
