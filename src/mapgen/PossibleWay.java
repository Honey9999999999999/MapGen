/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mapgen;

/**
 *
 * @author Honingard
 */

enum PossibleDirection{
    Left,
    Up,
    Right,
    Down
}
enum StatusWay{
    No_Conections,
    Connected    
}

public class PossibleWay {
    public PossibleWay(PossibleDirection direction){
        this.direction = direction;
        status = StatusWay.No_Conections;
    }
    
    private PossibleDirection direction;
    private StatusWay status;

    public PossibleDirection getDirection(){
        return direction;
    }    
    public StatusWay getStatus(){
        return status;
    }

    private void setDirection(PossibleDirection direction){
        this.direction = direction;
    }
    private void setStatus(StatusWay status){
        this.status = status;
    }
    
    public void paveTheWay(){
        status = StatusWay.Connected;
    }
}
