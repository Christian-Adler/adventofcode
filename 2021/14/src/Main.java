import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Hallo");
        Template template = new Template();

        Files.lines(new File("D:/java/adventofcode/14/input.txt").toPath()).forEach((line) -> {
                    if (line.isEmpty())
                        return;
                    else if (!line.contains(">"))
                        template.polymer = line;
                    else template.addMapping(line);
                }
        );

        System.out.println(template);
        long t1 = System.currentTimeMillis();
        template.calc(40);
        long t2 = System.currentTimeMillis();
        System.out.println((t2 - t1) + "ms");
    }
}
