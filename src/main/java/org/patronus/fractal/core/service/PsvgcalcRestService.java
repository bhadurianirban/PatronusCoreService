/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patronus.fractal.core.service;

import javax.servlet.ServletContext;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.patronus.constants.PatronusConstants;
import org.patronus.fractal.core.PSVG.PSVGCalcService;
import org.patronus.core.dto.FractalDTO;
import org.patronus.constants.PatronusServicePaths;
import org.patronus.fractal.core.service.utils.JSONParseAndDBConn;

@Path(PatronusServicePaths.PSVG_BASE)
public class PsvgcalcRestService {

    
    
    @Context
    private ServletContext context;
    PSVGCalcService psvgcs;

    public PsvgcalcRestService() {
        psvgcs = new PSVGCalcService();
        
    }
    
    @Path(PatronusServicePaths.CALCULATE_PSVG)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getPsvgCalcInstance(String fractalDTOJSON) {
        PatronusConstants.TEMP_FILE_PATH = context.getRealPath("/");
        FractalDTO fractalDTO = (FractalDTO)JSONParseAndDBConn.authCreateDB (fractalDTOJSON,FractalDTO.class);
        fractalDTO = psvgcs.calculatePSVG(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);
        
    }

    @Path(PatronusServicePaths.GET_PSVG_RESULTS)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getIpsvgResults(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO)JSONParseAndDBConn.authCreateDB (fractalDTOJSON,FractalDTO.class);
        fractalDTO = psvgcs.getPsvgResults(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);
        
    }
    
    @Path(PatronusServicePaths.DELETE_PSVG_RESULTS)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deletePSVGResults(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO)JSONParseAndDBConn.authCreateDB (fractalDTOJSON,FractalDTO.class);
        fractalDTO = psvgcs.deletePSVGResults(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);
        
    }
    
    @Path(PatronusServicePaths.QUEUE_PSVG_RESULTS)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String queuePSVGCalculation(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO)JSONParseAndDBConn.authCreateDB (fractalDTOJSON,FractalDTO.class);
        fractalDTO = psvgcs.queuePSVGCalculation(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);
        
    }
    
//    @Path(PatronusServicePaths.DELETE_PSVG_VG)
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public String deleteVisibilityGraph(String fractalDTOJSON) {
//        FractalDTO fractalDTO = (FractalDTO)JSONParseAndDBConn.authCreateDB (fractalDTOJSON,FractalDTO.class);
//        fractalDTO = psvgcs.deleteVisibilityGraph(fractalDTO);
//        return JSONParseAndDBConn.getDTOJSON(fractalDTO);
//        
//    }
}
