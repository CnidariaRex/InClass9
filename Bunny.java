package bunnyhunt;

public class Bunny extends Animal {

    private boolean canSeeWolfNow = false;
    private boolean haveSeenWolf = false;
    private int distanceToWolf;
    private int directionToWolf;
    private int currentDirection;
    private int directionToRun;
    private int grazing = 0;
    private int turnsRunning = 0;

    public Bunny(Model model, int row, int column) {
        super(model, row, column);
    }
    
    @Override
    int decideMove() {
        canSeeWolfNow = false;
        for (int i = Model.MIN_DIRECTION; i <= Model.MAX_DIRECTION; i++) {
            if (look(i) == Model.WOLF) {
                canSeeWolfNow = haveSeenWolf = true;
                directionToWolf = i;
                distanceToWolf = distance(i);
            }
        }

        if(canSeeWolfNow)
        {
        	//run away from wolf
        	currentDirection = (directionToWolf + 4) % 8;
        	grazing = 1;
        }
        else if(haveSeenWolf)
        {
        	turnsRunning = turnsRunning + 1;
        }
        else
        {
        	//grazing
        	if(grazing <= 5)
        	{
	        	grazing = grazing + 1;
	        	return Model.STAY;
	        }
	        else
	        {
	        	grazing = 1;
	        	return Model.random(Model.MIN_DIRECTION, Model.MAX_DIRECTION);
	        }
        }

        if(haveSeenWolf && !canSeeWolfNow)
        {
        	if(turnsRunning == 5)
        	{
        		turnsRunning = 0;
        		haveSeenWolf = false;
        	}
        }

        if (canMove(currentDirection))
            return currentDirection;
        else if (canMove(Model.turn(currentDirection, 1)))
            return Model.turn(currentDirection, 1);
        else if (canMove(Model.turn(currentDirection, -1)))
            return Model.turn(currentDirection, -1);
        else {
            currentDirection = Model.random(Model.MIN_DIRECTION,
                                            Model.MAX_DIRECTION);
            for (int i = 0; i < 8; i++) {
                if (canMove(currentDirection))
                    return currentDirection;
                else
                    currentDirection = Model.turn(currentDirection, 1);
            }
        }
        // stuck! cannot move
        return Model.STAY;
    }
}
