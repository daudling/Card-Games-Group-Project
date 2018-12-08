class Card {

    private int rank;
    private int suit;
    
    //constructor
    public Card(int suit, int rank){
        this.rank = rank;
        this.suit = suit;
    }
    
    //getter method
    public int getCard(){
        return rank; 
    }
    
    //setter method
    public void setCard(int rank){
        this.rank = rank;
    }
    
    @Override
    public String toString(){
    //combine rank and suit together into a single string(ex: Ace of Diamonds)

        //suing StringBuilder for modifiability later on
        StringBuilder displayCard = new StringBuilder();
        
        
        switch(rank){
            case 11:
                displayCard.append("Jack");
                break;
            case 12:
                displayCard.append("Queen");
                break;
            case 13:
                displayCard.append("King");
                break;
            case 14:
                displayCard.append("Ace");
                break;    
            default:
                displayCard.append(rank); //number from 2 to 10 does not need to modify
                break;
        }
        
        displayCard.append(" of ");
        
        switch(suit){
            case 0:
                displayCard.append("Spades");
                break;
            case 1:
                displayCard.append("Hearts");
                break;
            case 2:
                displayCard.append("Clubs");
                break;
            case 3:
                displayCard.append("Diamonds");
                break;
            default:
                break;
        }
        
        //return the result of an entire combined string
        return displayCard.toString();
    }
    
}