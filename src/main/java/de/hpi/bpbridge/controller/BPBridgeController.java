package de.hpi.bpbridge.controller;

import de.hpi.bpbridge.service.BPBridgeService;
import de.hpi.restclient.dto.GetRandomOffersResponse;
import de.hpi.restclient.dto.ShopIDToUrlResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
