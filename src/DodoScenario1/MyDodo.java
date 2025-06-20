import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
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
            if(borderAhead() == false){
                move();
            }
        }
    }
    
    /*
     * Mimi will walk to the world edge. On her way she will pick up all the grain that she ecounter
     */
    
    public void pickUpGrainWhenWalking(){
        walkToWorldEdge();
        if(onGrain()==true){
                pickUpGrain();
            }
    }
    
    /*
     * This function gets the information of the nearest egg
     */
    public Egg getNearestEgg(){
        List<Egg> eggs = getWorld().getObjects(Egg.class);
        
        Egg closestEgg = null;
        int distance = Integer.MAX_VALUE;
        
        for (Egg egg : eggs) {
            int distanceX = Math.abs(egg.getX() - getX());
            int distanceY = Math.abs(egg.getY() - getY());
            
            int distanceTotal = distanceX + distanceY;
            
            if (distanceTotal < distance) {
                closestEgg = egg;
                distance = distanceTotal;
            }
        }
        
        return closestEgg;
    }
    
    /*
     * This function gets the highest value of egg in the map
     */
    public Egg getValuableAEgg(){
        List<Egg> eggs = getWorld().getObjects(Egg.class);
        
        Egg closestEgg = null;
        int value = 0;
        
        for (Egg egg : eggs) {    
            if(egg.getValue() > value){
                value = egg.getValue();
                int distanceX = Math.abs(egg.getX() - getX());
                int distanceY = Math.abs(egg.getY() - getY());
            
                int distanceTotal = distanceX + distanceY;
            
                closestEgg = egg;
            }
        }
        
        return closestEgg;
    }
    
    /*
     * This function goes to the most valuable egg
     */
    public void goToValueableEgg(){
        Egg egg = getValuableAEgg();
        goToLocation(egg.getX(),egg.getY());
    }

    
    
    /*
     * This function goes to the nearest egg
     */
    public void goToNearestEgg(){
        Egg egg = getNearestEgg();
        goToLocation(egg.getX(),egg.getY());
    }
    
    /*
     * Mimi will walk to the world edge. On her way she will lay a egg in every nest that she ecounter
     */
    public void layEggInNestWhenWalking(){
        walkToWorldEdge();
        if(onNest()==true && onEgg()==false){
                layEgg();
            }
    }
    
    /*
     * Mimi will walk in a row nonstop until stopping on a egg
     */
    public void goToEgg(){
        while(onEgg()==false || borderAhead()==false){
            move();
        }
    }
    
    /*
     * Mimi will clim over the fence ahead of her
     */
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
    
    /*
     * Mimi will walk 1 step back
     */
    public void stepOneBack(){
        turn180();
        move();
        turn180();
    }
    
    /*
     * Mimi will detect if theres a grain ahead of her
     */
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
    
    /*
     * Mimi will walk back to the world edge
     */
    public void goBackRowFaceBack(){
        turn180();
        walkToWorldEdge( );
        turn180();
    }
    
    /*
     * Mimi will walk around a fence depending on her direction she will walk clockwise around the fence or anti clockwise
     */
    public void walkAroundFence(){
        if(getDirection()== NORTH || getDirection()== WEST){
            walkAroundFenceAntiClockWise();
        }else{
            walkAroundFenceClockWise();
        }
    }
    
    /*
     * Mimi will walk around a fence clockwise
     */
    private void walkAroundFenceClockWise(){
        while(onEgg()==false){
            turnRight();
            while(fenceAhead()==true){
                turnLeft();
            }
            move();
        }
    }
    
    /*
     * Mimi will walk around a fence anticlockwise
     */
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
    
    /*
     * Mimi will walk and follow a train of egg stopping until she steps onto a nest
     */
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
    
    /*
     * Mimi will solve a simple maze. She will walk clockwise or anticlockwise depending on her starting direction
     */
    public void solveVerySimpleMaze(){
        if(getDirection()== NORTH || getDirection()== WEST){
            solveVerySimpleMazeAntiClockWise();
        }else{
            solveVerySimpleMazeClockWise();
        }
    }
    
    /*
     * Mimi will solve a simple maze. She will walk clockwise
     */
    private void solveVerySimpleMazeClockWise(){
        while(onNest()==false){
            turnRight();
            while(fenceAhead()==true){
                turnLeft();
            }
            move();
        }
    }
    
    /*
     * Mimi will solve a simple maze. She will walk anticlockwise
     */
    private void solveVerySimpleMazeAntiClockWise(){
        while(onNest()==false){
            turnLeft();
            while(fenceAhead()==true){
                turnRight();
            }
            move();
        }
    }
    
    /*
     * Mimi will face the given direction
     */
    public void faceDirection(int direction){
        if (direction > -1 && direction < 4){
            while(getDirection() != direction){
                turnLeft();
            }
        }
    }

    /*
     * Mimi will face the x axis
     */
    private void alignToXCord(int xCord){
        if(getX() > xCord){
            faceDirection(3);
        }else{
            faceDirection(1);
        }
    }
    
    /*
     * Mimi will face the y axis
     */
    private void alignToYCord(int yCord){
        if(getY() > yCord){
            faceDirection(0);
        }else{
            faceDirection(2);
        }
    }
    
    /*
     * Mimi will walk to the given location
     */
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
    
    /*
     * Mimi will check if the cordinates exist
     */
    public boolean validCordintes(int xCord, int yCord){
        int worldHeight = getWorld().getHeight();
        int worldWidth = getWorld().getWidth();
        if(worldHeight < yCord || worldWidth < xCord || yCord < 0 || xCord < 0){
            return false;
        }else{
            return true;
        }
    }
    
    /*
     * Mimi will count eggs in a row
     */
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
            if(borderAhead()==false){
                move();
            }
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
    
    /*
     * Mimi will get the lenght of the row
     */
    private int getRowLenght(int dir){
        if(dir == 0 || dir == 2){
             return getWorld().getHeight();
        }else{
             return getWorld().getWidth();
        }
    }
    
    /*
     * Mimi will lay a trail of egg. The amount that she lays is given by the user
     */
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
    
    
    /*
     * Mimi move one step down
     */
    public void moveOneStepDown(){
        int startingDir = getDirection();
        faceDirection(2);
        if(borderAhead() == false){
            move();
        }
        faceDirection(startingDir);
    }
    
    /*
     * Mimi will count the total eggs in the world
     */
    public int getAmountEggInMap(){
        int StartingX = getX();
        int StartingY = getY();
        int StartingDir = getDirection();
        int amount = 0;
        
        goToLocation(0, 0);
        faceDirection(1);
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
    
    /*
     * Mimi will find the most eggs in a row
     */
    public int getMostEggInRow(){
        int StartingX = getX();
        int StartingY = getY();
        int StartingDir = getDirection();
        int row = 0;
        int thatRowAmount = 0;
        int mostEgg = 0;
        int rowWithMostEgg = 0;
        
        goToLocation(0, 0);
        faceDirection(1);
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
    
    /*
     * Mimi will find the average eggs in that row
     */
    public double getAverageEggOfRow(){
        double worldWidth = getWorld().getWidth();
        double eggs = countEggsInRow();
        double total = eggs/worldWidth;
        
        return total;
    }
    
    /*
     * Mimi will lay eggs in a row and return to her original postion. The amount of eggs she lays is given by the user
     */
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

    /*
     * Mimi will make a stair of eggs the height is given by the user
     */
    public void makeMonument(int height){
        int layEgg = 0;
        faceDirection(3);
        
        for(int i = 0; i < height; i++){
            layEgg = layEgg + 1;
            layEggTrailAndReturn(layEgg);
            moveOneStepDown();
        } 
    }
    
    /*
     * Mimi will make a stair of eggs with a stronger foundation the height is given by the user
     */
    public void makeStrongMonument(int height){
        int layEgg = 1;
        faceDirection(3);
        for(int i = 0; i < height; i++){
            layEgg = layEgg + (1 * layEgg);
            layEggTrailAndReturn(layEgg);
            moveOneStepDown();
        } 
    }
    
    /*
     * Mimi will make a pyramid of eggs the height is given by the user
     */
    public void makePyramid(int height){
        int layEgg = 1;
        faceDirection(3);
        for(int i = 0; i < height; i++){
            layEggTrailAndReturn(layEgg);
            layEgg = layEgg + 2;
            moveOneStepDown();
        }
    }
    
    /*
     * Check if the Row is a even number
     * the input dir is the direction that mimi should face
     * 
     */
    private boolean checkEvenEgg(int dir){
        int startX = getX();
        int startY = getY();
        int startDir = getDirection();
        int eggs = -1;
        
        faceDirection(dir);
        eggs = countEggsInRow();
        
        goToLocation(startX, startY);
        faceDirection(startDir);
        if (eggs % 2 == 0) {
          return true;
        } else {
          return false;
        }
    }
    
    /*
     *This function will parit only one mistake in a map
     * 
     * 
     */
    public void paritOneMistake(){
        goToLocation(0,0);
        boolean rowIsEven = true;
        boolean columnIsEven = true;
        
        while(rowIsEven == true){
            if(checkEvenEgg(1) == true){
                moveOneStepDown();
            }else{
                rowIsEven = false;
            }
        }
        while(columnIsEven == true){
            if(checkEvenEgg(2) == true){
                faceDirection(1);
                move();
            }else{
                columnIsEven = false;
            }
        }
        layEgg();
    }
    
}
