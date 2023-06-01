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
    FirstGeneration,
    Empty,
    Sand,
    Rock,
    Water    
}

public class MapCell {
    public MapCell(int posX, int posY, int widthMap, int heightMap){
        type = TypeCell.Empty;
        position = new Dot2D(posX, posY);

        ways = new ArrayList<PossibleWay>();
        if(posX != 0)
            addNewWay(PossibleDirection.Left);
        if(posX != widthMap - 1)
            addNewWay(PossibleDirection.Right);
        if(posY != 0)            
            addNewWay(PossibleDirection.Up);
        if(posY != heightMap - 1)
            addNewWay(PossibleDirection.Down);
        countFreeWays = ways.size();

        neighboringCells = new ArrayList<MapCell>();
    }

    ArrayList<PossibleWay> ways;
    TypeCell type;    
    Dot2D position;
    ArrayList<MapCell>neighboringCells;
    
    private int countFreeWays;

    private void addNewWay(PossibleDirection dir){
        ways.add(new PossibleWay(dir));
    }

    public void paveTheWay(PossibleDirection dir){
        for(PossibleWay way : ways){
            if(way.getDirection() == dir)
                way.paveTheWay();
        }
        countFreeWays--;
    }

    public int getCountFreeWays(){
        return countFreeWays;
    }
    public ArrayList<PossibleWay> getFreeWays(){
        ArrayList<PossibleWay> freeWays = new ArrayList<>();
        for(PossibleWay way : ways)
            if(way.getStatus() == StatusWay.No_Conections)
                freeWays.add(way);
        return freeWays;
    }
    
    public ArrayList<PossibleWay> getConnectedWays(){
        ArrayList<PossibleWay> connectedWays = new ArrayList<>();
        for(PossibleWay way : ways)
            if(way.getStatus() == StatusWay.Connected)
                connectedWays.add(way);
        return connectedWays;
    }
    
    public void registerNeighbours(MapCell neighbour){
        addNeighbour(neighbour);
        neighbour.addNeighbour(this);
    }
    
    private void addNeighbour(MapCell neighbour){        
        if(!isAlreadyNeighbor(neighbour)){
            neighboringCells.add(neighbour);            
            PossibleDirection dir = calculateDirection(this, neighbour);
            paveTheWay(dir);            
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
                return PossibleDirection.Up;
            else return PossibleDirection.Down;
        }
        else{
            if(dot.getX() > 0)
                return PossibleDirection.Left;
            else return PossibleDirection.Right;
        }
    }

    public void printInfo(){
        System.out.print(position.print() + "\tType\t:\t" + String.valueOf(type)+"\n"
                + "Number Of Ways\t:\t" + String.valueOf(ways.size()) +"\n"
                        + "Number Of Neighbor\t" + String.valueOf(neighboringCells.size()) + "\n");
        
        for (int i = 0; i < neighboringCells.size(); i++) {
            System.out.print(neighboringCells.get(i).position.print() + "\t" + String.valueOf(calculateDirection(this, neighboringCells.get(i))) + "\n");
        }
        
        for (int i = 0; i < ways.size(); i++) {
            System.out.print(String.valueOf(ways.get(i).getDirection()) + "\t\t" + String.valueOf(ways.get(i).getStatus()) + "\n");
        }
    }
}