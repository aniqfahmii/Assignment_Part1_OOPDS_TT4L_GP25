import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GoBoomGame {
    private ArrayList<Card> deck;
    private Card centerCard;
    private ArrayList<Player> players;
    private Player currentPlayer;

    public void startNewGame() {
        // Initialize deck
        deck = new ArrayList<>();
        for (Card.Rank rank : Card.Rank.values()) {
            for (Card.Suit suit : Card.Suit.values()) {
                deck.add(new Card(rank, suit));
            }
        }

        // Shuffle the deck
        Collections.shuffle(deck);

        // Initialize players
        players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
        players.add(new Player("Player3"));
        players.add(new Player("Player4"));

        // Deal cards to players
        int cardsPerPlayer = 7;
        for (int i = 0; i < cardsPerPlayer; i++) {
            for (Player player : players) {
                player.addCard(deck.remove(deck.size() - 1));
            }
        }

        // Place the first lead card at the center
        centerCard = deck.remove(deck.size() - 1);

        // Determine the first player based on the lead card
        currentPlayer = determineFirstPlayer(centerCard);

        // Print initial game state
        printGameState();
        playGame();
    }

    public Player determineFirstPlayer(Card leadCard) {
        Card.Rank rank = leadCard.getRank();

        if (rank == Card.Rank.ACE || rank == Card.Rank.FIVE || rank == Card.Rank.NINE || rank == Card.Rank.KING) {
            return players.get(0);
        } else if (rank == Card.Rank.TWO || rank == Card.Rank.SIX || rank == Card.Rank.TEN) {
            return players.get(1);
        } else if (rank == Card.Rank.THREE || rank == Card.Rank.SEVEN || rank == Card.Rank.JACK) {
            return players.get(2);
        } else if (rank == Card.Rank.FOUR || rank == Card.Rank.EIGHT || rank == Card.Rank.QUEEN) {
            return players.get(3);
        }

        return null;
    }

    public void printGameState() {
        System.out.println("Player1: " + players.get(0).getHand());
        System.out.println("Player2: " + players.get(1).getHand());
        System.out.println("Player3: " + players.get(2).getHand());
        System.out.println("Player4: " + players.get(3).getHand());
        System.out.println("Center : " + centerCard);
        System.out.println("Deck : " + deck);
        System.out.println("Score: Player1 = 0 | Player2 = 0 | Player3 = 0 | Player4 = 0");
        System.out.println("Turn : " + currentPlayer.getName());
        System.out.println("> ");
    }
    private void playGame() {
        Scanner scanner = new Scanner(System.in);
    
        while (!isGameOver()) {
            System.out.println("Enter the index of the card you want to play: ");
            int cardIndex = scanner.nextInt();
    
            if (isValidCardIndex(cardIndex)) {
                Card playedCard = currentPlayer.playCard(cardIndex);
    
                if (isCardPlayable(playedCard)) {
                    centerCard = playedCard;
                    System.out.println(currentPlayer.getName() + " played " + playedCard);
    
                    // Move to the next player
                    currentPlayer = getNextPlayer();
    
                    // Print updated game state
                    printGameState();
                } else {
                    System.out.println("Invalid move! You cannot play that card. Try again.");
                }
            } else {
                System.out.println("Invalid card index! Try again.");
            }
        }
    
        System.out.println("Game over!");
    }
    
    private Player getNextPlayer() {
        int currentPlayerIndex = players.indexOf(currentPlayer);
        int nextPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return players.get(nextPlayerIndex);
    }
    
    private boolean isCardPlayable(Card card) {
        return card.getRank() == centerCard.getRank() || card.getSuit() == centerCard.getSuit();
    }
    
    private boolean isValidCardIndex(int index) {
        return index >= 0 && index < currentPlayer.getHandSize();
    }
    
    private boolean isGameOver() {
        for (Player player : players) {
            if (player.getHandSize() == 0) {
                return true;
            }
        }
        return false;
    }
    

    private static class Player {
        private String name;
        private ArrayList<Card> hand;
    
        public Player(String name) {
            this.name = name;
            this.hand = new ArrayList<>();
        }
    
        public void addCard(Card card) {
            hand.add(card);
        }
    
        public ArrayList<Card> getHand() {
            return hand;
        }
    
        public String getName() {
            return name;
        }
    
        public Card playCard(int cardIndex) {
            return hand.remove(cardIndex);
        }
    
        public int getHandSize() {
            return hand.size();
        }
    }
    
public static void main(String[] args) {
    GoBoomGame game = new GoBoomGame();
    game.startNewGame();
}

private static class Card {
    private Rank rank;
    private Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
    public enum Rank {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;
    }
    
    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES;}
}
}
 