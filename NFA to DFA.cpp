#include <iostream>
#include <cstdio>
#include <algorithm>
#include <set>
#include <map>
#include <vector>
#include <cstdlib>
#include <queue>

using namespace std;

/**
    Program to solve the problem:
        Converting from NFA to DFA.

    Step:
        1 - Find the initial state of DFA.
        2 - Construct the transition table.
        3 - Find the final states.
		
	It's 7/12/2022 
*/

int n, // The number of transitions.
initial_state;
vector<vector<pair<int, char> > > G; // NFA.
vector<int> F; // Final states of the NFA.
vector<char> sigma;

int dfa = 1;
set<int> dfa_initial_state;
vector<int> dfaF;
vector<bool> vis;
map<set<int>, int> dfaMap;
queue<set<int> > Q;

/*
case 1:
1 1 a
1 2 a
1 4 ^
2 4 ^
2 3 a
4 4 b
4 3 b
3 5 ^
5 5 b
5 4 b

case 2:
1 1 a
1 1 b
1 2 b
2 3 a
*/

void dfs(int u)
{
    for(int i=0;i<G[u].size(); i++)
    {
        if(!vis[G[u][i].first] && G[u][i].second == '^')
        {
            vis[G[u][i].first] = true;
            dfa_initial_state.insert(G[u][i].first);
            dfs(G[u][i].first);
        }
    }
}

set<int> temp, temp2, temp3;
set<int>::iterator it;

void lambdaDfs(int u)
{
    for(int i=0;i<G[u].size(); i++)
    {
        if(!vis[G[u][i].first] && G[u][i].second == '^')
        {
            vis[G[u][i].first] = true;
            temp2.insert(G[u][i].first);
            dfs(G[u][i].first);
        }
    }
}

void debug(const set<int> &s)
{
    set<int>::iterator it;
    printf("\n--------------\n");
    for(it = s.begin();it!=s.end();it++)
    {
        printf("%d ", *it);
    }
    printf("\n--------------\n");
}

int main()
{
    printf("Program for converting NFA to DFA:\n\n");

    int m;
    printf("Enter the size of sigma: ");
    scanf("%d", &m);
    printf("\nEnter the elements of sigma: \n\n");
    char c;
    for(int i=0;i<m;i++)
    {
        scanf(" %c", &c);
        sigma.push_back(c);
    }
    sort(sigma.begin(), sigma.end());

    printf("Enter the number of transitions: ");
    scanf("%d", &n);

    G.resize(n + 1); // 1 - Based

    printf("Enter the transitions as edges (i.e. from to value): \n");
    printf("to insert lambda use the character \'^\'\n\n");
    int from, to;
    char value;
    // read as digraph.
    for(int i=0;i<n;i++)
    {
        scanf("%d %d %c", &from, &to, &value);
        G[from].push_back(make_pair(to, value));
    }

    printf("\nEnter the name of the initial state: ");
    scanf("%d", &initial_state);

    printf("\nEnter the number of final states: ");
    scanf("%d", &m);
    printf("\nEnter the names of final states: \n");
    int x;
    for(int i=0;i<m;i++)
    {
        scanf("%d", &x);
        F.push_back(x);
    }

    vis.resize(n + 1);
    dfa_initial_state.insert(initial_state);
    dfs(initial_state);
    dfaMap[dfa_initial_state] = dfa++;
    Q.push(dfa_initial_state);
    // debug(dfa_initial_state);
    printf("\n----------\n\nInitial state = 1\n");
    printf("State number | ");
    for(int i=0;i<sigma.size();i++)
    {
        printf("%c ", sigma[i]);
    }
    printf("\n");
    char s[] = "             ";

    while(!Q.empty())
    {
        temp = Q.front(); Q.pop();
        //debug(temp);
        printf("%d%s", dfaMap[temp], s);
        for(int i=0;i<F.size();i++)
        {
            if(temp.find(F[i]) != temp.end())
            {
                dfaF.push_back(dfaMap[temp]);
                break;
            }
        }

        for(int i=0;i<sigma.size();i++)
        {
            temp3.clear();
            for(it = temp.begin(); it != temp.end(); it++)
            {
                temp2.clear();
                for(int j=0;j<G[*it].size();j++)
                {
                    if(G[*it][j].second == sigma[i])
                    {
                        vis.resize(n+1);
                        temp2.insert(G[*it][j].first);
                        lambdaDfs(G[*it][j].first);
                    }
                }
                temp3.insert(temp2.begin(), temp2.end());
            }
            if(dfaMap.find(temp3) == dfaMap.end())
            {
                Q.push(temp3);
                dfaMap[temp3] = dfa++;

            }
            printf("%d ", dfaMap[temp3]);
        }
        printf("\n");
    }

    printf("The number of final states: %d\n", dfaF.size());
    for(int i=0;i<dfaF.size();i++)
    {
        printf("%d ", dfaF[i]);
    }
    printf("\n");

    return 0;
}
