package domain.participant;

import domain.card.Card;
import domain.card.CardNumber;
import domain.card.CardPattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.assertj.core.api.InstanceOfAssertFactories.type;

class DealerTest {

    private Card card;
    private Dealer dealer;

    @BeforeEach
    void init() {
        card = Card.create(CardPattern.HEART, CardNumber.ACE);
        dealer = Dealer.create();
    }

    @Test
    @DisplayName("create()는 호출하면, 딜러를 생성한다")
    void create_whenCall_thenSuccess() {
        assertThatCode(() -> Dealer.create())
                .doesNotThrowAnyException();

        assertThat(Dealer.create())
                .isExactlyInstanceOf(Dealer.class);
    }

    @Test
    @DisplayName("addCard()는 카드를 건네주면 참가자의 카드에 추가한다")
    void addCard_givenCard_thenSuccess() {
        // when
        dealer.addCard(card);

        // then
        assertThat(dealer)
                .extracting("participantCard", type(ParticipantCard.class))
                .extracting("cards", as(list(Card.class)))
                .hasSize(1);
    }

    @Test
    @DisplayName("getFirst()는 호출하면, 딜러의 첫 번째 카드를 조회한다")
    void getFirst_whenCall_thenReturnFirstCard() {
        // given
        dealer.addCard(card);

        // when
        Card actual = dealer.getFirstCard();

        // then
        assertThat(actual).isEqualTo(card);
    }

    @MethodSource(value = "domain.helper.ParticipantArguments#makeCards")
    @ParameterizedTest(name = "calculateScore()는 호출하면 점수를 계산한다")
    void calculateScore_whenCall_thenReturnScore(final List<Card> cards, final int expected) {
        // given
        cards.forEach(dealer::addCard);

        // when
        int score = dealer.calculateScore();

        // then
        assertThat(score)
                .isSameAs(expected);
    }

    @MethodSource(value = "domain.helper.ParticipantArguments#makeBustCard")
    @ParameterizedTest(name = "isBust()는 호출하면 버스트인지 확인한다")
    void isBust_whenCall_thenReturnIsBust(final List<Card> cards, final boolean expected) {
        // given
        cards.forEach(dealer::addCard);

        // when
        boolean actual = dealer.isBust();

        // then
        assertThat(actual).isSameAs(expected);
    }

    @MethodSource(value = "domain.helper.ParticipantArguments#makeBlackJackCard")
    @ParameterizedTest(name = "isBlackJack()은 호출하면 블랙잭인지 확인한다")
    void isBlackJack_whenCall_thenReturnIsBust(final List<Card> cards, final boolean expected) {
        // given
        cards.forEach(dealer::addCard);

        // when
        boolean actual = dealer.isBlackJack();

        // then
        assertThat(actual).isSameAs(expected);
    }
}
