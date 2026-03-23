interface PricingStrategy {
    double calculate(long hours, SlotType type);
}