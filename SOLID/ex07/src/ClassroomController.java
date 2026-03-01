// public class ClassroomController {
//     private final DeviceRegistry reg;

//     public ClassroomController(DeviceRegistry reg) { this.reg = reg; }

//     public void startClass() {
//         Interfaces pj = reg.getFirstOfType("Projector");
//         pj.powerOn();
//         pj.connectInput("HDMI-1");

//         Interfaces lights = reg.getFirstOfType("LightsPanel");
//         lights.setBrightness(60);

//         Interfaces ac = reg.getFirstOfType("AirConditioner");
//         ac.setTemperatureC(24);

//         Interfaces scan = reg.getFirstOfType("AttendanceScanner");
//         System.out.println("Attendance scanned: present=" + scan.scanAttendance());
//     }

//     public void endClass() {
//         System.out.println("Shutdown sequence:");
//         reg.getFirstOfType("Projector").powerOff();
//         reg.getFirstOfType("LightsPanel").powerOff();
//         reg.getFirstOfType("AirConditioner").powerOff();
//     }
// }
public class ClassroomController {

    private final DeviceRegistry reg;

    public ClassroomController(DeviceRegistry reg) {
        this.reg = reg;
    }

    public void startClass() {

        PowerControl pjPower = reg.getFirstOfType(Projector.class);
        InputConnectable pjInput = reg.getFirstOfType(Projector.class);

        pjPower.powerOn();
        pjInput.connectInput("HDMI-1");

        BrightnessControl lights = reg.getFirstOfType(BrightnessControl.class);
        lights.setBrightness(60);

        TemperatureControl ac = reg.getFirstOfType(TemperatureControl.class);
        ac.setTemperatureC(24);

        AttendanceControl scan = reg.getFirstOfType(AttendanceControl.class);
        System.out.println("Attendance scanned: present=" + scan.scanAttendance());
    }

    public void endClass() {

        System.out.println("Shutdown sequence:");

        reg.getFirstOfType(Projector.class).powerOff();
        reg.getFirstOfType(LightsPanel.class).powerOff();
        reg.getFirstOfType(AirConditioner.class).powerOff();
    }
}