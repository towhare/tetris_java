package square;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TetrisFrame extends JFrame implements ActionListener{
	static JMenu game = new JMenu("游戏");
	JMenuItem newgame = game.add("新游戏");
	JMenuItem pause = game.add("暂停");
	JMenuItem goon = game.add("继续");
	JMenuItem exit = game.add("退出");
	static JMenu help = new JMenu("帮助");
	JMenuItem about = help.add("关于");
	Tetrisblok a =new Tetrisblok();
	public TetrisFrame(){
		addKeyListener(a);
		this.add(a);
		newgame.addActionListener(this);//新游戏菜单
		pause.addActionListener(this);//暂停菜单
		goon.addActionListener(this);//继续菜单
		about.addActionListener(this);
		exit.addActionListener(this);
	}
	

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		TetrisFrame frame = new TetrisFrame();
		JMenuBar menu = new JMenuBar();
		frame.setJMenuBar(menu);
		menu.add(game);
		menu.add(help);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//"结束"按钮可用
		frame.setSize(375,400);
		frame.setTitle("俄罗斯方块0.1版本");
		frame.setVisible(true);
		frame.setResizable(false);
	}
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource()==newgame){a.newGame();}
		else if(e.getSource()==pause){a.pauseGame();}
		else if(e.getSource()==goon){a.continueGame();}
		else if(e.getSource()==about){DisplayToast("AD键移动，W键旋转");}
		else if(e.getSource()==exit){System.exit(0);}
	}


	private void DisplayToast(String string) {
		// TODO 自动生成的方法存根
		JOptionPane.showMessageDialog(null, string,"提示",JOptionPane.ERROR_MESSAGE);
	}

}
