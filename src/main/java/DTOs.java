import lombok.Getter;

import java.util.concurrent.ThreadLocalRandom;


/* todo
   in more developed form
    1 . All attributes would be private and will use Lombok's @Getter @Setter
    2. each class will be in separate files since more attributes maybe necessary

*/
public class DTOs {

    static class RoleResult {
        boolean hasWinner;
        int roll;
    }


    @Getter
    static class Player {
        int location;
        int orderNumber;

        Player(int order) {
            this.orderNumber = order;
        }

        @Override
        public String toString() {
            return "Player " + orderNumber;
        }
    }


    static class Dice {
        private int currentValue;

        int nextRoll() {
            currentValue = ThreadLocalRandom.current().nextInt(1, 7);
            return this.currentValue;
        }
    }
}
