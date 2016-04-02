
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javafx.util.Pair;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author HitmanBBa
 */
public class NFAtoDFA extends JFrame{
    public NFAtoDFA() {}
    String sigma;
    int sigmaSize;
    ArrayList<Pair<Integer, Character> > G[];
    JLabel t[] = new JLabel[3];
    JTextField tf[][];
    JButton btn;
    int n, m;
    JLabel lbl1, lbl2;
    JTextField txt1, txt2;
	
    public NFAtoDFA(String s, int n, int m)
    {
        super("NFA to DFA");
        this.n = n;
        this.m = m;
        sigma = s;
        sigmaSize = s.length() / 2;
        sigmaSize++;
        G = new ArrayList[m+1];
        for(int i=0;i<=m;i++)
            G[i] = new ArrayList();
        setLayout(new GridLayout(5+n,3));
        String ts[] = {"From", "To", "Character"};
        for(int i=0;i<3;i++)
        {
            t[i] = new JLabel(ts[i]);
            add(t[i]);
        }
        tf = new JTextField[n][3];
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<3;j++)
            {
                tf[i][j] = new JTextField(15);
                add(tf[i][j]);
            }
        }
        
        lbl1 = new JLabel("Initial State: ");
        add(lbl1);
        txt1 = new JTextField(15);
        add(txt1);
        add(new JLabel(""));
        lbl2 = new JLabel("Final States (separated by single space): ");
        add(lbl2);
        txt2 = new JTextField(15);
        add(txt2);
        add(new JLabel(""));
        add(new JLabel("*Note: for lambda use character '^'."));
        add(new JLabel(""));
        add(new JLabel(""));
        btn = new JButton("Next");
        add(new JLabel(""));
        btn.addActionListener(new Handle());
        add(btn);
        add(new JLabel(""));
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(500, 500);
        pack();
        setVisible(true);
    }
    
    
    class Handle implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae) {
            // building graph
            for(int i=0;i<n;i++)
            {
                int from, to;
                char val;
                from = Integer.parseInt(tf[i][0].getText());
                to = Integer.parseInt(tf[i][1].getText());
                val = tf[i][2].getText().charAt(0);
                G[from].add(new Pair(to, val));
            }
            
            setVisible(false);
            Solve o = new Solve(m, sigma, G, Integer.parseInt(txt1.getText()), txt2.getText());      
        }        
    }
}
