/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patronus.fractal.core.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.patronus.constants.PatronusServicePaths;
import org.patronus.fractal.core.service.utils.JSONParseAndDBConn;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.patronus.core.dto.FractalDTO;
import org.patronus.fractal.core.graph.EdgeListDataService;

/**
 * REST Web Service
 *
 * @author dgrfi
 */
@Path(PatronusServicePaths.GRAPH_BASE)
public class GraphResource {

    @Context
    private UriInfo context;
    private   String UPLOAD_FOLDER;
    EdgeListDataService gcs;

    /**
     * Creates a new instance of GraphResource
     */
    public GraphResource() {
        gcs = new EdgeListDataService();
    }

    /**
     * Retrieves representation of an instance of
     * org.dgrf.fractal.core.service.GraphResource
     *
     * @param fractalDTOJSON
     * @return an instance of java.lang.String
     */
    @Path(PatronusServicePaths.GRAPH_IMPORT_FROM_VG)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String importPSVGGraph(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO) JSONParseAndDBConn.authCreateDB(fractalDTOJSON, FractalDTO.class);
        fractalDTO = gcs.importPSVGGraph(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);

    }

    @Path(PatronusServicePaths.GRAPH_DELETE)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteGraph(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO) JSONParseAndDBConn.authCreateDB(fractalDTOJSON, FractalDTO.class);
        fractalDTO = gcs.deleteGraph(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);

    }

    @Path(PatronusServicePaths.NETWORK_STATS_CALCULATION)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String calculateNetworkStats(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO) JSONParseAndDBConn.authCreateDB(fractalDTOJSON, FractalDTO.class);
        fractalDTO = gcs.calculateNetworkStats(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);

    }

    @Path(PatronusServicePaths.NETWORK_STATS_DELETE)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteNetworkStats(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO) JSONParseAndDBConn.authCreateDB(fractalDTOJSON, FractalDTO.class);
        fractalDTO = gcs.deleteNetworkStats(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);

    }
    
    @Path(PatronusServicePaths.GRAPH_UPLOAD)
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA})
    @Produces(MediaType.TEXT_PLAIN)
    public String uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @FormDataParam("uploadDTO") String fractalDTOJSON) throws IOException {
        UPLOAD_FOLDER = System.getProperty("user.dir") + File.separator;
        FractalDTO fractalDTO = (FractalDTO) JSONParseAndDBConn.authCreateDB(fractalDTOJSON, FractalDTO.class);

        // check if all form parameters are provided
        if (uploadedInputStream == null || fileDetail == null) {
            return null;
        }
        String uploadedFileLocation = UPLOAD_FOLDER + fileDetail.getFileName();
        
        OutputStream out = null;
        try {
            int read = 0;
            byte[] bytes = new byte[2048];
            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            return null;
        }


        //upload data series
        fractalDTO.setCsvFilePath(uploadedFileLocation);
        fractalDTO = gcs.uploadGraph(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);
    }
}
