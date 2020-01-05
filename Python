//Rozwiązanie autorstwa użytkownika Peggaz w opraciu o wykłady Krzysztofa Ślota, jeśli pobierasz to rozwiązanie zachowaj tę linie ;)
LiczbaMisionarzy = 3
LiczbaKanibali = 3
PojemnoscTratfy = 2


class Stan:
    def __init__(self, kl=0, ml=0, kp=0, mp=0, pl=True, p=None):
        self.MisjonarzePoLewej = ml
        self.KanibalePoLewej = kl
        self.KanibalePoPrawej = kp
        self.MisjonarzePoPrawej = mp
        self.TratfaPoLewej = pl
        self.Przodek = p


HistoriaStanow = []
StanyDoEkspansji = []


def szukaj():
    S = Stan(LiczbaKanibali, LiczbaMisionarzy)
    StanyDoEkspansji.append(S)
    HistoriaStanow.append(S)
    while True:
        S = wybierz_stan_do_ekspansji()
        if S is None:
            print("Brak rozwiazanie")
            for h in HistoriaStanow:
                print(h.MisjonarzePoLewej)
            break
        if czy_rozwiazanie(S):
            while True:
                if S is not None:
                    print("Kanibale po lewej" + str(S.KanibalePoLewej) + "\n" +
                          "Misjonarze po lewej" + str(S.MisjonarzePoLewej) + "\n" +
                          "Kanibale po prawej" + str(S.KanibalePoPrawej) + "\n" +
                          "Misjonarze po prawej" + str(S.MisjonarzePoPrawej) + "\n" +
                          "===========================================\n")
                    S = S.Przodek
                else:
                    break
            break
        LS = ekspanduj_stan(S)
        LS = filtruj_stany(LS)
        uaktualnij_stany(LS)


def uaktualnij_stany(LS):
    for s in LS:
        StanyDoEkspansji.append(s)
        HistoriaStanow.append(s)


def spelnia_ograniczenia(S=Stan()):
    return (S.MisjonarzePoPrawej == 0 or (S.KanibalePoPrawej <= S.MisjonarzePoPrawej)) and (
            (S.MisjonarzePoLewej == 0) or S.KanibalePoLewej <= S.MisjonarzePoLewej)


def jest_nowa(S):
    for h in HistoriaStanow:
        if S.KanibalePoLewej == h.KanibalePoLewej and \
           S.MisjonarzePoLewej == h.MisjonarzePoLewej and S.TratfaPoLewej == h.TratfaPoLewej:
            return False
    return True


def filtruj_stany(LS):
    StanyOk = []
    for s in LS:
        if spelnia_ograniczenia(s) and jest_nowa(s):
            StanyOk.append(s)
    return StanyOk


def ekspanduj_stan(S=Stan()):
    NoweStany = []
    if S.TratfaPoLewej:
        for i in range(PojemnoscTratfy + 1):
            for j in range(PojemnoscTratfy + 1 - i):
                if i == 0 and j == 0 or (i + j > PojemnoscTratfy):
                    continue
                elif S.MisjonarzePoLewej >= i and S.KanibalePoLewej >= j:
                    NoweStany.append(Stan(S.KanibalePoLewej - j, S.MisjonarzePoLewej - i,
                                          S.KanibalePoPrawej + j, S.MisjonarzePoPrawej + i, False, S))
    else:
        for i in range(PojemnoscTratfy + 1):
            for j in range(PojemnoscTratfy + 1 - i):
                if i == 0 and j == 0 or (i + j > PojemnoscTratfy):
                    continue
                elif S.MisjonarzePoPrawej >= i and S.KanibalePoPrawej >= j:
                    NoweStany.append(Stan(S.KanibalePoLewej + j, S.MisjonarzePoLewej + i,
                                          S.KanibalePoPrawej - j, S.MisjonarzePoPrawej - i, True, S))
    return NoweStany


def wybierz_stan_do_ekspansji():
    if len(StanyDoEkspansji) == 0:
        return None
    S = StanyDoEkspansji[0]
    StanyDoEkspansji.remove(S)
    return S


def czy_rozwiazanie(S=Stan()):
    return (S.KanibalePoLewej) == 0 and (S.MisjonarzePoLewej == 0) and (not S.TratfaPoLewej)


szukaj()
