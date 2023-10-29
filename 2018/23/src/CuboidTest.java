public class CuboidTest {
    public static void main(String[] args) {
        Cuboid c1 = new Cuboid(new Pos(-2, -2, -2), new Pos(2, 2, 2));
        Cuboid c3 = new Cuboid(new Pos(2, 0, 0), new Pos(4, 2, 2));
        Cuboid c2 = new Cuboid(new Pos(-1, -1, -1), new Pos(5, 5, 5));

        Cuboid intersect = c1.intersect(c2);
        System.out.println(intersect);
        Cuboid union = c1.union(c2);
        System.out.println(union);

        System.out.println(c1.split2());

        intersect = c1.intersect(c3);
        System.out.println(intersect);
    }
}
