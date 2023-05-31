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
    
    public static MapCell[][] generate() throws Exception{
        cells = new ArrayList<MapCell>();
        map = new MapCell[5][5];
        
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                map[j][i] = new MapCell(j, i);
            }
        }
        
        MapCell start = map[0][ran.nextInt(0, 5)];
        start.type = TypeCell.Start;
        cells.add(start);
        
        for (int i = 0; i < start.ways.size(); i++) {
           System.out.print(start.ways.get(i).getStatus() + "\n");
        }       

        int countIteration = 1;
        int allIteration = 6;
        int limiter = 0;
        int countCells = 0;
        for (int i = 0; i < allIteration; i++) {
            for (int j = 0; j < countIteration; j++) {
                //cells.add(addNearestNeighbor(TypeCell.values()[ran.nextInt(3, 6)]));
                cells.add(addNearestNeighbor(TypeCell.Rock));
                
                limiter = calculateFreeCells();
                countCells = (map.length * map.length) - 1 - limiter;
            }
            countIteration = limiter < countCells ? limiter : countCells;
            
            System.out.print("Pull\t\t" + map.length * map.length + "\n"
                + "limiter\t\t" + limiter + "\n"
                + "all cells\t" + countCells + "\n\n");
            
            if(i == allIteration - 1){
                cells.add(addNearestNeighbor(TypeCell.Exit));
            }
        }
        
        do{
        cells = createListEmptyCells();
        
        for (int i = 0; i < cells.size(); i++) {        
            for(PossibleWay way: cells.get(i).ways){
                if(cells.size() != 0){
                    MapCell neighbour = getNeighbour(cells.get(i), way.getDirection());
                
                    int chance = 4 - neighbour.neighboringCells.size();
                    if(chance != 4){
                        if(chance > ran.nextInt(4)){
                            cells.get(i).type = TypeCell.Sand;
                            cells.get(i).registerNeighbours(neighbour);
                            cells.remove(i);
                            i = 0;
                            break;
                        }
                    }
                }
            }
        }
        }while(!cells.isEmpty());
                        
            
            
                        
        
        
        
        
        
        
        return map;
    }
    private static ArrayList<MapCell> createListEmptyCells(){
        ArrayList<MapCell> cells = new ArrayList<MapCell>();
        for (int i = 0; i < map.length; i++)
            for (int j = 0; j < map.length; j++)
                if(map[j][i].type == TypeCell.Empty)
                    cells.add(map[j][i]);
        return cells;
    }
    
    private static int calculateFreeCells(){
        int value = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if(map[j][i].type == TypeCell.Empty)
                    value++;
            }
        }
        return value;
    }
    
    private static MapCell addNearestNeighbor(TypeCell type) throws Exception{
        MapCell cell;
        PossibleDirection dir;
        MapCell neighbour;
        int counter = 0;        
            do{
                
                cell = selectCell();
                dir = selectDirection(cell);
                neighbour = getNeighbour(cell, dir); 
                if(counter++ == 200)
                        throw new Exception("Good Neighbor does not exist ");
            }while(type == TypeCell.Exit && neighbour.type == TypeCell.Start);
        if(neighbour.type != TypeCell.Start)
            neighbour.type = type;
        tieNeighbors(cell, neighbour);
        
        return neighbour;
    }
    private static MapCell selectCell() throws Exception{
        if(!cells.isEmpty()){
            MapCell cell;
            int counter = 0;
            int min = 1;
            do {
                cell = cells.get(ran.nextInt(cells.size()));
                if(counter++ == 100){
                    min = 0;                    
                }
                if(counter == 200)
                        throw new Exception("Good choises does not exist ");
            }while(!isGoodChoise(cell, min));
            return cell;
        }
        else{
            Exception ex = new Exception("Good Cell is not find");
            throw ex;
        }        
    }    
    private static boolean isGoodChoise(MapCell cell, int min){
        int freeWays = 0;
        for(PossibleWay way : cell.ways){
            if(way.getStatus() == StatusCell.No_Conections)
                freeWays++;
        }
        if(freeWays == 0)
            return false;
        else
            return min < ran.nextInt(freeWays + 1);
    }
    
    private static PossibleDirection selectDirection(MapCell cell)throws Exception{
        ArrayList<PossibleWay> freeWays = new ArrayList<PossibleWay>();
        for(PossibleWay way : cell.ways)
            if(way.getStatus() == StatusCell.No_Conections)
                freeWays.add(way);
        if(!freeWays.isEmpty()){
            return freeWays.get(ran.nextInt(freeWays.size())).getDirection();
        }
        throw new Exception("No free ways");
    }
    private static MapCell getNeighbour(MapCell cell, PossibleDirection dir)throws Exception{       
        switch (dir){
            case Left :
                return map[cell.position.getX() - 1][cell.position.getY()];
            case Up :
                return map[cell.position.getX()][cell.position.getY() - 1];
            case Right :
                return map[cell.position.getX() + 1][cell.position.getY()];
            case Down :
                return map[cell.position.getX()][cell.position.getY() + 1];
            case default :
                throw new Exception("Failed to get neighbor");
        }
    }    
    private static void tieNeighbors(MapCell cell1, MapCell cell2){
        cell1.registerNeighbours(cell2);
    }
}
