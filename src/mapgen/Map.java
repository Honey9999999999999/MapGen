/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mapgen;

/**
 *
 * @author Honingard
 */
public class Map {
    public Map(int width, int height){
        this.width = width;
        this.height = height;
        size = width * height;
        
        cells = new MapCell[width][height];
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[j][i] = new MapCell(j, i, width, height);
            }
        }
    }
    
    public MapCell[][] cells;
    public final int width;
    public final int height;
    public final int size;
    
    public void generate() throws Exception{
        cells = MapGen.generate(width, height);
    }
    
    public MapCell getCell(Dot2D dot){
        return cells[dot.getX()][dot.getY()];
    }
    public MapCell getCell(int x, int y){
        return cells[x][y];
    }
    
    public void printInfo(){
        for (int i = 0; i < height; i++) {
            for (MapCell[] cell : cells) {
                cell[i].printInfo();
                System.out.print("\n");
            }
        }
    }
}
