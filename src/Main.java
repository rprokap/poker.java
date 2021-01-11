import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final String[] SUITS = { "C", "D", "H", "S" };
    private final String[] RANKS = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K" };

    private final Player player;
    private List<Card> deck;
    private final Scanner in;

    public Main() {
        this.player = new Player();
        this.in = new Scanner(System.in);
    }

    public void game() {
        shuffle();

        short chipsToBuy = 0;
        do {
            System.out.println("\nyour chip total is: " + player.chipBalance() + " chip(s). enter a number between (inclusive) 1 and 32767 to place a bet.");
            try {
                chipsToBuy = in.nextShort();
            }
            catch (Exception e) {
                chipsToBuy = 0;
                in.next();
            }
        } while (chipsToBuy <= 0);
        in.nextLine();
        player.addChips(chipsToBuy);

        while (deck.size() >= 8 && player.chipBalance() > 0) {
            for (int i = 0; i < 5; i++) {
                player.deal(deck.get(0));
                deck.remove(0);
            }
            player.sortHand();

            takeTurn();

            player.clearHand();
            System.out.println("\n☆:.｡. next hand .｡.:☆");
        }

        endGame();
    }

    public void takeTurn() {
        int playerChipPurchase = 0;
        do {
            System.out.println("\nyour chip total is " + player.chipBalance() + " chip(s). enter a number between 1 and 25 (inclusive) to obtain chips.");
            try {
                playerChipPurchase = in.nextInt();
            } catch (Exception e) {
                playerChipPurchase = 0;
                in.next();
            }
            if (playerChipPurchase > player.chipBalance()) {
                System.out.println("you don't have that many chips in your balance.");
            }
        } while (playerChipPurchase <= 0 || playerChipPurchase > player.chipBalance() || playerChipPurchase > 25);
        in.nextLine();
        player.addChips(-1 * playerChipPurchase);

        System.out.print("\nplayer hand: ");
        System.out.println(player.getHand());

        int cardsToTrade = -1;
        do {
            System.out.println("\nenter the number of cards you'd like to trade in. (enter a number between 0 and 3, inclusive)");
            try {
                cardsToTrade = in.nextInt();
            } catch (Exception e) {
                cardsToTrade = -1;
                in.next();
            }
        } while (cardsToTrade < 0 || cardsToTrade > 3);
        in.nextLine();

        if (cardsToTrade > 0) System.out.println("\nselect the numbered slot of the card you would like to trade : " + cardsToTrade);

        int[] indexes = new int[cardsToTrade];
        for (int i = 0; i < cardsToTrade; i++) {
            indexes[i] = 0;
        }
        for (int i = 0; i < cardsToTrade; i++) {
            int indexPlusOne = -1;
            do {
                System.out.println("\npick your next card.");
                try {
                    indexPlusOne = in.nextInt();
                }
                catch (Exception e) {
                    indexPlusOne = -1;
                    in.next();
                }
                finally {
                    for (int j = 0; j < cardsToTrade; j++) {
                        if (indexPlusOne == indexes[j]) {
                            indexPlusOne = 0;
                        }
                    }
                    if (indexPlusOne > 0 && indexPlusOne <= player.getHand().size()) {
                        indexes[i] = indexPlusOne;
                        System.out.println(player.getHand().get(indexPlusOne - 1).toString() + " has been removed from your hand.");
                    }
                }
            } while (indexPlusOne <= 0 || indexPlusOne > player.getHand().size());
            in.nextLine();
        }

        for (int i = 0; i < cardsToTrade; i++) {
            player.setCard(indexes[i] - 1, new Card("X", "X"));
            player.deal(deck.get(0));
            deck.remove(0);
        }
        for (int i = 0; i < player.getHand().size(); i++) {
            if (player.getHand().get(i).getRank().matches("X")) {
                player.removeCard(i);
                i = -1;
            }
        }

        player.sortHand();

        if (cardsToTrade > 0) {
            System.out.print("\nplayer hand: ");
            System.out.println(player.getHand());
        }

        int payOutMultiplier = player.evaluateHand();
        switch (payOutMultiplier) {
            case 100:
                System.out.println("\nroyal flush! player is paid 100x the wager.");
                break;
            case 50:
                System.out.println("\nstraight flush. player is paid 50x the wager.");
                break;
            case 25:
                System.out.println("\n4 of a kind. player is paid 25x the wager.");
                break;
            case 15:
                System.out.println("\nfull house. player is paid 15x the wager.");
                break;
            case 10:
                System.out.println("\nflush. player is paid 10x the wager.");
                break;
            case 5:
                System.out.println("\nstraight. player is paid 5x the wager");
                break;
            case 3:
                System.out.println("\nthree of a kind. player is paid 3x the wager.");
                break;
            case 2:
                System.out.println("\ntwo pair. player is paid twice the wager.");
                break;
            case 1:
                System.out.println("\npair of jacks. no money gained or lost. ");
                break;
            case 0:
                System.out.println("\nbad hand. wager lost.");
                break;
        }
        player.addChips(payOutMultiplier * playerChipPurchase);
    }

    public void shuffle() {
        deck = new ArrayList<>(52);

        for (String suit : SUITS) {
            for (String rank : RANKS) {
                deck.add(new Card(rank, suit));
            }
        }

        Collections.shuffle(deck);
    }

    public void endGame() {
        String endMessage = (player.chipBalance() == 0) ? "\nyou have no more chips. game over!" : "\nthere are no more cards in the deck. game over!";
        System.out.println(endMessage);

        player.clearHand();
    }

    public static void main(String[] args) {
        new Main().game();
    }
}
