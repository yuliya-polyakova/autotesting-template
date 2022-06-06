package polyakova.test.demo.clipboardhealth.page.amazon;

/**
 * Available sort
 *
 * @author Iuliia Poliakova
 */
public enum SortEnum {
    PriceLowToHigh("Price: Low to High"),
    PriceHighToLow("Price: High to Low"),
    AvgCustomerReview("Avg. Customer Review"),
    NewestArrivals("Newest Arrivals");

    private final String label;

    private SortEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
