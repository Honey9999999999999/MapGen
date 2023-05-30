/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mapgen;

/**
 *
 * @author Honingard
 */
class Dot2D {
    public Dot2D(){
        x = 0;
        y = 0;
    }
    public Dot2D(int x, int y){
        this.x = x;
        this.y = y;
    }
        
    private int x;
    private int y;
    
    
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    
    public static Dot2D difference(Dot2D a, Dot2D b){
        return new Dot2D(a.x - b.x, a.y - b.y);
    }
    
    public String print(){
        return new String("[" + x + ";" + y + "]");
    }
}