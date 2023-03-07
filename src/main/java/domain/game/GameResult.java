package domain.game;

import domain.participant.Dealer;
import domain.participant.Participant;
import domain.participant.Player;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class GameResult {

    private final Map<Participant, Result> gameResults;

    private GameResult(final Map<Participant, Result> gameResults) {
        this.gameResults = gameResults;
    }

    public static GameResult create(final Dealer dealer, final List<Player> players) {
        final Map<Participant, Result> gameResults = makeGameResults(dealer, players);
        return new GameResult(gameResults);
    }

    public Map<String, Result> getPlayerGameResults() {
        return gameResults.keySet().stream()
                .collect(Collectors.toMap(Participant::getName, gameResults::get,
                        (newValue, oldValue) -> oldValue, LinkedHashMap::new));
    }

    private static Map<Participant, Result> makeGameResults(final Participant dealer, final List<Player> players) {
        Map<Participant, Result> gameResults = new LinkedHashMap<>();
        for (Participant player : players) {
            Result playerResult = calculateResult(dealer, player);
            gameResults.put(player, playerResult);
        }
        return gameResults;
    }

    private static Result calculateResult(final Participant dealer, final Participant player) {
        if (isDealerWin(dealer, player)) {
            return Result.LOSE;
        }
        if (isPlayerWin(dealer, player)) {
            return Result.WIN;
        }
        return Result.DRAW;
    }

    private static boolean isDealerWin(final Participant dealer, final Participant player) {
        return player.isBust()
                || dealer.isBlackJack()
                || dealer.isBust() && player.isBust()
                || !dealer.isBust() && dealer.calculateScore() > player.calculateScore();
    }

    private static boolean isPlayerWin(final Participant dealer, final Participant player) {
        return dealer.isBust()
                || player.isBlackJack()
                || dealer.calculateScore() < player.calculateScore();
    }
}
