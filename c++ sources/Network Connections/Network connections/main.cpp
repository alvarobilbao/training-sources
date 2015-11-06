#include <iostream>
#include <bits/stdc++.h>
using namespace std;

vector<bool> visited;
vector<vector<int> > g;
void dfs(int n);

int main()
{
    int n;
    cin >> n;

    for(int i=0;i<n;i++){
        int cn;
        if(i!=0) cout << endl;
        cin >> cn;

        g = vector<vector<int> >(cn+1);
        visited = vector<bool>(cn+1);
        fill(visited.begin(),visited.end(),false);
        char o;
        getchar();
        int succ=0,unsucc=0;
        while((o=getchar()) && isalpha(o)){
                //cout <<"in"<< o << endl;
            int c1,c2;
            cin >> c1 >> c2;
            getchar();
            if(o=='c'){
                g[c1].push_back(c2);
                g[c2].push_back(c1);
            }else if(o=='q'){
                dfs(c1);
                if(visited[c2]) succ++;
                else unsucc++;
                fill(visited.begin(),visited.end(),false);
            }

        }
        cout << succ << "," << unsucc << endl;


    }
    return 0;
}

void dfs(int n){
    visited[n]=true;
    for(int i=0;i<g[n].size();i++){
        if(!visited[g[n][i]]) dfs(g[n][i]);
    }
}
