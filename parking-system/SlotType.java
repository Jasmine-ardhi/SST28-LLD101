import java.util.*;
enum SlotType {
    SMALL, MEDIUM, LARGE;

    public static List<SlotType> getUpgradePath(SlotType type) {

        List<SlotType> list = new ArrayList<>();

        if (type == SMALL) {
            list.add(SMALL);
            list.add(MEDIUM);
            list.add(LARGE);
        } else if (type == MEDIUM) {
            list.add(MEDIUM);
            list.add(LARGE);
        } else {
            list.add(LARGE);
        }

        return list;
    }
}