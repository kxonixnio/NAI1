# NAI1

Dane wejściowe:
Dane treningowe – plik iris_training.txt
Dane testowe – plik iris_test.txt

Program musi wczytać dane z podanego pliku tekstowego. Zakładamy, ze:
-Atrybut decyzyjny znajduje się w ostatniej kolumnie.
-Wszystkie atrybuty poza decyzyjnym są numeryczne.
-Program musi akceptować dowolną liczbę atrybutów warunkowych, tzn. nie może zakładać, że ich jest ustalona liczba.

Następnie program wczyta wartość parametru k od użytkownika, a potem zaklasyfikuje wszystkie przykłady wczytane z pliku tekstowego zawierającego zbiór testowy algorytmem k-NN.
Jako wynik ma wypisać liczbę prawidłowo zaklasyfikowanych przykładów oraz dokładność eksperymentu wyrażoną w procentach.
Program musi umożliwiać wielokrotne ręczne wpisanie wektora atrybutów i wypisać dla takiego wektora jego wynik klasyfikacji k-NN.

Opcjonalnie można dodać wykres (np. w Excelu) zależności uzyskanej dokładności od k oraz krótka dyskusję.

Nie można używać żadnych bibliotek ML, wszystko ma być zaimplementowane od zera w pętlach, if-ach, odległość trzeba liczyć za pomocą dzialań arytmetycznych, etc.
