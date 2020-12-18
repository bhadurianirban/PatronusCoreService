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
import org.patronus.fractal.core.MFDFA.MFDFACalcService;
import org.patronus.core.dto.FractalDTO;
import org.patronus.constants.PatronusServicePaths;
import org.patronus.fractal.core.service.utils.JSONParseAndDBConn;

/**
 * REST Web Service
 *
 * @author dgrfiv
 */
@Path(PatronusServicePaths.MFDFA_BASE)
public class MfdfacalcRestService {

    @Context
    private UriInfo context;
    MFDFACalcService mfdfacs;

    public MfdfacalcRestService() {
        mfdfacs = new MFDFACalcService();
    }

    @Path(PatronusServicePaths.CALCULATE_MFDFA)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getMfdfaCalcResults(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO) JSONParseAndDBConn.authCreateDB(fractalDTOJSON, FractalDTO.class);
        fractalDTO = mfdfacs.calculateMFDFA(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);

    }

    @Path(PatronusServicePaths.QUEUE_MFDFA_RESULTS)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getQueueMfdfaCalcResults(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO) JSONParseAndDBConn.authCreateDB(fractalDTOJSON, FractalDTO.class);
        fractalDTO = mfdfacs.queueMFDFACalculation(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);

    }

    @Path(PatronusServicePaths.GET_MFDFA_RESULTS)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getMfdfaResults(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO) JSONParseAndDBConn.authCreateDB(fractalDTOJSON, FractalDTO.class);
        fractalDTO = mfdfacs.getMfdfaResults(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);

    }

    @Path(PatronusServicePaths.GET_DFA_RESULTS)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getDfaResults(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO) JSONParseAndDBConn.authCreateDB(fractalDTOJSON, FractalDTO.class);
        fractalDTO = mfdfacs.getDfaResults(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);

    }

    @Path(PatronusServicePaths.DELETE_MFDFA_RESULTS)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteMFDFAResults(String fractalDTOJSON) {
        FractalDTO fractalDTO = (FractalDTO) JSONParseAndDBConn.authCreateDB(fractalDTOJSON, FractalDTO.class);
        fractalDTO = mfdfacs.deleteMFDFAResults(fractalDTO);
        return JSONParseAndDBConn.getDTOJSON(fractalDTO);

    }

}
