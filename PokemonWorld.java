import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.lang.Math;


/**
 * Write a description of class PokemonWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PokemonWorld extends World
{
    static final int START_TURN = 0;
    static final int CHOOSE_FIGHT = 1;
    static final int POKE_CHANGE = 2;
    static final int CALC_DAMAGE = 3;
    int garyChoice;

    int playerAction = START_TURN; 

    Trainer Ash;
    Trainer Gary;
    GreenfootImage attackImage1;
    Image attImg1;
    GreenfootImage attackImage2;
    Image attImg2;
    GreenfootImage attackImage3;
    Image attImg3;
    GreenfootImage attackImage4;
    Image attImg4;
    Image pokImg1;
    Image pokImg2;
    Image pokImg3;
    Image pokImg4;
    Image pokImg5;
    Image pokImg6;
   
    boolean ashTurn;
    boolean ashPokeFaint;
    int worldWidth;    

    Boolean AI = true;
    
    public PokemonWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 600, 1); 
        
        ashTurn = true;
        worldWidth = this.getWidth() * this.getCellSize();
        
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

        ArrayList<Pokemon> AshPokemon = new ArrayList<Pokemon>();
        ArrayList<Pokemon> GaryPokemon = new ArrayList<Pokemon>();
        int ind;
        for (int i = 0; i < 12; i++) {
            ind = (int)((Math.random() * pokemon.size()) % pokemon.size());
            if (ashTurn) {
                AshPokemon.add(pokemon.remove(ind));
                ashTurn = false;
            } else {
                GaryPokemon.add(pokemon.remove(ind));
                ashTurn = true;
            }
        }
        ashTurn = true;
        ashPokeFaint = false;
        
        addObject(AshPokemon.get(0), 150, 250);
        addObject(GaryPokemon.get(0), 650, 250);
        System.out.println("Ash sends out " + AshPokemon.get(0).getName() + "!");
        System.out.println("Gary sends out " + GaryPokemon.get(0).getName() + "!");
        Ash = new Trainer(AshPokemon, "Ash");
        Gary = new Trainer(GaryPokemon, "Gary");
        garyChoice = Gary.nextMove();
        addObject(Ash, 160, 475);
        
        attImg1 = new Image();
        attImg2 = new Image();
        attImg3 = new Image();
        attImg4 = new Image();
        pokImg1 = new Image();
        pokImg2 = new Image();
        pokImg3 = new Image();
        pokImg4 = new Image();
        pokImg5 = new Image();
        pokImg6 = new Image();
    }
    
    public void act() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        
        Stats AshStats;
        Stats GaryStats;
        double AshDamage;
        double GaryDamage;

        Attack AshAttack = null;
        Attack GaryAttack = null;
        
        int x = 35, y = 400;
        if (playerAction == START_TURN) {
            if (mouse != null) {
                if(ashTurn) {
                    if(mouse.getClickCount() == 1 && mouse.getX() > 50 && mouse.getX() < 160 && mouse.getY() > 460 && mouse.getY() < 490){
                        playerAction = 1;
                        displayAttacks(Ash);
                    }
                    else if(mouse.getClickCount() == 1 && mouse.getX() > 50 && mouse.getX() < 160 && mouse.getY() > 490 && mouse.getY() < 520) {
                        playerAction = 2;
                        displayPokemon(Ash);
                        drawPokemon(x, y);
                    }
                } else {
                    if (!AI && mouse.getClickCount() == 1 && mouse.getX() < worldWidth - 160 && mouse.getX() > worldWidth - 270 && mouse.getY() > 460 && mouse.getY() < 490) {
                        playerAction = 1;
                        displayAttacks(Gary);
                    }
                    else if(!AI && mouse.getClickCount() == 1 && mouse.getX() < worldWidth - 160 && mouse.getX() > worldWidth - 270 && mouse.getY() > 490 && mouse.getY() < 520) {
                        playerAction = 2;
                        x = worldWidth - 325;
                        displayPokemon(Gary);
                        drawPokemon(x, y);
                    }
                    else if (AI) {
                        playerAction = garyChoice;
                    }
               }
            }
        }

        if (playerAction == CHOOSE_FIGHT) {
            if (ashTurn) {
                AshAttack = chooseAttack(Ash);
            } else {
                GaryAttack = chooseAttack(Gary);
            }
            //System.out.println("Choosing attack");
            if(ashTurn && AshAttack != null) {
                playerAction = 3;
                System.out.println(Ash.getCurrentPokemon().getName() + " uses " + AshAttack.getName() + "!");
            }
            else if (!AI && !ashTurn && GaryAttack != null) {
                playerAction = 3;
                System.out.println(Gary.getCurrentPokemon().getName() + " uses " + GaryAttack.getName() + "!");
            }
            //AI
            else if (AI && !ashTurn) {
                playerAction = 3;
                System.out.println(Gary.getCurrentPokemon().getName() + " uses " + GaryAttack.getName() + "!");
            }
        }

        if(playerAction == POKE_CHANGE) {
            int ind;
            Pokemon p, p2;
            
            if (ashTurn)
                ind = choosePokemon(Ash);
            else
                ind = Gary.choosePokemon(Gary);
                
            if (ind != -1) {
                if (ashTurn) {
                    p2 = Ash.getCurrentPokemon();
                    p = Ash.changePokemon(ind);
                    if (p == null)
                        System.out.println(Ash.getAllPokemon().get(ind).getName() + " has fainted! Choose another!");
                    else {
                        System.out.println("Ash brings back " + p2.getName() + " and takes out " + p.getName() + "!");
                        removeObject(p2);
                        addObject(p, 150, 250);
                        
                        clearPokemon();
                        if (ashPokeFaint) {
                            ashPokeFaint = false;
                            addObject(Ash, 160, 475);
                        } else {
                            removeObject(Ash);
                            ashTurn = false;
                            if (!AI)
                                addObject(Gary, worldWidth - 160, 475);
                        }
                        playerAction = 0;
                    }
                } else {
                    p2 = Gary.getCurrentPokemon();
                    p = Gary.changePokemon(ind);
                    if (p == null)
                        System.out.println(Gary.getAllPokemon().get(ind).getName() + " has fainted! Choose another!");
                    else {
                        System.out.println("Gary brings back " + p2.getName() + " and takes out " + p.getName() + "!");
                        removeObject(p2);
                        addObject(p, 650, 250);
                        
                        clearPokemon();
                        removeObject(Gary);
                        ashTurn = true;
                        addObject(Ash, 160, 475);
                        playerAction = 0;
                    }
                }
            }
            
        }
        
        if(playerAction == CALC_DAMAGE) {
            
            if (ashTurn) 
                calculateDamage(Ash, Gary, AshAttack);
            else 
                calculateDamage(Gary, Ash, GaryAttack);

            playerAction = 0;
        
            clearAttack();  
            if (ashTurn) {
                removeObject(Ash);
                ashTurn = false;
            } else {
                removeObject(Gary);
                ashTurn = true;
            }
            AshStats = Ash.getCurrentPokemon().getStats();
            GaryStats = Gary.getCurrentPokemon().getStats();
        
            if (AshStats.getHP() <= 0) {
                System.out.println(Ash.getCurrentPokemon().getName() + " has fainted!");
                Ash.getCurrentPokemon().getImage().clear();
                if (blackout(Ash)) {
                    System.out.println("Gary Wins!");
                    Greenfoot.stop();
                } else {
                    ashPokeFaint = true;
                    playerAction = 2;
                    displayPokemon(Ash);
                    drawPokemon(x, y);
                }
            }
            else if (GaryStats.getHP() <= 0) {
                System.out.println(Gary.getCurrentPokemon().getName() + " has fainted!");
                Gary.getCurrentPokemon().getImage().clear();
                if (blackout(Gary)) {
                    System.out.println("Ash Wins!");
                    Greenfoot.stop();
                } else {
                    playerAction = 2;
                    if (!AI) {
                        x = worldWidth - 325;
                        displayPokemon(Gary);
                        drawPokemon(x, y);
                    }
                }
            }
            else {
                if (ashTurn)
                    addObject(Ash, 160, 475);
                else
                    if (!AI)
                        addObject(Gary, worldWidth - 160, 475);
            }
            garyChoice = Gary.nextMove();
        }
    }
    
    public int getDamage(Stats s1, Stats s2, Attack a) {
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
    
    public void displayPokemon(Trainer trainer) {
        ArrayList<Pokemon> pokemon = trainer.getAllPokemon();
        GreenfootImage img = new GreenfootImage("images/" + pokemon.get(0).getName() + "Sprite.png");
        img.scale(60,60);
        pokImg1.setImage(img);
        img = new GreenfootImage("images/" + pokemon.get(1).getName() + "Sprite.png");
        img.scale(60,60);
        pokImg2.setImage(img);
        img = new GreenfootImage("images/" + pokemon.get(2).getName() + "Sprite.png");
        img.scale(60,60);
        pokImg3.setImage(img);
        img = new GreenfootImage("images/" + pokemon.get(3).getName() + "Sprite.png");
        img.scale(60,60);
        pokImg4.setImage(img);
        img = new GreenfootImage("images/" + pokemon.get(4).getName() + "Sprite.png");
        img.scale(60,60);
        pokImg5.setImage(img);
        img = new GreenfootImage("images/" + pokemon.get(5).getName() + "Sprite.png");
        img.scale(60,60);
        pokImg6.setImage(img);
    }
    
    public void drawAttack(Trainer trainer) {
        attImg1.getImage().scale(attImg1.getImage().getWidth() - 25,attImg1.getImage().getHeight());
        attImg2.getImage().scale(attImg2.getImage().getWidth() - 25,attImg2.getImage().getHeight());
        attImg3.getImage().scale(attImg3.getImage().getWidth() - 25,attImg3.getImage().getHeight());
        attImg4.getImage().scale(attImg4.getImage().getWidth() - 25,attImg4.getImage().getHeight());
        addObject(attImg1, trainer.getX() - 65, trainer.getY() + 30);
        addObject(attImg2, trainer.getX() + 70, trainer.getY() + 30);
        addObject(attImg3, trainer.getX() + 70, trainer.getY() - 10);
        addObject(attImg4, trainer.getX() - 65, trainer.getY() - 10);    
    }
    
    public void drawPokemon(int x, int y) {
        addObject(pokImg1, x, y);
        addObject(pokImg2, x += 60, y);
        addObject(pokImg3, x += 60, y);
        addObject(pokImg4, x += 60, y);
        addObject(pokImg5, x += 60, y);
        addObject(pokImg6, x += 60, y);
    }
    
    public void clearAttack() {
        removeObject(attImg1);
        removeObject(attImg2);
        removeObject(attImg3);
        removeObject(attImg4);
    }
    
    public void clearPokemon() {
        removeObject(pokImg1);
        removeObject(pokImg2);
        removeObject(pokImg3);
        removeObject(pokImg4);
        removeObject(pokImg5);
        removeObject(pokImg6);
    }

    public Attack chooseAttack(Trainer trainer) {
        if(!AI || ashTurn) {
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
            return trainer.getAttackChoice(Ash.getCurrentPokemon(), Gary.getCurrentPokemon());
        }
    }
    
    public int choosePokemon(Trainer trainer) {
        double rand;
        int r = 0;
        if(!AI || ashTurn) {
            if (Greenfoot.mouseClicked(pokImg1)) {
                return 0;
            }
            else if (Greenfoot.mouseClicked(pokImg2)) {
                return 1;
            }
            else if (Greenfoot.mouseClicked(pokImg3)) {
                return 2;
            }
            else if (Greenfoot.mouseClicked(pokImg4)) {
                return 3;
            }
            else if (Greenfoot.mouseClicked(pokImg5)) {
                return 4;
            }
            else if (Greenfoot.mouseClicked(pokImg6)) {
                return 5;
            }
        } 
        return -1;
    }
    
    public boolean blackout(Trainer trainer) {
        ArrayList<Pokemon> p = trainer.getAllPokemon();
        for (int i = 0; i < p.size(); i++) {
            if (p.get(i).isAlive())
                return false;
        }
        return true;
    }

    public void calculateDamage(Trainer local, Trainer enemy, Attack attack) {
        Stats localStats = local.getCurrentPokemon().getStats();
        Stats enemyStats = enemy.getCurrentPokemon().getStats();
        int localDamage = this.getDamage(localStats, enemyStats, attack);
        String enemyPokemon = enemy.getCurrentPokemon().getName();
        String localPokemon = local.getCurrentPokemon().getName();

        if (new String(attack.getName()).equals(new String("Sand Attack"))) {
            enemyStats.setAccuracy(enemyStats.getAccuracy() - 0.1);
            if (enemyStats.getAccuracy() < 0.5) {
                enemyStats.setAccuracy(0.5);
                System.out.println("Accuracy cannot be lowered anymore!");
            }
        }
        else if (isHit(localStats, enemyStats, attack)) {
            if (attack.getType().isStrong(enemy.getCurrentPokemon().getType())) {
                localDamage *= 2;
                local.superEffective(enemyPokemon, attack.getType().getName());
				enemy.notEffective(localPokemon, attack.getType().getName());
                System.out.println("It's super effective!");
            }
            else if (attack.getType().isWeak(enemy.getCurrentPokemon().getType())) {
                localDamage = (int)(localDamage * 0.5);
                if (!(new String(attack.getName()).equals(new String("Sand Attack")))) {
                    System.out.println("It's not very effective...");
					local.notEffective(enemyPokemon, attack.getType().getName());
					enemy.superEffective(localPokemon, attack.getType().getName());
				}
            }
            enemyStats.setHP(Math.max(0, enemyStats.getHP() - (int)localDamage));
        }
        else {
            System.out.println(local.getCurrentPokemon().getName() + "'s Attack Missed!");
        }
        //HP printing purposes
        if(local.getName() == "Ash") {
            System.out.println(local.getCurrentPokemon().getName() + " HP : " + localStats.getHP());
            System.out.println(enemy.getCurrentPokemon().getName() + " HP : " + enemyStats.getHP());
        }
        else {
            System.out.println(enemy.getCurrentPokemon().getName() + " HP : " + enemyStats.getHP());
            System.out.println(local.getCurrentPokemon().getName() + " HP : " + localStats.getHP());
        }
    }
}
