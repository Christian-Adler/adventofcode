import java.util.Objects;

public class TreeItem {
    String name = null;
    TreeItem parent = null;
    TreeItem left = null;
    TreeItem right = null;
    long number = 0;
    CalcBase calcBase = new CalcBase();

    boolean inMyPath = false;

    static CalcBase add = new Add();
    static CalcBase sub = new Sub();
    static CalcBase mult = new Mult();
    static CalcBase div = new Div();
    static CalcBase equ = new Equ();

    public TreeItem(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TreeItem{");
        sb.append("name='").append(name).append('\'');
        sb.append(", inMyPath=").append(inMyPath);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeItem treeItem = (TreeItem) o;
        return Objects.equals(name, treeItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    long calc() {
        return calcBase.calc(this);
    }

    void calcMe(long calcResShouldBe) {
        if (name.equals("humn")) {
            System.out.println("humn: " + calcResShouldBe);
            this.calcBase = new CalcBase();
            this.number = calcResShouldBe;
            return;
        }
        calcBase.calcMe(this, calcResShouldBe);
    }

    static class CalcBase {
        long calc(TreeItem treeItem) {
            return treeItem.number;
        }

        void calcMe(TreeItem treeItem, long calcResShouldBe) {
            if (treeItem.name.equals("humn"))
                System.out.println("humn: " + calcResShouldBe);
        }
    }

    static class Equ extends CalcBase {
        @Override
        long calc(TreeItem treeItem) {
            long calcLeft = treeItem.left.calc();
            long calcRight = treeItem.right.calc();
            long number = (calcLeft == calcRight) ? 1 : 0;
            return number;
        }

        void calcMe(TreeItem treeItem, long calcResShouldBe) {
            if (treeItem.left.inMyPath) {
                treeItem.left.calcMe(treeItem.right.number);
            } else {
                treeItem.right.calcMe(treeItem.left.number);
            }
        }
    }

    static class Add extends CalcBase {
        @Override
        long calc(TreeItem treeItem) {
            treeItem.number = treeItem.left.calc() + treeItem.right.calc();
            return treeItem.number;
        }

        void calcMe(TreeItem treeItem, long calcResShouldBe) {
            if (treeItem.left.inMyPath) {
                long subValue = calcResShouldBe - treeItem.right.number;
                treeItem.left.calcMe(subValue);
            } else {
                long subValue = calcResShouldBe - treeItem.left.number;
                treeItem.right.calcMe(subValue);
            }
        }
    }

    static class Sub extends CalcBase {
        @Override
        long calc(TreeItem treeItem) {
            treeItem.number = treeItem.left.calc() - treeItem.right.calc();
            return treeItem.number;
        }

        void calcMe(TreeItem treeItem, long calcResShouldBe) {
            if (treeItem.left.inMyPath) {
                long subValue = calcResShouldBe + treeItem.right.number;
                treeItem.left.calcMe(subValue);
            } else {
                long subValue = treeItem.left.number - calcResShouldBe;
                treeItem.right.calcMe(subValue);
            }
        }
    }

    static class Mult extends CalcBase {
        @Override
        long calc(TreeItem treeItem) {
            treeItem.number = treeItem.left.calc() * treeItem.right.calc();
            return treeItem.number;
        }

        void calcMe(TreeItem treeItem, long calcResShouldBe) {
            if (treeItem.left.inMyPath) {
                long subValue = calcResShouldBe / treeItem.right.number;
                treeItem.left.calcMe(subValue);
            } else {
                long subValue = calcResShouldBe / treeItem.left.number;
                treeItem.right.calcMe(subValue);
            }
        }
    }

    static class Div extends CalcBase {
        @Override
        long calc(TreeItem treeItem) {
            treeItem.number = treeItem.left.calc() / treeItem.right.calc();
            return treeItem.number;
        }

        void calcMe(TreeItem treeItem, long calcResShouldBe) {
            if (treeItem.left.inMyPath) {
                long subValue = calcResShouldBe * treeItem.right.number;
                treeItem.left.calcMe(subValue);
            } else {
                long subValue = treeItem.left.number / calcResShouldBe;
                treeItem.right.calcMe(subValue);
            }
        }
    }

}
