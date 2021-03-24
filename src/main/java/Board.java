import java.util.HashMap;
import java.util.Map;

public class Board {
    final Map<Integer, Integer> chute = new HashMap<>();

    final Map<Integer, Integer> ladder = new HashMap<>();

    Board() {
        chute.put(63, 18);
        chute.put(97, 78);
        chute.put(95, 56);
        chute.put(88, 24);
        chute.put(36, 6);
        chute.put(32, 10);
        chute.put(48, 26);

        ladder.put(80, 99);
        ladder.put(21, 42);
        ladder.put(4, 14);
        ladder.put(8, 10);
        ladder.put(26, 48);
        ladder.put(71, 92);
        ladder.put(28, 76);
        ladder.put(1, 38);
    }

}
