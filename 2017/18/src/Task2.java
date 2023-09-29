import java.util.*;

public class Task2 {
    static List<Instruction> instructions = new ArrayList<>();

    public void init() {
    }

    public void addLine(String input) {
        instructions.add(new Instruction(input.split("\\s")));
    }

    public void afterParse() {
        out(instructions);

        Program p0 = new Program(0);
        Program p1 = new Program(1);

        p0.setpPartner(p1);
        p1.setpPartner(p0);

        while (true) {
            p0.exec();
            p1.exec();

            if (p0.finished && p1.finished || p0.queue.isEmpty() && p1.queue.isEmpty()) {
                out("Part 2: P1 sendCounter", p1.sndCounter);
                break;
            }
        }
    }

    public void out(Object... str) {
        Util.out(str);
    }

    private static Long tryParse(String val) {
        try {
            return Long.parseLong(val);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

    private record Instruction(String[] instruction) {

        @Override
        public String toString() {
            return "[" + String.join(" ", instruction) + "]";
        }

        public String cmd() {
            return instruction[0];
        }

        public String register() {
            return instruction[1];
        }

        public String value() {
            return instruction[2];
        }
    }

    private static class Program {
        final Long ID;
        int actIdx = 0;
        Map<String, Long> registers = new HashMap<>();
        LinkedList<Long> queue = new LinkedList<>();
        long sndCounter = 0;
        boolean finished = false;

        Program pPartner;

        private Program(long id) {
            ID = id;
            registers.put("p", id);
        }

        void setpPartner(Program p) {
            pPartner = p;
        }

        void onSend(Long value) {
            queue.add(value);
        }

        void exec() {


            while (actIdx >= 0 && actIdx < instructions.size()) {
//            out("actIdx", actIdx);
//            out(registers);
                Instruction instruction = instructions.get(actIdx);
                String cmd = instruction.cmd();
                String register = instruction.register();

                if (cmd.equals("snd")) {
                    pPartner.onSend(registers.getOrDefault(register, 0L));
                    sndCounter++;
                } else if (cmd.equals("set")) {
                    String y = instruction.value();
                    Long yAsLong = tryParse(y);
                    if (yAsLong != null)
                        registers.put(register, yAsLong);
                    else
                        registers.put(register, registers.get(y));
                } else if (cmd.equals("add")) {
                    Long soFarValue = registers.getOrDefault(register, 0L);
                    String y = instruction.value();
                    Long yAsLong = tryParse(y);
                    if (yAsLong != null)
                        registers.put(register, soFarValue + yAsLong);
                    else
                        registers.put(register, soFarValue + registers.get(y));
                } else if (cmd.equals("mul")) {
                    Long soFarValue = registers.getOrDefault(register, 0L);
                    String y = instruction.value();
                    Long yAsLong = tryParse(y);
                    if (yAsLong != null)
                        registers.put(register, soFarValue * yAsLong);
                    else
                        registers.put(register, soFarValue * registers.get(y));
                } else if (cmd.equals("mod")) {
                    Long soFarValue = registers.getOrDefault(register, 0L);
                    String y = instruction.value();
                    Long yAsLong = tryParse(y);
                    if (yAsLong != null)
                        registers.put(register, soFarValue % yAsLong);
                    else
                        registers.put(register, soFarValue % registers.get(y));
                } else if (cmd.equals("rcv")) {
                    if (queue.isEmpty()) return;
                    registers.put(register, queue.remove(0));
                } else if (cmd.equals("jgz")) {
                    Long registerAsInt = tryParse(register);
                    Long soFarValue = (registerAsInt != null ? registerAsInt : registers.getOrDefault(register, 0L));

                    if (soFarValue > 0) {
                        String y = instruction.value();
                        Long yAsLong = tryParse(y);
                        Long offset = (yAsLong != null) ? yAsLong : registers.getOrDefault(y, 0L);

                        actIdx += offset;
                        continue;
                    }
                } else throw new IllegalStateException("unknown cmd");

                actIdx++;
            }

            finished = true;
        }
    }
}
