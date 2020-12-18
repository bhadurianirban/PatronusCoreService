/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patronus.fractal.core.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.patronus.fractal.core.MFDXA.MFDXACalcService;
import org.patronus.core.dto.FractalDTO;
import org.patronus.constants.PatronusServicePaths;
import org.patronus.fractal.core.service.utils.JSONParseAndDBConn;

/**
 * REST Web Service
 *
 * @author dgrfiv
 */
@Path(PatronusServicePaths.MFDXA_BASE)
public class MfdxacalcRestService {

    @Context
    private UriInfo context;
    MFDXACalcService mfdxacs;

    public MfdxacalcRestService() {
        mfdxacs = new MFDXACalcService();
    }
    
    @Path(PatronusServicePaths.CALCULATE_MFDXA)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getMfdxaCalcResults(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO)JSONParseAndDBConn.authCreateDB (fractalDTOJSON,FractalDTO.class);
        fractalDTO = mfdxacs.calculateMFDXA(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);
        
    }
    
    @Path(PatronusServicePaths.QUEUE_MFDXA_RESULTS)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getQueueMFDXACalculation(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO)JSONParseAndDBConn.authCreateDB (fractalDTOJSON,FractalDTO.class);
        fractalDTO = mfdxacs.queueMFDXACalculation(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);
        
    }

}
