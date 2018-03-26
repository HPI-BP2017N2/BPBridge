package de.hpi.bpbridge.service;

import de.hpi.bpbridge.dto.IdealoOffer;
import de.hpi.bpbridge.dto.IdealoOffers;
import de.hpi.bpbridge.dto.IdealoShop;
import de.hpi.bpbridge.model.database.OfferRepository;
import de.hpi.bpbridge.model.database.ShopRepository;
import de.hpi.bpbridge.properties.IdealoBridgeProperties;
import de.hpi.restclient.pojo.Offer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE)
public class BPBridgeService {

    @Autowired
    @Getter(AccessLevel.PRIVATE)
    private RestTemplate oAuthRestTemplate;

    @Autowired
    @Getter(AccessLevel.PRIVATE)
    private IdealoBridgeProperties properties;

    private OfferRepository offerRepository;
    private ShopRepository shopRepository;

    @Autowired
    public BPBridgeService(ShopRepository shopRepository, OfferRepository offerRepository) {
        setShopRepository(shopRepository);
        setOfferRepository(offerRepository);
    }

    public String getShopUrlForShopID(long shopID) {
        return getOAuthRestTemplate().getForObject(getRootUrlURI(shopID), IdealoShop.class).getShopUrl();
    }

    public List<Offer> getFirstNOffersOfShop(long shopID, int maxCount, int offset) {
        return getFirstOffersOfShop(shopID, maxCount, offset);
    }

    public List<Offer> getMatchAttribute(long shopID, String searchAttribute, Object attributeValue) {
        return getOfferRepository().matchOffersOfShopWithAttribute(shopID, searchAttribute,
                attributeValue);
    }

    public List<Offer> getFirstOffersOfShop(long shopID, int maxOffers, int offset) {
        maxOffers += offset;
        List<IdealoOffer> idealoOffers = getOAuthRestTemplate().getForObject(getRandomOffersURI(shopID, maxOffers),
                IdealoOffers.class);
        idealoOffers = idealoOffers.subList(offset, maxOffers);
        return idealoOffers.stream().map((idealoOffer -> {
            Offer offer = new Offer();
            offer.setCategoryPaths(idealoOffer.getCategoryPaths());
            offer.setDescription(idealoOffer.getDescriptions());
            offer.setEan((!idealoOffer.getEans().isEmpty()) ? idealoOffer.getEans().get(0) : null);

            if (idealoOffer.getAttributeSearchText().size() > 0) {
                List<String> searchTexts = idealoOffer.getAttributeSearchText().values().iterator().next();
                offer.setAttrSearchtext((!searchTexts.isEmpty()) ? searchTexts.get(0) : null);
            }

            offer.setBrandSearchtext(idealoOffer.getBrandName());
            offer.setOfferTitle(idealoOffer.getTitles());

            HashMap<String, Number> prices = new HashMap<>();
            for (Map.Entry<String, Integer> entry : idealoOffer.getPrice().entrySet()) {
                prices.put(entry.getKey(), entry.getValue());
            }
            offer.setPrice(prices);
            offer.setHan((!idealoOffer.getHans().isEmpty()) ? idealoOffer.getHans().get(0) : null);
            offer.setProductSearchtext(idealoOffer.getProductSearchText());
            offer.setSku(idealoOffer.getSku());
            offer.setUrl(idealoOffer.getUrls());
            offer.setShopId(shopID);
            return offer;
        })).collect(Collectors.toList());
    }

    private URI getRandomOffersURI(long shopID, int maxCount) {
        return UriComponentsBuilder.fromUriString(getProperties().getApiUrl())
                .path(getProperties().getRandomOfferRoute() + "/" + shopID)
                .queryParam("maxCount", maxCount)
                .build()
                .encode()
                .toUri();
    }

    private URI getRootUrlURI (long shopID) {
        return UriComponentsBuilder.fromUriString(getProperties().getApiUrl())
                .path(getProperties().getRootUrlRoute() + "/" + shopID)
                .build()
                .encode()
                .toUri();
    }

}
