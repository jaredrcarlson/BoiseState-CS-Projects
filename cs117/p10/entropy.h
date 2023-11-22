/*
UML

-------------------------------------------------
             Entropy
-------------------------------------------------
     - temperature: double
     - entropy: double
     - table: double[][]
-------------------------------------------------
     + Entropy()
     + ~Entropy()
     + readFile(string): bool
     + calculateEntropy(double, bool&): double
-------------------------------------------------
*/

using namespace std;

class Entropy {
    private:
        double temperature;
        double entropy;
        double** table;
                  
    public:
        Entropy();
        ~Entropy();
        bool readFile(string);
        double calculateEntropy(double, bool&);
};



