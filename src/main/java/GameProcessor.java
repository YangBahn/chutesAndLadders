import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.System.in;
import static java.lang.System.out;

public class GameProcessor {

    private final Scanner scanner = new Scanner(in);
    private final int WIN_VALUE = 100;
    // sorted in order
    private List<DTOs.Player> players;
    private int playersCount;
    private int currentPlayerIndex = 1;
    private DTOs.Dice dice = new DTOs.Dice();
    private Board board;



    void start() throws IOException {
        board = new Board();
        out.println("\n\nHello, this is a Chutes and Ladders terminal.\n Please enter number of players (1-4)");
        playersCount = scanner.nextInt();

        out.printf("You entered %s.", playersCount);
        if (playersCount == 0)
            playersCount = 2;

        // todo ideally player's play order is independent of the player identity number
        players = IntStream.range(1, playersCount + 1)
                .mapToObj(DTOs.Player::new)
                // keeping players in order of play
                .sorted(Comparator.comparingInt(DTOs.Player::getOrderNumber))
                .peek(p -> out.printf("\n-- %s created.", p.toString()))
                .collect(Collectors.toList());


        DTOs.Player currentPlayer = getNextPlayer();
        DTOs.RoleResult result = new DTOs.RoleResult();
        while (!result.hasWinner) {
            result = roll(currentPlayer);
            currentPlayer = getNextPlayer();
        }

    }


    // todo: make print out class by EVENT_TYPE
    private DTOs.RoleResult roll(DTOs.Player player) throws IOException {
        DTOs.RoleResult result = new DTOs.RoleResult();

        out.printf("\n\nPress Enter to roll the dice [ Player %s ]    ", player.orderNumber);// placeholder
        System.in.read();

        int roll = dice.nextRoll();
        result.roll = roll;
        out.printf("\n\tPlayer %s rolls %s, ", player.orderNumber, roll);

        int newInitLocation = player.location + roll;
        out.printf("and moves from %s to %s!", player.location, newInitLocation);
        player.location = newInitLocation;

//        todo: to move up, use enum TYPE set in RoleResult
        if (board.chute.containsKey(newInitLocation)) {
            int loc = board.chute.get(newInitLocation);
            out.printf("CHUTE landed on a chute! Player slides down to %s!!%n", loc);
            player.location = loc;
            result.hasWinner = false;
        }

        if (board.ladder.containsKey(newInitLocation)) {
            int loc = board.ladder.get(newInitLocation);
            out.printf("\n\tLADDER - landed on a ladder! Player climbs up directly to %s!!%n", loc);
            player.location = loc;
            result.hasWinner = false;
        }

        if (newInitLocation >= WIN_VALUE) {
            out.printf("\n\tWINNER - Player %s wins!!%n", player.orderNumber);
            // todo print game result info\
            result.hasWinner = true;
        }

        players.forEach(p -> out.printf("\n\tPlayer %s = %s", p.orderNumber, p.location));
        return result;

    }


    DTOs.Player getNextPlayer() {
        DTOs.Player curPlayer = this.players.stream().filter(p -> p.orderNumber == currentPlayerIndex).findAny().orElse(null);
        currentPlayerIndex = currentPlayerIndex == playersCount ? 1 : currentPlayerIndex + 1;
        return curPlayer;
    }


}
