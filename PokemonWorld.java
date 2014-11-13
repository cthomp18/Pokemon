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
    public PokemonWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 600, 1); 
        //draw wombat somwhere in the first 3 rows
        /*Wombat w1 = new Wombat();
        x = Greenfoot.getRandomNumber(width);
        y = Greenfoot.getRandomNumber(3);
        addObject(w1, x, y);*/
    }
    public void act() {
        ArrayList<Attack> MewtwoAttacks = new ArrayList<Attack>(); 
        MewtwoAttacks.add(new Psychic());
        MewtwoAttacks.add(new Psybeam());
        MewtwoAttacks.add(new SandAttack());
        MewtwoAttacks.add(new Tackle());
        Pokemon mewtwo = new Mewtwo(MewtwoAttacks);
        addObject(mewtwo, 120, 300);
        
        ArrayList<Attack> BlastoiseAttacks = new ArrayList<Attack>();
        BlastoiseAttacks.add(new AuroraBeam());
        BlastoiseAttacks.add(new HydroPump());
        BlastoiseAttacks.add(new Slash());
        BlastoiseAttacks.add(new Tackle());
        Pokemon blastoise = new Blastoise(BlastoiseAttacks);
        addObject(blastoise, 680, 300);
        
        ArrayList<Pokemon> AshPokemon = new ArrayList<Pokemon>();
        AshPokemon.add(mewtwo);
        ArrayList<Pokemon> GaryPokemon = new ArrayList<Pokemon>();
        GaryPokemon.add(blastoise);
        
        Trainer Ash = new Trainer(AshPokemon);
        addObject(Ash, 160, 475);
        Trainer Gary = new Trainer(GaryPokemon);
        //addObject(Ash, 160, 475);
        
        MouseInfo mouse = Greenfoot.getMouseInfo();
        
        Attack a1;
        Attack a2;
        String action = "";
        Stats stat1;
        Stats stat2;
        double damage1;
        double damage2;
        while (mewtwo.isAlive() && blastoise.isAlive()) {
            action = Ash.action();
            a1 = Ash.attack();
            action = Gary.action();
            a2 = Gary.attack();
            stat1 = mewtwo.getStats();
            stat2 = blastoise.getStats();
            damage1 = this.getDamage(stat1, stat2, a1);
            damage2 = this.getDamage(stat2, stat1, a2);
            
            if (new String(a1.getName()).equals(new String("Sand Attack"))) {
                stat2.setAccuracy(stat2.getAccuracy() - 0.1);
                if (stat2.getAccuracy() < 0.5) {
                    stat2.setAccuracy(0.5);
                    System.out.println("Accuracy cannot be lowered anymore.");
                }
            }
            else if (isHit(stat1, stat2, a1)) {
                stat1.setHP(stat1.getHP() - (int)damage1);
            }
            
            if (blastoise.isAlive() && isHit(stat2, stat1, a2)) {
                stat2.setHP(stat2.getHP() - (int)damage2);
            }
        }
        
        if (mewtwo.isAlive()) {
            System.out.println("Trainer 1 Wins!");
        }
        else {
            System.out.println("Trainer 2 Wins!");
        }
        Greenfoot.stop();
    }
    
    public double getDamage(Stats s1, Stats s2, Attack a) {
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
