package demogame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;




import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements ActionListener,KeyListener{
	private boolean play=false;   //game should not play automatically that's 
	private int totalBrick=21;     //the reason to declare variable as private
	private Timer timer;           //user can't modify them
	private int delay=8;
	private int Score=0;
    private int ballposX=120;
    private int ballposY=350;
    private int balldirX=-1;
    private int balldirY=-2;
    private int playerX=350;
    private MapGenerator map;
    
    public GamePlay(){
    	addKeyListener(this);
    	setFocusable(true);
    	setFocusTraversalKeysEnabled(true);
    	
    	timer=new Timer(delay,this);
    	timer.start();
    	
    	
    	
    	map=new MapGenerator(3,7);  
    	
    }
    public  void paint(Graphics g){
    	//black canvas
    	g.setColor(Color.black);
    	g.fillRect(1,1,692,592);
    	
    	//border
    	g.setColor(Color.yellow);
    	g.fillRect(0,0, 692,5);
    	g.fillRect(0,3, 3,592);
    	g.fillRect(691,3, 3,592);
    	
    	//paddle
    	g.setColor(Color.green);
    	g.fillRect(playerX, 550, 100, 8);
    	
    	//bricks
    	map.draw((Graphics2D)g);
    		
    	
   
    	//ball
    	g.setColor(Color.red);
    	g.fillOval(ballposX,ballposY,20,20);
    	
    	//Score
    	g.setColor(Color.green);
    	g.setFont(new Font("serif",Font.BOLD,20));
    	g.drawString("Score:"+Score,550,30);
    	
    	//gameOver
    	if(ballposY>=570){
    		play=false;
    		balldirX=0;
    		balldirY=0;
    		
    		g.setColor(Color.green);
    		g.setFont(new Font("serif",Font.BOLD,30));
    		g.drawString("gameOver:"+Score,200,300);
    		
    		g.setColor(Color.green);
    		g.setFont(new Font("serif",Font.BOLD,25));
    		g.drawString("press enter to start again",230,350);
    	}
    	
    	if(totalBrick<=0){
    		play=false;
    		balldirX=0;
    		balldirY=0;
    		
    		g.setColor(Color.green);
    		g.setFont(new Font("serif",Font.BOLD,30));
    		g.drawString("you won!!:"+Score,200,300);
    		g.setColor(Color.green);
    		g.setFont(new Font("serif",Font.BOLD,25));
    		g.drawString("press enter to start again",230,350);
    	
    		
    	}
    	    }
    
    //override not implements methods from  GamePlay
    private void moveLeft(){
    	play=true;
    	playerX-=20;
    }
    private void moveRight(){
    	play=true;
    	playerX+=20;
    }
    
	@Override
	public void keyPressed(KeyEvent e){
		if (e.getKeyCode()==KeyEvent.VK_LEFT){
			moveLeft();
			if(playerX<=0)
				playerX=0;
			else
				moveLeft();

		}
			if (e.getKeyCode()==KeyEvent.VK_RIGHT){
				moveRight();
				if(playerX>=600)
					playerX=600;
				else
					moveRight();
		}
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				if(!play){
					Score=0;
					totalBrick=21;
					ballposX=120;
					ballposY=350;
					balldirX=-1;
					balldirY=-2;
					playerX=320;
					
					map=new MapGenerator(3,7);
					
				}
			}
		
         repaint();
	}
       
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {		
		if(play){
			if (ballposX<=0){
				balldirX=-balldirX;
			}
			if (ballposX>=670){
				balldirX=-balldirX;
			}
			if (ballposY<=0){
				balldirY=-balldirY;
			}
			Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);
			Rectangle paddleRect=new Rectangle(playerX, 550, 100, 8);
			
			if(ballRect.intersects(paddleRect)){
				balldirY=-balldirY;
			}
			A:for(int i=0;i<map.map.length;i++){
				for(int j=0;j<map.map[i].length;j++){
					
					
					if(map.map[i][j]>0){
						
						int width=map.brickwidth;
						int height=map.brickheight;
						
						int brickposX=80+j*width;
						int brickposY=50+i*height;
						
						Rectangle brickRect=new Rectangle(brickposX,brickposY,width,height);
						if(ballRect.intersects(brickRect)){
							map.setBrick(0,i,j);
							totalBrick--;
							Score+=5;
							
							if(ballposX+19<=brickposX||ballposY+1>=brickposX+width){
								balldirX=-balldirX;}
							else
							{
								
								balldirY=-balldirY;
							}
							break A;
						}
						
					}
				}
			}
			ballposX+=balldirX;
			ballposY+=balldirY;
		}
		
		repaint();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
