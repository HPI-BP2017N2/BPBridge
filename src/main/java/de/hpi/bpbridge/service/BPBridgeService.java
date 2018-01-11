package de.hpi.bpbridge.service;

import de.hpi.bpbridge.model.database.ShopRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE)
public class BPBridgeService {

    private ShopRepository repository;

    @Autowired
    public BPBridgeService(ShopRepository repository) {
        setRepository(repository);
    }

    public String getShopUrlForShopID(long shopID) {
        return getRepository().findByShopID(shopID).getUrl();
    }

}
