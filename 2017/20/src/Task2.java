import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task2 {

    List<Particle> particles = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        String[] pSplit = input.replaceAll("\\w+=", "").split(",\\s");
        particles.add(new Particle(pSplit));
    }

    public void afterParse() {
//        out(particles);
        out("Num Particles:", particles.size());

        long smallestDistance = Long.MAX_VALUE;

        while (true) {
//            out("step");
            boolean atLeastOnParticleMovingTowardsCenter = false;
            for (Particle particle : particles) {
                atLeastOnParticleMovingTowardsCenter |= particle.step();
            }

            Map<Vector, List<Particle>> collisionMap = new HashMap<>();
            for (Particle particle : particles) {
                List<Particle> list = collisionMap.computeIfAbsent(particle.position, k -> new ArrayList<>());
                list.add(particle);
            }

            boolean foundCollision = false;
            for (List<Particle> collisionList : collisionMap.values()) {
                if (collisionList.size() > 1) {
                    foundCollision = true;
                    particles.removeAll(collisionList);
                }
            }

            // abbruchbedingung
            if (particles.size() <= 1)
                break;

            // Solange noch Partikel Richtung Zentrum unterweg sind, brauchen wir nicht auf Entfernungen zu pruefen.
            if (atLeastOnParticleMovingTowardsCenter)
                continue;

            // Kleinster Abstand wird groesser
            long minDist = Long.MAX_VALUE;
            for (int i = 0; i < particles.size() - 1; i++) {
                Particle particle = particles.get(i);

                for (int j = i + 1; j < particles.size(); j++) {
                    Particle otherParticle = particles.get(j);
                    long dist = particle.dist(otherParticle);
                    if (dist < minDist)
                        minDist = dist;
                }
            }
//            out("minDist", minDist);

            if (!foundCollision && minDist > smallestDistance) {
                out("no collision and min distance increasing");
                break;
            }
            smallestDistance = minDist;
        }

//        out("smallestDistance", smallestDistance);
        out("Num Particles after collisions:", particles.size()); // < 1000
    }

    public void out(Object... str) {
        Util.out(str);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

}
