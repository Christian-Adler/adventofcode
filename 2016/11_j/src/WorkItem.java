import java.util.ArrayList;

public record WorkItem(int elevator, ArrayList<ArrayList<Device>> floors, long soFarSteps) {
}
