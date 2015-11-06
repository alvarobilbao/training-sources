#include <iostream>
#include <bits/stdc++.h>
using namespace std;

vector<vector<int> > g;
vector<int> visited,match;

int Aug(int l) { // return 1 if an augmenting path is found
if (visited[l]) return 0; // return 0 otherwise
visited[l] = 1;
for (int j = 0; j < (int)g[l].size(); j++) {
int r = g[l][j]; // edge weight not needed -> vector<vi> AdjList
if (match[r] == -1 || Aug(match[r])) {
match[r] = l; return 1; // found 1 matching
} }
return 0; // no matching
}

int main()
{
    int r,c;
    while(cin >> r >> c){
        getchar();
        g.assign((r*c+r-1),vector<int>());
        int counter=-1;
        int nodes=0;
        int board[r][c];
        for(int i=0;i<r;i++){
            string s;
            getline(cin,s);
            counter++;
			for(int j=0;j<c;j++){
				if(s[j]=='.'){
					nodes++;
					board[i][j]=counter;
					if(j>0 && board[i][j-1]!=-1){
						(g[counter]).push_back(board[i][j-1]);
						(g[(board[i][j-1])]).push_back(counter);
					}
					if(i>0 && board[i-1][j]!=-1){
						(g[counter]).push_back(board[i-1][j]);
						(g[(board[i-1][j])]).push_back(counter);
					}
				}
				else board[i][j]=-1;
				counter++;
			}
        }
        int MCBM = 0;
        match.assign(r*c+r-1, -1); // V is the number of vertexes in bipartite graph
        for (int l = 0; l < r*c+r-1; l++) { // n = size of the left set only if your graph is ordered first left sidem then rightt side
            visited.assign(r*c+r-1, 0); // reset before each recursion
            MCBM += Aug(l);
        }
        if(MCBM == nodes) cout << 2 << endl;
        else cout << 1 << endl;
    }
    return 0;
}
