class HourlyPricingStrategy implements PricingStrategy {

    public double calculate(long hours, SlotType type) {
        if (type == SlotType.SMALL) return hours * 10;
        if (type == SlotType.MEDIUM) return hours * 20;
        return hours * 50;
    }
}