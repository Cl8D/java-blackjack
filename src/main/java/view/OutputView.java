package view;

import domain.card.Card;
import domain.card.CardNumber;
import domain.card.CardPattern;
import domain.participant.Participants;
import view.message.NumberMessage;
import view.message.PatternMessage;

import java.util.List;
import java.util.stream.Collectors;

import static view.message.Message.CARD_MESSAGE;
import static view.message.Message.DRAW_MESSAGE;

public class OutputView {

    public static void print(final String message) {
        System.out.println(message);
    }

    public void printParticipantMessage(final Participants participants) {
        List<String> participantNames = participants.getParticipantNames();
        String participantNamesMessage = String.join(", ", participantNames)
                .replace(",", "와");
        String participantNameMessage = String.format(System.lineSeparator() + DRAW_MESSAGE.getMessage(), participantNamesMessage);
        print(participantNameMessage);
    }

    public void printDealerCard(final String dealerName, final Card dealerFirstCard) {
        String dealerCardMessage = String.format(CARD_MESSAGE.getMessage(), dealerName, getCardMessage(dealerFirstCard));
        print(dealerCardMessage);
    }

    public void printPlayerCard(final String playerName, final List<Card> playerCards) {
        String cardsMessage = playerCards.stream().map(this::getCardMessage)
                .collect(Collectors.joining(", "));
        String playerCardMessage = String.format(CARD_MESSAGE.getMessage(), playerName, cardsMessage);
        print(playerCardMessage);
    }

    private String getCardMessage(final Card participantCard) {
        CardNumber cardNumber = participantCard.getCardNumber();
        CardPattern cardPattern = participantCard.getCardPattern();

        String numberMessage = NumberMessage.findMessage(cardNumber);
        String patternMessage = PatternMessage.findMessage(cardPattern);

        return numberMessage + patternMessage;
    }
}
