package org.acme;

import static jakarta.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;
import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;

import jakarta.ws.rs.FormParam;
import java.io.InputStream;
import org.jboss.resteasy.reactive.PartType;

public class MultipartBody {

    @FormParam("file")
    @PartType(APPLICATION_OCTET_STREAM)
    public InputStream file;

    @FormParam("fileName")
    @PartType(TEXT_PLAIN)
    public String fileName;
}
