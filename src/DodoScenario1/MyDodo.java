import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;
    
    public MyDodo() {
        super( EAST );
        myNrOfEggsHatched = 0;
    }

    public void act() {
    }

    /**
     * Move one cell forward in the current direction.
     * 
     * <P> Initial: Dodo is somewhere in the world
     * <P> Final: If possible, Dodo has moved forward one cell
     *
     */
    public void move() {
        if ( canMove() ) {
            step();
        } else {
            showError( "I'm stuck!" );
        }
    }

    /**
     * Test if Dodo can move forward, (there are no obstructions
     *    or end of world in the cell in front of her).
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can move (no obstructions ahead)
     *                 false if Dodo can't move
     *                      (an obstruction or end of world ahead)
     */
    public boolean canMove() {
        if ( borderAhead()||fenceAhead() ){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Hatches the egg in the current cell by removing
     * the egg from the cell.
     * Gives an error message if there is no egg
     * 
     * <p> Initial: Dodo is somewhere in the world. There is an egg in Dodo's cell.
     * <p> Final: Dodo is in the same cell. The egg has been removed (hatched).     
     */    
    public void hatchEgg () {
        if ( onEgg() ) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError( "There was no egg in this cell" );
        }
    }
    
    /**
     * Returns the number of eggs Dodo has hatched so far.
     * 
     * @return int number of eggs hatched by Dodo
     */
    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
    }
    
    /**
     * Move given number of cells forward in the current direction.
     * 
     * <p> Initial:   
     * <p> Final:  
     * 
     * @param   int distance: the number of steps made
     */
    public void jump( int distance ) {
        int nrStepsTaken = 0;               // set counter to 0
        while ( nrStepsTaken < distance ) { // check if more steps must be taken  
            move();                         // take a step
            nrStepsTaken++;                 // increment the counter
        }
    }

    
    
    
    /**
     * Walks to edge of the world printing the coordinates at each step
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     *              Coordinates of each cell printed in the console.
     */

    public void walkToWorldEdge(){
        while(borderAhead() == false){
            climbOverFence();
            move();
        }
    }
    
    public void pickUpGrainWhenWalking(){
        walkToWorldEdge();
        if(onGrain()==true){
                pickUpGrain();
            }
    }
    
    public void layEggInNestWhenWalking(){
        walkToWorldEdge();
        if(onNest()==true && onEgg()==false){
                layEgg();
            }
    }
    
    public void goToEgg(){
        while(onEgg()==false || borderAhead()==true){
            move();
        }
    }
    
        public void climbOverFence(){
            while(fenceAhead()==true){
                turnLeft();
                move();
                turnRight();
                for(int i = 0; i< 2; i++){
                    move();
                }
                turnRight();
                move();
                turnLeft();
            }
    }
    
    public void stepOneBack(){
        turn180();
        move();
        turn180();
    }
    
    public boolean grainAhead(){
        boolean grain = false;
        move();
        
        if(onGrain()==true){
            grain = true;
        }else{
            grain = false;
        }
        stepOneBack();
        return grain;
    }
    
    public void goBackRowFaceBack(){
        turn180();
        walkToWorldEdge( );
        turn180();
    }
    
    public void walkAroundFence(){
        if(getDirection()== NORTH || getDirection()== WEST){
            walkAroundFenceAntiClockWise();
        }else{
            walkAroundFenceClockWise();
        }
    }

    private void walkAroundFenceClockWise(){
        while(onEgg()==false){
            turnRight();
            while(fenceAhead()==true){
                turnLeft();
            }
            move();
        }
    }
    
    private void walkAroundFenceAntiClockWise(){
        while(onEgg()==false){
            turnLeft();
            while(fenceAhead()==true){
                turnRight();
            }
            move();
        }
    }
    /**
     * Test if Dodo can lay an egg.
     *          (there is not already an egg in the cell)
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can lay an egg (no egg there)
     *                 false if Dodo can't lay an egg
     *                      (already an egg in the cell)
     */
    
    public boolean canLayEgg( ){
           if( onEgg() ){
            return false;
           }else{
            return true;
          }
    }
    
    public void followEggTrail(){
        while(onNest() == false){
            if(onEgg()== true){
                hatchEgg();
            }
            while(eggAhead()==false){
                if(nestAhead()==true){
                    move();
                }
                if(onNest()==false){
                    turnLeft();
                }
            }if(onNest()==false){
                move();
             }
        }
    }
    
    public void solveVerySimpleMaze(){
        if(getDirection()== NORTH || getDirection()== WEST){
            solveVerySimpleMazeAntiClockWise();
        }else{
            solveVerySimpleMazeClockWise();
        }
    }

    private void solveVerySimpleMazeClockWise(){
        while(onNest()==false){
            turnRight();
            while(fenceAhead()==true){
                turnLeft();
            }
            move();
        }
    }
    
    private void solveVerySimpleMazeAntiClockWise(){
        while(onNest()==false){
            turnLeft();
            while(fenceAhead()==true){
                turnRight();
            }
            move();
        }
    }
    private void faceNorth(){
        while(getDirection() != NORTH){
            turnLeft();
        }
    }
    private void faceEast(){
        while(getDirection() != EAST){
            turnLeft();
        }
    }
    private void faceSouth(){
        while(getDirection() != SOUTH){
            turnLeft();
        }
    }
    private void faceWest(){
        while(getDirection() != WEST){
            turnLeft();
        }
    }
    
    private void alignToXCord(int xCord){
        if(getX() > xCord){
            faceWest();
        }else{
            faceEast();
        }
    }
    
    private void alignToYCord(int yCord){
        if(getY() > yCord){
            faceNorth();
        }else{
            faceSouth();
        }
    }
    
    public void goToLocation(int xCord, int yCord){
        alignToXCord(xCord);
        while(getX()!=xCord){
            if(canMove()==true){
                move();
            }
        }
        alignToYCord(yCord);
        while(getY()!=yCord){
            if(canMove()==true){
                move();
            }
        }
    }
    
    public boolean validCordintes(int xCord, int yCord){
        int worldHeight = getWorld().getHeight();
        int worldWidth = getWorld().getWidth();
        if(worldHeight < yCord || worldWidth < xCord || yCord < 0 || xCord < 0){
            return false;
        }else{
            return true;
        }
    }
    
    public int countEggsInRow(){
        int startingX = getX();
        int startingY = getY();
        int startingDir = getDirection();
        int eggOnRow = 0;
        goBackRowFaceBack();
        while(borderAhead() == false){
            if(onEgg()==true){
                eggOnRow = eggOnRow + 1;
            }
            move();
        }
        if(onEgg()==true){
                eggOnRow = eggOnRow + 1;
        }
        goToLocation(startingX, startingY);
        while(getDirection() != startingDir){
            turnLeft();
        }
        return eggOnRow;
    }
    
    private int getRowLenght(int dir){
        if(dir == 0 || dir == 2){
             return getWorld().getHeight();
        }else{
             return getWorld().getWidth();
        }
    }
    
    public void layTrailOfEgg(int amount){
            if(getRowLenght(getDirection()) > amount){
                for(int i = 0; i < amount; i++){
                    if(borderAhead()==false){
                        layEgg();
                        move();
                    }
                }
                if(borderAhead()==false){
                    stepOneBack();
                }
            }
    }
    
    public void moveOneStepDown(){
        int startingDir = getDirection();
        
        faceSouth();
        if(borderAhead()==false){
            move();
        }
        while(getDirection()!=startingDir){
            turnLeft();
        }
    }
    
    public int getAmountEggInMap(){
        int StartingX = getX();
        int StartingY = getY();
        int StartingDir = getDirection();
        int amount = 0;
        
        goToLocation(0, 0);
        faceEast();
        for(int i = 0; i < getWorld().getHeight(); i++){
            amount = amount + countEggsInRow();
            moveOneStepDown();
        }
        goToLocation(StartingX, StartingY);
        while(getDirection() != StartingDir){
            turnLeft();
        }
        return amount;
    }
    
    public int getMostEggInRow(){
        int StartingX = getX();
        int StartingY = getY();
        int StartingDir = getDirection();
        int row = 0;
        int thatRowAmount = 0;
        int mostEgg = 0;
        int rowWithMostEgg = 0;
        
        goToLocation(0, 0);
        faceEast();
        for(int i = 0; i < getWorld().getHeight(); i++){
            thatRowAmount = countEggsInRow();
                row = row + 1;
            if(thatRowAmount>mostEgg){
                rowWithMostEgg = row;
                mostEgg = thatRowAmount;
            }
            moveOneStepDown();
        }
        goToLocation(StartingX, StartingY);
        while(getDirection() != StartingDir){
            turnLeft();
        }
        return rowWithMostEgg;
    }
    
    public double getAverageEggOfRow(){
        double worldWidth = getWorld().getHeight();
        double eggs = countEggsInRow();
        double total = eggs/worldWidth;
        
        return total;
    }
    
    private void layEggTrailAndReturn(int amount){
        for(int i = 0; i < amount; i++){
            if(borderAhead()==false){
                layEgg();
                move();
            }
        }
        if(borderAhead()==false){
            stepOneBack();
        }
        for(int i = 0; i < amount; i++){
            stepOneBack();
        }
    }

    public void makeMonument(int height){
        int layEgg = 0;
        faceWest();
        
        for(int i = 0; i < height; i++){
            layEgg = layEgg + 1;
            layEggTrailAndReturn(layEgg);
            moveOneStepDown();
        } 
    }
}
