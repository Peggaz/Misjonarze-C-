//Rozwiązanie autorstwa użytkownika Peggaz w opraciu o wykłady Krzysztofa Ślota, jeśli pobierasz to rozwiązanie zachowaj tę linie ;)
package algortm.przeszukiwania.modyfikacja;

import java.util.ArrayList;

public class AlgortmPrzeszukiwaniaModyfikacja {
    public static void main(String[] args) {
        Przeszukiwanie P = new Przeszukiwanie();
        P.Szukaj();
    }
}

class Przeszukiwanie{
    ArrayList<Stan> Q;
    ArrayList<Stan> H;
    int M=42;//liczba misjonarzy
    int K=42;//liczba kanibali
    int k=14;//pojemnosc ludki
    Przeszukiwanie(){        
        Q = new ArrayList<>();
        Stan S = new Stan(M,K,0,0,true, null);
        Q.add(S);
        H = new ArrayList<>();
        H.add(S);
    }
    
    void Szukaj(){
        int liczbaOperacji=0;
        while(true){
            liczbaOperacji++;
            Stan S = wybierzStanDoEkspansji();
            if(S==null)
            {
                System.out.println("brak rozwiazania");
                break;
            }
            if(czyRozwiazanie(S)){
                int krokiDoRozwiazania=0;
                while(S.przodek!=null){
                    krokiDoRozwiazania++;
                System.out.println("Liczba KANIBALI po PRAWEJ: "+S.LKP+
                        "\nLiczba MISJONARZY po PRAWEJ: "+S.LMP+
                        "\nLiczba KANIBALI po LEWEJ: "+S.LKL+
                        "\nLiczba MISJONARZY po LEWEJ: "+S.LML+"\n\n ===========================\n");
                S=S.przodek;
                }
                System.out.println("wykonano w: "+krokiDoRozwiazania+" krokach i w: "+liczbaOperacji+ " operacjach");
                break;
            }
            ArrayList<Stan> LS = ekspandujStan(S);
            LS = filtrujStany(LS);
            uaktualnijListy(LS);
        } 
    }
    void uaktualnijListy(ArrayList<Stan> LS){
        for(Stan s : LS){
            Q.add(s);
            H.add(s);
        }
    }
    ArrayList<Stan> filtrujStany(ArrayList<Stan> LS){
        ArrayList<Stan> StanyOk = new ArrayList<>();
        //for(int i=0; LS.size(); i++)
        //Stan s = LS.get(i);
        for(Stan s : LS){
            if(spelniaOgraniczenia(s) && jestNowa(s))
                StanyOk.add(s);
        }
        return StanyOk;
    }
    boolean spelniaOgraniczenia(Stan S){
        return ((S.LMP==0 ||(S.LKP<=S.LMP)) && (S.LML==0 || (S.LKL <= S.LML)));
    }
    boolean jestNowa(Stan S){
       //for(Stan s : H){
          //  if(s.LKL==S.LKL && s.LML==S.LML && s.PoLewej==S.PoLewej)
             //  return false;
       // }
       // return true; 
        return H.stream().noneMatch((s) -> (s.LKL==S.LKL && s.LML==S.LML && s.PoLewej==S.PoLewej));
    }
    ArrayList<Stan> ekspandujStan(Stan S){
        Stan S1;
        ArrayList<Stan> NoweStany = new ArrayList<>();
        if(S.PoLewej){
            for(int i=1; i<=k; i++){
                if(S.LML>=i){//2 M
                    S1 = new Stan(S.LML-i,S.LKL, S.LMP+i, S.LKP, false,S);
                    NoweStany.add(S1);
                }
                if(S.LKL>=i){//2 K
                    S1 = new Stan(S.LML,S.LKL-i, S.LMP, S.LKP+i, false,S);
                    NoweStany.add(S1);
                }
                for(int j=0; j<=(k-i);j++)
                    if(S.LML>=j&&S.LKL>=i){//1 M 1 K
                        S1 = new Stan(S.LML-j,S.LKL-i, S.LMP+j, S.LKP+i, false,S);
                        NoweStany.add(S1);
                    }
            }
            }
        else {
            for(int i=1; i<=k; i++){
                if(S.LMP>=i){//2 M
                    S1 = new Stan(S.LML+i,S.LKL, S.LMP-i, S.LKP, true,S);
                    NoweStany.add(S1);
                }
                if(S.LKP>=i){//2 K
                    S1 = new Stan(S.LML,S.LKL+i, S.LMP, S.LKP-i, true,S);
                    NoweStany.add(S1);
                }
                for(int j=0; j<=(k-i);j++)
                if(S.LMP>=i&&S.LKP>=j){//1 M 1 K)
                        S1 = new Stan(S.LML+i,S.LKL+j, S.LMP-i, S.LKP-j, true,S);
                        NoweStany.add(S1);
                    }
            }
        }
        return NoweStany;
    }
    Stan wybierzStanDoEkspansji(){
        //BFS
        if(Q.isEmpty())
            return null;
        Stan S = Q.get(0);
        Q.remove(0);
        return S;
        
        //
    }
    boolean czyRozwiazanie(Stan S){
        return S.LKL==0&&S.LML==0&& S.PoLewej==false;
    }
}

class Stan{
    int LMP, LKP, LML, LKL;
    boolean PoLewej;
    Stan przodek;
    Stan(int lml, int lkl, int lmp, int lkp, boolean l, Stan p){
        LML=lml;
        LKL=lkl;
        LMP=lmp;
        LKP=lkp;
        PoLewej=l;
        przodek = p;
    }
}
