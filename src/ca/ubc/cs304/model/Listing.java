package ca.ubc.cs304.model;

public class Listing {
    private Integer listingID;
    private String streetAddress;
    private String province;
    private String cityName;
    private ListingType type;
    private Integer price;
    private Integer active;

    public Integer getListingID() {
        return listingID;
    }

    public void setListingID(Integer listingID) {
        this.listingID = listingID;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public ListingType getType() {
        return type;
    }

    public void setType(ListingType type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) throws Exception {
        if (active != 0 && active != 1) {
            throw new Exception("active must be 0 or 1");
        }
        this.active = active;
    }
}
