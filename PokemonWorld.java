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
    Trainer Ash;
    Trainer Gary;
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

    Boolean AI = true;
    
    public PokemonWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 600, 1); 
        
        ashTurn = true;
        worldWidth = this.getWidth() * this.getCellSize();
        
        //draw wombat somwhere in the first 3 rows
        /*Wombat w1 = new Wombat();
        x = Greenfoot.getRandomNumber(width);
        y = Greenfoot.getRandomNumber(3);
        addObject(w1, x, y);*/
        ArrayList<Pokemon> pokemon = new ArrayList<Pokemon>();
        pokemon.add(new Mewtwo());
        pokemon.add(new Blastoise());
        pokemon.add(new Arcanine());
        pokemon.add(new Dragonite());
        pokemon.add(new Kangaskhan());
        pokemon.add(new Onix());
        pokemon.add(new Marowak());
        pokemon.add(new Pikachu());
        pokemon.add(new Venusaur());
        pokemon.add(new Scyther());
        pokemon.add(new Jigglypuff());
        pokemon.add(new Rapidash());
        pokemon.add(new Lapras());
        pokemon.add(new Voltorb());
        pokemon.add(new Pidgeot());
        pokemon.add(new Alakazam());
        
        /*
         * ArrayList AshPokemon = new ArrayList();
         * ArrayList GaryPokemon = new ArrayList();
         * int ind;
         * for (int i = 0; i < 12; i++) {
         *     ind = (Math.random() * pokemon.size()) % pokemon.size();
         *     if (ashTurn) {
         *         AshPokemon.add(pokemon.remove(ind));
         *         ashTurn = false;
         *     } else {
         *         GaryPokemon.add(pokemon.remove(ind));
         *         ashTurn = true;
         *     }
         * }
         * ashTurn = true;
         */
        
        //Pokemon mewtwo = new Mewtwo();
		Pokemon ashPoke = pokemon.remove(3);
        addObject(ashPoke, 120, 300);
        Pokemon blastoise = new Blastoise();
        addObject(blastoise, 680, 300);
        
        ArrayList AshPokemon = new ArrayList();
        //AshPokemon.add(mewtwo);
		AshPokemon.add(ashPoke);
        ArrayList GaryPokemon = new ArrayList();
        GaryPokemon.add(blastoise);
        
        Ash = new Trainer(AshPokemon, "Ash");
        Gary = new Trainer(GaryPokemon, "Gary");
        addObject(Ash, 160, 475);
        
        attImg1 = new Image();
        attImg2 = new Image();
        attImg3 = new Image();
        attImg4 = new Image();        
        
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
                    } 
                    else if (!AI && !ashTurn && mouse.getClickCount() == 1 && mouse.getX() < worldWidth - 160 && mouse.getX() > worldWidth - 270 && 
                     mouse.getY() > 460 && mouse.getY() < 490) {
                        playerAction = 1;
                        displayAttacks(Gary);
                        System.out.println("Clicked fight!!");
                    } 
                    else if(AI && !ashTurn) {
                        playerAction = 1;
                    }
                    else if(false) { //Choose Pokemon and display them
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
                else if (!AI && !ashTurn && GaryAttack != null) {
                    playerAction = 3;
                    System.out.println("Gary: " + GaryAttack.getName());
                }
                //AI
                else if (AI && !ashTurn) {
                    playerAction = 3;
                }
            }
           // System.out.println("~~");
            if(playerAction == 3) {
                //enemyAttack = Gary.attack(); //Add AI for choosing attacks. This will always choose Hydro
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
                    else {
                        System.out.println(Ash.getCurrentPokemon().getName() + "'s Attack Missed!");
                    }
                }
                else if (!AI){
                    System.out.println("Attacking with: " + GaryAttack.getName());
                    if (isHit(GaryStats, AshStats, GaryAttack)) {
                        GaryDamage = this.getDamage(GaryStats, AshStats, GaryAttack);
                        AshStats.setHP(AshStats.getHP() - (int)GaryDamage);
                    }
                    else {
                        System.out.println(Gary.getCurrentPokemon().getName() + "'s Attack Missed!");
                    }
                }
                else if(AI){
                    if (isHit(GaryStats, AshStats, GaryAttack)) {
                        GaryDamage = this.getDamage(GaryStats, AshStats, GaryAttack);
                        AshStats.setHP(AshStats.getHP() - (int)GaryDamage);
                    }
                    else {
                        System.out.println(Gary.getCurrentPokemon().getName() + "'s Attack Missed!");
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
        ArrayList<Attack> attacks = attackingPokemon.getAttacks();
        
        attImg1.setImage(new GreenfootImage("images/Attacks/" + attacks.get(3).getName().replaceAll("\\s+","") + ".png"));
        attImg2.setImage(new GreenfootImage("images/Attacks/" + attacks.get(2).getName().replaceAll("\\s+","") + ".png"));
        attImg3.setImage(new GreenfootImage("images/Attacks/" + attacks.get(1).getName().replaceAll("\\s+","") + ".png"));
        attImg4.setImage(new GreenfootImage("images/Attacks/" + attacks.get(0).getName().replaceAll("\\s+","") + ".png"));
        drawAttack(trainer);
    }
    
    public void drawAttack(Trainer trainer) {
        addObject(attImg1, trainer.getX() - 55, trainer.getY() + 30);
        addObject(attImg2, trainer.getX() + 55, trainer.getY() + 30);
        addObject(attImg3, trainer.getX() + 55, trainer.getY() - 10);
        addObject(attImg4, trainer.getX() - 55, trainer.getY() - 10);    
    }
    
    public void clearAttack() {
        removeObject(attImg1);
        removeObject(attImg2);
        removeObject(attImg3);
        removeObject(attImg4);
    }

    public Attack chooseAttack(Trainer trainer) {
        if(!AI || trainer.getName() == "Ash") {
            if (Greenfoot.mouseClicked(attImg1)) {
                return trainer.getCurrentPokemon().getAttacks().get(3);
            }
            else if (Greenfoot.mouseClicked(attImg2)) {
                return trainer.getCurrentPokemon().getAttacks().get(2);
            }
            else if (Greenfoot.mouseClicked(attImg3)) {
                return trainer.getCurrentPokemon().getAttacks().get(1);
            }
            else if (Greenfoot.mouseClicked(attImg4)) {
                return trainer.getCurrentPokemon().getAttacks().get(0);
            }
            return null;
        }
        else {
            return trainer.getCurrentPokemon().getAttacks().get(0);
        }
    }
    
    public void displayPokemon(Trainer trainer) {
        
    }
}
