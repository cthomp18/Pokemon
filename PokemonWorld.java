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
    GreenfootImage attackImage2;
    GreenfootImage attackImage3;
    GreenfootImage attackImage4;
    
    
    
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
        Gary = new Trainer(GaryPokemon);
        System.out.println("second");
    }
    public void act() {
        
        //addObject(Ash, 160, 475);
        
        MouseInfo mouse = Greenfoot.getMouseInfo();
        
        Stats playerStats;
        Stats enemyStats;
        double playerDamage;
        double enemyDamage;

        Attack playerAttack = null;
        Attack enemyAttack = null;
        
        //While trainers haven't blacked out yet
        if (Ash.getCurrentPokemon().isAlive() && Gary.getCurrentPokemon().isAlive()) {
           // System.out.println("Hey...");
            if (playerAction == 0) {
               // System.out.println("Yo...");
                if (mouse != null) {
                    //System.out.println("Checking...");
                    if(mouse.getClickCount() == 1 && mouse.getX() > 50 && mouse.getX() < 160 && mouse.getY() > 460 && mouse.getY() < 490){
                        playerAction = 1;
                        displayAttacks(Ash);
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
                displayAttacks(Ash);
                playerAttack = chooseAttack(Ash);
                System.out.println("Choosing attack");
                if(playerAttack != null) {
                    playerAction = 3;
                    System.out.println(playerAttack.getName());
                }
            }
           // System.out.println("~~");
            if(playerAction == 3) {
                System.out.println("Attacking with: " + playerAttack.getName());
               // a1 = Ash.attack();
                //action = Gary.action();
                enemyAttack = Gary.attack(); //Add AI for choosing attacks. This will always choose Hydro
                //Ash.getCurrentPokemon().getStats().getHurt(getDamage(Gary.getCurrentPokemon().getStats(), Ash.getCurrentPokemon().getStats(), Ash.getCurrentPokemon().getAttacks().get(0)));
              
                playerStats = Ash.getCurrentPokemon().getStats();
                enemyStats = Gary.getCurrentPokemon().getStats();
                playerDamage = this.getDamage(playerStats, enemyStats, playerAttack);
                enemyDamage = this.getDamage(enemyStats, playerStats, enemyAttack);
                
                if (new String(playerAttack.getName()).equals(new String("Sand Attack"))) {
                    enemyStats.setAccuracy(enemyStats.getAccuracy() - 0.1);
                    if (enemyStats.getAccuracy() < 0.5) {
                        enemyStats.setAccuracy(0.5);
                        System.out.println("Accuracy cannot be lowered anymore.");
                    }
                }
                else if (isHit(playerStats, enemyStats, playerAttack)) {
                    playerStats.setHP(playerStats.getHP() - (int)playerDamage);
                }
                
                if (Gary.getCurrentPokemon().isAlive() && isHit(enemyStats, playerStats, enemyAttack)) {
                    enemyStats.setHP(enemyStats.getHP() - (int)enemyDamage);
                }
                System.out.println("Mewtwo : " + playerStats.getHP());
                System.out.println("Blastoise : " + enemyStats.getHP());
                playerAction = 0;
                //clearAttack();
                attackImage1.clear();
                attackImage2.clear();
                attackImage3.clear();
                attackImage4.clear();  
                if (playerStats.getHP() <= 0) {
                    System.out.println("Trainer 1 Wins!");
                    Gary.getCurrentPokemon().getImage().clear();
                    Greenfoot.stop();
                }
                else if (enemyStats.getHP() <= 0) {
                    System.out.println("Trainer 2 Wins!");
                    Ash.getCurrentPokemon().getImage().clear();
                    Greenfoot.stop();
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
        attackImage1 = new GreenfootImage(/*attack.getName()*/"Psychic" +".png");
        attackImage2 = new GreenfootImage(/*attack.getName()*/"Psychic" +".png");
        attackImage3 = new GreenfootImage(/*attack.getName()*/"Psychic" +".png");
        attackImage4 = new GreenfootImage(/*attack.getName()*/"Tackle" +".png");
        for(int i = 0; i < 4; i++) {
            drawAttack(trainer, attackingPokemon.getAttacks().get(i), i);
        }
        
        
        /*Attack attack1 = attackingPokemon.getAttacks().get(0);
        GreenfootImage attack1Image = new GreenfootImage(attack1.getName() +".png");
        
        Attack attack2 = attackingPokemon.getAttacks().get(1);
        GreenfootImage attack2Image = new GreenfootImage(attack2.getName()+".png");
        Attack attack3 = attackingPokemon.getAttacks().get(2);
        GreenfootImage attack3Image = new GreenfootImage(attack3.getName()+".png");
        Attack attack4 = attackingPokemon.getAttacks().get(3);
        GreenfootImage attack4Image = new GreenfootImage(attack4.getName()+".png");
        
        trainer.getImage().drawImage(attack1Image, 10, 0);
        trainer.getImage().drawImage(attack4Image, 150, 0);
        trainer.getImage().drawImage(attack2Image, 10, 40);
        trainer.getImage().drawImage(attack3Image, 150, 40);*/
    }
    
    public void drawAttack(Trainer trainer, Attack attack, int attackNum) {
      //GreenfootImage attackImage = new GreenfootImage(/*attack.getName()*/"Psychic" +".png");
      int x = 10, y = 0;
      System.out.println("AttackNum: " +attackNum);
      switch(attackNum) {
          case 0 :
            trainer.getImage().drawImage(attackImage1, 10, 40);
            break;
          case 1 :
            trainer.getImage().drawImage(attackImage2, 150, 40);
            break;
          case 2 :
            trainer.getImage().drawImage(attackImage3, 10, 0);
            break;
          case 3 :
            trainer.getImage().drawImage(attackImage4, 150, 0);
            break;
          default :
            break;
        }
        
    }
    
     public void clearAttack() {
      
      System.out.println("Clear the image");
      attackImage1.scale(0,0);
      attackImage2.clear();
      attackImage3.clear();
      attackImage4.clear();
        
    }

    public Attack chooseAttack(Trainer trainer) {
        MouseInfo mouse = Greenfoot.getMouseInfo();
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
        return null;
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