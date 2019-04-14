package hackaton.com.twitterhelphackaton.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ederdoski on 14/04/2019.
 */

public class States {

    private String id;
    private String quantity;
    private String city;
    private LatLng ubication;

    public States(String id, String quantity, String city, LatLng ubication) {
        this.id = id;
        this.quantity = quantity;
        this.city = city;
        this.ubication = ubication;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LatLng getUbication() {
        return ubication;
    }

    public void setUbication(LatLng ubication) {
        this.ubication = ubication;
    }
}
