/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.patronus.fractal.core.service.utils;


import org.hedwig.cloud.dto.DataConnDTO;


import org.hedwig.cloud.client.DataConnClient;
import org.hedwig.cloud.dto.HedwigAuthCredentials;

/**
 *
 * @author bhaduri
 */
public class PatronusAuthentication {

    public static DataConnDTO authenticateSubcription(HedwigAuthCredentials authCredentials) {
//
        DataConnClient dataConnClient = new DataConnClient(authCredentials.getHedwigServer(),authCredentials.getHedwigServerPort());
        DataConnDTO dataConnDTO = new DataConnDTO();
        dataConnDTO.setCloudAuthCredentials(authCredentials);
        dataConnDTO = dataConnClient.getDataConnParams(dataConnDTO);
        return dataConnDTO;
    }
    
//    public static void setCMSAuthCredentials( int productId, int tenantId, String userId, String password ){
//        CMSClientAuthDetails.USER_ID = userId;
//        CMSClientAuthDetails.PASSWORD = password;
//        CMSClientAuthDetails.PRODUCT_ID = productId;
//        CMSClientAuthDetails.TENANT_ID = tenantId;
//    }
}
