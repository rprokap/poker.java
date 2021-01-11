import java.util.List;

public class Evaluate {
    public static boolean royalFlush(List<Card> hand) {
        int count = 0;
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(0).getRank().equals("T") && Card.getOrderedRank(hand.get(i).getRank()) == Card.getOrderedRank(hand.get(i - 1).getRank()) + 1 && hand.get(i).getSuit().matches(hand.get(i - 1).getSuit())) count++;
        }
        return (count == 4);
    }

    public static boolean straightFlush(List<Card> hand) {
        int count = 0;
        for (int i = 1; i < hand.size(); i++) {
            if (Card.getOrderedRank(hand.get(i).getRank()) == Card.getOrderedRank(hand.get(i - 1).getRank()) + 1 && hand.get(i).getSuit().matches(hand.get(i - 1).getSuit())) count++;
        }
        return (count == 4);
    }

    public static boolean fourOfAKind(List<Card> hand) {
        int count = 0;
        int maxCount = 0;
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i).getRank().matches(hand.get(i - 1).getRank())) count++;
            else count = 0;
            if (count > maxCount) maxCount = count;
        }
        return (maxCount == 3);
    }

    public static boolean fullHouse(List<Card> hand) {
        int count = 0;
        for (int i = 1; i < 3; i++) {
            if (count == 1 && !hand.get(i).getRank().matches(hand.get(i - 1).getRank())) break;
            else if (hand.get(i).getRank().matches(hand.get(i - 1).getRank())) count++;
            else count = 0;
        }
        if (count == 1) {
            count = 0;
            for (int i = 3; i < hand.size(); i++) {
                if (hand.get(i).getRank().matches(hand.get(i - 1).getRank())) count++;
                else count = 0;
            }
            return (count == 2);
        }
        else if (count == 2) {
            if (hand.get(4).getRank().matches(hand.get(3).getRank())) return true;
        }
        return false;
    }

    public static boolean flush(List<Card> hand) {
        int count = 0;
        int maxCount = 0;
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i).getSuit().matches(hand.get(i - 1).getSuit())) count++;
            else count = 0;
            if (count > maxCount) maxCount = count;
        }
        return (maxCount == 4);
    }

    public static boolean straight(List<Card> hand) {
        int count = 0;
        for (int i = 1; i < hand.size(); i++) {
            if (Card.getOrderedRank(hand.get(i).getRank()) == Card.getOrderedRank(hand.get(i - 1).getRank()) + 1) count++;
        }
        return (count == 4);
    }

    public static boolean threeOfAKind(List<Card> hand) {
        int count = 0;
        int maxCount = 0;
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i).getRank().matches(hand.get(i - 1).getRank())) count++;
            else count = 0;
            if (count > maxCount) maxCount = count;
        }
        return (maxCount == 2);
    }

    public static boolean twoPair(List<Card> hand) {
        int pairCount = 0;
        String lastPairRank = "";
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i).getRank().matches(hand.get(i - 1).getRank())) {
                pairCount++;
                lastPairRank = hand.get(i).getRank();
                break;
            }
        }
        if (pairCount == 1) {
            for (int i = 1; i < hand.size(); i++) {
                if (hand.get(i).getRank().matches(hand.get(i - 1).getRank()) && !hand.get(i).getRank().matches(lastPairRank)) {
                    pairCount++;
                    break;
                }
            }
        }
        return (pairCount == 2);
    }

    public static boolean pairOfJacksOrBetter(List<Card> hand) {
        for (int i = 1; i < hand.size(); i++) {
            if (hand.get(i).getRank().matches(hand.get(i - 1).getRank()) && Card.getOrderedRank(hand.get(i).getRank()) >= 11) {
                return true;
            }
        }
        return false;
    }
}
