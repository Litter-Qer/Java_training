import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public class CHMTest {
    static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) throws InterruptedException {
        int length = ALPHA.length();
        int count = 200;

        List<String> list = new ArrayList<>(length * count);
        for (int i = 0; i < length; i++) {
            char chr = ALPHA.charAt(i);
            for (int j = 0; j < count; j++) {
                list.add(String.valueOf(chr));
            }
        }
        Collections.shuffle(list);
        for (int i = 0; i < 26; i++) {
            try (PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream("tmp/" + (i + 1) + ".txt")))) {
                String collect = String.join("\n", list.subList(i * count, (i + 1) * count));
                out.print(collect);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
