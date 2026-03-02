// import java.util.*;

// public class DeviceRegistry {
//     private final java.util.List<Interfaces> devices = new ArrayList<>();

//     public void add(Interfaces d) { devices.add(d); }

//     public Interfaces getFirstOfType(String simpleName) {
//         for (Interfaces d : devices) {
//             if (d.getClass().getSimpleName().equals(simpleName)) return d;
//         }
//         throw new IllegalStateException("Missing: " + simpleName);
//     }
// }
import java.util.*;

public class DeviceRegistry {

    private final List<Object> devices = new ArrayList<>();

    public void add(Object device) {
        devices.add(device);
    }

    public <T> T getFirstOfType(Class<T> type) {
        for (Object d : devices) {
            if (type.isInstance(d)) {
                return type.cast(d);
            }
        }
        throw new IllegalStateException("Missing device of type: " + type.getSimpleName());
    }
}