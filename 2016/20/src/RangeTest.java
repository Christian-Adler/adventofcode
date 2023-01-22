public class RangeTest {
    public static void main(String[] args) {
        testGreaterThan();
        testLessThan();
        testCovers();
        testIntersects();
        testCombine();
    }

    private static void testCombine() {
        Range r1 = new Range(2, 7);
        Range r2 = new Range(8, 12);
        Range r3 = new Range(5, 12);
        Range r4 = new Range(7, 12);
        Range r5 = new Range(9, 12);

        assertNotNull(r1.combine(r2));
        assertNotNull(r2.combine(r1));
        assertNull(r1.combine(r5));
        assertNull(r5.combine(r1));
        assertNotNull(r1.combine(r3));
        assertNotNull(r3.combine(r1));
        assertNotNull(r1.combine(r4));
        assertNotNull(r4.combine(r1));
    }

    private static void testIntersects() {
        Range r1 = new Range(2, 7);
        Range r2 = new Range(8, 12);
        Range r3 = new Range(5, 12);
        Range r4 = new Range(7, 12);

        assertTrue(r3.intersects(r2));
        assertTrue(r2.intersects(r3));
        assertTrue(r3.intersects(r1));
        assertTrue(r1.intersects(r3));
        assertFalse(r2.intersects(r1));
        assertFalse(r1.intersects(r2));
        assertTrue(r4.intersects(r1));
        assertTrue(r1.intersects(r4));
    }

    private static void testCovers() {
        Range r1 = new Range(2, 7);
        Range r2 = new Range(8, 12);
        Range r3 = new Range(5, 12);

        assertTrue(r3.covers(r2));
        assertFalse(r2.covers(r3));
        assertFalse(r3.covers(r1));
        assertFalse(r1.covers(r3));
    }

    private static void testGreaterThan() {
        Range r1 = new Range(2, 7);
        Range r2 = new Range(8, 12);
        Range r3 = new Range(5, 12);

        assertTrue(r2.greaterThan(r1));
        assertFalse(r1.greaterThan(r2));
        assertFalse(r3.greaterThan(r1));
    }

    private static void testLessThan() {
        Range r1 = new Range(2, 7);
        Range r2 = new Range(8, 12);
        Range r3 = new Range(5, 6);

        assertFalse(r2.lessThan(r1));
        assertTrue(r1.lessThan(r2));
        assertFalse(r3.lessThan(r1));
    }

    private static void assertTrue(boolean value) {
        if (!value) throw new IllegalArgumentException("Fail");
    }

    private static void assertFalse(boolean value) {
        if (value) throw new IllegalArgumentException("Fail");
    }

    private static void assertNull(Object value) {
        if (value != null) throw new IllegalArgumentException("Fail");
    }

    private static void assertNotNull(Object value) {
        if (value == null) throw new IllegalArgumentException("Fail");
    }
}
