import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Cell{
	
	private int x, y;
	private int vx;
	private int vy;
	private int status = 0; // 0 - healthy
	private int time = 5000;
	private int size;
	
	
	//add a default constructor
	//it should randomize a position
	//within a 800x600 window
	
	public Cell() {
		
		x = (int)(Math.random()*(800-0+1))+0;
		y = (int)(Math.random()*(600-0+1))+0;
		
		vx = (int)(Math.random()*(4+4+1))-4;
		vy = (int)(Math.random()*(4+4+1))-4;
		
		size = (int)(Math.random()*(25-10+1))+10;
		
	}
	
	// add a constructor that would allow the following line of code
	// to compile
	// Cell cell = new Cell(50, 150);
	public Cell(int initX, int initY) {
		
		this(); // call default constructor to set vx, vy too
		status = 1; // infected
		x = initX;
		y = initY;
		
	}
	
	
	public void intersects(Cell other) {
		
		//use Rectangle class to check for intersection
		Rectangle og = new Rectangle(x, y, size, size);
		Rectangle otherCell = new Rectangle(other.x, other.y, size, size);
		
		//use built-in intersect method
		if(og.intersects(otherCell)) {
			
			//check if a cell gets infected!
			if(this.status == 1 && other.status == 0) {
				other.status = 1;
			}else if(other.status == 1 && this.status == 0) {
				this.status = 1;
			}
			
			//don't let dead cells eat each other
			if(other.status == 3 || this.status == 3) return;
			if(this.size > other.size) {
				
				//this cell eats the other cell
				this.size += other.size / 4;
				
				//other cell dies off-screen
				other.x = -100;
				other.y = -100;
				other.status = 3;
				other.vx = 0;
				other.vy = 0;
			}else {
				other.size += this.size/4;
				
				//this cell dies off-screen
				this.x = -100;
				this.y = -100;
				this.vx = 0;
				this.vy = 0;
				this.status = 3;
			}
			
		}
		
		
	}
	
	
	
	//add the method below
	public void paint(Graphics g) {
		
		if(status == 0) {
			g.setColor(Color.green); //healthy
		}else if(status == 1) {
			g.setColor(Color.red); //infected
			
			time -= 16;
			if(time <= 0) {
				
				//write an if-statement to simulate a 50% chance
				//that something will happen
				if(Math.random() < 0.50) {
					//we recover 50% of the time
					status = 2;
				}else {
					//dead
					status = 3;
					vx = 0; 
					vy = 0;
				}
				
				time = 5000;              
			}
			
			
		}else if(status == 2) {
			g.setColor(Color.blue); //recovered
			
			//a recovered cell eventually becomes normal again
			time -= 16;
			if(time <= 0) {
				status = 0; 
				time = 500;
			}
			
		}else if(status == 3) {
			g.setColor(Color.black); //dead
		}
		
		g.fillOval(x, y, size, size);
		
		//velocity affects position
		x += vx;
		y += vy;
		
		//keep the cells in the frame view
		if(x < 0 || x >= 800) {
			vx *= -1;
		}
		if(y < 0 || y >= 800) {
			vy *= -1;
		}
	}
	
}