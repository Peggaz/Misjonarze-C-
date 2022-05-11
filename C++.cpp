//Rozwiązanie autorstwa użytkownika Peggaz w opraciu o wykłady Krzysztofa Ślota, jeśli pobierasz to rozwiązanie zachowaj tę linie ;)
#include "pch.h"
#include <iostream>
#include <list>
using namespace std;

const int LM = 3; // liczba misjonarzy
const int LK = 3; // liczba kanibali
const int PT = 2; //pojemnosc tratwy


class Stan {
public:
	bool PoLewej;
	int ML;
	int MP;
	int KL;
	int KP;
	Stan *Przodek;
	
	Stan() {
		PoLewej = false;
		ML = LM;
		KL = LK;
		MP = 0;
		KP = 0;
		Przodek = nullptr;
	}
	Stan(int kl, int ml, int kp, int mp, bool pl, Stan *p) {
		PoLewej = pl;
		ML = ml;
		KL = kl;
		MP = mp;
		KP = kp;
		Przodek = p;
	}
};
class Przeszukiwanie {
	list<Stan*> Q;
	list<Stan*> H;

public:
	Przeszukiwanie() {
		Stan *S1 = new Stan(LK, LM, 0, 0, true, nullptr);
		Q.push_front(S1);
		H.push_front(S1);
	}
	void Szukaj() {
		while (true) {
			
			Stan *S =  wybierzStanDoEkspansji();
			
			if(S == nullptr) {
				cout << "Brak rozwiazania" << endl;
				break;
			}
			if (czyRozwiazanie(S)) {
				 do{
						cout << "liczba KANIBALI po PRAWEJ: " << S->KP << "\nLiczba MISJONARZY po PRAWEJ: " << S->MP << "\nLiczba KANIBALI po LEWEJ: " << S->KL << "\nLiczba MISJONARZY po LEWEJ: " << S->ML << "\n==================================" << endl;
						S = S->Przodek;
						/*S.PoLewej = S.Przodek->PoLewej;
						S.ML = S.Przodek->ML;
						S.KL = S.Przodek->KL;
						S.MP = S.Przodek->MP;
						S.KP = S.Przodek->KP;
						S.Przodek = S.Przodek->Przodek;*/
				 }while (S->Przodek != nullptr);
				break;
			}
			list<Stan*> LS = ekspandujStan(S);
			LS = filtrujStany(LS);
			uaktualnijListy(LS);
			
		}
	}
	void uaktualnijListy(list<Stan*> LS) {
		for(Stan *S : LS) {
			Q.push_back(S);
			H.push_back(S);
		}
	}
	list<Stan*> filtrujStany(list<Stan*> LS) {
		list<Stan*> StanyOk;
		for (Stan *s : LS) {
			if (spelniaOgraniczenia(s) && jestNowa(s))
				StanyOk.push_back(s);
		}
		return StanyOk;
	}
	bool spelniaOgraniczenia(Stan *S) {
		return(((S->MP == 0) || (S->KP <= S->MP)) && ((S->ML == 0) || (S->KL <= S->ML)));
	}
	bool jestNowa(Stan *S) {
		for (Stan *s : H) {
			if (s->KL == S->KL && s->ML == S->ML && s->PoLewej == S->PoLewej)
				return false;
		}
		return true;
	}
	list<Stan*> ekspandujStan(Stan *S){
		Stan *S1;
		list<Stan*> NoweStany;
		if(S->PoLewej)
			for(int i = 0; i <= PT; i++)
				for (int j = 0; j <= PT; j++) {
					if (i == 0 && j == 0 || (i+j > PT));
					else if (S->ML >= i && S->KL >= j) {
						S1 = new Stan(S->KL - j, S->ML - i, S->KP + j, S->MP + i, false, S);
						NoweStany.push_back(S1);
					}
				}
		else
			for (int i = 0; i <= PT; i++)
				for (int j = 0; j <= PT; j++) {
					if (i == 0 && j == 0 || (i + j > PT));
					else if (S->MP >= i && S->KP >= j) {
						S1 = new Stan(S->KL + j, S->ML + i, S->KP - j, S->MP - i, true, S);
						NoweStany.push_back(S1);
					}
				}
		
		return NoweStany;
	}
	Stan *wybierzStanDoEkspansji() {
		if (Q.size() == 0)
			return nullptr;
		Stan* S = Q.front();
		Q.pop_front();
		return S;
	}
	bool czyRozwiazanie(Stan *S) {
		if (S->KL == 0 && S->ML == 0 && S->PoLewej == false)
			return true;
		else
			return false;
	}
};

int main(){
	Przeszukiwanie P;
	P.Szukaj();
    std::cout << "Hello World!\n"; 
}
