package domain.game;

import domain.card.Deck;
import domain.participant.Dealer;
import domain.participant.Participant;
import domain.participant.Participants;
import domain.participant.Player;

import java.util.List;

public final class GameManager {

    private final Deck deck;
    private final Participants participants;

    private GameManager(final Deck deck, final Participants participants) {
        this.deck = deck;
        this.participants = participants;
    }

    public static GameManager create(final Dealer dealer, final List<Player> players, final Deck deck) {
        return new GameManager(deck, Participants.create(dealer, players));
    }

    public void handFirstCards() {
        participants.getParticipants()
                .forEach(participant -> participant.addCard(deck.draw(), deck.draw()));
    }

    public void handCard(final Participant participant) {
        participant.addCard(deck.draw());
    }

    public List<Participant> getParticipants() {
        return List.copyOf(participants.getParticipants());
    }
}
