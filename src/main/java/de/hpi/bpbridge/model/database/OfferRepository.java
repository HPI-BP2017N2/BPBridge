package de.hpi.bpbridge.model.database;

import de.hpi.restclient.pojo.Offer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository {

    List<Offer> getFirstOffersOfShop(long shopID, int maxOffers, int offset);

}
