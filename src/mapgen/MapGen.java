/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mapgen;
import java.util.ArrayList;
import java.util.Random;
import static mapgen.PossibleDirection.Down;
import static mapgen.PossibleDirection.Left;
import static mapgen.PossibleDirection.Right;
import static mapgen.PossibleDirection.Up;

/**
 *
 * @author Honey
 */
public class MapGen {
    private static Random ran = new Random();
    private static ArrayList<MapCell> cells;
    private static MapCell[][] map;
    
    private static PossibleDirection direction;
    private static MapCell neighbour;
    
    public static MapCell[][] generate(){
        cells = new ArrayList<MapCell>();
        map = new MapCell[5][5];
        
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                map[j][i] = new MapCell(j, i);
            }
        }
        
        MapCell start = map[0][ran.nextInt(0, 5)];
        start.type = TypeCell.Start;
           
        for (int i = 0; i < 2; i++) {
            if(tryFindNeighbour(start)){
                if(!isAlreadyNeighbor(neighbour))
                    cells.add(neighbour);
                neighbour.type = TypeCell.Sand;
                start.addNeighbour(neighbour);
            }
        }
        

        int countIteration = ran.nextInt(1, cells.size());
        int allIteration = 12;
        for (int i = 0; i < allIteration; i++) {
            for (int j = 0; j < countIteration; j++) {
               MapCell cell;
                do {
                    cell = cells.get(ran.nextInt(cells.size()));
                }while(!tryFindNeighbour(cell));
                
                if(!isAlreadyNeighbor(neighbour))
                    cells.add(neighbour);    
                if(neighbour.type == TypeCell.Empty)
                    neighbour.type = TypeCell.values()[ran.nextInt(3, 6)];
                    //neighbour.type = TypeCell.Sand;
                cell.addNeighbour(neighbour);
                clearChance();
            }
            if(i == allIteration - 1){
                MapCell cell;
                do {
                    cell = cells.get(ran.nextInt(cells.size()));
                }while(!tryFindFreeNeighbour(cell));
                
                if(!isAlreadyNeighbor(neighbour))
                    cells.add(neighbour);    
                if(neighbour.type == TypeCell.Empty)
                    neighbour.type = TypeCell.Exit;
                cell.addNeighbour(neighbour);
            }
            
            }
        countIteration = ran.nextInt(cells.size());
        
        return map;
    }
    
    private static void clearChance(){
        for (int i = 0; i < cells.size(); i++) {
            for (int j = i + 1; j < cells.size(); j++) {
                if(cells.get(i) == cells.get(j)){
                    cells.remove(j--);
                }
            }            
        }
        
        int countFreeWays = 0;
        for (int i = 0; i < cells.size(); i++) {
            for (int j = 0; j < cells.get(i).ways.size(); j++) {
                if(cells.get(i).ways.get(j).getStatus() == StatusCell.No_Conections)
                    countFreeWays++;
            }
            if(countFreeWays < 2)
                cells.remove(i);
            countFreeWays = 0;
        }
        
        int nativeSize = cells.size();
        for (int i = 0; i < nativeSize; i++) {
            for (int j = 0; j < cells.get(i).ways.size(); j++) {
                if(cells.get(i).ways.get(j).getStatus() == StatusCell.No_Conections){
                    cells.add(cells.get(i));
                }                    
            }
        }
    }
    
    private static boolean isAlreadyNeighbor(MapCell neighbour){
        boolean isAlreadyNeighbor = false;
        for(MapCell cell : cells){
                isAlreadyNeighbor = cell == neighbour;
                if(isAlreadyNeighbor)
                    return isAlreadyNeighbor;
        }
        return isAlreadyNeighbor;
    }
    
    private static boolean tryFindFreeNeighbour(MapCell cell){        
        for (int i = 0; i < 100; i++) {
            tryFindNeighbour(cell);
            if(neighbour.type == TypeCell.Empty){
                return true;
            }
        }
        return false;
    }
    
    private static boolean tryFindNeighbour(MapCell cell){
        if(tryChoiseDirection(cell.ways)){        
            switch (direction){
                case Left :
                    neighbour = map[cell.position.getX() - 1][cell.position.getY()];  
                    return true;
                case Up :
                    neighbour = map[cell.position.getX()][cell.position.getY() - 1];
                    return true;
                case Right :
                    neighbour = map[cell.position.getX() + 1][cell.position.getY()];
                    return true;
                case Down :
                    neighbour = map[cell.position.getX()][cell.position.getY() + 1];
                    return true;

                case default :
                    return false;
            }
        }
        else return false;
    }    
    private static boolean tryChoiseDirection(ArrayList<PossibleWay> ways){
        ArrayList<PossibleWay> freeWays = new ArrayList<PossibleWay>();
        
        for(PossibleWay way : ways)
            if(way.getStatus() == StatusCell.No_Conections)
                freeWays.add(way);
        if(!freeWays.isEmpty()){
            direction = freeWays.get(ran.nextInt(freeWays.size())).getDirection();
            return true;
        }
        return false;
    }    
}
