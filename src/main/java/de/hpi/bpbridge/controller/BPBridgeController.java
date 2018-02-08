package de.hpi.bpbridge.controller;

import de.hpi.bpbridge.service.BPBridgeService;
import de.hpi.restclient.dto.GetRandomOffersResponse;
import de.hpi.restclient.dto.MatchAttributeResponse;
import de.hpi.restclient.dto.ShopIDToUrlResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE)
public class BPBridgeController {

    private BPBridgeService service;

    @Autowired
    public BPBridgeController(BPBridgeService service){
        setService(service);
    }

    @RequestMapping(value = "/shopIDToUrl", method = RequestMethod.GET, produces = "application/json")
    public ShopIDToUrlResponse shopIDToUrl(@RequestParam(value="shopID") long shopID) {
        ShopIDToUrlResponse response = new ShopIDToUrlResponse();
        response.setShopUrl(getService().getShopUrlForShopID(shopID));
        return response;
    }

    @RequestMapping(value = "/getRandomOffers", method = RequestMethod.GET, produces = "application/json")
    public GetRandomOffersResponse getRandomOffers(@RequestParam(value="shopID") long shopID, @RequestParam
            (value="count") int count, @RequestParam(value = "offset", defaultValue = "0") int offset) {
        GetRandomOffersResponse response = new GetRandomOffersResponse();
        response.setOffers(getService().getFirstNOffersOfShop(shopID, count, offset));
        return response;
    }

    @RequestMapping(value = "/matchAttribute" , method = RequestMethod.GET, produces = "application/json")
    public MatchAttributeResponse matchAttribute(@RequestParam(value="shopID") long shopID, @RequestParam
            (value="searchAttribute") String searchAttribute, @RequestParam(value = "attributeValue")
            String attributeValue){
        MatchAttributeResponse response = new MatchAttributeResponse();
        response.setOffers(getService().getMatchAttribute(shopID,  searchAttribute, attributeValue));
        return response;
    }

    @RequestMapping(value = "/matchAttributeWithMap" , method = RequestMethod.GET, produces = "application/json")
    public MatchAttributeResponse matchAttributeWithMap(@RequestParam(value="shopID") long shopID, @RequestParam
            (value="searchAttribute") String searchAttribute, @RequestParam(value = "attributeValue") String attributeValue){
        Map<String, String> value = new HashMap<>();
        value.put("0", attributeValue);
        MatchAttributeResponse response = new MatchAttributeResponse();
        response.setOffers(getService().getMatchAttribute(shopID,  searchAttribute, value));
        return response;
    }
}
