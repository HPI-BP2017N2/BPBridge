package de.hpi.bpbridge.model.database;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import de.hpi.restclient.pojo.Offer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class OfferRepositoryImpl implements OfferRepository {

    @Autowired
    @Qualifier(value = "shopDataTemplate")
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MongoTemplate mongoTemplate;

    @Override
    public List<Offer> getFirstOffersOfShop(long shopID, int maxOffers, int offset) {
        List<Offer> offers = new LinkedList<>();
        DBCollection collection = getCollectionByShopID(shopID);
        if (collection != null){
            DBCursor cursor = collection.find().skip(offset).limit(maxOffers);
            while (cursor.hasNext()){
                offers.add(convertDBObjectToOffer(cursor.next()));
            }
        }
        return offers;
    }

    @Override
    public List<Offer> matchOffersOfShopWithAttribute(long shopID, String searchAttribute, Object attributeValue) {
        List<Offer> offers = new LinkedList<>();
        DBCollection collection = getCollectionByShopID(shopID);
        if(searchAttribute.equals("offerId")) attributeValue = Long.valueOf(attributeValue.toString());
        if (collection != null) {
            DBCursor cursor = collection.find(new BasicDBObject(searchAttribute, attributeValue));
            while (cursor.hasNext()) {
                offers.add(convertDBObjectToOffer(cursor.next()));
            }
        }
        return offers;
    }

    //actions
    private DBCollection getCollectionByShopID(long shopID){
        DBCollection collection;
        for (String collectionName : getMongoTemplate().getCollectionNames()){
            collection = getMongoTemplate().getCollection(collectionName);
            if (getFirstOffer(collection).getShopId().longValue() == shopID){
                return collection;
            }
        }
        return null;
    }

    private Offer getFirstOffer(DBCollection collection){
        return convertDBObjectToOffer(collection.findOne());
    }

    //conversion
    private Offer convertDBObjectToOffer(DBObject offerDbObject){
        return getMongoTemplate().getConverter().read(Offer.class, offerDbObject);
    }
}
