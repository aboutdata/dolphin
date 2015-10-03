package com.sabsari.dolphin.api.model.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabsari.dolphin.api.model.constants.PropertyValues;
import com.sabsari.dolphin.core.validator.annotation.IpAddresses;

import lombok.Data;

@Data
public class DeleteBlockIpRequest {

	@JsonProperty(value=PropertyValues.IP_LIST, required=true)
	@IpAddresses(mandatory=true)
	private List<String> ipList;
}
