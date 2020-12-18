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
import org.patronus.fractal.core.IPSVG.IPSVGCalcService;
import org.patronus.core.dto.FractalDTO;
import org.patronus.constants.PatronusServicePaths;
import org.patronus.fractal.core.service.utils.JSONParseAndDBConn;

/**
 * REST Web Service
 *
 * @author bhaduri
 */
@Path(PatronusServicePaths.IMPROVED_PSVG_BASE)
public class IPSVGCalService {
    
    @Context
    private UriInfo context;
    IPSVGCalcService ipsvgcs;

    public IPSVGCalService() {
        ipsvgcs = new IPSVGCalcService();
    }
    
    @Path(PatronusServicePaths.CALCULATE_IMPROVED_PSVG)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getImprovedPSVG(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO)JSONParseAndDBConn.authCreateDB (fractalDTOJSON,FractalDTO.class);
        fractalDTO = ipsvgcs.calculateImprovedPSVG(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);
        
    }
    
    @Path(PatronusServicePaths.QUEUE_IMPROVED_PSVG)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getQueueImprovedPSVG(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO)JSONParseAndDBConn.authCreateDB (fractalDTOJSON,FractalDTO.class);
        fractalDTO = ipsvgcs.queueImprovedPSVGCalculation(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);
        
    }
    
    @Path(PatronusServicePaths.DELETE_IMPROVED_PSVG)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteIpsvgResults(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO)JSONParseAndDBConn.authCreateDB (fractalDTOJSON,FractalDTO.class);
        fractalDTO = ipsvgcs.deletePSVGResults(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);
        
    }
    
    @Path(PatronusServicePaths.GET_IMPROVED_PSVG_RESULTS)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getIpsvgResults(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO)JSONParseAndDBConn.authCreateDB (fractalDTOJSON,FractalDTO.class);
        fractalDTO = ipsvgcs.getPsvgResults(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);
        
    }
}
