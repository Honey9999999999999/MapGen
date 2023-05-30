/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mapgen;
import java.util.ArrayList;

/**
 *
 * @author Honey
 */
enum TypeCell{
    Start,
    Exit,
    Sand,
    Rock,
    Water,
    Empty
}
enum PossibleDirection{
    Left,
    Up,
    Right,
    Down
}
enum StatusCell{
    No_Conections,
    Connected    
}

class PossibleWay{
    public PossibleWay(PossibleDirection direction){
        this.direction = direction;
        status = StatusCell.No_Conections;
    }
    
    private PossibleDirection direction;
    private StatusCell status;

    public PossibleDirection getDirection(){
        return direction;
    }    
    public StatusCell getStatus(){
        return status;
    }

    private void setDirection(PossibleDirection direction){
        this.direction = direction;
    }
    private void setStatus(StatusCell status){
        this.status = status;
    }
    
    public void paveTheWay(PossibleDirection dir){
        status = StatusCell.Connected;
    }
}



public class MapCell {
    public MapCell(int posX, int posY){
        type = TypeCell.Empty;
        position = new Dot2D(posX, posY);

        ways = new ArrayList<PossibleWay>();
        if(posX != 0)
            addNewWay(PossibleDirection.Left);
        if(posX != 4)
            addNewWay(PossibleDirection.Right);
        if(posY != 0)            
            addNewWay(PossibleDirection.Up);
        if(posY != 4)
            addNewWay(PossibleDirection.Down);

        neighboringCells = new ArrayList<MapCell>();
    }

    ArrayList<PossibleWay> ways;
    TypeCell type;    
    Dot2D position;
    ArrayList<MapCell>neighboringCells;

    private void addNewWay(PossibleDirection dir){
        ways.add(new PossibleWay(dir));
    }

    public void paveTheWay(PossibleDirection dir){
        for(PossibleWay way : ways){
            if(way.getDirection() == dir)
                way.paveTheWay(dir);
        }
    }

    public void addNeighbour(MapCell neighbour){        
        if(!isAlreadyNeighbor(neighbour)){
            neighboringCells.add(neighbour);
            neighbour.neighboringCells.add(this);
            PossibleDirection dir = calculateDirection(this, neighbour);
            paveTheWay(dir);
            dir = calculateDirection(neighbour, this);
            neighbour.paveTheWay(dir);
        }
    }
    
    private boolean isAlreadyNeighbor(MapCell neighbour){
        boolean isAlreadyNeighbor = false;
        for(MapCell cell : neighboringCells){
                isAlreadyNeighbor = cell == neighbour;
                if(isAlreadyNeighbor)
                    return isAlreadyNeighbor;
        }
        return isAlreadyNeighbor;
    }
    
    private PossibleDirection calculateDirection(MapCell main, MapCell neighbour){
        Dot2D dot = Dot2D.difference(main.position, neighbour.position);
        
        if(dot.getX() == 0){
            if(dot.getY() > 0)
                return PossibleDirection.Down;
            else return PossibleDirection.Up;
        }
        else{
            if(dot.getX() > 0)
                return PossibleDirection.Right;
            else return PossibleDirection.Left;
        }
    }

    public void printInfo(){
        System.out.print(position.print() + "\tType\t:\t" + String.valueOf(type)+"\n"
                + "Number Of Ways\t:\t" + String.valueOf(ways.size()) +"\n"
                        + "Number Of Neighbor\t" + String.valueOf(neighboringCells.size()) + "\n");
        for (int i = 0; i < ways.size(); i++) {
            System.out.print(String.valueOf(ways.get(i).getDirection()) + "\t\t" + String.valueOf(ways.get(i).getStatus()) + "\n");
        }
    }
}