package de.hpi.bpbridge.model.database;

import de.hpi.restclient.pojo.Shop;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository{
    Shop findByShopID(long shopID);
}
