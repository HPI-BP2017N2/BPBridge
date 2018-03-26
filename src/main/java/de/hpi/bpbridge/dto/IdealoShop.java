package de.hpi.bpbridge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdealoShop {

    private long oracleShopId, shopId;
    private String shopName, shopUrl;
}
