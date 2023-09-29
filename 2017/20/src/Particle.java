import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Particle {
    private static final AtomicLong uuidProvider = new AtomicLong();

    private final long ID = Particle.uuidProvider.incrementAndGet();
    Vector position;
    Vector velocity;
    Vector acceleration;

    public Particle(String[] input) {
        position = new Vector(input[0]);
        velocity = new Vector(input[1]);
        acceleration = new Vector(input[2]);
    }

    public boolean step() {
        long distTo0 = position.dist(Vector.vec0);
        velocity.add(acceleration);
        position.add(velocity);
        long distTo0_afterStep = position.dist(Vector.vec0);
        return (distTo0_afterStep < distTo0);
    }

    public long dist(Particle particle) {
        return position.dist(particle.position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return ID == particle.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Particle{");
        sb.append("position=").append(position);
        sb.append(", velocity=").append(velocity);
        sb.append(", acceleration=").append(acceleration);
        sb.append('}');
        return sb.toString();
    }
}
