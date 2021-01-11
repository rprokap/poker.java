import java.util.ArrayList;
import java.util.List;

public class Player {
    private final List<Card> hand;
    private int chips;

    public Player() {
        this.hand = new ArrayList<>();
        this.chips = 0;
    }
    public List<Card> getHand() {
        return hand;
    }
    public int chipBalance() {
        return chips;
		}
    public void deal(Card card) {
        hand.add(card);
    }
    public void removeCard(int index) {
        hand.remove(index);
    }
    public void setCard(int index, Card card) {
        hand.set(index, card);
    }
    public void clearHand() {
        while (hand.size() > 0) {
            hand.remove(0);
        }
    }
    public void addChips(int addend) {
        chips += addend;
    }

    public void sortHand() {
        hand.sort((a, b) -> {
            if (Card.getOrderedRank(a.getRank()) == Card.getOrderedRank(b.getRank())) {
                return Card.getOrderedSuit(a.getSuit()) - Card.getOrderedSuit(b.getSuit());
            }

            return Card.getOrderedRank(a.getRank()) - Card.getOrderedRank(b.getRank());
        });
    }

    public int evaluateHand() {
        if (Evaluate.royalFlush(hand)) return 100;
        else if (Evaluate.straightFlush(hand)) return 50;
        else if (Evaluate.fourOfAKind(hand)) return 25;
        else if (Evaluate.fullHouse(hand)) return 15;
        else if (Evaluate.flush(hand)) return 10;
        else if (Evaluate.straight(hand)) return 5;
        else if (Evaluate.threeOfAKind(hand)) return 3;
        else if (Evaluate.twoPair(hand)) return 2;
        else if (Evaluate.pairOfJacksOrBetter(hand)) return 1;
        else return 0;
    }
}
