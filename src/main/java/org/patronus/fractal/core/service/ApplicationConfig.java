/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patronus.fractal.core.service;

import java.util.Set;
import javax.ws.rs.core.Application;


/**
 *
 * @author bhaduri
 */
@javax.ws.rs.ApplicationPath("rest")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(org.patronus.fractal.core.service.BheuResource.class);
        resources.add(org.patronus.fractal.core.service.DataSeriesResource.class);
        resources.add(org.patronus.fractal.core.service.GraphResource.class);
        resources.add(org.patronus.fractal.core.service.IPSVGCalService.class);
        resources.add(org.patronus.fractal.core.service.MfdfacalcRestService.class);
        resources.add(org.patronus.fractal.core.service.MfdxacalcRestService.class);
        resources.add(org.patronus.fractal.core.service.PsvgcalcRestService.class);
        
    }
    
}
