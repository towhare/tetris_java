package square;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TetrisFrame extends JFrame implements ActionListener{
	static JMenu game = new JMenu("��Ϸ");
	JMenuItem newgame = game.add("����Ϸ");
	JMenuItem pause = game.add("��ͣ");
	JMenuItem goon = game.add("����");
	JMenuItem exit = game.add("�˳�");
	static JMenu help = new JMenu("����");
	JMenuItem about = help.add("����");
	Tetrisblok a =new Tetrisblok();
	public TetrisFrame(){
		addKeyListener(a);
		this.add(a);
		newgame.addActionListener(this);//����Ϸ�˵�
		pause.addActionListener(this);//��ͣ�˵�
		goon.addActionListener(this);//�����˵�
		about.addActionListener(this);
		exit.addActionListener(this);
	}
	

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		TetrisFrame frame = new TetrisFrame();
		JMenuBar menu = new JMenuBar();
		frame.setJMenuBar(menu);
		menu.add(game);
		menu.add(help);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//"����"��ť����
		frame.setSize(375,400);
		frame.setTitle("����˹����0.1�汾");
		frame.setVisible(true);
		frame.setResizable(false);
	}
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getSource()==newgame){a.newGame();}
		else if(e.getSource()==pause){a.pauseGame();}
		else if(e.getSource()==goon){a.continueGame();}
		else if(e.getSource()==about){DisplayToast("AD���ƶ���W����ת");}
		else if(e.getSource()==exit){System.exit(0);}
	}


	private void DisplayToast(String string) {
		// TODO �Զ����ɵķ������
		JOptionPane.showMessageDialog(null, string,"��ʾ",JOptionPane.ERROR_MESSAGE);
	}

}
