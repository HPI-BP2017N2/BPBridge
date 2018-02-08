package de.hpi.bpbridge.service;

import de.hpi.bpbridge.model.database.OfferRepository;
import de.hpi.bpbridge.model.database.ShopRepository;
import de.hpi.restclient.pojo.Offer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE)
public class BPBridgeService {

    private OfferRepository offerRepository;
    private ShopRepository shopRepository;

    @Autowired
    public BPBridgeService(ShopRepository shopRepository, OfferRepository offerRepository) {
        setShopRepository(shopRepository);
        setOfferRepository(offerRepository);
    }

    public String getShopUrlForShopID(long shopID) {
        return getShopRepository().findByShopID(shopID).getUrl();
    }

    public List<Offer> getFirstNOffersOfShop(long shopID, int maxCount, int offset) {
        return getOfferRepository().getFirstOffersOfShop(shopID, maxCount, offset);
    }

    public List<Offer> getMatchAttribute(long shopID, String searchAttribute, Object attributeValue) {
        return getOfferRepository().matchOffersOfShopWithAttribute(shopID, searchAttribute,
                attributeValue);
    }

}
