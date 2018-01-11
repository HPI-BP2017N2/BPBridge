package de.hpi.bpbridge.controller;

import de.hpi.bpbridge.dto.GetRandomOffersResponse;
import de.hpi.bpbridge.dto.ShopIDToUrlResponse;
import de.hpi.bpbridge.service.BPBridgeService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BPBridgeController {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private BPBridgeService service;

    @Autowired
    public BPBridgeController(BPBridgeService service){
        setService(service);
    }

    @RequestMapping(value = "/shopIDToUrl", method = RequestMethod.GET, produces = "application/json")
    public ShopIDToUrlResponse parse(@RequestParam(value="shopID") long shopID) {
        ShopIDToUrlResponse response = new ShopIDToUrlResponse();
        response.setShopUrl(getService().getShopUrlForShopID(shopID));
        return response;
    }

    @RequestMapping(value = "/getRandomOffers", method = RequestMethod.GET, produces = "application/json")
    public GetRandomOffersResponse parse(@RequestParam(value="shopID") long shopID, @RequestParam(value="count") int
            count) {
        GetRandomOffersResponse response = new GetRandomOffersResponse();
        response.setOffers(getService().getFirstNOffersOfShop(shopID, count));
        return response;
    }
}
