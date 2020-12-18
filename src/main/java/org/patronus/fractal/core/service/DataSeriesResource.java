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
import org.patronus.fractal.core.dataseries.DataSeriesService;
import org.patronus.constants.PatronusServicePaths;
import org.patronus.core.dto.FractalDTO;
import org.patronus.fractal.core.service.utils.JSONParseAndDBConn;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 * REST Web Service
 *
 * @author bhaduri
 */
@Path(PatronusServicePaths.DATA_SERIES_BASE)
public class DataSeriesResource {

    @Context
    private UriInfo context;
    private   String UPLOAD_FOLDER;
    DataSeriesService dss;

    /**
     * Creates a new instance of DataSeriesResource
     */
    public DataSeriesResource() {
        dss = new DataSeriesService();
    }

    @Path(PatronusServicePaths.DELETE_DATA_SERIES)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteDataSeries(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO) JSONParseAndDBConn.authCreateDB(fractalDTOJSON, FractalDTO.class);
        fractalDTO = dss.deleteDataSeries(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);

    }

    @Path(PatronusServicePaths.GET_DATA_SERIES)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getDataSeries(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO) JSONParseAndDBConn.authCreateDB(fractalDTOJSON, FractalDTO.class);
        fractalDTO = dss.getDataSeries(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);

    }

    @Path(PatronusServicePaths.CREATE_DATA_SERIES_CMS_INSTANCE)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createDataSeriesTermInstance(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO) JSONParseAndDBConn.authCreateDB(fractalDTOJSON, FractalDTO.class);
        fractalDTO = dss.createDataSeriesTermInstance(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);

    }
    @Path(PatronusServicePaths.UPLOAD_DATA_SERIES)
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
        fractalDTO = dss.uploadDataSeries(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);
    }
}
