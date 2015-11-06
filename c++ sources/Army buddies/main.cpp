#include <iostream>
#include <bits/stdc++.h>
using namespace std;

int main()
{

    hash<string> hf;
  clock_t start = clock();
  for(long i=0;i<100000;i++)
      hf("Hello");

  clock_t end = clock();
  double time = (double) (end-start) / CLOCKS_PER_SEC * 1000.0;
  cout << time;
    int s,b;
    while((cin >> s >> b)&& (s!=0 && b!=0)){
        vector<pair<int,int> > line(s+2);



        for(int i=1;i<=s;i++){
            line[i]=pair<int,int>(i-1,i+1);
        }
        for(int i=0;i<b;i++){
            // how to indexOf find(line.begin(),line.end(),obj)-line.begin()
            // how to contaibns find(line.begin(),line.end(),obj) != line.end()
            int l,r;
            cin >> l >> r;
            //for(int j=0;j<line.size();j++){
              //  cout << line[j] << " ";
            //}
            if(line[l].first==0) cout << "* ";
            else cout << line[l].first << " ";
            if(line[r].second>s) cout << "*" << endl;
            else cout << line[r].second << endl;
            int aux=l;

            line[line[l].first].second = line[r].second;
            line[line[r].second].first = line[l].first;

        }
        cout << "-" << endl;
    }
    return 0;
}
