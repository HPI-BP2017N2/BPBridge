package de.hpi.bpbridge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdealoOffer {

    private HashMap<String, Integer> price;
    private HashMap<String, List<String>> attributeSearchText;
    private HashMap<String, String> titles, urls, descriptions;
    private String brandName, offerKey, productSearchText, sku;
    private List<String> categoryPaths, hans, eans;
}