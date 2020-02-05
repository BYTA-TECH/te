package com.bytatech.ayoos.patient.client.dms.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.bytatech.ayoos.patient.client.dms.ClientConfiguration;

@FeignClient(name="${dms.name:dms}", url="${dms.url }", configuration = ClientConfiguration.class)
public interface TagsApiClient extends TagsApi {
}