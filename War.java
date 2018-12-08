import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.Collections;
import java.util.LinkedList;

public class War {

    public static void main(String[] args) {
        
        List<Card> cardDeck = new ArrayList<Card>(); //create an ArrayList "cardDeck"
        
        for(int x=0; x<4; x++){          //0-3 for suit
            for(int y=2; y<15; y++){     //2-14 for rank
                cardDeck.add(new Card(x,y)); //create new card
            } 
        }
        
        Collections.shuffle(cardDeck, new Random()); //shuffle
        
        //creating 2 decks
        LinkedList<Card> deck1 = new LinkedList<Card>();
        LinkedList<Card> deck2 = new LinkedList<Card>();
        
        deck1.addAll(cardDeck.subList(0, 25));                  
        deck2.addAll(cardDeck.subList(26, cardDeck.size()));
        
        while(true){
            Card p1Card = deck1.pop();  //each player place one card face up
            Card p2Card = deck2.pop();
            
            //display the face up card
            System.out.println("Player 1 plays card is " + p1Card.toString());
            System.out.println("Player 2 plays card is " + p2Card.toString());
            
            //rank  two cards
            if(p1Card.getCard() > p2Card.getCard()){//if player 1 win 
                deck1.addLast(p1Card);  //higher rank wins both cards and 
                deck1.addLast(p2Card);  //places them at the bottom of his deck.
                System.out.println("PLayer 1 wins the round");
            }
 
            else if(p1Card.getCard() < p2Card.getCard()){//if player 2 win 
                deck2.addLast(p1Card);   
                deck2.addLast(p2Card);  
                System.out.println("PLayer 2 wins the round");
            }
            
            else { //war happens when both cards' rank matched
                System.out.println("War"); 
                
                //creating war cards
                List<Card> war1 = new ArrayList<Card>(); 
                List<Card> war2 = new ArrayList<Card>();
                
                //checking do players have enough cards to stay in game
                for(int x=0; x<3; x++){ 
                    //either one player runs out of card is game over
                    if(deck1.size() == 0 || deck2.size() == 0 ){                      
                        break;
                    }
                    
                    System.out.println("War card for player1 is xx\nWar card for player2 is xx");

                    war1.add(deck1.pop());  //place additional card for war
                    war2.add(deck2.pop());                  
                }
                
                //only compare result when both players have enough cards for war
                if(war1.size() == 3 && war2.size() == 3 ){
                    //display the war cards from each player
                    System.out.println("War card for player1 is " + war1.get(0).toString());
                    System.out.println("War card for player2 is " + war2.get(0).toString());
                    
                    //if player 1 wins the war round
                    if(war1.get(2).getCard() > war2.get(2).getCard()){
                        deck1.addAll(war1); //player1 get all cards
                        deck1.addAll(war2);
                        System.out.println("Player 1 wins the war round");
                    }
                    //otherwise player 2 wins the war
                    else{
                        deck2.addAll(war1); //player2 get all cards
                        deck2.addAll(war2);
                        System.out.println("Player 2 wins the war round");
                    }                    
                }
                
            }
            
            //game over either one player runs out of cards
            if(deck1.size() == 0 ){
                System.out.println("game over\nPlayer 1 wins the game");
                break;
            }
            else if(deck2.size() == 0){
                System.out.println("game over\nPlayer 2 wins the game");
                break;
            }
        }  

    }       
}