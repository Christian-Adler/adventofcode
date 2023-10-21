package d19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Registers {
    protected ArrayList<Integer> regis = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0));

    public Registers() {
    }


    public Registers(Registers src) {
        regis = new ArrayList<>(src.regis);
    }

    public Registers copy() {
        return new Registers(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayList<Integer> other = ((Registers) o).regis;
        for (int i = 0; i < regis.size(); i++) {
            if (regis.get(i).intValue() != other.get(i).intValue())
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(regis);
    }

    @Override
    public String toString() {
        return regis.toString();
    }

    public void setRegister(int idx, int value) {
        regis.set(idx, value);
    }

    public int getRegister(int idx) {
        return regis.get(idx);
    }

}
