package org.acme;

import static jakarta.ws.rs.core.MediaType.MULTIPART_FORM_DATA;
import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.acme.service.FileService;
import org.jboss.resteasy.reactive.MultipartForm;

@Path("/file")
public class FileResource {

    @Inject
    FileService fileService;

    @POST
    @Consumes(MULTIPART_FORM_DATA)
    @Produces(TEXT_PLAIN)
    public Response uploadFile(@MultipartForm MultipartBody data) {
        fileService.saveFileOnS3(data.fileName, data.file);
        return Response.ok().build();
    }

    @GET
    @Produces("application/pdf")
    @Path("/{fileName}")
    public Response downloadFile(@PathParam("fileName") String fileName) {
        byte[] fileInBytes = fileService.downloadFileFromS3(fileName);
        Response.ResponseBuilder responseBuilder = Response.ok(fileInBytes);
        responseBuilder.type("application/pdf");
        responseBuilder.header("Content-Disposition", "filename=" + fileName);
        return responseBuilder.build();
    }

}
