/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mapgen;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

/**
 *
 * @author Honey
 */
public class PictureGenerator {
    public static ImageIcon createPicture(JScrollPane pane, List<MapCell> map){
        int width = pane.getWidth() - pane.getInsets().left - pane.getInsets().right;
        int height = pane.getHeight() - pane.getInsets().top - pane.getInsets().bottom;
        int lenght = height > width ? width : height;
        return new ImageIcon(createBitMap(lenght, map));
    }
    
    private static BufferedImage createBitMap(int lenght, List<MapCell> map){
        int lenghtCell = lenght / map.size() * 5;
        System.out.print(String.valueOf(lenght) + " / " + String.valueOf(map.size()) + " = " + String.valueOf(lenghtCell));
        BufferedImage bitMap = new BufferedImage(lenght,lenght,2);
        
        for (int i = 0; i < map.size(); i++) {
            bitMap.getGraphics().setColor(Color.red);
            bitMap.getGraphics().drawRect((i - i * (i / 5)) * lenghtCell, (i / 5) * lenghtCell, lenghtCell, lenghtCell);
            
            //bitMap.setRGB(50 + i, 50 + i, Color.red.getRGB());
        }
        
        return bitMap;
    }
}
