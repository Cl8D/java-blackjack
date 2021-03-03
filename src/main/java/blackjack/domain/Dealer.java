package blackjack.domain;

public class Dealer extends Participant {

    private static final String DEALER_NAME = "딜러";

    public Dealer() {
        super(DEALER_NAME);
    }

    @Override
    public boolean isAbleToReceiveCard() {
        return false;
    }
}
