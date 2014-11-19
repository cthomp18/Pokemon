import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class PokemonWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PokemonWorld extends World
{

    /**
     * Constructor for objects of class PokemonWorld.
     * 
     */
    Trainer Ash = new Trainer(new ArrayList());
    Trainer Gary = new Trainer(new ArrayList());
    int playerAction = 0; //make enum, with 1 being for choosing fight, 2 being Pokemon change, 0 for start of turn, 3 for attacking, 4 for choosing Pokemon
    GreenfootImage attackImage1;
    Image attImg1;
    GreenfootImage attackImage2;
    Image attImg2;
    GreenfootImage attackImage3;
    Image attImg3;
    GreenfootImage attackImage4;
    Image attImg4;
    boolean ashTurn;
    int worldWidth;    
    
    public PokemonWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 600, 1); 
        System.out.println("first");
        //draw wombat somwhere in the first 3 rows
        /*Wombat w1 = new Wombat();
        x = Greenfoot.getRandomNumber(width);
        y = Greenfoot.getRandomNumber(3);
        addObject(w1, x, y);*/
        ArrayList MewtwoAttacks = new ArrayList(); 
        MewtwoAttacks.add(new HydroPump());
        MewtwoAttacks.add(new HydroPump());
        MewtwoAttacks.add(new HydroPump());
        MewtwoAttacks.add(new HydroPump());
        Pokemon mewtwo = new Mewtwo();
        addObject(mewtwo, 120, 300);
        
        ArrayList BlastoiseAttacks = new ArrayList();
        BlastoiseAttacks.add(new HydroPump());
        BlastoiseAttacks.add(new HydroPump());
        BlastoiseAttacks.add(new HydroPump());
        BlastoiseAttacks.add(new HydroPump());
        Pokemon blastoise = new Blastoise();
        addObject(blastoise, 680, 300);
        
        ArrayList AshPokemon = new ArrayList();
        AshPokemon.add(mewtwo);
        ArrayList GaryPokemon = new ArrayList();
        GaryPokemon.add(blastoise);
        
        Ash = new Trainer(AshPokemon);
        addObject(Ash, 160, 475);
        
        attImg1 = new Image();
        attImg2 = new Image();
        attImg3 = new Image();
        attImg4 = new Image();
        
        ashTurn = true;
        worldWidth = this.getWidth() * this.getCellSize();
        
        Gary = new Trainer(GaryPokemon);
        System.out.println("second");
    }
    public void act() {
        
        //addObject(Ash, 160, 475);
        
        MouseInfo mouse = Greenfoot.getMouseInfo();
        
        Stats AshStats;
        Stats GaryStats;
        double AshDamage;
        double GaryDamage;

        Attack AshAttack = null;
        Attack GaryAttack = null;
        
        //While trainers haven't blacked out yet
        if (Ash.getCurrentPokemon().isAlive() && Gary.getCurrentPokemon().isAlive()) {
           // System.out.println("Hey...");
            if (playerAction == 0) {
               // System.out.println("Yo...");
                if (mouse != null) {
                    //System.out.println("Checking...");
                    if(ashTurn && mouse.getClickCount() == 1 && mouse.getX() > 50 && mouse.getX() < 160 && mouse.getY() > 460 && mouse.getY() < 490){
                        playerAction = 1;
                        displayAttacks(Ash);
                        System.out.println("Clicked fight!!");
                    } else if (!ashTurn && mouse.getClickCount() == 1 && mouse.getX() < worldWidth - 160 && mouse.getX() > worldWidth - 270 && mouse.getY() > 460 && mouse.getY() < 490) {
                        playerAction = 1;
                        displayAttacks(Gary);
                        System.out.println("Clicked fight!!");
                    } else if(false) { //Choose Pokemon and display them
                        displayPokemon(Ash);
                        playerAction = 2;
                    }
                }
            }
            //System.out.println("playerAction: "+ playerAction);

            //action = Ash.action();
            if (playerAction == 1) {
                if (ashTurn) {
                    AshAttack = chooseAttack(Ash);
                } else {
                    GaryAttack = chooseAttack(Gary);
                }
                //System.out.println("Choosing attack");
                if(ashTurn && AshAttack != null) {
                    playerAction = 3;
                    System.out.println("Ash: " + AshAttack.getName());
                }
                else if (!ashTurn && GaryAttack != null) {
                    playerAction = 3;
                    System.out.println("Gary: " + GaryAttack.getName());
                }
            }
           // System.out.println("~~");
            if(playerAction == 3) {
                //enemyAttack = Gary.attack(); //Add AI for choosing attacks. This will always choose Hydro
                //Ash.getCurrentPokemon().getStats().getHurt(getDamage(Gary.getCurrentPokemon().getStats(), Ash.getCurrentPokemon().getStats(), Ash.getCurrentPokemon().getAttacks().get(0)));
              
                AshStats = Ash.getCurrentPokemon().getStats();
                GaryStats = Gary.getCurrentPokemon().getStats();
                
                if (ashTurn) {
                    System.out.println("Attacking with: " + AshAttack.getName());
                    AshDamage = this.getDamage(AshStats, GaryStats, AshAttack);
                    if (new String(AshAttack.getName()).equals(new String("Sand Attack"))) {
                        GaryStats.setAccuracy(GaryStats.getAccuracy() - 0.1);
                        if (GaryStats.getAccuracy() < 0.5) {
                            GaryStats.setAccuracy(0.5);
                            System.out.println("Accuracy cannot be lowered anymore.");
                        }
                    }
                    else if (isHit(AshStats, GaryStats, AshAttack)) {
                        GaryStats.setHP(GaryStats.getHP() - (int)AshDamage);
                    }
                }
                else {
                    System.out.println("Attacking with: " + GaryAttack.getName());
                    if (isHit(GaryStats, AshStats, GaryAttack)) {
                        GaryDamage = this.getDamage(GaryStats, AshStats, GaryAttack);
                        AshStats.setHP(AshStats.getHP() - (int)GaryDamage);
                    }
                }
                System.out.println("Mewtwo : " + AshStats.getHP());
                System.out.println("Blastoise : " + GaryStats.getHP());
                playerAction = 0;
                
                clearAttack();  
                if (ashTurn) {
                    removeObject(Ash);
                    ashTurn = false;
                } else {
                    removeObject(Gary);
                    ashTurn = true;
                }
                
                //addObject(Ash, 160, 475);
                if (AshStats.getHP() <= 0) {
                    System.out.println("Trainer 2 Wins!");
                    Ash.getCurrentPokemon().getImage().clear();
                    Greenfoot.stop();
                }
                else if (GaryStats.getHP() <= 0) {
                    System.out.println("Trainer 1 Wins!");
                    Gary.getCurrentPokemon().getImage().clear();
                    Greenfoot.stop();
                }
                else {
                    if (ashTurn)
                        addObject(Ash, 160, 475);
                    else
                        addObject(Gary, worldWidth - 160, 475);
                }
            }
        }
        //If blackout/victory stuff
        
       // 
    }
    
    public int getDamage(Stats s1, Stats s2, Attack a) {
        if(a == null)
            System.out.println("It's null");
        double lev = 110.0 / 250.0;
        double rat = (double)s1.getAttack() / (double)s2.getDefense();
        
        
        
        return ((int)(lev * rat * a.getBase())) + 2;
    }
    
    public boolean isHit(Stats s1, Stats s2, Attack a) {
        double p = a.getBaseAccuracy() * (s1.getAccuracy() / s2.getEvade());
        double rand = Math.random();
        
        if (rand <= p)
            return true;
        else
            return false;
    }

    public void displayAttacks(Trainer trainer) {
        Pokemon attackingPokemon = trainer.getCurrentPokemon();
        
        drawAttack(trainer);
        attImg1.setImage(new GreenfootImage("images/" + /*attack.getName()*/"Psychic" +".png"));
        attImg2.setImage(new GreenfootImage("images/" + /*attack.getName()*/"Psychic" +".png"));
        attImg3.setImage(new GreenfootImage("images/" + /*attack.getName()*/"Psychic" +".png"));
        attImg4.setImage(new GreenfootImage("images/" + /*attack.getName()*/"Psychic" +".png"));
    }
    
    public void drawAttack(Trainer trainer) {
        addObject(attImg1, trainer.getX() - 55, trainer.getY() + 30);
        addObject(attImg2, trainer.getX() + 55, trainer.getY() + 30);
        addObject(attImg3, trainer.getX() + 55, trainer.getY() - 10);
        addObject(attImg4, trainer.getX() - 55, trainer.getY() - 10);
        /*trainer.getImage().drawImage(attackImage1, 10, 40);
        trainer.getImage().drawImage(attackImage2, 150, 40);
        trainer.getImage().drawImage(attackImage3, 10, 0);
        trainer.getImage().drawImage(attackImage4, 150, 0);  */      
    }
    
    public void clearAttack() {
        removeObject(attImg1);
        removeObject(attImg2);
        removeObject(attImg3);
        removeObject(attImg4);
    }

    public Attack chooseAttack(Trainer trainer) {
        if (Greenfoot.mouseClicked(attImg1)) {
            return trainer.getCurrentPokemon().getAttacks().get(0);
        }
        else if (Greenfoot.mouseClicked(attImg2)) {
            return trainer.getCurrentPokemon().getAttacks().get(1);
        }
        else if (Greenfoot.mouseClicked(attImg3)) {
            return trainer.getCurrentPokemon().getAttacks().get(2);
        }
        else if (Greenfoot.mouseClicked(attImg4)) {
            return trainer.getCurrentPokemon().getAttacks().get(3);
        }
        return null;
        /*MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse != null) {
            if(mouse.getClickCount() == 1 && mouse.getX() > 0 && mouse.getX() < 110 && mouse.getY() > 330 && mouse.getY() < 370){
                //Attack with attack1
                return trainer.getCurrentPokemon().getAttacks().get(0);
            } else if(mouse.getClickCount() == 1 && mouse.getX() > 130 && mouse.getX() < 230 && mouse.getY() > 330 && mouse.getY() < 370) {
                //Attack with attack4
                return trainer.getCurrentPokemon().getAttacks().get(3);        
            } else if(mouse.getClickCount() == 1 && mouse.getX() > 0 && mouse.getX() < 110 && mouse.getY() > 370 && mouse.getY() < 410) {
                //Attack with attack2
                return trainer.getCurrentPokemon().getAttacks().get(1);        
            } else if(mouse.getClickCount() == 1 && mouse.getX() > 130 && mouse.getX() < 230 && mouse.getY() > 370 && mouse.getY() < 410) {
                //Attack with attack3
                return trainer.getCurrentPokemon().getAttacks().get(2);        
            }
            System.out.println("GUUUUUUHHHHHHH");   
        }
        return null;*/
    }
    
    public void displayPokemon(Trainer trainer) {
        
    }
}
 /*       if (mouse != null) {
            if(mouse.getClickCount() == 1 && mouse.getX() > 50 && mouse.getX() < 160 && mouse.getY() > 460 && mouse.getY() < 490){
                System.out.println("Fight chosen");
                Trainer attackingTrainer = (Trainer)mouse.getActor();
                Pokemon attackingPokemon = attackingTrainer.getCurrentPokemon();
                Attack attack1 = attackingPokemon.getAttacks().get(0);
                GreenfootImage attack1Image = new GreenfootImage(attack1.getName() +".png");
                Attack attack2 = attackingPokemon.getAttacks().get(1);
                GreenfootImage attack2Image = new GreenfootImage(attack2.getName()+".png");
                Attack attack3 = attackingPokemon.getAttacks().get(2);
                GreenfootImage attack3Image = new GreenfootImage(attack3.getName()+".png");
                Attack attack4 = attackingPokemon.getAttacks().get(3);
                GreenfootImage attack4Image = new GreenfootImage(attack4.getName()+".png");
                
                attackingTrainer.getImage().drawImage(attack1Image, 10, 0);//Psychic
                attackingTrainer.getImage().drawImage(attack4Image, 150, 0);//Tackle
                attackingTrainer.getImage().drawImage(attack2Image, 10, 40);//HydroPump
                attackingTrainer.getImage().drawImage(attack3Image, 150, 40);//Slash
                
                
                //Bring up attacks method
                
            } else if(mouse.getClickCount() == 1 && mouse.getX() > -40 && mouse.getX() < 60 && mouse.getY() > -50 && mouse.getY() < 50){
                System.out.println("Psychic chosen");
                
            } else if(mouse.getClickCount() == 1 && mouse.getX() > 55 && mouse.getX() < 150 && mouse.getY() > 460 && mouse.getY() < 490) {
                
            }
        }
    }
}*/