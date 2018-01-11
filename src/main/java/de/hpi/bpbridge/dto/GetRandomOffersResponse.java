package de.hpi.bpbridge.dto;

import de.hpi.bpbridge.model.database.data.Offer;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GetRandomOffersResponse {

    private List<Offer> offers;

}
