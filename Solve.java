import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.util.Pair;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author HitmanBBa
 */
public class Solve extends JFrame{
    public Solve() {}
    ArrayList<Pair<Integer, Character> > G[];
    String finalStates;
    TreeSet<Integer> dfaInitial;
    HashMap<String, Integer> DFAMap;
    boolean vis[];
    int dfaCounter;
    public void dfs(int u)
    {
        
        for(int i=0;i<G[u].size();i++)
        {
            int v = G[u].get(i).getKey();
            char c = G[u].get(i).getValue();
            if(!vis[v] && c == '^')
            {
                vis[u] = true;
                dfaInitial.add(v);
                dfs(v);
            }
        }
    }
    int m;
    String sigma;
    StringBuilder DFAFinal;
    TreeSet<Integer> temp, temp2;
    public void lambdadfs(int u)
    {
        
        for(int i=0;i<G[u].size();i++)
        {
            int v = G[u].get(i).getKey();
            char c = G[u].get(i).getValue();
            if(!vis[v] && c == '^')
            {
                vis[u] = true;
                temp2.add(v);
                dfs(v);
            }
        }
    }
    
    public Solve(int m,String sigma, ArrayList<Pair<Integer, Character> > G[], int initialState, String finalStates)
    {
        super("NFA to DFA");
        this.G = G;
        this.finalStates = finalStates;
        this.sigma = sigma;
        this.m = m;
        DFAFinal = new StringBuilder("");
        dfaInitial = new TreeSet();
        dfaInitial.add(initialState);
        vis = new boolean[G.length];
        dfs(initialState);
        DFAMap = new HashMap();
        dfaCounter = 1;
        DFAMap.put(dfaInitial.toString(), dfaCounter++);
        Queue<TreeSet<Integer> > Q = new LinkedList();
        Q.add(dfaInitial);
        int sigmaSize = sigma.length() / 2;
        sigmaSize++;
        JPanel p = new JPanel();        
        p.setLayout(new GridLayout(2*m+1, sigmaSize + 1));
        p.add(new JLabel("State number"));
        for(int i=0;i<sigma.length();i+=2)
        {
            p.add(new JLabel(" "+sigma.charAt(i)));
        }
        
        temp2 = new TreeSet();
        Iterator<Integer> it;
        StringBuilder ts = new StringBuilder("");
        JLabel lbl;
        while(!Q.isEmpty())
        {
            temp = Q.poll();
            //System.out.println(temp.toString());
            lbl = new JLabel(DFAMap.get(temp.toString())+"="+temp.toString());
            lbl.setBackground(Color.white);
            p.add(lbl);
            ts = new StringBuilder("");
            for(int i=0;i<finalStates.length();i++)
            {
                if(finalStates.charAt(i) == ' ')
                {
                    if(temp.contains(Integer.parseInt(ts.toString())))
                    {
                        DFAFinal.append(DFAMap.get(temp.toString())+", ");
                        break;
                    }
                    ts = new StringBuilder("");
                }
                else
                {
                    ts.append(""+finalStates.charAt(i));
                }
                    
            }
            
            if(temp.contains(Integer.parseInt(ts.toString())))
            {
                DFAFinal.append(DFAMap.get(temp.toString())+", ");
            }
            ts = new StringBuilder("");
            
            for(int i=0;i<sigma.length();i+=2)
            {
                temp2 = new TreeSet();
                it = temp.iterator();
                while(it.hasNext())
                {
                    int x = it.next();
                    for(int j=0;j<G[x].size();j++)
                    {
                        if(G[x].get(j).getValue() == sigma.charAt(i))
                        {
                            vis = new boolean[G.length];
                            temp2.add(G[x].get(j).getKey());
                            lambdadfs(G[x].get(j).getKey());
                        }
                    }
                }
                if(!DFAMap.containsKey(temp2.toString()))
                {
                    Q.add(temp2);                    
                    DFAMap.put(temp2.toString(), dfaCounter++);
                }
                lbl = new JLabel(DFAMap.get(temp2.toString())+"="+temp2.toString());
                lbl.setBackground(Color.white);
                p.add(lbl);
            }
        }
        
        add(p);
        JTextArea ta = new JTextArea(2,50);
        ta.setEditable(false);
        ts = new StringBuilder("Initial State = 1\n");
        ts.append("Final States = {");
        if(DFAFinal.length() != 0)
            ts.append(DFAFinal.toString().substring(0, DFAFinal.length()-2));
        ts.append("}\n");    
        ta.setText(ts.toString());
        add(ta, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}
