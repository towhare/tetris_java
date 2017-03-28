package square;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Tetrisblok extends JPanel implements KeyListener {
	private int blockType;//��������
	private int turnState;//��ת״̬
	private boolean disabledMove = true;//��ֹ�ƶ� disabled Key when paused
	private int score = 0;//used for store score and show score
	private int nextblockType = -1,nextturnState = -1;//��һ��������ͺ�״̬ next state and type of blocks.number'-1'means the first block of the whole game
	private int x,y;//��ǰ�����λ��
	private Timer timer;
	//��Ϸ��ͼ    game map(including walls and bottom border)
	int [][] map = new int [14][23];
	//�������״ shape of different blocks ands states
	private final int shapes[][][] = new int[][][]{
		//����I��
			{
				{0,0,0,0,
				 1,1,1,1,
				 0,0,0,0,
				 0,0,0,0},
				{0,1,0,0,
				 0,1,0,0,
				 0,1,0,0,
				 0,1,0,0},
				{0,0,0,0,
			     1,1,1,1,
			     0,0,0,0,
			     0,0,0,0},
				{0,1,0,0,
			     0,1,0,0,
			     0,1,0,0,
			     0,1,0,0}
			},
			//��z mirror z
			{
				{0,1,1,0,
				 1,1,0,0,
				 0,0,0,0,
				 0,0,0,0},
				{1,0,0,0,
				 1,1,0,0,
				 0,1,0,0,
				 0,0,0,0},
				{0,1,1,0,
				 1,1,0,0,
				 0,0,0,0,
				 0,0,0,0},
				{1,0,0,0,
				 1,1,0,0,
				 0,1,0,0,
				 0,0,0,0}
			},
			//z
			{
				{1,1,0,0,
				 0,1,1,0,
				 0,0,0,0,
				 0,0,0,0},
				{0,1,0,0,
				 1,1,0,0,
				 1,0,0,0,
				 0,0,0,0},
				{1,1,0,0,
				 0,1,1,0,
				 0,0,0,0,
				 0,0,0,0},
				{0,1,0,0,
				 1,1,0,0,
				 1,0,0,0,
				 0,0,0,0}
			},
			//j
			{
				{0,1,0,0,
				 0,1,0,0,
				 1,1,0,0,
				 0,0,0,0},
				{1,0,0,0,
				 1,1,1,0,
				 0,0,0,0,
				 0,0,0,0},
				{1,1,0,0,
				 1,0,0,0,
				 1,0,0,0,
				 0,0,0,0},
				{1,1,1,0,
				 0,0,1,0,
				 0,0,0,0,
				 0,0,0,0}
			},
			//������   
			{
				{1,1,0,0,
				 1,1,0,0,
				 0,0,0,0,
				 0,0,0,0},
				{1,1,0,0,
				 1,1,0,0,
				 0,0,0,0,
				 0,0,0,0},
				{1,1,0,0,
				 1,1,0,0,
				 0,0,0,0,
				 0,0,0,0},
				{1,1,0,0,
				 1,1,0,0,
				 0,0,0,0,
				 0,0,0,0}
			},
			//L
			{
				{1,0,0,0,
				 1,0,0,0,
				 1,1,0,0,
				 0,0,0,0},
				{1,1,1,0,
				 1,0,0,0,
				 0,0,0,0,
				 0,0,0,0},
				{1,1,0,0,
				 0,1,0,0,
				 0,1,0,0,
				 0,0,0,0},
				{0,0,1,0,
				 1,1,0,0,
				 0,0,0,0,
				 0,0,0,0}
			},
			//T
			{
				{0,1,0,0,
				 1,1,1,0,
				 0,0,0,0,
				 0,0,0,0},
				{0,1,0,0,
				 1,1,0,0,
				 0,1,0,0,
				 0,0,0,0},
				{0,0,0,0,
				 1,1,1,0,
				 0,1,0,0,
				 0,0,0,0},
				{0,1,0,0,
				 0,1,1,0,
				 0,1,0,0,
				 0,0,0,0}
			},
			//3
			{
				{1,1,1,0,
				 0,0,0,0,
				 0,0,0,0,
				 0,0,0,0},
				{1,0,0,0,
				 1,0,0,0,
				 1,0,0,0,
				 0,0,0,0},
				{1,1,1,0,
				 0,0,0,0,
				 0,0,0,0,
				 0,0,0,0},
				{1,0,0,0,
				 1,0,0,0,
				 1,0,0,0,
				 0,0,0,0}
			},
			//die!special �ر����״
			{
				{1,1,1,0,
				 1,1,1,0,
				 0,1,0,0,
				 0,0,0,0},
				{0,1,1,0,
				 1,1,1,0,
				 0,1,1,0,
				 0,0,0,0},
				{0,1,0,0,
				 1,1,1,0,
				 1,1,1,0,
				 0,0,0,0},
				{1,1,0,0,
				 1,1,1,0,
				 1,1,0,0,
				 0,0,0,0}
			}
	};

	//�����µķ���ĺ��� function(void) that used for create new blocks
	public void newblock(){
		if(nextblockType == -1 && nextturnState == -1){
			blockType =(int)((Math.random()*2000)%shapes.length);//current block type ��ǰ���������
			turnState = (int)((Math.random()*2000)%4);//current block state ��ǰ�������ת״̬
			nextblockType = (int)(Math.random()*2000 % shapes.length);
			nextturnState = (int)(Math.random()*2000 % 4);
		}
		else{
			blockType = nextblockType;
			turnState = nextturnState;
			nextblockType = (int)(Math.random()*2000%shapes.length);
			nextturnState = (int)(Math.random()*2000%4);
		}
		x = 5;y=0;//create new block in the center top of screen
		if(gameover(x,y)==1){
			newmap();//create a new map
			drawwall();//draw a new wall
			JOptionPane.showMessageDialog(null,"GAME OVER YOUR SCORE:"+score);//show game over characters
			score = 0 ;//set score to zero
		}
	}
	//void used for draw walls
	public void drawwall(){
		int i ,j;
		for( i = 0;i <14;i++){//draw bottom wall
			map[i][22] = 2;
		};
		for(j = 0;j < 23;j++){//draw left and right wall
			map[0][j] = 2;
			map[13][j] = 2;
		}
	}
	//new map void used for draw a new map
	public void newmap(){
		int i,j;
		for(i = 0;i < 14;i++){
			for(j = 0;j < 23;j++){
				map[i][j] = 0;
			}
		}
	}
	Tetrisblok(){
		disabledMove = false;
		newblock();
		newmap();
		drawwall();
		timer = new Timer(500, new TimerListener());//0.5seconds per check and move
		timer.start();
	}
	class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(blow(x, y+1,blockType,turnState)==1){
				y = y+1;
			}
			if(blow(x,y+1,blockType,turnState)==0){
				add(x,y,blockType,turnState);
				delline();//��ȥ����
				newblock();//�����µķ���
			}
			repaint();
		}
	}
	public void newGame(){
		newblock();
		newmap();
		drawwall();
		disabledMove = false ;
	}
	public void pauseGame(){
		timer.stop();
		disabledMove = true;
	}
	public void continueGame(){
		disabledMove = false;
		timer.start();
	}
	//void for turn block
	public void turn(){
		if(disabledMove)return;
		int tempturnState = turnState;
		turnState =(turnState+1)%4;
		if(blow(x,y,blockType,turnState)==1){//������ת
			
		};
		if(blow(x,y,blockType,turnState)==0){//������ת
			turnState = tempturnState;
		};
		repaint();
	
	}
	//���Ʒ��� translate to left
	public void left(){
		if(disabledMove)return;
		if(blow(x-1,y,blockType,turnState)==1){
			x = x-1;
		}
		repaint();
	}
	//���Ʒ��� translate to right
	public void right(){
		if(disabledMove)return;
		if(blow(x+1,y,blockType,turnState)==1){
			x =x +1;
		}
		repaint();
	}
	//���䷽�� going down
	public void down(){
		if(disabledMove)return;
		if(blow(x,y+1,blockType,turnState)==0){
			add(x,y,blockType,turnState);
			delline();
			newblock();
		}
		else if(blow(x,y+1,blockType,turnState)==1){
			y = y+1;
		}
		repaint();
	}
	public int blow(int x, int y, int blockType,int turnState){
		for(int a = 0; a < 4;a++){
			for(int b =0; b < 4;b++){
				if(((shapes[blockType][turnState][a*4+b]==1)&&(map[x+b+1][y+a]==1))//otherblocks
				 ||((shapes[blockType][turnState][a*4+b]==1)&&(map[x+b+1][y+a]==2))//walls
				 ){
					return 0 ;
				}
			}
		}
		return 1;
	}
	public void delline(){
		int c = 0;
		for(int b = 0;b < 23;b++){
			for(int a = 0;a < 14;a++){
				if(map[a][b]==1){
					c = c+1;
					if(c==12){
						score+=12;
						for(int d = b;d > 0;d--){
							for(int e = 0;e < 14;e++){
								map[e][d] = map[e][d-1];
							}
						}
					}
				}
			}
			c=0;
		}
	}
	//�ж���Ϸ�Ƿ����
	public int gameover(int x,int y){
		if(blow(x,y,blockType,turnState)==0)return 1;
		return 0 ;
	}
	//���������ȥ
	public void add(int x,int y,int blockType,int turnState){
		int j = 0;
		for(int a = 0; a < 4;a++){
			for(int b = 0;b < 4;b++){
				if(shapes[blockType][turnState][j]==1){
					map[x+b+1][y+a] = shapes[blockType][turnState][j];
				}
				j++;
			}
		}
	}
	//��Ļ�ػ�

	public void paint(Graphics g){
		super.paint(g);
		int i,j;
		//����ǰ���� draw current block
		for(j = 0;j < 16;j++){
			if(shapes[blockType][turnState][j]==1){
				g.fillRect((j%4+x+1)*15, (j/4+y)*15, 14, 14);
			}
		}
		for(j = 0;j < 23;j++){
			for(i = 0;i < 14;i++){
				if(map[i][j] == 1){//������
						g.fillRect(i*15, j*15, 14, 14);
				}
				if(map[i][j] == 2){//��Χǽ
					g.fillRect(i * 15, j * 15, 15, 15);
				}
			}
		}
		g.drawString("score:"+score,225,15);
		g.drawString("��һ����״", 225, 50);
		//�ڴ��ڵ��Ҳ������һ�鷽��
		for(j = 0;j < 16;j++){
			if(shapes[nextblockType][nextturnState][j] == 1){
				g.fillRect(255+(j%4)*15, (j/4)*15+100, 14, 14);
			}
		}
	}
		
	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_S:
			down();
			break;
		case KeyEvent.VK_W:
			turn();
			break;
		case KeyEvent.VK_A:
			left();
			break;
		case KeyEvent.VK_D:
			right();
			break;
		}
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO �Զ����ɵķ������
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO �Զ����ɵķ������
		
	}
}
