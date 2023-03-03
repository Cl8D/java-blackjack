package view;

import domain.card.Card;
import domain.card.CardNumber;
import domain.card.CardPattern;
import domain.game.Result;
import domain.participant.Participants;
import view.message.GameResultMessage;
import view.message.NumberMessage;
import view.message.PatternMessage;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static view.message.Message.CARD_MESSAGE;
import static view.message.Message.DEALER_GAME_RESULT;
import static view.message.Message.DRAW_MESSAGE;
import static view.message.Message.FINAL_GAME_RESULT;
import static view.message.Message.PARTICIPANT_CARD_RESULT;
import static view.message.Message.PLAYER_GAME_RESULT;

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
        String cardsMessage = getCardsMessage(playerCards);
        String playerCardMessage = String.format(CARD_MESSAGE.getMessage(), playerName, cardsMessage);
        print(playerCardMessage);
    }

    public void printCardResult(final String participantName, final List<Card> participantCards, final int participantScore) {
        String cardsResultMessage = String.format(PARTICIPANT_CARD_RESULT.getMessage(),
                participantName, getCardsMessage(participantCards), participantScore);
        print(cardsResultMessage);
    }

    public void printFinalGameResult(final String dealerName, final Map<String, Result> playerGameResults) {
        print(System.lineSeparator() + FINAL_GAME_RESULT.getMessage());
        printDealerGameResult(dealerName, playerGameResults);
        printPlayerGameResult(playerGameResults);
    }

    private String getCardsMessage(final List<Card> participantCards) {
        return participantCards.stream().map(this::getCardMessage)
                .collect(Collectors.joining(", "));
    }

    private String getCardMessage(final Card participantCard) {
        CardNumber cardNumber = participantCard.getCardNumber();
        CardPattern cardPattern = participantCard.getCardPattern();

        String numberMessage = NumberMessage.findMessage(cardNumber);
        String patternMessage = PatternMessage.findMessage(cardPattern);

        return numberMessage + patternMessage;
    }

    private void printDealerGameResult(final String dealerName, final Map<String, Result> playerGameResults) {
        int dealerWinCount = Collections.frequency(playerGameResults.values(), Result.LOSE);
        int dealerLoseCount = Collections.frequency(playerGameResults.values(), Result.WIN);
        int drawCount = Collections.frequency(playerGameResults.values(), Result.DRAW);
        String dealerGameResultMessage = String.format(DEALER_GAME_RESULT.getMessage(), dealerName, dealerWinCount, dealerLoseCount, drawCount);
        print(dealerGameResultMessage);
    }

    private void printPlayerGameResult(final Map<String, Result> playerGameResults) {
        playerGameResults.keySet().forEach(playerName -> {
            Result playerResult = playerGameResults.get(playerName);
            String playerGameResultMessage = String.format(PLAYER_GAME_RESULT.getMessage(), playerName, GameResultMessage.findMessage(playerResult));
            print(playerGameResultMessage);
        });
    }
}
