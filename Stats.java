/**
 * Write a description of class Stats here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Stats  
{
    private int HP;
    private int attack; //may not be needed
    private int defense;
    private double evade;
    private double accuracy;// instance variables - replace the example below with your own

    /**
     * Constructor for objects of class Stats
     */
    public Stats(int HP, int attack, int defense, double evade, double accuracy)
    {
        this.HP = HP;
        this.attack = attack;
        this.defense = defense;
        this.evade = evade;
        this.accuracy = accuracy;
    }
    public int getHP() {
        return this.HP;
    }
    public int getAttack() {
        return this.attack;
    }
    public int getDefense() {
        return this.defense;
    }
    public double getEvade() {
        return this.evade;
    }
    public double getAccuracy() {
        return this.accuracy;
    }
    public void setHP(int v) {
        this.HP = v;
    }
    public void setAttack(int v) {
        this.attack = v;
    }
    public void setDefense(int v) {
        this.defense = v;
    }
    public void setEvade(double v) {
        this.evade = v;
    }
    public void setAccuracy(double v) {
        this.accuracy = v;
    }
}
