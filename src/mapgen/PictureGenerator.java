/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mapgen;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import static mapgen.TypeCell.Empty;
import static mapgen.TypeCell.Exit;
import static mapgen.TypeCell.Rock;
import static mapgen.TypeCell.Sand;
import static mapgen.TypeCell.Start;

/**
 *
 * @author Honey
 */
public class PictureGenerator {
    public static ImageIcon createPicture(JScrollPane pane, MapCell[][] map){
        int width = pane.getWidth() - pane.getInsets().left - pane.getInsets().right;
        int height = pane.getHeight() - pane.getInsets().top - pane.getInsets().bottom;
        int lenght = height > width ? width : height;
        return new ImageIcon(createBitMap(lenght, map));
    }
    
    private static BufferedImage createBitMap(int lenght, MapCell[][] map){
        int lenghtCell = lenght / map.length;
        BufferedImage bitMap = new BufferedImage(lenght,lenght,2);
        Graphics g = bitMap.getGraphics();
        
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {                
                switch(map[j][i].type){
                    case Start :
                        g.setColor(new Color(125, 255, 125));
                        break;
                    case Exit :
                        g.setColor(new Color(125, 125, 255));
                        break;
                    case Empty :
                        g.setColor(Color.WHITE);
                        break;
                    case Rock :
                        g.setColor(Color.GRAY);
                        break;
                    case Sand :
                        g.setColor(Color.orange);
                        break;
                }                
                g.fillRect(j * lenghtCell + lenghtCell / 4, i * lenghtCell + lenghtCell / 4, lenghtCell / 2, lenghtCell / 2);
            }
        }
        
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if(!map[j][i].neighboringCells.isEmpty()){
                    for (int k = 0; k < map[j][i].neighboringCells.size(); k++) {
                        MapCell neighbor = map[j][i].neighboringCells.get(k);
                        Dot2D mainCell = new Dot2D(j,i);
                        Dot2D neighborCell = neighbor.position;
                        
                        Dot2D dir = Dot2D.difference(mainCell, neighborCell);
                        
                        g.setColor(Color.BLACK);
                        g.drawLine(mainCell.getX() * lenghtCell + lenghtCell / 2, mainCell.getY() * lenghtCell + lenghtCell / 2, neighborCell.getX() * lenghtCell + lenghtCell / 2, neighborCell.getY() * lenghtCell + lenghtCell / 2);
                    }                    
                }
            }
        }
        
        return bitMap;
    }
}
