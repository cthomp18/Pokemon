import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Image here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Image extends Actor
{
    private GifImage gifImg;
    private boolean isGif;
    public boolean toBeDrawn = true;
    public Image() {
        isGif = false;
    }
    
    public void setImgGif(GifImage gif) {
        isGif = true;
        gifImg = gif;
        setImage(gif.getCurrentImage());
    }
    
    public void setIsGif() {
        isGif = false;
    }
    
    public void setDrawStatus(boolean status) {
        toBeDrawn = status;
    }
    
    public boolean getDrawStatus() {
        return toBeDrawn;
    }
    
   
    /**
     * Act - do whatever the Image wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
        if (isGif) {
            setImage(gifImg.getCurrentImage());
        }
    }    
}
