package Graphics;

import java.util.LinkedList;
import Behavior.Entity;
/**
 * Will possibly contain the Entity list to be rendered or a reference to it.
 * 
 * @author (Andrew Matzureff) 
 * @version (2/16/19)
 */
public class Render
{
    private static class Node{
        private Entity entity;
        private Node prev = null, next = null;
        
        public Node(Entity e){entity = e;}
    }
    private static class List{
        private Node head;
        
        public void add(Node n){
            if(head == null)
                head = n;
            else{
                head.prev = n;
                n.next = head;
                head = n;
            }
        }
    }
    private static List QUEUE = new List();
    private static void filter(Node n){
            Node p = n.prev;
            Entity e = p.entity;
            p.entity = n.entity;
            n.entity = e;
    }
    public static void add(Entity e){
        synchronized(QUEUE){
            QUEUE.add(new Node(e));
        }
    }
    public static void refresh(Screen screen){
        Node i = QUEUE.head;
        while(i != null){
            Sprite s = i.entity.getSprite();
            float x = i.entity.getX();
            float y = i.entity.getY();
            int t = i.entity.getTransform();
            double scale = 1;//5;//Math.sin(System.currentTimeMillis() * 0.001) + 1.25;
            screen.drawSprite(s, (int)x, (int)y, t, (float)scale);
            if(i.prev != null && i.prev.entity.getZ() > i.entity.getZ()){
                filter(i);
            }
            i = i.next;
        }
    }
}
