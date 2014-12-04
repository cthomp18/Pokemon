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
    static final int TOURNAMENT_LENGTH = 3;
    static final int START_TURN = 0;
    static final int CHOOSE_FIGHT = 1;
    static final int POKE_CHANGE = 2;
    static final int CALC_DAMAGE = 3;
    int garyChoice;

    int playerAction = START_TURN; 
    
    int ashScore;
    int garyScore;
    
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
    AttackAnimation attGif;
    Image pokImg1;
    Image pokImg2;
    Image pokImg3;
    Image pokImg4;
    Image pokImg5;
    Image pokImg6;
    //GifImage gif;
    Attack curAttack;
    
    Image hpImg1;
    Image hpImg2;
    Image hpImg3;
    Image hpImg4;
    Image hpImg5;
    Image hpImg6;
    Image AshHP;
    Image GaryHP;
    Terrain terrain;
    boolean ashTurn;
    boolean ashPokeFaint;
    int worldWidth;    
    int time;
    int matches;
    int round;

    Boolean AI = true;
    
    public PokemonWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 600, 1); 
        
        ashTurn = true;
        worldWidth = this.getWidth() * this.getCellSize();
        
        newRound();
        
        time = 0;
        ashTurn = true;
        ashPokeFaint = false;
        
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
        attGif = new AttackAnimation();
        curAttack = null;
        
        hpImg1 = new Image();
        hpImg2 = new Image();
        hpImg3 = new Image();
        hpImg4 = new Image();
        hpImg5 = new Image();
        hpImg6 = new Image();
        AshHP= new Image();
        GaryHP = new Image();
        
        setPaintOrder(AttackAnimation.class, Pokemon.class);
        
        ashScore = 0;
        garyScore = 0;
        matches = TOURNAMENT_LENGTH;
        round = 0;
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
        
        GreenfootImage HP1 = new GreenfootImage("images/HPBar.png");
        GreenfootImage HP2 = new GreenfootImage("images/HPBar.png");
        if(Ash.getCurrentPokemon().getStats().getHP() > 0) {
            //System.out.println("Ash's current Pokemon's HP: " + Ash.getCurrentPokemon().getStats().getHP());
            HP1.scale(Ash.getCurrentPokemon().getStats().getHP()*2, 75);
            AshHP.setImage(HP1);
            
            addObject(AshHP, 100, 100);
        } else {
            removeObject(AshHP);
        }
        if(Gary.getCurrentPokemon().getStats().getHP() > 0) {
            HP2.scale(Gary.getCurrentPokemon().getStats().getHP()*2, 75);
            GaryHP.setImage(HP2);
            
            addObject(GaryHP, 600, 100);
        } else {
            removeObject(GaryHP);
        }
        
        if (playerAction == START_TURN) {
            if (mouse != null) {
                if(ashTurn) {
                    if(mouse.getClickCount() == 1 && mouse.getX() > 50 && mouse.getX() < 160 && mouse.getY() > 460 && mouse.getY() < 490){
                        playerAction = CHOOSE_FIGHT;
                        displayAttacks(Ash);
                    }
                    else if(mouse.getClickCount() == 1 && mouse.getX() > 50 && mouse.getX() < 160 && mouse.getY() > 490 && mouse.getY() < 520) {
                        playerAction = POKE_CHANGE;
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
                curAttack = AshAttack;
            } else {
                GaryAttack = chooseAttack(Gary);
                curAttack = GaryAttack;
            }
            
            //if (curAttack == null)
                //System.out.println("k");
                
            if(ashTurn && AshAttack != null) {
                System.out.println(Ash.getCurrentPokemon().getName() + " uses " + curAttack.getName() + "!");
            }
            else if (!AI && !ashTurn && GaryAttack != null) {
                System.out.println(Gary.getCurrentPokemon().getName() + " uses " + curAttack.getName() + "!");
            }
            //AI
            else if (AI && !ashTurn) {
                System.out.println(Gary.getCurrentPokemon().getName() + " uses " + curAttack.getName() + "!");
            }
            
            AshStats = Ash.getCurrentPokemon().getStats();
            GaryStats = Gary.getCurrentPokemon().getStats();
            if (ashTurn && curAttack != null) {
                if (isHit(AshStats, GaryStats, curAttack)) {
                    playerAction = 5;
                }
                else {
                    System.out.println(Ash.getCurrentPokemon().getName() + "'s Attack Missed!");
                    playerAction = 6;
                }
            } else if (curAttack != null) {
                if (isHit(GaryStats, AshStats, curAttack)) {
                    playerAction = 5;
                }
                else {
                    System.out.println(Gary.getCurrentPokemon().getName() + "'s Attack Missed!");
                    playerAction = 6;
                }
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
                        Gary.opponentChangedTo(p);
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
                calculateDamage(Ash, Gary, curAttack);
            else 
                calculateDamage(Gary, Ash, curAttack);
            playerAction = 6;
        }
        if (playerAction == 5) {
            //gif = new GifImage("images/Attacks/" + curAttack.getName().replaceAll("\\s+","") + ".gif");
            if (time == 0) {
                attGif = new AttackAnimation();
                attGif.setImage("images/Attacks/" + curAttack.getName().replaceAll("\\s+","") + ".gif");
                if (ashTurn)
                    addObject(attGif, 650, 250);
                else
                    addObject(attGif, 150, 250);
            }
            if (time > 75) {
                removeObject(attGif);
                playerAction = CALC_DAMAGE;
            }
            time++;
        }
        if(playerAction == 6) {
            AshStats = Ash.getCurrentPokemon().getStats();
            GaryStats = Gary.getCurrentPokemon().getStats();
            
            clearAttack();  
            if (ashTurn) {
                removeObject(Ash);
                ashTurn = false;
            } else {
                removeObject(Gary);
                ashTurn = true;
            }
            
            time = 0;
    
            playerAction = 0;
        
            if (AshStats.getHP() <= 0) {
                System.out.println(Ash.getCurrentPokemon().getName() + " has fainted!");
                Ash.getCurrentPokemon().getImage().clear();
                if (blackout(Ash)) {
                    System.out.println("Gary Wins!");
                    round++;
                    garyScore++;
                    if (round < matches) {
                        newRound();
                    } else {
                        if (ashScore > garyScore)
                            System.out.println("Ash Wins " + ashScore + " - " + garyScore + "!!!");
                        else
                            System.out.println("Gary Wins " + ashScore + " - " + garyScore + "!!!");
                        Greenfoot.stop();
                    }
                } else {
                    ashPokeFaint = true;
                    playerAction = 2;
                    displayPokemon(Ash);
                    drawPokemon(x, y);
                }
            }
            else if (GaryStats.getHP() <= 0) {
                Gary.saveOpponent(Ash.getCurrentPokemon().getName());
                System.out.println(Gary.getCurrentPokemon().getName() + " has fainted!");
                Gary.getCurrentPokemon().getImage().clear();
                if (blackout(Gary)) {
                    round++;
                    garyScore++;
                    if (round < matches) {
                        newRound();
                    } else {
                        if (ashScore > garyScore)
                            System.out.println("Ash Wins " + ashScore + " - " + garyScore + "!!!");
                        else
                            System.out.println("Gary Wins " + ashScore + " - " + garyScore + "!!!");
                        Greenfoot.stop();
                    }
                } else {
                    playerAction = POKE_CHANGE;
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
                else if (!AI)
                    addObject(Gary, worldWidth - 160, 475);
            }
        }
        garyChoice = Gary.nextMove();
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
        else {
           if (attack.getType().isStrong(enemy.getCurrentPokemon().getType())) {
                System.out.println("It's super effective!");
                localDamage *= 2;
                local.superEffective(enemyPokemon, attack.getType().getName());
                enemy.opponentSuperEffective(localPokemon);
            }
            else if (attack.getType().isWeak(enemy.getCurrentPokemon().getType())) {
                localDamage = (int)(localDamage * 0.5);
                if (!(new String(attack.getName()).equals(new String("Sand Attack")))) {
                    System.out.println("It's not very effective...");
                    local.notEffective(enemyPokemon, attack.getType().getName());
                    enemy.opponentNotEffective(localPokemon);
                }
            }
            
            if (terrain.isStrong(attack.getType().getName())) {
                localDamage = (int)(localDamage * 1.25);
                System.out.println("The Terrain helps this attack!!");
            } else if (terrain.isWeak(attack.getType().getName())) {
                localDamage = (int)(localDamage * 0.75);
                System.out.println("The Terrain suppresses this attack...");
            }
            
            if (Math.random() * 100 <= 6.25) {
                localDamage = (int)(localDamage * 1.75);
                System.out.println("Critical Hit!!");
            }
            
            if (attack.getType().getName().equals(local.getCurrentPokemon().getType().getName())) {
                localDamage = (int)(localDamage * 1.25);
            }
            enemyStats.setHP(Math.max(0, enemyStats.getHP() - (int)localDamage));
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
        
        img = new GreenfootImage("images/HPBar.png");
        if(pokemon.get(0).getStats().getHP() > 0) {
            img.scale((int)(pokemon.get(0).getStats().getHP()*0.5), 10);
            hpImg1.setImage(img);
        }
        img = new GreenfootImage("images/HPBar.png");
        if(pokemon.get(1).getStats().getHP() > 0) {
            img.scale((int)(pokemon.get(1).getStats().getHP()*0.5), 10);
            hpImg2.setImage(img);
        }
        img = new GreenfootImage("images/HPBar.png");
        if(pokemon.get(2).getStats().getHP() > 0) {
            img.scale((int)(pokemon.get(2).getStats().getHP()*0.5), 10);
            hpImg3.setImage(img);
        }
        img = new GreenfootImage("images/HPBar.png");
        if(pokemon.get(3).getStats().getHP() > 0) {
            img.scale((int)(pokemon.get(3).getStats().getHP()*0.5), 10);
            hpImg4.setImage(img);
        }
        img = new GreenfootImage("images/HPBar.png");
        if(pokemon.get(4).getStats().getHP() > 0) {
            img.scale((int)(pokemon.get(4).getStats().getHP()*0.5), 10);
            hpImg5.setImage(img);
        }
        img = new GreenfootImage("images/HPBar.png");
        if(pokemon.get(5).getStats().getHP() > 0) {
            img.scale((int)(pokemon.get(5).getStats().getHP()*0.5), 10);
            hpImg6.setImage(img);
        }
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
        addObject(hpImg1, x, y + 20);
        addObject(pokImg2, x += 60, y);
        addObject(hpImg2, x, y + 20);
        addObject(pokImg3, x += 60, y);
        addObject(hpImg3, x, y + 20);
        addObject(pokImg4, x += 60, y);
        addObject(hpImg4, x, y + 20);
        addObject(pokImg5, x += 60, y);
        addObject(hpImg5, x, y + 20);
        addObject(pokImg6, x += 60, y);
        addObject(hpImg6, x, y + 20);
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
        removeObject(hpImg1);
        removeObject(hpImg2);
        removeObject(hpImg3);
        removeObject(hpImg4);
        removeObject(hpImg5);
        removeObject(hpImg6);
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
    
    public void newRound() {
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
        
        addObject(AshPokemon.get(0), 150, 250);
        addObject(GaryPokemon.get(0), 650, 250);
        System.out.println("Ash sends out " + AshPokemon.get(0).getName() + "!");
        System.out.println("Gary sends out " + GaryPokemon.get(0).getName() + "!");
        if (round == 0) {
            Ash = new Trainer(AshPokemon, "Ash");
            Gary = new CameronTrainer(GaryPokemon, "Gary");
        } else {
            Ash.replacePokemon(AshPokemon);
            Gary.replacePokemon(GaryPokemon);
        }
        garyChoice = Gary.nextMove();
        addObject(Ash, 160, 475);
        garyChoice = Gary.nextMove();
        addObject(Ash, 160, 475);
        
        ArrayList<String> s = new ArrayList<String>();
        ArrayList<String> w = new ArrayList<String>();
        //Terrain
        ind = (int)((Math.random() * 6) % 6);
        if (ind == 0) {
            setBackground(new GreenfootImage("images/Windy.jpg"));
            s.add("Flying");
            w.add("Psychic");
            terrain = new Terrain(s, w);
        } else if (ind == 1) {
            setBackground(new GreenfootImage("images/Rainy.jpg"));
            s.add("Water");
            s.add("Electric");
            w.add("Fire");
            terrain = new Terrain(s, w);
        } else if (ind == 2) {
            setBackground(new GreenfootImage("images/Lightning...y.jpg"));
            s.add("Electric");
            w.add("Water");
            w.add("Fire");
            terrain = new Terrain(s, w);
        } else if (ind == 3) {
            setBackground(new GreenfootImage("images/Foggy.jpg"));
            w.add("Flying");
            s.add("Psychic");
            terrain = new Terrain(s, w);
        } else if (ind == 4) {
            setBackground(new GreenfootImage("images/Sunny.jpg"));
            s.add("Grass");
            s.add("Fire");
            w.add("Water");
            terrain = new Terrain(s, w);
        } else if (ind == 5) {
            setBackground(new GreenfootImage("images/Sandy.jpg"));
            s.add("Rock");
            w.add("Elecric");
            terrain = new Terrain(s, w);
        }
    }
}
