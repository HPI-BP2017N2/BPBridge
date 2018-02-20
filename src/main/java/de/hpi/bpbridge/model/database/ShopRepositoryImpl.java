package de.hpi.bpbridge.model.database;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import de.hpi.restclient.pojo.Offer;
import de.hpi.restclient.pojo.Shop;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShopRepositoryImpl implements ShopRepository {
    @Autowired
    @Qualifier(value = "shopDataTemplate")
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MongoTemplate mongoTemplate;

    @Override
    public Shop findByShopID(long shopID) {
        DBCursor cursor = getMongoTemplate().getCollection("shop").find(new BasicDBObject("shopID", shopID));
        if (cursor.hasNext()) {
            return convertDBObjectToShop(cursor.next());
        }
        return null;
    }

    private Shop convertDBObjectToShop(DBObject shopDBObject) {
        return getMongoTemplate().getConverter().read(Shop.class, shopDBObject);
    }
}
